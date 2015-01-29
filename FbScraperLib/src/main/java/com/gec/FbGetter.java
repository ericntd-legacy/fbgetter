package com.gec;

import com.ning.http.client.Response;
import com.restfb.*;
import com.restfb.types.NamedFacebookType;
import com.sun.istack.internal.Nullable;

import java.io.IOException;

/**
 * Created by Eric on 24/01/15.
 */
public abstract class FbGetter implements HttpRequester.Callback {
    protected final String FB_GRAPH_BASE = "https://graph.facebook.com";
    protected final String FB_ACCESS_TOKEN = "access_token";
    protected final String FB_FIELDS = "fields";

    @Nullable protected String callbackUrl;
    @Nullable protected Callback callback;

    // private Log l = (Log) Logger.getLogger(FbGetter.class.getSimpleName());
    protected Log l = new Log(this.getClass().getSimpleName());

    public FbGetter(Callback callback, String callbackUrl) {
        this.callback = callback;
        this.callbackUrl = callbackUrl;
    }

    protected void pingCallbackUrl(String callbackUrl, NamedFacebookType object) {
        l.i("pingCallbackUrl");
        HttpRequester httpRequester = new HttpRequester(this);
        DefaultJsonMapper jsonMapper = new DefaultJsonMapper();
        httpRequester.post(callbackUrl, jsonMapper.toJson(object));
    }

//    private UserFb getTheUser(String accessToken, String userId) {
//        l.i("getTheUser");
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        try {
//            UserFb user = facebookClient.fetchObject(userId, UserFb.class, Parameter.with(FB_FIELDS, FB_USER_FIELDS));
//            return user;
//        } catch (Throwable e) {
//            l.log(Level.SEVERE, "", e);
//        }
//        return null;
//    }

//    protected void getObject(String accessToken, String objectId, String fields) {
//        l.i("getTheUser");
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        NamedFacebookType obj = new NamedFacebookType();
//        try {
//            if (fields!=null) {
//                obj  = facebookClient.fetchObject(objectId, NamedFacebookType.class, Parameter.with(FB_FIELDS,
//                        fields));
//            } else {
//                obj = facebookClient.fetchObject(objectId, NamedFacebookType.class);
//            }
//
//            if (this.callback != null) this.callback.onSuccess(obj);
//            if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
//        } catch (Throwable t) {
//            // l.log(Level.SEVERE, "caught", e);
//            l.e(t);
//        }
//    }

    @Override
    public void onSuccess(Response response) {
        System.out.println("onSuccess");
        HttpRequester httpRequester = new HttpRequester(null);
        try {
            System.out.println("response body is " + response.getResponseBody());
            httpRequester.post(callbackUrl, response.getResponseBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    public interface Callback {
        public void onSuccess(NamedFacebookType object);
        public void onSuccess(Connection connection);
        public void onError(Throwable t);
    }
}
