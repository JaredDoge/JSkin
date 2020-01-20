package com.insect.jskin.library.util;

import android.util.Log;

import com.insect.jskin.library.SkinPlugin;

public class JSkinLog {

    private static final boolean DEBUG;

    private static final String TAG="JSkin";

    static {
        DEBUG = SkinPlugin.getInstance().isDebugLog();
    }

    public static void i( String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e( String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }
}
