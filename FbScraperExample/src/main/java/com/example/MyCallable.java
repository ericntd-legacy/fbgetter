package com.example;

import com.gec.*;
import com.gec.getters.*;
import com.gec.getters.FbObjectGetter;
import com.gec.getters.PostGetter;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by eric on 30/1/15.
 */
public class MyCallable implements Callable<Object>, FbObjectGetter.Callback {
    private Object object;
    @NotNull
    private String accessToken;
    @NotNull
    private String objectId;
    @Nullable
    private String callbackUrl;

    private byte jobCode;

    private byte status = 0;
    private final byte STATUS_DONE = 1;
    private final byte STATUS_ERROR = 2;

    // private Log l = new Log(this.getClass().getSimpleName());
    private Logger l = Logger.getLogger(this.getClass().getSimpleName());

    public MyCallable(String accessToken, String objectId, String callbackUrl, byte jobCode) {
        this.accessToken = accessToken;
        this.objectId = objectId;
        this.callbackUrl = callbackUrl;
        this.jobCode = jobCode;
    }

    @Override
    public Object call() throws Exception {
        // l.info("call");
        FbObjectGetter fbObjectGetter = FbGetterFactory.getFbGetter(this.jobCode, this, this.callbackUrl);
        switch (jobCode) {
            case FbObjectGetter.JOB_GET_USER:
//                UserGetter userGetter = (UserGetter) fbObjectGetter; // FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_USER,
//                // this, null);
//                userGetter.getUser(accessToken, objectId);
                fbObjectGetter.getUser(accessToken, objectId);
                break;
            case FbObjectGetter.JOB_GET_USER_PAGES:
                // PageGetter pageGetter = FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_POST, this, null);
//                PageGetter pageGetter = (PageGetter) fbObjectGetter;
//                pageGetter.getUserPages(accessToken, objectId);
                fbObjectGetter.getUserPages(accessToken, objectId);
                break;
            case FbObjectGetter.JOB_GET_PAGE_VIDEO_POSTS:
//                PostGetter postGetter = (PostGetter) fbObjectGetter;
//                postGetter.getPagePosts(accessToken, objectId);
                fbObjectGetter.getPagePosts(accessToken, objectId);
                break;
            case FbObjectGetter.JOB_GET_POST:
//                postGetter = (PostGetter) fbObjectGetter;
//                postGetter.getPost(accessToken, objectId);
                fbObjectGetter.getPost(accessToken, objectId);
                break;
        }

        while (this.status == 0) {
            try {
                Thread.sleep(100);
                // l.info("waiting");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.status == STATUS_ERROR || this.status == STATUS_DONE) {
                break;
            }
        }
        return object;
    }

    @Override
    public void onSuccess(Object object) {
        l.info("onSuccess");
        this.status = STATUS_DONE;
        this.object = object;
    }

//    @Override
//    public void onSuccess(Connection connection) {
//        l.info("onSuccess");
//        this.object = connection;
//    }

    @Override
    public void onError(Throwable t) {
        l.info("onError");
        this.status = STATUS_ERROR;
        this.object = t;
    }
}
