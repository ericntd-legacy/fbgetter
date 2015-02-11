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

    public static final String EDGE_POST_CONSUMPTIONS = "post_consumptions";
    public static final String EDGE_POST_CONSUMPTIONS_BY_TYPE = "post_consumptions_by_type";
    public static final String EDGE_POST_IMPRESSIONS = "post_impressions";
    public static final String EDGE_POST_VIDEO_AVG_TIME_WATCHED = "post_video_avg_time_watched";
    public static final String EDGE_POST_VIDEO_COMPLETE_VIEWS_ORGANIC = "post_video_complete_views_organic";
    public static final String EDGE_POST_VIDEO_VIEWS_ORGANIC = "post_video_views_organic";

    public PostGetter(Facebook facebook, Callback callback, String callbackUrl) {super(facebook, callback, callbackUrl);}

    public void getPost(String postId) {
        l.info("getPost");
        FbCallable callable1 = new FbCallable(this, JOB_GET_POST, this.facebook, postId, null, null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }

    @Override
    public void getPostInsightsCore(String postId) {
        l.info("getPostInsightsCore");
        FbCallable callable1 = new FbCallable(this, JOB_GET_POST_INSIGHTS_CORE, this.facebook, postId, null,
                null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
    @Override
    public void getPostInsightsAll(String postId) {
        l.info("getPostInsightsAll");
        FbCallable callable1 = new FbCallable(this, JOB_GET_POST_INSIGHTS_ALL, this.facebook, postId, null,
                null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
}
