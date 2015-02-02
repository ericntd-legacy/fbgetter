package com.gec.getters;

import com.gec.FbCallable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 28/1/15.
 */
public class PageGetter extends FbObjectGetter {
    public PageGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }

    @Override
    public void getUser(String accessToken, String userId) {
        UnsupportedOperationException t = new UnsupportedOperationException("Use UserGetter implementation instead");
        onError(t);
    }

    public void getPage(String accessToken, String pageId) {
        getPage(accessToken, pageId, null);
    }

    public void getUserPages(String accessToken, String userId) {
        l.info("getUserPages");
        FbCallable callable1 = new FbCallable(this, JOB_GET_USER_PAGES, accessToken, userId, EDGE_ACCOUNTS, null);
        FutureTask<Void> task1 = new FutureTask<Void>(callable1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(task1);

//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        Connection<Page> pages = null;
//        try {
//            pages = facebookClient.fetchConnection(userId + FB_ACCOUNTS, Page.class);
//            // l.info("page count is " + pages.getData().size());
//            // if (this.callback != null) this.callback.onHttpCompleted(pages);
//        } catch (Throwable t) {
//            l.log(Level.SEVERE, "", t);
//            this.callback.onError(t);
//        }
//        return pages;
    }

    @Override
    public void getPagePosts(String accessToken, String pageId) {
        UnsupportedOperationException t = new UnsupportedOperationException("Use PostGetter implementation instead");
        onError(t);
    }

    private void getPage(String accessToken, String objectId, String fields) {
        l.info("getPage");

    }

//    public interface Callback {
//        public void onHttpCompleted(Connection<Page> pageConnection);
//        public void onHtttpError(Throwable t);
//    }
}
