package main;

import com.gec.FbScraper;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Eric on 25/01/15.
 */
public class Main {
    public static void main(String[] args) {
        FbScraper fbScraper = new FbScraper();
        String accessToken = "CAACEdEose0cBAHVQvvx6mmYnsO959ouv53V7Mkkg2CXWIpyIOKZCmZAxDZCJRnMvVwxfv6Uj8ZArwaeoBJjtw1P2kqlix1CcZBnyQIGQBdEiTl1412zCk6HlYAKZCsrkgZCYqC6YYwymRkRpTeAaX0uYFiv7akbsTUdO5GO61JHIi16amoPv9E3gEx39hrHrUiEwjGfXSQWHM68eiK9P5esrm4sBiZApzNoZD";
        String userId = "me";
        String callBackUrl = "http://waach.local/callback.php";
        // fbScraper.getUser(accessToken, userId, callBackUrl);
        fbScraper.getCurrentUser(accessToken, callBackUrl);
    }
}
