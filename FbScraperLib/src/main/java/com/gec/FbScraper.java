package com.gec;

import com.gec.entities.UserFb;
import com.ning.http.client.Response;
import com.restfb.*;
import com.restfb.types.Post;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 24/01/15.
 */
public class FbScraper implements HttpRequester.Callback {
    private final String FB_GRAPH_BASE = "https://graph.facebook.com";
    private final String FB_ACCESS_TOKEN = "access_token";
    private final String FB_ME = "me";
    private final String FB_FIELDS = "fields";

    private final String FB_NAME = "name";
    private final String FB_FIRST_NAME = "first_name";
    private final String FB_LAST_NAME = "last_name";
    private final String FB_LINK = "link";
    private final String FB_GENDER = "gender";
    private final String FB_AGE_RANGE = "age_range";
    private final String FB_ID = "id";
    private final String FB_EMAIL = "email";
    private final String FB_LOCALE = "locale";

    private final String FB_FIELDS_DEFAULT = FB_NAME + ", " + FB_FIRST_NAME + ", " + FB_LAST_NAME + ", " + FB_LINK + ", " + FB_GENDER + ", " + FB_AGE_RANGE + ", " + FB_ID + ", " + FB_EMAIL + ", " + FB_LOCALE;

    private String callbackUrl;
    private Callback callback;

    private Logger l = Logger.getLogger(FbScraper.class.getSimpleName());

    public FbScraper(Callback callback) {
        this.callback = callback;
    }

    /**
     * My own implementation without third-party library
     *
     * @param accessToken
     * @param userId
     * @param callbackUrl
     */
    public void getUserM(String accessToken, String userId, final String callbackUrl) {
        this.callbackUrl = callbackUrl;

        String finalUrl = FB_GRAPH_BASE + "/" + userId + "?" + FB_ACCESS_TOKEN + "=" + accessToken;
        System.out.println("final URL is " + finalUrl);
        HttpRequester httpRequester = new HttpRequester(this);
        httpRequester.get(finalUrl);
    }

    public void getUser(String accessToken, String userId, String callbackUrl) {
        UserFb user = getTheUser(accessToken, userId);
        if (callbackUrl == null || callbackUrl.isEmpty()) {
            if (this.callback != null) this.callback.onSuccess(user);
        } else {
            pingCallbackUrl(callbackUrl, user);
        }
    }

    private void pingCallbackUrl(String callbackUrl, UserFb userFb) {
        l.info("pingCallbackUrl");
        HttpRequester httpRequester = new HttpRequester(this);
        DefaultJsonMapper jsonMapper = new DefaultJsonMapper();
        httpRequester.post(callbackUrl, jsonMapper.toJson(userFb));
    }

    public void getUser(String accessToken, String userId) {
        UserFb user = getTheUser(accessToken, userId);
        if (this.callback != null) this.callback.onSuccess(user);
    }

    public void getUser(String accessToken, String userId, Callback callback) {
        callback.onSuccess(getTheUser(accessToken, userId));
    }

    public void getVideoPost(String accessToken, String postId, Callback callback) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Post post = facebookClient.fetchObject(postId, Post.class);
    }

    private UserFb getTheUser(String accessToken, String userId) {
        l.info("getTheUser");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        try {
            UserFb user = facebookClient.fetchObject(userId, UserFb.class, Parameter.with(FB_FIELDS, FB_FIELDS_DEFAULT));
            return user;
        } catch (Throwable e) {
            l.log(Level.SEVERE, "", e);
        }
        return null;
    }

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
        public void onSuccess(UserFb user);
    }
}
