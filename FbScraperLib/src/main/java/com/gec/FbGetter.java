package com.gec;

import com.ning.http.client.Response;
import com.restfb.*;
import com.restfb.types.NamedFacebookType;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 24/01/15.
 */
public abstract class FbGetter implements HttpRequester.Callback, FbCallable.Callback {
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

    @Nullable protected String callbackUrl;
    @Nullable protected Callback callback;

    protected Logger l = Logger.getLogger(this.getClass().getSimpleName());
    // protected Log l = new Log(this.getClass().getSimpleName());

    public FbGetter(Callback callback, String callbackUrl) {
        this.callback = callback;
        this.callbackUrl = callbackUrl;
    }

    protected void pingCallbackUrl(String callbackUrl, NamedFacebookType object) {
        l.info("pingCallbackUrl");
        HttpRequester httpRequester = new HttpRequester(this);
        DefaultJsonMapper jsonMapper = new DefaultJsonMapper();
        httpRequester.post(callbackUrl, jsonMapper.toJson(object));
    }

//    private UserFb getTheUser(String accessToken, String userId) {
//        l.info("getTheUser");
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        try {
//            UserFb user = facebookClient.fetchObject(userId, UserFb.class, Parameter.with(PARAM_FIELDS, FB_USER_FIELDS));
//            return user;
//        } catch (Throwable e) {
//            l.log(Level.SEVERE, "", e);
//        }
//        return null;
//    }

//    protected void getObject(String accessToken, String objectId, String fields) {
//        l.info("getTheUser");
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        NamedFacebookType obj = new NamedFacebookType();
//        try {
//            if (fields!=null) {
//                obj  = facebookClient.fetchObject(objectId, NamedFacebookType.class, Parameter.with(PARAM_FIELDS,
//                        fields));
//            } else {
//                obj = facebookClient.fetchObject(objectId, NamedFacebookType.class);
//            }
//
//            if (this.callback != null) this.callback.onHttpCompleted(obj);
//            if (this.callbackUrl!=null&&this.callbackUrl.infosEmpty()) pingCallbackUrl(callbackUrl, obj);
//        } catch (Throwable t) {
//            // l.log(Level.SEVERE, "caught", e);
//            l.e(t);
//        }
//    }

    /**
     *
     * @param response
     */
    @Override
    public void onHttpCompleted(Response response) {
        System.out.println("onHttpCompleted");
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
        l.log(Level.SEVERE, "", t);
    }

    public void onSuccess(Object object) {
        l.info("onSuccess");
        if (this.callback!=null) this.callback.onSuccess(object);
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
