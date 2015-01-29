package com.gec;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.Post;

/**
 * Created by eric on 28/1/15.
 */
public class VideoPostGetter extends FbGetter {

    public VideoPostGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }

    public void getPost(String accessToken, String postId) {
        l.i("getPost");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        try {
            Post post = facebookClient.fetchObject(postId, Post.class);
            // l.i("page count is " + pages.getData().size());
            if (this.callback != null) this.callback.onSuccess(post);
        } catch (Throwable t) {
            l.e(t);
            this.callback.onError(t);
        }
    }

    public Connection<Post> getPagePosts(String accessToken, String pageId) {
        l.i("getPagePosts");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Connection<Post> postConnection = null;
        try {
            postConnection = facebookClient.fetchConnection(pageId, Post.class);
            // l.i("page count is " + pages.getData().size());
            // if (this.callback != null) this.callback.onSuccess(pages);
        } catch (Throwable t) {
            l.e(t);
            this.callback.onError(t);
        }
        return postConnection;
    }
}
