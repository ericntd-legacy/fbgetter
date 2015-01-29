package com.gec;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Page;

/**
 * Created by eric on 28/1/15.
 */
public class PageGetter extends FbGetter {
    private final String FB_ACCOUNTS = "/accounts";

    public PageGetter(Callback callback, String callbackUrl) {
        super(callback, callbackUrl);
    }

    public void getPage(String accessToken, String pageId) {
        getPage(accessToken, pageId, null);
    }

    public Connection<Page> getUserPages(String accessToken, String userId) {
        l.i("getUserPages");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Connection<Page> pages = null;
        try {
            pages = facebookClient.fetchConnection(userId + FB_ACCOUNTS, Page.class);
            // l.i("page count is " + pages.getData().size());
            // if (this.callback != null) this.callback.onSuccess(pages);
        } catch (Throwable t) {
            l.e(t);
            this.callback.onError(t);
        }
        return pages;
    }

    private void getPage(String accessToken, String objectId, String fields) {
        l.i("getPage");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Page obj;
        try {
            if (fields!=null) {
                obj  = facebookClient.fetchObject(objectId, Page.class, Parameter.with(FB_FIELDS,
                        fields));
            } else {
                obj = facebookClient.fetchObject(objectId, Page.class);
            }
            if (this.callback != null) this.callback.onSuccess(obj);
            // if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
        } catch (Throwable t) {
            // l.log(Level.SEVERE, "caught", e);
            l.e(t);
            this.callback.onError(t);
        }
    }

//    public interface Callback {
//        public void onSuccess(Connection<Page> pageConnection);
//        public void onError(Throwable t);
//    }
}
