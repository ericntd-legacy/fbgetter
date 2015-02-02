package com.gec.getters;

import com.gec.FbCallable;
import com.restfb.Parameter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 28/1/15.
 */
public class PostGetter extends FbObjectGetter {
    public static final String TYPE_VIDEO = "video";

    public PostGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }

    @Override
    public void getUser(String accessToken, String userId) {
        UnsupportedOperationException t = new UnsupportedOperationException("Use UserGetter implementation instead");
        onError(t);
    }

    @Override
    public void getUserPages(String accessToken, String userId) {
        UnsupportedOperationException t = new UnsupportedOperationException("Use PageGetter implementation instead");
        onError(t);
    }

    public void getPost(String accessToken, String postId) {
        l.info("getPost");
        FbCallable callable1 = new FbCallable(this, JOB_GET_POST, accessToken, postId, null, null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }

    public void getPagePosts(String accessToken, String pageId) {
        l.info("getPagePosts");
        FbCallable callable1 = new FbCallable(this, JOB_GET_PAGE_VIDEO_POSTS, accessToken, pageId, EDGE_FEED, Parameter
                .with(PARAM_SINCE, SINCE_DEFAULT));
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
}
