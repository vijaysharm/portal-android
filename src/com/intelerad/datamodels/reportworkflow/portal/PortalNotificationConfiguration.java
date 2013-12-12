package com.intelerad.datamodels.reportworkflow.portal;

public interface PortalNotificationConfiguration
{
    public static final String KEY_PORTAL_NOTIFY_PREF_FINAL_AVAIL      = "notifyFinalReportAvailable";
    public static final String KEY_PORTAL_NOTIFY_PREF_CRITICAL_RESULT  = "notifyCriticalResult";
    public static final String KEY_PORTAL_NOTIFY_PREF_IMAGES_AVAIL     = "notifyImagesAvailable";
    // Feature temporarily hidden, see BZ30990 & BZ32240
    //private static final String KEY_PORTAL_EMAIL_PREF_DICT_AVAIL        = "notifyDicationAvailable";
    public static final String KEY_PORTAL_NOTIFY_PREF_IMPRESSION_ADDED = "notifyImpressionAdded";
    public static final String KEY_PORTAL_NOTIFY_PREF_PRELIM_AVAIL     = "notifyPrelimReportAvailable";
    public static final String KEY_PORTAL_DELIVERY_EMAIL               = "deliveryEmailEnabled";
    public static final String KEY_ENABLE_IMAGE_NOTIFICATON            = "enableImageNotification";
    public static final String KEY_ENABLE_IMPRESSION_NOTIFICATION      = "enableImpressionNotification";
    public static final String KEY_PRELIM_REPORT_ACCESSIBLE            = "prelimReportAccessible";
    public static final String KEY_AUDIO_ACCESSIBLE                   = "audioAccessible";
     
    public boolean isImageNotificationEnabled();
        
    public boolean isImpressionNotificationEnabled();
   
    public boolean isPrelimReportsAccessible();
    
    public boolean getFinalReportAvailableDefault();
    
    public boolean getCriticalResultDefault();
    
    public boolean getDictationAvailableDefault();
    
    public boolean getImagesAvailableDefault();
    
    public boolean getImpressionAddedDefault();
    
    public boolean getPrelimAvailableDefault();

    public boolean getDeliveryEmailEnabledDefault();
    
    public String getAudioAccessible();
}
