package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.Date;

import com.intelerad.datamodels.reportworkflow.portal.EventType;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;

@SuppressWarnings("serial")
public class GwtNotification implements Serializable
{
    private GwtOrder mOrder;
    private EventType mEventType;
    private Date mEventTime;
    private boolean mViewed;
    
    public GwtOrder getOrder()
    {
        return mOrder;
    }
    
    public void setOrder( GwtOrder order )
    {
        mOrder = order;
    }
    
    public EventType getEventType()
    {
        return mEventType;
    }
    
    public void setEventType( EventType eventType )
    {
        mEventType = eventType;
    }
    
    public Date getEventTime()
    {
        return mEventTime;
    }
    
    public void setEventTime( Date eventTime )
    {
        mEventTime = eventTime;
    }

    public boolean isCriticalResult()
    {
        return mOrder.isCriticalResult();
    }
    
    public boolean isViewed()
    {
        return mViewed;
    }

    public void setViewed( boolean viewed )
    {
        mViewed = viewed;
    }
}
