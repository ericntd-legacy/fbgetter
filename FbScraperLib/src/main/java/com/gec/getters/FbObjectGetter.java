package com.gec.getters;

import com.gec.FbCallable;
import com.gec.http.HttpRequester;
import com.ning.http.client.Response;
import com.restfb.*;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 24/01/15.
 */
public abstract class FbObjectGetter implements HttpRequester.Callback, FbCallable.Callback {
    public final String FB_GRAPH_BASE = "https://graph.facebook.com";
    public final String FB_ACCESS_TOKEN = "access_token";

    public static final String PARAM_FIELDS = "fields";
    public static final String PARAM_SINCE = "since";
    public static final String PARAM_LIMIT = "limit";

    public static final String SINCE_DEFAULT = "2006-09-05"; // the day Facebook launched News Feed

    public static final String EDGE_ACCOUNTS = "accounts";
    public static final String EDGE_FEED = "feed";

    public static final byte JOB_GET_USER = 1;
    public static final byte JOB_GET_USER_PAGES = 2;
    public static final byte JOB_GET_PAGE_VIDEO_POSTS = 3;
    public static final byte JOB_GET_POST = 4;

    private static  final String ERR_IMPL_NOT_FOUND = "No specific implementation found; No " +
            "default implementation";

    @Nullable protected String callbackUrl;
    @Nullable protected Callback callback;

    protected Logger l = Logger.getLogger(this.getClass().getSimpleName());
    // protected Log l = new Log(this.getClass().getSimpleName());

    /**
     * The result can be returned via either the callback interface or the callback URL (JSON object in POST request
     * body)
     *
     *
     * @param callback
     * @param callbackUrl
     */
    public FbObjectGetter(Callback callback, String callbackUrl) {
        this.callback = callback;
        this.callbackUrl = callbackUrl;
    }

    public void getUser(String accessToken, String userId) {
        UnsupportedOperationException t = new UnsupportedOperationException(ERR_IMPL_NOT_FOUND);
        onError(t);
    }
    public void getUserPages(String accessToken, String userId) {
        UnsupportedOperationException t = new UnsupportedOperationException(ERR_IMPL_NOT_FOUND);
        onError(t);
    }
    public void getPagePosts(String accessToken, String pageId) {
        UnsupportedOperationException t = new UnsupportedOperationException(ERR_IMPL_NOT_FOUND);
        onError(t);
    }
    public void getPost(String accessToken, String postId) {
        UnsupportedOperationException t = new UnsupportedOperationException(ERR_IMPL_NOT_FOUND);
        onError(t);
    }
    public void publishVideoToPage() {
        UnsupportedOperationException t = new UnsupportedOperationException(ERR_IMPL_NOT_FOUND);
        onError(t);
    }
    public void shareVideoToPage() {
        UnsupportedOperationException t = new UnsupportedOperationException(ERR_IMPL_NOT_FOUND);
        onError(t);
    }

    private void pingCallbackUrl(Object object) {
        l.info("pingCallbackUrl");
        HttpRequester httpRequester = new HttpRequester(this);
        DefaultJsonMapper jsonMapper = new DefaultJsonMapper();
        httpRequester.post(callbackUrl, jsonMapper.toJson(object));
    }

    /**
     *
     * @param response
     */
    @Override
    public void onHttpCompleted(Response response) {
        l.info("onHttpCompleted");
        HttpRequester httpRequester = new HttpRequester(null);
        try {
            System.out.println("response body is " + response.getResponseBody());
            httpRequester.post(callbackUrl, response.getResponseBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHtttpError(Throwable t) {
        l.info("onHtttpError");
        l.log(Level.SEVERE, "", t);
    }

    public void onSuccess(Object object) {
        l.info("onSuccess");
        if (this.callback!=null) this.callback.onSuccess(object);
        if (this.callbackUrl!=null&&!this.callbackUrl.isEmpty()) {
            pingCallbackUrl(object);
//            if (object instanceof  NamedFacebookType) {
//                pingCallbackUrl((NamedFacebookType) object);
//            } else if (object instanceof  Connection) {
//
//            }
        }
    }
    public void onError(Throwable t) {
        l.info("onError");
        if (this.callback!=null) this.callback.onError(t);
    }

    public interface Callback {
        public void onSuccess(Object object);
        // public void onSuccess(Connection connection);
        public void onError(Throwable t);
    }
}
