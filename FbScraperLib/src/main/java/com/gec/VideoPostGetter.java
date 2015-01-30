package com.gec;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;

/**
 * Created by eric on 28/1/15.
 */
public class VideoPostGetter extends FbGetter {

    public VideoPostGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }

    public void getPost(String accessToken, String postId) {
        l.info("getPost");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        try {
            Post post = facebookClient.fetchObject(postId, Post.class);
            // l.info("page count is " + pages.getData().size());
            if (this.callback != null) this.callback.onSuccess(post);
        } catch (Throwable t) {
            l.log(Level.SEVERE, "", t);
            this.callback.onError(t);
        }
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
