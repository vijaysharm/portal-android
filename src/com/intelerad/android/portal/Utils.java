package com.intelerad.android.portal;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.models.Session.SerializedForAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

public class Utils
{
    private static final String SESSION_PARCELABLE_KEY = SerializedForAndroid.class.getName();
    
    public static Intent convertToIntent( Bundle arguments )
    {
        Intent intent = new Intent();
        if ( arguments == null )
            return intent;

        intent.putExtras( arguments );
        return intent;    
    }
    
    public static Bundle convertToFragmentArgument( Intent intent )
    {
        Bundle arguments = new Bundle();
        if ( intent == null )
            return arguments;
        
        final Bundle extras = intent.getExtras();
        if ( extras != null )
            arguments.putAll( intent.getExtras() );

        return arguments;
    }

    public static void addAccession( Intent intent, String accession )
    {
        intent.putExtra( "OrderAccessionNumber", accession );
    }
    
    public static String extractAccession( Intent intent )
    {
        return intent.getStringExtra( "OrderAccessionNumber" );
    }
    
    public static void addPatientId( Intent intent, String accession )
    {
        intent.putExtra( "PatientId", accession );
    }
    
    public static String extractPatientId( Intent intent )
    {
        return intent.getStringExtra( "PatientId" );
    }
    
//    public static void addSessions( Intent intent, Session[] sessions )
//    {
//        SerializedForAndroid[] result = new SerializedForAndroid[ sessions.length ];
//        for ( int index = 0; index < sessions.length; index++ )
//            result[index] = new SerializedForAndroid( sessions[index] );
//        
//        intent.putExtra( SESSION_PARCELABLE_KEY, result );
//    }
//    
//    public static Session[] extractSessions( Intent intent )
//    {
//        Parcelable[] extras = intent.getParcelableArrayExtra( SESSION_PARCELABLE_KEY );
//        Session[] sessions = new Session[ extras.length ];
//        
//        int index = 0;
//        for ( Parcelable parcelable : extras )
//        {
//            if ( parcelable instanceof SerializedForAndroid )
//                sessions[ index++ ] = ( (SerializedForAndroid) parcelable ).getSession();
//        }
//        
//        return sessions;
//    }

    public static String extractSearch( Bundle bundle )
    {
        return bundle.getString( "SearchString" );
    }
    
    public static void addSearch( Bundle bundle, String search )
    {
        bundle.putString( "SearchString", search );
    }
}
