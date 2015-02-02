package com.gec;

import com.gec.getters.*;
import com.gec.getters.FbObjectGetter;
import com.gec.getters.PageGetter;

/**
 * Created by eric on 29/1/15.
 */
public class FbGetterFactory {
//    public final static byte TYPE_USER = 1;
//    public final static byte TYPE_PAGE = 2;
//    public final static byte TYPE_POST = 3;
    public static FbObjectGetter getFbGetter(byte type, FbObjectGetter.Callback callback, String callbackUrl) {
        switch (type) {
            case FbObjectGetter.JOB_GET_USER:
                return new UserGetter(callback, callbackUrl);
            case FbObjectGetter.JOB_GET_USER_PAGES:
                return new PageGetter(callback, callbackUrl);
            case FbObjectGetter.JOB_GET_PAGE_VIDEO_POSTS:
                return new PostGetter(callback, callbackUrl);
            case FbObjectGetter.JOB_GET_POST:
                return new PostGetter(callback, callbackUrl);
            default:
                return null;
        }
    }
}
