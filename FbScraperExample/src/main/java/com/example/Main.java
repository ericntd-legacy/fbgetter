package com.example;

import com.gec.entities.UserFb;
import com.gec.getters.FbObjectGetter;
import com.gec.utils.Log;
import com.restfb.Connection;
import com.restfb.types.Page;
import com.restfb.types.Post;

import facebook4j.*;
import facebook4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Eric on 25/01/15.
 */
public class Main {

    private static Main mainObj;
    private static UserFb userFb;
    private static Page page;
    private static Post post;

    private static String accessToken
            =
            "CAAV5ZCs146SoBAAd46g3YbFUuEChFqXOdZCnmD5ptoRcZCKycN0IdpRroZBOvhmT0ovPHmfTRegDhuEV9KWdq4AWlEckMzQs1gbetPnImpf77zjayd3NZAjPAoNFe9rkLa6DFzRjsqrZCGYZBh5PGpoNS0ZCZAUFXT4moi4Ac5qk8bZAxmTkAJSVJ0gHkkdIK7y7h4T0PJLyMpOFfplPfQxKnY";
    private static String userId = "me";
    private static String callBackUrl = "http://waach.local/callback.php";

    private static Log l = new Log("main");

    public static void main(String[] args) {
        doFb4j();
    }

    private static void doFb4j() {
        Facebook facebook = new FacebookFactory().getInstance();

//        facebook.setOAuthAppId("1541510159460650", "aabd8b2514b191b6182d19ca6138307b");
//        facebook.setOAuthPermissions("email,user_friends,manage_pages");
//        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
//
//        ResponseList<Account> accounts = null;
//        Account yourPageAccount = null;
//        try {
//            accounts = facebook.getAccounts();
//            yourPageAccount = accounts.get(0);  // if index 0 is your page account.
//            String pageAccessToken = yourPageAccount.getAccessToken();
//            l.i("the access token for my page is "+pageAccessToken);
//        } catch (FacebookException e) {
//            e.printStackTrace();
//        }

        String pageAccessToken = "CAAV5ZCs146SoBAOvDNTzjcZAswWoaogiqnzLg7ctVFNeddj1hvpCjghXdaYxBChWYnkNXfUtmxBUEWWW7EvBCZBudbKUZAZB3qGnGxMznv0vb0oxFxpadZAvcj2gCRfGV7PM4ZCSZB3zYmElcaZBok5ZC8D7ZC7gpOxXC45ruTNk7QJZBZBspRicMpmQZBLCnfQLZBMkacZD";
        // facebook.setOAuthAccessToken(new AccessToken(pageAccessToken, null));
        getPagePosts(pageAccessToken);
    }

    private static void getPagePosts(String accessToken) {
        MyCallable callable3 = new MyCallable(accessToken, page.getId(), callBackUrl, FbObjectGetter
                .JOB_GET_PAGE_VIDEO_POSTS);
        FutureTask<Object> task3 = new FutureTask<Object>(callable3);

        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        executor2.execute(task3);


        List<Post> videoPosts = null;
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

    private static void doRestFb() {
        MyCallable callable1 = new MyCallable(accessToken, userId, callBackUrl, FbObjectGetter.JOB_GET_USER);
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
            MyCallable callable2 = new MyCallable(accessToken, userId, callBackUrl, FbObjectGetter.JOB_GET_USER_PAGES);
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
            /*
            page access token
             */
            accessToken =
                    "CAACEdEose0cBAGBWcHHw6nNFztGowbiiNQgfoaiZBZCxkkAhhmvST6i8o7NrZA9Vcgjkn6qZCVFKLuDCSk7pNb6IZAYOboO8KbEiLo2ZCM9kksnlKK1ZCfBrBe4uL1QyUBEhRCbThlrnRQc32kmuGooGxYFPrqjKeIcQNZA85aoSVmZBek2ecchXqf5bSyD7ND0unRSY52zZBBPQZDZD";
            MyCallable callable3 = new MyCallable(accessToken, page.getId(), callBackUrl, FbObjectGetter
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
    }
}
