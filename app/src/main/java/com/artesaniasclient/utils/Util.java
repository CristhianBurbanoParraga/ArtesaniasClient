package com.artesaniasclient.utils;

import android.os.Bundle;

import java.util.Properties;

public class Util {

    public static final String adminMail = "cristhian.crypton@gmail.com";
    public static final String adminPassword = "1207334184.Qwerty";

    public static Bundle getBundleFusion(Bundle bundleOld, Bundle bundleNew) {
        if (bundleOld == null) return bundleNew;
        bundleNew.putAll(bundleOld);
        return bundleNew;
    }

    public static Properties getPropertiesJavaMail (){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

}
