package com.example;

import com.gec.*;
import com.gec.entities.UserFb;
import com.restfb.Connection;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.Video;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by Eric on 25/01/15.
 */
public class Main {

    private static Main mainObj;
    private static UserFb userFb;
    private static Page page;
    private static Post post;

    private Log l = new Log(this.getClass().getSimpleName());

    public static void main(String[] args) {
        Log l = new Log("main");
        l.w("testing");

        mainObj = new Main();

//        final String accessToken =
//                "CAACEdEose0cBADoNdB9ACN71lYFagMR9ZA0i2VSq1UvVSMo2BqGZBzPmYtvrRFUQ93z4lM786IRkw1Xgp6he01mIpDNVBvN6Jx0GtV602xsF7h10SDgAJFvjSWbpxlxF14rmiU8o98NARmrA6PZCM55ayV4b8KqHV6WGj2zMqzhycQhevgIil8UQhMpbMyixCWjMp4f804DFDpIuEyHTBHVL7AZAXQgZD";
//        final String userId = "me";
//        final String callBackUrl = "http://waach.local/callback.php";

        MyHandler handler = new MyHandler();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(handler);

//        final FbHelper fbHelper = new FbHelper();

//        UserGetter userGetter = (UserGetter) FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_USER, fbHelper, callBackUrl);
//        userGetter.getUser(accessToken, userId);
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
//    public void onSuccess(NamedFacebookType object) {
//        l.i("onSuccess");
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
//    public void onSuccess(Connection connection) {
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
//    public void onError(Throwable t) {
//
//    }
}
