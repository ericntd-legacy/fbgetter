package com.gec.getters;

import com.gec.FbCallable;
import facebook4j.Facebook;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 28/1/15.
 */
public class UserGetter extends FbObjectGetter {
    protected final String FB_ME = "me";

    public static final String FB_NAME = "name";
    public static final String FB_FIRST_NAME = "first_name";
    public static final String FB_LAST_NAME = "last_name";
    public static final String FB_LINK = "link";
    public static final String FB_GENDER = "gender";
    public static final String FB_AGE_RANGE = "age_range";
    public static final String FB_ID = "id";
    public static final String FB_EMAIL = "email";
    public static final String FB_LOCALE = "locale";

    public static final String FB_USER_FIELDS = FB_NAME + ", " + FB_FIRST_NAME + ", " + FB_LAST_NAME + ", " + FB_LINK +
            ", " + FB_GENDER + ", " + FB_AGE_RANGE + ", " + FB_ID + ", " + FB_EMAIL + ", " + FB_LOCALE;

    public static final String EDGE_ACCOUNTS = "accounts";

    public UserGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }
    public UserGetter(Facebook facebook, Callback callback, String callbackUrl) {super(facebook, callback, callbackUrl);}

//    /**
//     * My own implementation without third-party library
//     *
//     * @param accessToken
//     * @param userId
//     * @param callbackUrl
//     */
//    private void getUserM(String accessToken, String userId, final String callbackUrl) {
//        this.callbackUrl = callbackUrl;
//
//        String finalUrl = FB_GRAPH_BASE + "/" + userId + "?" + FB_ACCESS_TOKEN + "=" + accessToken;
//        System.out.println("final URL is " + finalUrl);
//        HttpRequester httpRequester = new HttpRequester(this);
//        httpRequester.get(finalUrl);
//    }

    @Override
    public void getUser(String userId) {
        getUser(userId, FB_USER_FIELDS);
    }

    private void getUser(String objectId, String fields) {
        l.info("getUser");

        FbCallable callable1 = new FbCallable(this, JOB_GET_USER, this.facebook, objectId, null, null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }

    @Override
    public void getUserPages(String userId) {
        l.info("getUserPages");
        FbCallable callable1 = new FbCallable(this, JOB_GET_USER_PAGES, this.facebook, userId, EDGE_ACCOUNTS, null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);

    }
}
