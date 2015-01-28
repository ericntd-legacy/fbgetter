package com.example;

import com.gec.FbScraper;
import com.gec.entities.UserFb;
import com.sun.javafx.tools.packager.Log;

import java.util.logging.Logger;

/**
 * Created by eric on 28/1/15.
 */
public class FbHelper implements FbScraper.Callback {
    private  FbScraper fbScraper;

    private Logger l = Logger.getLogger(FbScraper.class.getSimpleName());

    public  FbHelper() {
        this.fbScraper = new FbScraper(this);
    }

    @Override
    public void onSuccess(UserFb user) {
        l.info("onSuccess");
        if (user!=null) l.info("user name is "+user.getName());
    }

    public FbScraper getFbScraper() {
        return fbScraper;
    }

    public void setFbScraper(FbScraper fbScraper) {
        this.fbScraper = fbScraper;
    }
}
