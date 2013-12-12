package com.intelerad.datamodels.reportworkflow.portal;

import java.io.Serializable;

/** Represents a single row in the OrderReferrerAccess table. */
public class OrderReferrerAccess implements Serializable
{
    private String mAccessionNumber;
    private int mUserId;
    private boolean mRead;
    
    public OrderReferrerAccess()
    {
    }
    
    public OrderReferrerAccess( String accessionNumber, int userId, boolean read )
    {
        mAccessionNumber = accessionNumber;
        mUserId = userId;
        mRead = read;
    }
    
    public String getAccessionNumber()
    {
        return mAccessionNumber;
    }

    public void setAccessionNumber( String accessionNumber )
    {
        mAccessionNumber = accessionNumber;
    }

    public void setUserId( int userId )
    {
        mUserId = userId;
    }

    public int getUserId()
    {
        return mUserId;
    }
    
    public void setRead( boolean read )
    {
        mRead = read;
    }

    public boolean isRead()
    {
        return mRead;
    }

    /**
     * <b>WARNING</b>: does not take mRead into consideration.
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( mAccessionNumber == null ) ? 0
                                                                : mAccessionNumber.hashCode() );
        result = prime * result + mUserId;
        return result;
    }

    /**
     * <b>WARNING</b>: does not take mRead into consideration.
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        OrderReferrerAccess other = (OrderReferrerAccess) obj;
        if ( mAccessionNumber == null )
        {
            if ( other.mAccessionNumber != null )
                return false;
        }
        else if ( !mAccessionNumber.equals( other.mAccessionNumber ) )
            return false;
        if ( mUserId != other.mUserId )
            return false;
        return true;
    }


}
