package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.List;

import com.intelerad.datamodels.reportworkflow.portal.NotificationCounts;

@SuppressWarnings("serial")
public class NotificationResults implements Serializable
{
    private List<GwtNotification> mNotifications;
    private NotificationCounts mCounts;
    
    public NotificationResults()
    {   
    }
    
    public List<GwtNotification> getNotifications()
    {
        return mNotifications;
    }

    public void setNotifications( List<GwtNotification> notifications )
    {
        mNotifications = notifications;
    }
    
    public NotificationCounts getNotificationCounts()
    {
        return mCounts;
    }

    public void setNotificationCounts( NotificationCounts counts )
    {
        mCounts = counts;
    }
}
