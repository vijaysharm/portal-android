package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtPortalProfile implements Serializable
{
    private GwtAccountPreferences accountPrefs;
    private GwtNotificationPreferences notifyPrefs;
    
    public GwtPortalProfile()
    {
        // TODO Auto-generated constructor stub
    }

    public GwtAccountPreferences getAccountPrefs()
    {
        return accountPrefs;
    }

    public void setAccountPrefs( GwtAccountPreferences accountPrefs )
    {
        this.accountPrefs = accountPrefs;
    }

    public GwtNotificationPreferences getNotifyPrefs()
    {
        return notifyPrefs;
    }

    public void setNotifyPrefs( GwtNotificationPreferences notifyPrefs )
    {
        this.notifyPrefs = notifyPrefs;
    }    
}
