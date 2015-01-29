package com.gec;

import com.gec.entities.UserFb;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;

/**
 * Created by eric on 28/1/15.
 */
public class UserGetter extends FbGetter {
    protected final String FB_ME = "me";

    private final String FB_NAME = "name";
    private final String FB_FIRST_NAME = "first_name";
    private final String FB_LAST_NAME = "last_name";
    private final String FB_LINK = "link";
    private final String FB_GENDER = "gender";
    private final String FB_AGE_RANGE = "age_range";
    private final String FB_ID = "id";
    private final String FB_EMAIL = "email";
    private final String FB_LOCALE = "locale";

    private final String FB_USER_FIELDS = FB_NAME + ", " + FB_FIRST_NAME + ", " + FB_LAST_NAME + ", " + FB_LINK + ", " + FB_GENDER + ", " + FB_AGE_RANGE + ", " + FB_ID + ", " + FB_EMAIL + ", " + FB_LOCALE;

    public UserGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }

    /**
     * My own implementation without third-party library
     *
     * @param accessToken
     * @param userId
     * @param callbackUrl
     */
    private void getUserM(String accessToken, String userId, final String callbackUrl) {
        this.callbackUrl = callbackUrl;

        String finalUrl = FB_GRAPH_BASE + "/" + userId + "?" + FB_ACCESS_TOKEN + "=" + accessToken;
        System.out.println("final URL is " + finalUrl);
        HttpRequester httpRequester = new HttpRequester(this);
        httpRequester.get(finalUrl);
    }

    public void getUser(String accessToken, String userId) {
        getUser(accessToken, userId, FB_USER_FIELDS);
    }

//    @Override
//    public void onSuccess(Response response) {
//        l.i("onSuccess");
//    }

    private void getUser(String accessToken, String objectId, String fields) {
        l.i("getUser");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        UserFb obj = new UserFb();
        try {
            if (fields!=null) {
                obj  = facebookClient.fetchObject(objectId, UserFb.class, Parameter.with(FB_FIELDS,
                        fields));
            } else {
                obj = facebookClient.fetchObject(objectId, UserFb.class);
            }
            if (this.callback != null) this.callback.onSuccess(obj);
            // if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
        } catch (Throwable t) {
            // l.log(Level.SEVERE, "caught", e);
            l.e(t);
        }
    }
}
