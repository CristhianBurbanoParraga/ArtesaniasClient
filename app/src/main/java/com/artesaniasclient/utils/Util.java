package com.artesaniasclient.utils;

import android.os.Bundle;

public class Util {

    public static Bundle getBundleFusion(Bundle bundleOld, Bundle bundleNew) {
        if (bundleOld == null) return bundleNew;
        bundleNew.putAll(bundleOld);
        return bundleNew;
    }
}
