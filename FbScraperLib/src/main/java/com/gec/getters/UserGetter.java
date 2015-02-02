package com.gec.getters;

import com.gec.FbCallable;
import com.gec.http.HttpRequester;
import com.restfb.Parameter;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 28/1/15.
 */
public class UserGetter extends FbObjectGetter {
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

    @Override
    public void getUser(String accessToken, String userId) {
        getUser(accessToken, userId, FB_USER_FIELDS);

        /*
        Using Facebook4j
         */
        // Facebook facebook = new FacebookFactory().getInstance();
    }

    @Override
    public void getUserPages(String accessToken, String userId) {
        UnsupportedOperationException t = new UnsupportedOperationException("Use PageGetter implementation instead");
        onError(t);
    }

    @Override
    public void getPagePosts(String accessToken, String pageId) {
        UnsupportedOperationException t = new UnsupportedOperationException("Use PostGetter implementation instead");
        onError(t);
    }

//    @Override
//    public void onHttpCompleted(Response response) {
//        l.i("onHttpCompleted");
//    }

    private void getUser(String accessToken, String objectId, String fields) {
        l.info("getUser");
        Parameter parameter = Parameter.with(PARAM_FIELDS, FB_USER_FIELDS);
        FbCallable callable1 = new FbCallable(this, JOB_GET_USER, accessToken, objectId, null, parameter);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
}
