package com.gec;

import com.gec.getters.*;
import com.gec.getters.FbObjectGetter;
import com.gec.getters.PageGetter;
import facebook4j.Facebook;

import java.security.InvalidParameterException;

/**
 * Created by eric on 29/1/15.
 */
public class FbGetterFactory {
//    public final static byte TYPE_USER = 1;
//    public final static byte TYPE_PAGE = 2;
//    public final static byte TYPE_POST = 3;
    public static FbObjectGetter getFbGetter(Facebook facebook, byte type, FbObjectGetter.Callback callback, String
            callbackUrl) {
        switch (type) {
            case FbObjectGetter.JOB_GET_USER:
                return new UserGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_USER_PAGES:
                return new UserGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_PAGE_VIDEO_POSTS:
                return new PageGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_POST:
                return new PostGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_PAGE:
                return new PageGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_PAGE_INSIGHTS_CORE:
                return  new PageGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_PAGE_INSIGHTS_ALL:
                return  new PageGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_POST_INSIGHTS_CORE:
                return  new PostGetter(facebook, callback, callbackUrl);
            case FbObjectGetter.JOB_GET_POST_INSIGHTS_ALL:
                return  new PostGetter(facebook, callback, callbackUrl);
            default:
                throw new InvalidParameterException("Invalid job code, refer to "+FbObjectGetter.class);
        }
    }
}
