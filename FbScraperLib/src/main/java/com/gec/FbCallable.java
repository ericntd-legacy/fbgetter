package com.gec;

import com.gec.entities.UserFb;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by eric on 30/1/15.
 */
public class FbCallable implements Callable<Void> {
    @NotNull
    private String accessToken;
    @NotNull
    private String objectId;
    @Nullable
    private String edge;
    @Nullable
    private Parameter parameter;

    private byte status = 0;
    private final byte STATUS_DONE = 1;
    private final byte STATUS_ERROR = 2;

    private Callback callback;

    private byte jobCode;

    // protected Log l = new Log(this.getClass().getSimpleName());
    private Logger l = Logger.getLogger(this.getClass().getSimpleName());

    public FbCallable(Callback callback, byte jobCode, String accessToken, String objectId, String edge, Parameter
            parameter) {
        this.jobCode = jobCode;
        this.callback = callback;
        this.accessToken = accessToken;
        this.objectId = objectId;
        this.edge = edge;
        this.parameter = parameter;
    }

    @Override
    public Void call() throws Exception {
        // l.info("call");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Object result = new Object();
        String id = objectId;
        if (edge != null && !edge.isEmpty()) id = id + "/" + edge;
        l.info("edge is "+edge);

        try {
            switch (jobCode) {
                case FbGetter.JOB_GET_USER:
                    if (parameter!=null) result = facebookClient.fetchObject(id, UserFb.class, parameter);
                    else result = facebookClient.fetchObject(id, UserFb.class);
                    break;
                case FbGetter.JOB_GET_USER_PAGES:
                    if (parameter!=null) result = facebookClient.fetchConnection(id, Page.class, parameter);
                    else result = facebookClient.fetchConnection(id, Page.class);
                    break;
                case FbGetter.JOB_GET_PAGE_VIDEO_POSTS:
//
                    result = fetchConnection(id, Post.class);
                    break;
            }

            if (this.callback != null) this.callback.onSuccess(result);
            // if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
        } catch (Throwable t) {

            l.log(Level.SEVERE, "", t);
            if (this.callback != null) this.callback.onError(t);
        }

        return null;
    }

    private Object fetchConnection(String id, Class<Post> postClass) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        List<Post> posts = new ArrayList<Post>();
        List<Post> tmp = new ArrayList<Post>();
        Connection<Post> result;

        if (parameter!=null) {
            result = facebookClient.fetchConnection(id, Post.class, parameter);
            tmp = result.getData();
            while (tmp!=null) {
                l.info("getting a batch of posts");
                l.info("number of posts in this batch "+tmp.size());
                posts.addAll(tmp);

                String nextUrl = result.getNextPageUrl();
                l.info("next url is "+nextUrl);
                if (nextUrl==null||nextUrl.isEmpty()) break;
                result = facebookClient.fetchConnectionPage(nextUrl,
                        Post.class);

                tmp = result.getData();
                if (tmp==null) break;
            }
        }

        return posts;
    }

    public interface Callback {
        public void onSuccess(Object object);

        public void onError(Throwable t);
    }
}
