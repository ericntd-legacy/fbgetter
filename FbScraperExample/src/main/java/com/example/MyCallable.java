package com.example;

import com.gec.*;
import com.restfb.Connection;
import com.restfb.types.NamedFacebookType;
import com.sun.istack.internal.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;

/**
 * Created by eric on 30/1/15.
 */
public class MyCallable implements Callable<Object>, FbGetter.Callback {
    private Object object;
    @NotNull
    private String accessToken;
    @NotNull
    private String objectId;

    private byte jobCode;

    private byte status = 0;
    private final byte STATUS_DONE = 1;
    private final byte STATUS_ERROR = 2;

    // private Log l = new Log(this.getClass().getSimpleName());
    private Logger l = Logger.getLogger(this.getClass().getSimpleName());

    public MyCallable(String accessToken, String objectId, byte jobCode) {
        this.accessToken = accessToken;
        this.objectId = objectId;
        this.jobCode = jobCode;
    }

    @Override
    public Object call() throws Exception {
        // l.info("call");
        FbGetter fbGetter = FbGetterFactory.getFbGetter(this.jobCode, this, null);
        switch (jobCode) {
            case FbGetter.JOB_GET_USER:
                UserGetter userGetter = (UserGetter) fbGetter; // FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_USER,
                // this, null);
                userGetter.getUser(accessToken, objectId);
                break;
            case FbGetter.JOB_GET_USER_PAGES:
                // PageGetter pageGetter = FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_POST, this, null);
                PageGetter pageGetter = (PageGetter) fbGetter;
                pageGetter.getUserPages(accessToken, objectId);
                break;
            case FbGetter.JOB_GET_PAGE_VIDEO_POSTS:
                VideoPostGetter postGetter = (VideoPostGetter) fbGetter;
                postGetter.getPagePosts(accessToken, objectId);
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
