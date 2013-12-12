package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtNotificationPreferences implements Serializable
{ 
    private boolean mFinalReportAvailNotify = false;
    private boolean mCriticalResultNotify = false;
    private boolean mImagesAvailNotify = false;
    private boolean mDictationAvailNotify = false;
    private boolean mImpressionAddedNotify = false;
    private boolean mTranscriptionAvailNotify = false;
    
    // delivery options
    private boolean mEmailDeliver = false;
    
    public GwtNotificationPreferences()
    {
    }
    
    public boolean isFinalReportAvailNotify()
    {
        return mFinalReportAvailNotify;
    }

    public void setFinalReportAvailNotify( boolean finalReportAvailNotify )
    {
        mFinalReportAvailNotify = finalReportAvailNotify;
    }

    public boolean isCriticalResultNotify()
    {
        return mCriticalResultNotify;
    }

    public void setCriticalResultNotify( boolean criticalResultNotify )
    {
        mCriticalResultNotify = criticalResultNotify;
    }

    public boolean isImagesAvailNotify()
    {
        return mImagesAvailNotify;
    }

    public void setImagesAvailNotify( boolean imagesAvailNotify )
    {
        mImagesAvailNotify = imagesAvailNotify;
    }

    public boolean isDictationAvailNotify()
    {
        return mDictationAvailNotify;
    }

    public void setDictationAvailNotify( boolean dictationAvailNotify )
    {
        mDictationAvailNotify = dictationAvailNotify;
    }

    public boolean isImpressionAddedNotify()
    {
        return mImpressionAddedNotify;
    }

    public void setImpressionAddedNotify( boolean impressionAddedNotify )
    {
        mImpressionAddedNotify = impressionAddedNotify;
    }

    public boolean isTranscriptionAvailNotify()
    {
        return mTranscriptionAvailNotify;
    }

    public void setTranscriptionAvailNotify( boolean transcriptionAvailNotify )
    {
        mTranscriptionAvailNotify = transcriptionAvailNotify;
    }
    
    public boolean isEmailDeliver()
    {
        return mEmailDeliver;
    }

    public void setEmailDeliver( boolean emailDeliver )
    {
        mEmailDeliver = emailDeliver;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( mCriticalResultNotify ? 1231
                                                         : 1237 );
        result = prime * result + ( mDictationAvailNotify ? 1231
                                                         : 1237 );
        result = prime * result + ( mEmailDeliver ? 1231
                                                 : 1237 );
        result = prime * result + ( mFinalReportAvailNotify ? 1231
                                                           : 1237 );
        result = prime * result + ( mImagesAvailNotify ? 1231
                                                      : 1237 );
        result = prime * result + ( mImpressionAddedNotify ? 1231
                                                          : 1237 );
        result = prime * result + ( mTranscriptionAvailNotify ? 1231
                                                             : 1237 );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        GwtNotificationPreferences other = (GwtNotificationPreferences) obj;
        if ( mCriticalResultNotify != other.mCriticalResultNotify )
            return false;
        if ( mDictationAvailNotify != other.mDictationAvailNotify )
            return false;
        if ( mEmailDeliver != other.mEmailDeliver )
            return false;
        if ( mFinalReportAvailNotify != other.mFinalReportAvailNotify )
            return false;
        if ( mImagesAvailNotify != other.mImagesAvailNotify )
            return false;
        if ( mImpressionAddedNotify != other.mImpressionAddedNotify )
            return false;
        if ( mTranscriptionAvailNotify != other.mTranscriptionAvailNotify )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "GwtNotificationPreferences [mCriticalResultNotify=" + mCriticalResultNotify
               + ", mDictationAvailNotify=" + mDictationAvailNotify + ", mEmailDeliver="
               + mEmailDeliver + ", mFinalReportAvailNotify=" + mFinalReportAvailNotify
               + ", mImagesAvailNotify=" + mImagesAvailNotify + ", mImpressionAddedNotify="
               + mImpressionAddedNotify + ", mTranscriptionAvailNotify="
               + mTranscriptionAvailNotify + "]";
    }
}
