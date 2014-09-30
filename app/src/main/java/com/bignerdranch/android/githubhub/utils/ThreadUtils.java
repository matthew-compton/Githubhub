package com.bignerdranch.android.githubhub.utils;

import android.util.Log;

public class ThreadUtils {

    private static final String TAG = ThreadUtils.class.getSimpleName();

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
    }

}
