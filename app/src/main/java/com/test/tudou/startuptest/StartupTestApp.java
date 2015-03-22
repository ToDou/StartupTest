package com.test.tudou.startuptest;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by tudou on 15-3-20.
 */
public class StartupTestApp extends Application{

    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }

    public static Context getsAppContext() {
        return sAppContext;
    }

    public static Resources getAppResources() {
        return sAppContext.getResources();
    }
}
