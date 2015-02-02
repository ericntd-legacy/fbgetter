package com.gec.utils;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by eric on 29/1/15.
 */
public class Log {
    private Logger logger;

    public Log(String name) {
        logger = Logger.getLogger(name);
    }

    public void e(Throwable t) {
        if (this.logger!=null) this.logger.log(Level.SEVERE, "caught", t);
    }
    public void i(String logMsg) {
        if (this.logger!=null) this.logger.info(logMsg);
    }
    public void w(String logMsg) {
        if (this.logger!=null) this.logger.warning(logMsg);
    }
}
