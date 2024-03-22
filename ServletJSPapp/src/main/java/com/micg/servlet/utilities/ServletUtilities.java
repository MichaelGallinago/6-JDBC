package com.micg.servlet.utilities;

public class ServletUtilities {
    public static String makeRedirectUrl(String currentURL, String redirectPath) {
        return currentURL.substring(0, currentURL.lastIndexOf("/")) + redirectPath;
    }
}
