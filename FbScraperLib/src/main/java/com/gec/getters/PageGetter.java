package com.gec.getters;

import com.gec.FbCallable;
import facebook4j.Facebook;
import facebook4j.Reading;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 28/1/15.
 */
public class PageGetter extends FbObjectGetter {
    public static final String EDGE_PAGE_CONSUMPTIONS = "page_consumptions";
    public static final String EDGE_PAGE_CONSUMPTIONS_BY_TYPE = "page_consumptions_by_consumption_type";
    public static final String EDGE_PAGE_ENGAGED_USERS = "page_engaged_users";
    public static final String EDGE_PAGE_FANS = "page_fans";
    public static final String EDGE_PAGE_FANS_CITY = "page_fans_city";
    public static final String EDGE_PAGE_FANS_COUNTRY = "page_fans_country";
    public static final String EDGE_PAGE_FANS_GENDER_AGE = "page_fans_gender_age";
    public static final String EDGE_PAGE_IMPRESSIONS = "page_impressions";

    public PageGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }
    public PageGetter(Facebook facebook, Callback callback, String callbackUrl) {super(facebook, callback, callbackUrl);}

    @Override
    public void getPage(String pageId) {
        getPage(pageId, null);
    }

    private void getPage(String pageId, String fields) {
        l.info("getPage");
        FbCallable callable1 = new FbCallable(this, JOB_GET_PAGE, this.facebook, pageId, null, new Reading().fields
                (fields));
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }

    @Override
    public void getPageInsightsCore(String pageId) {
        l.info("getPageInsights");
        FbCallable callable1 = new FbCallable(this, JOB_GET_PAGE_INSIGHTS_CORE, this.facebook, pageId, EDGE_INSIGHTS,
                null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
    @Override
    public void getPageInsightsAll(String pageId) {
        l.info("getPageInsights");
        FbCallable callable1 = new FbCallable(this, JOB_GET_PAGE_INSIGHTS_ALL, this.facebook, pageId, EDGE_INSIGHTS,
                null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }


    public void getPagePosts(String pageId) {
        l.info("getPagePosts");
        Calendar cal = Calendar.getInstance();
        cal.set(2006, 9, 5);
        Date fbSince = cal.getTime();
        FbCallable callable1 = new FbCallable(this, JOB_GET_PAGE_VIDEO_POSTS, this.facebook, pageId, EDGE_FEED, new
                Reading().since(fbSince));
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);
    }
}
