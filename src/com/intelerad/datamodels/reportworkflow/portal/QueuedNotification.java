package com.intelerad.datamodels.reportworkflow.portal;

import java.io.Serializable;

/**
 * A notification queued for delivery.
 */
public class QueuedNotification implements Serializable
{
    private String mDeliveryId;
    private OrderEvent mOrderEvent;
    private int mUserId;
    
    public QueuedNotification()
    {
    }
    
    public QueuedNotification( OrderEvent orderEvent )
    {
        mOrderEvent = orderEvent;
    }
    
    public String getDeliveryId()
    {
        return mDeliveryId;
    }

    public void setDeliveryId( String deliveryId )
    {
        mDeliveryId = deliveryId;
    }

    public OrderEvent getOrderEvent()
    {
        return mOrderEvent;
    }

    public void setOrderEvent( OrderEvent orderEvent )
    {
        mOrderEvent = orderEvent;
    }

    public void setUserId( int userId )
    {
        mUserId = userId;
    }

    public int getUserId()
    {
        return mUserId;
    }
}
