package com.artesaniasclient.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class Util {

    public static final String adminMail = "cristhian.crypton@gmail.com";
    public static final String adminPassword = "1207334184.Qwerty";

    public static Bundle getBundleFusion(Bundle bundleOld, Bundle bundleNew) {
        if (bundleOld == null) return bundleNew;
        bundleOld.putAll(bundleNew);
        return bundleOld;
    }

    public static Properties getPropertiesJavaMail (){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    public static byte[] getBytesImageView(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String getMessageTask(Exception exception) {
        String message = null;
        if (exception != null) {
            message = exception.getMessage();
        }
        return message;
    }
}
