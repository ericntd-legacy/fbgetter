package com.gec.getters;

import com.gec.FbCallable;
import facebook4j.Facebook;
// import com.restfb.Parameter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 28/1/15.
 */
public class PostGetter extends FbObjectGetter {
    public static final String TYPE_VIDEO = "video";

    public PostGetter(Facebook facebook, Callback callback, String callbackUrl) {super(facebook, callback, callbackUrl);}

    public void getPost(String postId) {
        l.info("getPost");
        FbCallable callable1 = new FbCallable(this, JOB_GET_POST, this.facebook, postId, null, null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
}
