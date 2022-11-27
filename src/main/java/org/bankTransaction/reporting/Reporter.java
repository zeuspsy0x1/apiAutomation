package org.bankTransaction.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that helps to the logging function based on the log4j properties file.
 * It has methods that receive the text strings to log and sends them to the console with its important information.
 */
public class Reporter {

    public Reporter() {
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(Reporter.class);
    }

    public static void info(String text) {
        getLogger().info(text);
    }

    public static void error(String text) {
        getLogger().error(text);
    }
}
