package com.gec;

//import com.gec.entities.UserFb;
//import com.gec.getters.FbObjectGetter;
//import com.gec.getters.PostGetter;
//import com.restfb.Connection;
//import com.restfb.DefaultFacebookClient;
//import com.restfb.FacebookClient;
//import com.restfb.Parameter;
//import com.restfb.types.Page;
//import com.restfb.types.Post;
import com.gec.getters.FbObjectGetter;
import com.gec.getters.PostGetter;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by eric on 30/1/15.
 */
public class FbCallable implements Callable<Void> {
    // @NotNull private String accessToken;
    @NotNull private Facebook facebook;
    @NotNull private String objectId;
    @Nullable private String edge;
    // @Nullable private Parameter parameter;
    @Nullable Reading reading;

    private byte status = 0;
    private final byte STATUS_DONE = 1;
    private final byte STATUS_ERROR = 2;

    private Callback callback;

    private byte jobCode;

    protected final org.slf4j.Logger l = LoggerFactory.getLogger(this.getClass());

//    public FbCallable(Callback callback, byte jobCode, String accessToken, String objectId, String edge, Parameter
//            parameter) {
//        this.jobCode = jobCode;
//        this.callback = callback;
//        this.accessToken = accessToken;
//        this.objectId = objectId;
//        this.edge = edge;
//        // this.parameter = parameter;
//    }

    public FbCallable(Callback callback, byte jobCode, Facebook facebook, String objectId, String edge, Reading reading) {
        this.jobCode = jobCode;
        this.callback = callback;
        // this.accessToken = accessToken;
        this.facebook = facebook;
        this.objectId = objectId;
        this.edge = edge;
        this.reading = reading;
    }

    @Override
    public Void call() throws Exception {
        // l.info("call");
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        Object result = new Object();
//        String id = objectId;
//        if (edge != null && !edge.isEmpty()) id = id + "/" + edge;
//        l.info("edge is "+edge);
//
//        try {
//            switch (jobCode) {
//                case FbObjectGetter.JOB_GET_USER:
//                    if (parameter!=null) result = facebookClient.fetchObject(id, UserFb.class, parameter);
//                    else result = facebookClient.fetchObject(id, UserFb.class);
//                    break;
//                case FbObjectGetter.JOB_GET_USER_PAGES:
//                    if (parameter!=null) result = facebookClient.fetchConnection(id, Page.class, parameter);
//                    else result = facebookClient.fetchConnection(id, Page.class);
//                    break;
//                case FbObjectGetter.JOB_GET_PAGE_VIDEO_POSTS:
////
//                    result = fetchPagePosts(id, Post.class);
//                    break;
//                case FbObjectGetter.JOB_GET_POST:
//                    if (parameter!=null) result = facebookClient.fetchObject(id, Post.class, parameter);
//                    else result = facebookClient.fetchObject(id, Post.class);
//                    break;
//            }
//
//            if (this.callback != null) this.callback.onSuccess(result);
//            // if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
//        } catch (Throwable t) {
//
//            l.log(Level.SEVERE, "", t);
//            if (this.callback != null) this.callback.onError(t);
//        }

        doFb4j();

        return null;
    }

    private void doFb4j() {
//        Facebook facebook = new FacebookFactory().getInstance();
//        facebook.setOAuthAppId("1541510159460650", "aabd8b2514b191b6182d19ca6138307b");
//        facebook.setOAuthAccessToken(new AccessToken(this.accessToken, null));
        Object result = null;
        try {
            switch (jobCode) {
                case FbObjectGetter.JOB_GET_USER:
//                    if (parameter!=null) result = facebookClient.fetchObject(id, UserFb.class, parameter);
//                    else result = facebookClient.fetchObject(id, UserFb.class);
                    result = facebook.getUser(this.objectId);
                    // if (this.callback!=null) this.callback.on
                    break;
                case FbObjectGetter.JOB_GET_USER_PAGES:
//                    if (parameter!=null) result = facebookClient.fetchConnection(id, Page.class, parameter);
//                    else result = facebookClient.fetchConnection(id, Page.class);
                    result = facebook.getAccounts(this.objectId);
                    break;
                case FbObjectGetter.JOB_GET_PAGE_VIDEO_POSTS:
//
                    // result = fetchPagePosts(id, Post.class);
                    result = getPageVideoPosts();

                    break;
                case FbObjectGetter.JOB_GET_POST:
//                    if (parameter!=null) result = facebookClient.fetchObject(id, Post.class, parameter);
//                    else result = facebookClient.fetchObject(id, Post.class);
                    result = facebook.getPost(this.objectId);
                    break;
                case FbObjectGetter.JOB_GET_PAGE:
                    result = facebook.getPage(this.objectId);
                    break;
                case FbObjectGetter.JOB_GET_PAGE_INSIGHTS_CORE:
                    result = getPageInsightsCore();
                    break;
                case FbObjectGetter.JOB_GET_PAGE_INSIGHTS_ALL:
                    result = facebook.getPageInsights(this.objectId);// this will pull out all! 213 insights as of
                    // 10th Feb 2015
                    break;
                case FbObjectGetter.JOB_GET_POST_INSIGHTS_CORE:
                    break;
                case FbObjectGetter.JOB_GET_POST_INSIGHTS_ALL:
                    break;
                default:
                    l.error("invalid job code, nothing is done");
            }

            if (this.callback != null) this.callback.onSuccess(result);
            // if (this.callbackUrl!=null&&this.callbackUrl.isEmpty()) pingCallbackUrl(callbackUrl, obj);
        } catch (Throwable t) {
            l.error("caught", t);
            if (this.callback != null) this.callback.onError(t);
        }

    }

    private Object getPageInsightsCore() {
        JSONObject result = new JSONObject();
        try {
            RawAPIResponse res = facebook.callGetAPI(this.objectId+"/"+FbObjectGetter
                    .EDGE_INSIGHTS+"/"+FbObjectGetter.EDGE_PAGE_CONSUMPTIONS);
            JSONObject jsonObject = res.asJSONObject();
            result.put("testing", jsonObject);
        } catch (FacebookException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object getPageVideoPosts() throws FacebookException {
        List<Post> videoPosts = new ArrayList<Post>();
        ResponseList<Post> tmp = null;



            // result = facebookClient.fetchConnection(id, Post.class, parameter);
            tmp = facebook.getFeed(this.objectId, this.reading);

            // tmp = result.getData();
            while (tmp!=null) {
                l.info("getting a batch of videoPosts");
                l.info("number of posts in this batch "+tmp.size());
                // videoPosts.addAll(tmp);
                addVideoPosts(videoPosts, tmp);

                Paging<Post> currentPage = tmp.getPaging();
                if (currentPage==null) break;
                tmp = facebook.fetchNext(currentPage);
                if (tmp==null) break;
            }


        return videoPosts;
    }

//    /**
//     * loop through all the pages to get all the posts of a page
//     *
//     * @param id
//     * @param postClass
//     * @return
//     */
//    private Object fetchPagePosts(String id, Class<Post> postClass) {
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
//        List<Post> posts = new ArrayList<Post>();
//        List<Post> tmp = new ArrayList<Post>();
//        Connection<Post> result;
//
//        if (parameter!=null) {
//            result = facebookClient.fetchConnection(id, Post.class, parameter);
//            tmp = result.getData();
//            while (tmp!=null) {
//                l.info("getting a batch of posts");
//                l.info("number of posts in this batch "+tmp.size());
//                // posts.addAll(tmp);
//                addVideoPosts(posts, tmp);
//
//                String nextUrl = result.getNextPageUrl();
//                l.info("next url is "+nextUrl);
//                if (nextUrl==null||nextUrl.isEmpty()) break;
//                result = facebookClient.fetchConnectionPage(nextUrl,
//                        Post.class);
//
//                tmp = result.getData();
//                if (tmp==null) break;
//            }
//        }
//
//        return posts;
//    }
//
    private void addVideoPosts(List<Post> posts, List<Post> tmp) {
        for (int i=0;i<tmp.size();i++) {
            Post p = tmp.get(i);
            if (p.getType().equalsIgnoreCase(PostGetter.TYPE_VIDEO)) {
                posts.add(p);
            }
        }
    }

    public interface Callback {
        public void onSuccess(Object object);
        // public void onSuccess(ResponseList<Post> posts);
        public void onError(Throwable t);
    }
}
