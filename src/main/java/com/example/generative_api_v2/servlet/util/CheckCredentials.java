package com.example.generative_api_v2.servlet.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class CheckCredentials {
    public static boolean isLogined(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return session.getAttribute("isLogined") != null;
    }
    private CheckCredentials() {
    }
}

