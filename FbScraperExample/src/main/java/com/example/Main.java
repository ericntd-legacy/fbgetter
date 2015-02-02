package com.example;

import com.gec.entities.UserFb;
import com.gec.getters.FbObjectGetter;
import com.gec.utils.Log;
import com.restfb.Connection;
import com.restfb.types.Page;
import com.restfb.types.Post;

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

    public static void main(String[] args) {
        Log l = new Log("main");

        String accessToken =
                "CAACEdEose0cBADZCSarQW0cJHBWpQslOjIQZAMNePjdWsEvnXIucmDKdZAgCkil7Vax7bK4WnkET0h4PP1ap77sZAUoyrObs0r6wNdnehc9dPrveeSGx2k4mZCpTADKwHfL9daSJFWM7OZBkepS8ZCZCBG9H0G7gyj9mbKIihGleeRUxiXTCnh0NxOgLF2PD5Alb5wfhZCVoLzrHFshWFIBcU17An1rsqtT4ZD";
        final String userId = "me";
        final String callBackUrl = "http://waach.local/callback.php";

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
