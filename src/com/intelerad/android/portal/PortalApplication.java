package com.intelerad.android.portal;

import android.app.Application;
import android.content.Context;

public class PortalApplication extends Application
{
    private static Context CONTEXT;

    @Override
    public void onCreate()
    {
        super.onCreate();
        PortalApplication.CONTEXT = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return PortalApplication.CONTEXT;
    }
}
