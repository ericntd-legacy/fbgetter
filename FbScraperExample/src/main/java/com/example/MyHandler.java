//package com.example;
//
//import com.gec.FbGetter;
//import com.gec.FbGetterFactory;
//import com.gec.Log;
//import com.gec.UserGetter;
//import com.gec.entities.UserFb;
//import com.restfb.Connection;
//import com.restfb.types.NamedFacebookType;
//
///**
// * Created by eric on 29/1/15.
// */
//public class MyHandler implements Runnable, FbGetter.Callback {
//    private Log l = new Log(this.getClass().getSimpleName());
//
//    @Override
//    public void run() {
//        l.i("run");
//        final String accessToken =
//                "CAACEdEose0cBAPZAAF0kVx07TIjqk6o422sZCcVT4gDUcGqu715ISfbWGZB7WKtlxFgjKcicFQ0OxJIr3mGrtGXHaeI8ecEoCxbPfDbpBWIKdAFiHrBZA2b77LzhoeZAdhrFGRIb4oUOVcgvBi4aGFTLrIHyDUAsFwYWgJPbPW1jNJPGWDNjIzpUaoBIvNc2lX91u5q9MNZB9C7hXrl2Yhs9XsGclQ31YZD";
//        final String userId = "me";
//        final String callBackUrl = "http://waach.local/callback.php";
//
//        UserGetter userGetter = (UserGetter) FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_USER, this, null);
//        userGetter.getUser(accessToken, userId);
//    }
//
//    @Override
//    public void onSuccess(Object object) {
//        l.i("onHttpCompleted");
//        if (object!=null) {
//            try {
//                if (object instanceof UserFb) {
//                    UserFb user = (UserFb) object;
//                    l.i("object name is " + user.getName());
//                }
//
//            } catch (Throwable t) {
//                l.e(t);
//            }
//        }
//    }
//
//    @Override
//    public void onSuccess(Connection connection) {
//
//    }
//
//    @Override
//    public void onError(Throwable t) {
//
//    }
//}
