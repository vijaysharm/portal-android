package com.intelerad.datamodels.reportworkflow.portal;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

@SuppressWarnings("serial")
public class OrderEvent implements Serializable
{
    private String mOrderEventId;
    private String mAccessionNumber;
    private EventType mEventType;
    private Date mEventTime;
    
    public void checkConsistent()
    {
        if ( mAccessionNumber == null )
            throw new IllegalStateException( "accessionNumber not set" );
        
        if ( mEventType == null )
            throw new IllegalStateException( "eventType not set" );
        
        if ( mEventTime == null )
            throw new IllegalStateException( "eventTime not set" );
    }
    
    public static Comparator<OrderEvent> createEventTimeComparator()
    {
        return new Comparator<OrderEvent>() {
            public int compare( OrderEvent oe1, OrderEvent oe2 ) {
                Date date1 = oe1.getEventTime();
                Date date2 = oe2.getEventTime();
                return date1.compareTo( date2 );
            } 
        };
    }
    
    public String getOrderEventId()
    {
        return mOrderEventId;
    }

    public void setOrderEventId( String orderEventId )
    {
        mOrderEventId = orderEventId;
    }

    public String getAccessionNumber()
    {
        return mAccessionNumber;
    }

    public void setAccessionNumber( String accessionNumber )
    {
        mAccessionNumber = accessionNumber;
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

    @Override
    public String toString()
    {
        return "OrderEvent ["
                + "orderEventId=" + mOrderEventId
                + ", accessionNumber=" + mAccessionNumber
                + ", eventType=" + mEventType
                + ", eventTime=" + mEventTime + "]";
    }
}