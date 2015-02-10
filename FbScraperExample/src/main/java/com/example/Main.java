package com.example;

// import ch.qos.logback.classic.Logger;
// import com.gec.entities.UserFb;
import com.gec.getters.FbObjectGetter;
import com.gec.getters.UserGetter;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.internal.json.DataObjectFactoryUtil;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.json.DataObjectFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
// import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by Eric on 25/01/15.
 */
public class Main {

    private static Main mainObj;
    // private static UserFb userFb;
    private static Page page;
    private static Account account;
    private static Post post;

    private static String accessToken
            =
            "CAAV5ZCs146SoBAOZBvatZBWt2QrAlD3YYlBtO8tgYIqUPVixdpcxBCkN87YUvcO0v3oIecapwXYHfUNiaM3CgqvKG1ZBOBzd8ahhCsHFAKZCKDZBFWpmAo4E6jNOrPyWJt36VyM6GCXUZAQsT84odQcWMO7WbBKGPCsXDFWKqFfIKvjORZBavDjSwbFdrsJohudtaQkan1oYPX9fQer6B8fg";
    private static String userId = "me";
    private static String callBackUrl = "http://waach.local/callback.php";

    // private static Log l = new Log("main");
    // private static Logger l;
    private static final Logger l = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        doFb4j();
    }

    private static void doFb4j() {
        l.info("hello");
        Facebook facebook = new FacebookFactory().getInstance();

        facebook.setOAuthAppId("1541510159460650", "aabd8b2514b191b6182d19ca6138307b");
        facebook.setOAuthPermissions("email,user_friends,manage_pages");
        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));

//        MyCallable callable1 = new MyCallable(facebook, userId, callBackUrl, FbObjectGetter.JOB_GET_USER);
//        FutureTask<Object> task1 = new FutureTask<Object>(callable1);
//        ExecutorService executor1 = Executors.newSingleThreadExecutor();
//        executor1.execute(task1);
//
//        User user = null;
//        try {
//            // userFb = (UserFb) task1.get(30000L, TimeUnit.MILLISECONDS);
//            user = (User) task1.get(30000L, TimeUnit.MILLISECONDS);
//            l.info("user name is " + user.getName());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//
//        // get the pages of the user
//        if (user != null) {
//            MyCallable callable2 = new MyCallable(facebook, userId, callBackUrl, FbObjectGetter.JOB_GET_USER_PAGES);
//            FutureTask<Object> task2 = new FutureTask<Object>(callable2);
//
//            ExecutorService executor2 = Executors.newSingleThreadExecutor();
//            executor2.execute(task2);
//
//            List<Account> userPages;
//            try {
//                userPages = (List<Account>) task2.get(30000L, TimeUnit.MILLISECONDS);
//                account = userPages.get(0);
//                l.info("first page name is " + account.getName() + " and id is " + account.getId());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        }

        String pageAccessToken = "CAAV5ZCs146SoBABNKTaUqHQFfhvXS54LFjacrpXDQ3BCkxfpc1WZCYomx8hgPBZAZAkK0m958jCPomxbHPHFwUoQ1sWIvB5yvCU0dHj0ix3Wn50WZALShEIeRazGhg5lPhOk4PMhlMGQkTgPaL2UXitfmfSoKXbJgS7U7BmZClORhinUqBGZBX1htnoVQJgBScZD";
        facebook.setOAuthAccessToken(new AccessToken(pageAccessToken, null));
        String pageId = "101665995139";


        /*
        get a page's details
         */
            /*
            page access token
             */
            MyCallable callable3 = new MyCallable(facebook, pageId, callBackUrl, FbObjectGetter
                    .JOB_GET_PAGE_INSIGHTS_CORE);
            FutureTask<Object> task3 = new FutureTask<Object>(callable3);

            ExecutorService executor2 = Executors.newSingleThreadExecutor();
            executor2.execute(task3);



            try {
                JSONObject object = (JSONObject) task3.get(30000L, TimeUnit.MILLISECONDS);
                // l.info("there are "+tmp.size()+" - "+tmp.getCount()+" insights");
//                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//                String objJson = null;
//                objJson = ow.writeValueAsString(object);
                l.info("result is "+object.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
//            catch (JsonMappingException e) {
//                e.printStackTrace();
//            } catch (JsonGenerationException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


//        List<Post> videoPosts = new ArrayList<Post>();
//        if (account != null) {
//            /*
//            page access token
//             */
//            MyCallable callable3 = new MyCallable(facebook, account.getId(), callBackUrl, FbObjectGetter
//                    .JOB_GET_PAGE_VIDEO_POSTS);
//            FutureTask<Object> task3 = new FutureTask<Object>(callable3);
//
//            ExecutorService executor2 = Executors.newSingleThreadExecutor();
//            executor2.execute(task3);
//
//
//
//            try {
//                videoPosts = (List<Post>) task3.get(30000L, TimeUnit.MILLISECONDS);
//                l.info("number of posts is " + videoPosts.size());
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (videoPosts.size() > 0) {
//            int count = 0;
//            for (int i = 0; i < videoPosts.size(); i++) {
//                Post tmp = videoPosts.get(i);
//                if (tmp.getType().equalsIgnoreCase("video")) {
//                    count++;
//                    // l.i("a video post!");
//                    l.info("number of likes " + tmp.getLikes().getCount());
//                    l.info("number of comments " + tmp.getComments().getCount());
//                    l.info("number of shares " + tmp.getSharesCount());
//                }
//            }
//        }
    }

    private static void getPagePosts(Facebook facebook, String pageId) {
//        MyCallable callable3 = new MyCallable(accessToken, page.getId(), callBackUrl, FbObjectGetter
//                .JOB_GET_PAGE_VIDEO_POSTS);
        MyCallable callable3 = new MyCallable(facebook, pageId, callBackUrl, FbObjectGetter
                .JOB_GET_PAGE_VIDEO_POSTS);
        FutureTask<Object> task3 = new FutureTask<Object>(callable3);

        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        executor2.execute(task3);


        // List<Post> videoPosts = null;
        List<facebook4j.Post> videoPosts = null;
        try {
            videoPosts = (List<facebook4j.Post>) task3.get(30000L, TimeUnit.MILLISECONDS);
            l.info("number of posts is " + videoPosts.size());
            for (int i = 0; i < videoPosts.size(); i++) {
                l.info("number of shares "+videoPosts.get(i).getSharesCount());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void doRestFb() {
        MyCallable callable1 = new MyCallable(accessToken, userId, callBackUrl, FbObjectGetter.JOB_GET_USER);
        FutureTask<Object> task1 = new FutureTask<Object>(callable1);
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        executor1.execute(task1);

//        try {
//            userFb = (UserFb) task1.get(30000L, TimeUnit.MILLISECONDS);
//            l.info("user name is " + userFb.getName());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//
//        // get the pages of the user
//        if (userFb != null) {
//            MyCallable callable2 = new MyCallable(accessToken, userId, callBackUrl, FbObjectGetter.JOB_GET_USER_PAGES);
//            FutureTask<Object> task2 = new FutureTask<Object>(callable2);
//
//            ExecutorService executor2 = Executors.newSingleThreadExecutor();
//            executor2.execute(task2);
//
//            Connection<Page> pageConnection;
//            try {
//                pageConnection = (Connection<Page>) task2.get(30000L, TimeUnit.MILLISECONDS);
//                page = pageConnection.getData().get(0);
//                l.info("first page name is " + page.getName() + " and id is " + page.getId());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        }
//
//        List<Post> videoPosts = new ArrayList<Post>();
//        if (page != null) {
//            /*
//            page access token
//             */
//            accessToken =
//                    "CAACEdEose0cBAGBWcHHw6nNFztGowbiiNQgfoaiZBZCxkkAhhmvST6i8o7NrZA9Vcgjkn6qZCVFKLuDCSk7pNb6IZAYOboO8KbEiLo2ZCM9kksnlKK1ZCfBrBe4uL1QyUBEhRCbThlrnRQc32kmuGooGxYFPrqjKeIcQNZA85aoSVmZBek2ecchXqf5bSyD7ND0unRSY52zZBBPQZDZD";
//            MyCallable callable3 = new MyCallable(accessToken, page.getId(), callBackUrl, FbObjectGetter
//                    .JOB_GET_PAGE_VIDEO_POSTS);
//            FutureTask<Object> task3 = new FutureTask<Object>(callable3);
//
//            ExecutorService executor2 = Executors.newSingleThreadExecutor();
//            executor2.execute(task3);
//
//
//            Connection<Post> postConnection;
//            try {
//                videoPosts = (List<Post>) task3.get(30000L, TimeUnit.MILLISECONDS);
//                l.info("number of posts is " + videoPosts.size());
////                for (int i=0;i<postConnection.getData().size();i++) {
////                    Post tmp = postConnection.getData().get(i);
////                    if (tmp.getType().equalsIgnoreCase("video")) {
////                        l.i("a video post found");
////                        l.i("number of likes "+tmp.getLikesCount());
////                        l.i("number of comments "+tmp.getCommentsCount());
////                        l.i("number of shares "+tmp.getSharesCount());
////                        videoPosts.add(tmp);
////                    }
////                }
////                post = postConnection.getData().get(0);
////                JsonMapper jsonMapper = new DefaultJsonMapper();
////                l.i("first video post is " + jsonMapper.toJson(post));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        }

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
    }
}
