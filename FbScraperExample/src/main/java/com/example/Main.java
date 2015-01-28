package com.example;

import com.gec.FbScraper;
import com.gec.entities.UserFb;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Created by Eric on 25/01/15.
 */
public class Main {
    public static void main(String[] args) {
        Logger l = Logger.getLogger(FbScraper.class.getSimpleName());

        FbHelper fbHelper = new FbHelper();
        FbScraper fbScraper = fbHelper.getFbScraper();
        String accessToken = "CAACEdEose0cBANYnCHeqU2ayhdwFXWJWSxnouZBatli1ZAjQxfHZBLzJgkpeUBTIAep3ccF95kAKAtt8P81GO0UyLxAORrlIhyc5PbQZBD8WQr2RpWi7hxNpdzCPDeWU9HiMAaqrB2RfmYUNcCzdf6AZCE5InLMyYfqkI8eIZA2v5Na9sdno1hpDQEMO9JfQ1AGdnmhkt1LSih6X7u4SE4KZCAiI2hPv8IZD";
        String userId = "me";
        String callBackUrl = "http://waach.local/callback.php";

        fbScraper.getUser(accessToken, userId, callBackUrl);
    }
}
