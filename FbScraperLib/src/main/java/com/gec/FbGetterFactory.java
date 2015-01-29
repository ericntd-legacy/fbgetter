package com.gec;

/**
 * Created by eric on 29/1/15.
 */
public class FbGetterFactory {
    public final static byte TYPE_USER = 1;
    public final static byte TYPE_PAGE = 2;
    public final static byte TYPE_POST = 3;
    public static FbGetter getFbGetter(byte type, FbGetter.Callback callback, String callbackUrl) {
        switch (type) {
            case TYPE_USER:
                return new UserGetter(callback, callbackUrl);
            case TYPE_PAGE:
                return new PageGetter(callback, callbackUrl);
            case TYPE_POST:
                return new VideoPostGetter(callback, callbackUrl);
            default:
                return null;
        }
    }
}
