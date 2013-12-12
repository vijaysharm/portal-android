package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;

import com.intelerad.web.lib.gwt.model.AudioOption;

@SuppressWarnings("serial")
public class PortalOptions implements Serializable
{
    private boolean mPortalEnabled; 
    private boolean mPortalAccessible;
    
    private boolean mNonKeyImagesAccessible;
    private boolean mImageNotificationEnabled;
    private boolean mImpressionNotificationEnabled;
    private boolean mPrelimReportsAccessible;
    private AudioOption mAudioAccessible;
    
    private boolean mSearchByPatientNameAllowed;
    private boolean mViewPatientNameAllowed;
    private boolean mViewImageAllowed;
    private boolean mViewReportAllowed;
    private boolean mViewImpressionAllowed;
    private boolean mOnReferEnabled;
    
    private boolean mGlobalWebAnalyticsConfigured;
    private boolean mWebAnalyticsConfigured;
    private boolean mAddImpressionAllowed;

    public boolean isPortalEnabled()
    {
        return mPortalEnabled;
    }
    
    public void setPortalEnabled( boolean portalEnabled )
    {
        mPortalEnabled = portalEnabled;
    }
    
    public void setPortalAccessible( boolean hasPrivilege )
    {
        mPortalAccessible = hasPrivilege;
    }
    
    public boolean isPortalAccessible()
    {
        return mPortalAccessible;
    }
    
    public boolean isNonKeyImagesAccessible()
    {
        return mNonKeyImagesAccessible;
    }
    
    public void setNonKeyImagesAccessible( boolean nonKeyImagesAccessible )
    {
        mNonKeyImagesAccessible = nonKeyImagesAccessible;
    }
    
    public boolean isImageNotificationEnabled()
    {
        return mImageNotificationEnabled;
    }
    
    public void setImageNotificationEnabled( boolean imageNotificationEnabled )
    {
        mImageNotificationEnabled = imageNotificationEnabled;
    }
    
    public boolean isImpressionNotificationEnabled()
    {
        return mImpressionNotificationEnabled;
    }
    
    public void setImpressionNotificationEnabled( boolean impressionNotificationEnabled )
    {
        mImpressionNotificationEnabled = impressionNotificationEnabled;
    }
    
    public boolean isPrelimReportsAccessible()
    {
        return mPrelimReportsAccessible;
    }
    
    public void setPrelimReportsAccessible( boolean prelimReportsAccessible )
    {
        mPrelimReportsAccessible = prelimReportsAccessible;
    }
    
    public AudioOption getAudioAccessible()
    {
        return mAudioAccessible;
    }
    
    public void setAudioAccessible( AudioOption audioAccessible )
    {
        mAudioAccessible = audioAccessible;
    }

    public boolean isSearchByPatientNameAllowed()
    {
        return mSearchByPatientNameAllowed;
    }

    public void setSearchByPatientNameAllowed( boolean searchByPatientNameAllowed )
    {
        mSearchByPatientNameAllowed = searchByPatientNameAllowed;
    }

    public boolean isViewPatientNameAllowed()
    {
        return mViewPatientNameAllowed;
    }

    public void setViewPatientNameAllowed( boolean viewPatientNameAllowed )
    {
        mViewPatientNameAllowed = viewPatientNameAllowed;
    }

    public boolean isViewImageAllowed()
    {
        return mViewImageAllowed;
    }

    public void setViewImageAllowed( boolean viewImageAllowed )
    {
        mViewImageAllowed = viewImageAllowed;
    }

    public boolean isViewReportAllowed()
    {
        return mViewReportAllowed;
    }

    public void setViewReportAllowed( boolean viewReportAllowed )
    {
        mViewReportAllowed = viewReportAllowed;
    }

    public boolean isViewImpressionAllowed()
    {
        return mViewImpressionAllowed;
    }

    public void setViewImpressionAllowed( boolean viewImpressionAllowed )
    {
        mViewImpressionAllowed = viewImpressionAllowed;
    }
    
    public boolean isOnReferEnabled()
    {
        return mOnReferEnabled;
    }

    public void setOnReferEnabled( boolean onReferEnabled )
    {
    	mOnReferEnabled = onReferEnabled;
    }
    
    public boolean isGlobalWebAnalyticsConfigured()
    {
        return mGlobalWebAnalyticsConfigured;
    }
    
    public void setGlobalWebAnalyticsConfigured( boolean globalWebAnalyticsConfigured )
    {
        mGlobalWebAnalyticsConfigured = globalWebAnalyticsConfigured;
    }
    
    public boolean isWebAnalyticsConfigured()
    {
        return mWebAnalyticsConfigured;
    }
    
    public void setWebAnalyticsConfigured( boolean webAnalyticsConfigured )
    {
        mWebAnalyticsConfigured = webAnalyticsConfigured;
    }

    public boolean isAddImpressionAllowed()
    {
        return mAddImpressionAllowed;
    }
    
    public void setAddImpressionAllowed( boolean addImpressionAllowed )
    {
        mAddImpressionAllowed = addImpressionAllowed;
    }
}
