package br.com.wame.manager;

public class SessionManager {
    private static String token;

    public static void setToken(String t) { token = t; }
    public static String getToken() { return token; }
    public static void clear() { token = null; }
}
