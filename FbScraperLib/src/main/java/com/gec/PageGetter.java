package com.gec;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Page;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;

/**
 * Created by eric on 28/1/15.
 */
public class PageGetter extends FbGetter {
    public PageGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
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

    private void getPage(String accessToken, String objectId, String fields) {
        l.info("getPage");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Page obj;
        try {
            if (fields!=null) {
                obj  = facebookClient.fetchObject(objectId, Page.class, Parameter.with(PARAM_FIELDS,
                        fields));
            } else {
                obj = facebookClient.fetchObject(objectId, Page.class);
            }
            if (this.callback != null) this.callback.onSuccess(obj);
            // if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
        } catch (Throwable t) {
            // l.log(Level.SEVERE, "caught", e);
            l.log(Level.SEVERE, "", t);
            this.callback.onError(t);
        }
    }

//    public interface Callback {
//        public void onHttpCompleted(Connection<Page> pageConnection);
//        public void onHtttpError(Throwable t);
//    }
}
