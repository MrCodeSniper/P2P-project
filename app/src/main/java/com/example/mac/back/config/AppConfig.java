package com.example.mac.back.config;

public class AppConfig{

    public static final String HOST="192.168.1.100";
    public static final String PORT=":8888";
    public static final String PROTROL="http://";
    public static final String BASEURL=PROTROL+HOST+PORT+"/P2PInvest/";
    public static final String UserExisit=BASEURL+"UserExisit";
    public static final String PRODUCT_LIST=BASEURL+"ProductList";
    public static final String SEND_SMS=BASEURL+"SendEmail";
    public static final String NEW_USER_PARDEN="https://active.zcmlc.com/novice_changed_new?app=android&mobileversion=V2.7.2&platformname=androidtxyyb";
    public static final String PARDEN=BASEURL+"parden.html";
    public static final String SAFE=BASEURL+"safe_proguard.html";
    public static final String REGISTER=BASEURL+"Register";
    public static final String LOGIN=BASEURL+"login";
    public static final String UPLOAD_LOGS=BASEURL+"CrashUpload";


    public static final String TAG_LOGINED="登陆了";
    public static final String TAG_UNLOGINED="注销了";

}