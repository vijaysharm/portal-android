package com.intelerad.android.utils;

import java.util.logging.Logger;

public class Logs
{
    public static Logger create( Class<?> logClass )
    {
        return Logger.getLogger( logClass.getSimpleName() );
    }
}
