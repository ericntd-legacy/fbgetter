package com.example;

import com.gec.*;
import com.gec.entities.UserFb;
import com.restfb.Connection;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by Eric on 25/01/15.
 */
public class Main {

    private static Main mainObj;
    private static UserFb userFb;
    private static Page page;
    private static Post post;

    public static void main(String[] args) {
        Log l = new Log("main");

        final String accessToken =
                "CAACEdEose0cBAI6ZBJgwsaqgnhjjPFZBwqtsxlGO42RxJQbZBKuaZCS59E3jhSEMi90ZCVRZAcdR0v18DI4sGc6pJsop454tvtSmwjKmFaiwOWDMxYFZCNZCMnZC3gNCbQ48oJkbP9zVHHP2oHClhbtDU3aZBUhqEv1MR8ZB7dHLLDUrlJhfWuskpXGRW1GwXN2BMwJuK3WTLpzZBRNtw9oybCe73FfZAFlHzZCg4ZD";
        final String userId = "me";
        final String callBackUrl = "http://waach.local/callback.php";

        MyCallable callable1 = new MyCallable(accessToken, userId, callBackUrl, FbGetter.JOB_GET_USER);
        FutureTask<Object> task1 = new FutureTask<Object>(callable1);
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        executor1.execute(task1);

        try {
            userFb = (UserFb) task1.get(30000L, TimeUnit.MILLISECONDS);
            l.i("user name is " + userFb.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // get the pages of the user
        if (userFb != null) {
            MyCallable callable2 = new MyCallable(accessToken, userId, callBackUrl, FbGetter.JOB_GET_USER_PAGES);
            FutureTask<Object> task2 = new FutureTask<Object>(callable2);

            ExecutorService executor2 = Executors.newSingleThreadExecutor();
            executor2.execute(task2);

            Connection<Page> pageConnection;
            try {
                pageConnection = (Connection<Page>) task2.get(30000L, TimeUnit.MILLISECONDS);
                page = pageConnection.getData().get(0);
                l.i("first page name is " + page.getName() + " and id is " + page.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

        List<Post> videoPosts = new ArrayList<Post>();
        if (page != null) {
            MyCallable callable3 = new MyCallable(accessToken, page.getId(), callBackUrl, FbGetter
                    .JOB_GET_PAGE_VIDEO_POSTS);
            FutureTask<Object> task3 = new FutureTask<Object>(callable3);

            ExecutorService executor2 = Executors.newSingleThreadExecutor();
            executor2.execute(task3);


            Connection<Post> postConnection;
            try {
                videoPosts = (List<Post>) task3.get(30000L, TimeUnit.MILLISECONDS);
                l.i("number of posts is " + videoPosts.size());
//                for (int i=0;i<postConnection.getData().size();i++) {
//                    Post tmp = postConnection.getData().get(i);
//                    if (tmp.getType().equalsIgnoreCase("video")) {
//                        l.i("a video post found");
//                        l.i("number of likes "+tmp.getLikesCount());
//                        l.i("number of comments "+tmp.getCommentsCount());
//                        l.i("number of shares "+tmp.getSharesCount());
//                        videoPosts.add(tmp);
//                    }
//                }
//                post = postConnection.getData().get(0);
//                JsonMapper jsonMapper = new DefaultJsonMapper();
//                l.i("first video post is " + jsonMapper.toJson(post));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

//        if (videoPosts.size() > 0) {
//            int count = 0;
//            for (int i = 0; i < videoPosts.size(); i++) {
//                Post tmp = videoPosts.get(i);
//                if (tmp.getType().equalsIgnoreCase("video")) {
//                    count++;
//                    // l.i("a video post!");
//                    l.i("number of likes " + tmp.getLikesCount());
//                    l.i("number of comments " + tmp.getCommentsCount());
//                    l.i("number of shares " + tmp.getSharesCount());
//                }
//            }
//            l.i("number of video posts is "+count);
//        }

//        MyHandler handler = new MyHandler();
//        ExecutorService executor = Executors.newCachedThreadPool();

//        executor.submit(handler);
//        while (true) {
//            try {
//                if(task1.isDone()){
//                    System.out.println("Done");
//                    //shut down executor service
//                    executorService.shutdown();
//                    return;
//                }
//                System.out.println("Waiting for Task 1 to complete");
//                if(!task1.isDone()){
//                    //wait indefinitely for future task to complete
//                    System.out.println("Task1 output="+task1.get(200L, TimeUnit.MILLISECONDS));
//                }
//
//
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }catch(TimeoutException e){
//                //do nothing
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

//        final FbHelper fbHelper = new FbHelper();
//        fbHelper.getUser(accessToken, userId, null);


//        while (userFb==null) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (userFb!=null) {
//                l.i("the wait is over");
//                break;
//            }
//        }
//        FutureTask<Page> task = new FutureTask<Page>(
//                new Callable<Page>() {
//
//                    @Override
//                    public Page call() throws Exception {
//                        PageGetter pageGetter = (PageGetter) FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_PAGE, fbHelper, callBackUrl);
//                        Connection<Page> pageConnection = pageGetter.getUserPages(accessToken, userId);
//                        return pageConnection.getData().get(0);
//                    }
//                });
//        new Thread(task).start();
//        Page result = null;
//        try {
//            result = task.get();
//            l.i("the wait is over");
//            l.i("the first page name is "+result.getName());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        if (page!=null) {
//            String pageId = page.getId();
//            VideoPostGetter videoPostRetriever = (VideoPostGetter) FbGetterFactory.getFbGetter(FbGetterFactory
//                    .TYPE_POST, fbHelper, callBackUrl);
//            videoPostRetriever.getPagePosts(accessToken, pageId);
//        }
    }

//    @Override
//    public void onHttpCompleted(NamedFacebookType object) {
//        l.i("onHttpCompleted");
//        if (object!=null) {
//            try {
//                if (object instanceof  UserFb) {
////                    UserFb user = (UserFb) object;
////                    l.i("object name is " + user.getName());
//                    userFb = (UserFb) object;
//                }
//
//            } catch (Throwable t) {
//                l.e(t);
//            }
//        }
//    }
//
//    @Override
//    public void onHttpCompleted(Connection connection) {
//        try {
//            List<Object> list = connection.getData();
//            l.i("object count "+list.size());
//            for (int i=0;i<list.size();i++) {
//                if (list.get(i) instanceof Page) {
//                    l.i("page name is "+((Page) list.get(i)).getName());
//                    page = (Page) list.get(i);
//                }
//            }
//        } catch (Throwable t) {
//            l.e(t);
//        }
//    }
//
//    @Override
//    public void onHtttpError(Throwable t) {
//
//    }
}
