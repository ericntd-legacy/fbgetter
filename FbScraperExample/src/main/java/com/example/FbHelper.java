//package com.example;
//
//import com.gec.FbGetter;
//import com.gec.FbGetterFactory;
//import com.gec.Log;
//import com.gec.UserGetter;
//import com.gec.entities.UserFb;
//import com.restfb.Connection;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// * Created by eric on 30/1/15.
// */
//public class FbHelper implements FbGetter.Callback {
//    // private Log l = new Log(this.getClass().getSimpleName());
//    private Logger l = Logger.getLogger(this.getClass().getSimpleName());
//
//    private byte status = 0;
//    private final byte STATUS_DONE = 1;
//    private final byte STATUS_ERROR = 2;
//
//    public void getUser(String accessToken, String userId, String callBackUrl) {
//        l.info("getUser ");
//
//        UserGetter userGetter = (UserGetter) FbGetterFactory.getFbGetter(FbGetterFactory.TYPE_USER, this, callBackUrl);
//        userGetter.getUser(accessToken, userId);
//
//        while (this.status==0) {
//            try {
//                Thread.sleep(100);
//                l.info("waiting");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (this.status==STATUS_ERROR||this.status==STATUS_DONE) {
//                break;
//            }
//        }
//    }
//
//    @Override
//    public void onSuccess(Object object) {
//        l.info("onSuccess");
//        this.status = STATUS_DONE;
//        if (object instanceof UserFb) {
//            UserFb userFb = (UserFb) object;
//            l.info("user name is " + userFb.getName());
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
//        this.status = STATUS_ERROR;
//        l.info("onError");
//        l.log(Level.SEVERE, "", t);
//    }
//}
