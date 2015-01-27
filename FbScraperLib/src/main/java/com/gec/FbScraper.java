package com.gec;

import com.gec.entities.UserFb;
import com.ning.http.client.Response;
import com.restfb.*;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.Page;
import com.restfb.types.User;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Eric on 24/01/15.
 */
public class FbScraper implements HttpRequester.Callback {
    private final String FB_GRAPH_BASE = "https://graph.facebook.com";
    private final String FB_ACCESS_TOKEN = "access_token";
    private final String FB_ME = "me";

    private String callbackUrl;

    private Logger l = Logger.getLogger(FbScraper.class.getSimpleName());

    public FbScraper() {
    }

    public void getUser(String accessToken, String userId, final String callbackUrl) {
        this.callbackUrl = callbackUrl;

        String finalUrl = FB_GRAPH_BASE+"/"+userId+"?"+FB_ACCESS_TOKEN+"="+accessToken;
        System.out.println("final URL is "+finalUrl);
        HttpRequester httpRequester = new HttpRequester(this);
        httpRequester.get(finalUrl);
    }

    public void getCurrentUser(String accessToken, final String callbackUrl) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        // User user = facebookClient.fetchObject(FB_ME, User.class, Parameter.with("fields", "friends"));
        // l.info("User name: " + user.getName());
        // JsonObject me = facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", "name, age_range, friends"));
        UserFb me = facebookClient.fetchObject("me", UserFb.class, Parameter.with("fields", "name, age_range"));
        l.info("Your name is "+me.getName());
        l.info("Your age range is "+me.getAgeRange().getMin()+" - "+me.getAgeRange().getMax());

        // l.info(me.toString(3));
//        JsonMapper jsonMapper = new DefaultJsonMapper();
//        User userMe = jsonMapper.toJavaObject(me.toString(), User.class);
//        l.info("Your name is "+userMe.getName());

//        JsonArray friends = me.getJsonObject("friends").getJsonArray("data");
//        for (int i=0;i<friends.length();i++) {
//            l.info(friends.getJsonObject(i).toString(3));
//        }
    }

    @Override
    public void onSuccess(Response response) {
        System.out.println("onSuccess");
        HttpRequester httpRequester = new HttpRequester(null);
        try {
            System.out.println("response body is "+response.getResponseBody());
            httpRequester.post(callbackUrl, response.getResponseBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
