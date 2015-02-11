package com.example;

import com.gec.*;
import com.gec.getters.*;
import com.gec.getters.FbObjectGetter;
import com.gec.getters.PostGetter;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import facebook4j.Facebook;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by eric on 30/1/15.
 */
public class MyCallable implements Callable<Object>, FbObjectGetter.Callback {
    private Object object;
    @NotNull private Facebook facebook;
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

    private final org.slf4j.Logger l = LoggerFactory.getLogger(this.getClass());

    public MyCallable(String accessToken, String objectId, String callbackUrl, byte jobCode) {
        this.accessToken = accessToken;
        this.objectId = objectId;
        this.callbackUrl = callbackUrl;
        this.jobCode = jobCode;
    }

    public MyCallable(Facebook facebook, String objectId, String callBackUrl, byte jobCode) {
        this.facebook = facebook;
        this.objectId = objectId;
        this.callbackUrl = callBackUrl;
        this.jobCode = jobCode;
    }

    @Override
    public Object call() throws Exception {
        // l.info("call");
        FbObjectGetter fbObjectGetter = FbGetterFactory.getFbGetter(this.facebook, this.jobCode, this, this
                .callbackUrl);
        switch (jobCode) {
            case FbObjectGetter.JOB_GET_USER:
//                UserGetter userGetter = (UserGetter) fbObjectGetter; // FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_USER,
//                // this, null);
//                userGetter.getUser(accessToken, objectId);
                fbObjectGetter.getUser(objectId);
                break;
            case FbObjectGetter.JOB_GET_USER_PAGES:
                // PageGetter pageGetter = FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_POST, this, null);
//                PageGetter pageGetter = (PageGetter) fbObjectGetter;
//                pageGetter.getUserPages(accessToken, objectId);
                fbObjectGetter.getUserPages(objectId);
                break;
            case FbObjectGetter.JOB_GET_PAGE_VIDEO_POSTS:
//                PostGetter postGetter = (PostGetter) fbObjectGetter;
//                postGetter.getPagePosts(accessToken, objectId);
                fbObjectGetter.getPagePosts(objectId);
                break;
            case FbObjectGetter.JOB_GET_POST:
//                postGetter = (PostGetter) fbObjectGetter;
//                postGetter.getPost(accessToken, objectId);
                fbObjectGetter.getPost(objectId);
                break;
            case FbObjectGetter.JOB_GET_PAGE:
                fbObjectGetter.getPage(objectId);
                break;
            case FbObjectGetter.JOB_GET_PAGE_INSIGHTS_CORE:
                fbObjectGetter.getPageInsightsCore(this.objectId);
                break;
            case FbObjectGetter.JOB_GET_PAGE_INSIGHTS_ALL:
                fbObjectGetter.getPageInsightsAll(this.objectId);
                break;
            case FbObjectGetter.JOB_GET_POST_INSIGHTS_CORE:
                fbObjectGetter.getPostInsightsCore(this.objectId);
                break;
            case FbObjectGetter.JOB_GET_POST_INSIGHTS_ALL:
                fbObjectGetter.getPostInsightsAll(this.objectId);
                break;
            default:
                l.error("invalid job code, nothing is done");
        }

        while (this.status == 0) {
            try {
                Thread.sleep(100);
                // l.info("waiting");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.status == STATUS_ERROR || this.status == STATUS_DONE) {
                l.info("job done or error encountered");
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
