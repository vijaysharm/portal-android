package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class GwtCriticalResult implements Serializable
{
    protected GwtBasicUser mCompletedBy;
    protected Date mCompletedTime;
    protected Date mEnteredTime;
    protected boolean mPending;
    protected boolean mAcknowledged;
    protected String mFinding;
    protected GwtPriorityLevel mLevel; 
    protected String mCommunicatedTo;
    protected String mComment;
    protected String mAccessionNumber;
    protected Date mLastModifiedTime;
    
    public GwtCriticalResult()
    {
    }
    
    public GwtCriticalResult( Date enteredTime, boolean pending )
    {
        mEnteredTime = enteredTime;
        mPending = pending;
    }
    
    public Date getEnteredTime()
    {
        return mEnteredTime;
    }
    
    public void setEnteredTime( Date enteredTime )
    {
        mEnteredTime = enteredTime;
    }
    
    public boolean isPending()
    {
        return mPending;
    }
    
    public void setPending( boolean pending )
    {
        mPending = pending;
    }

    public GwtBasicUser getCompletedBy()
    {
        return mCompletedBy;
    }

    public void setCompletedBy( GwtBasicUser completedBy )
    {
        mCompletedBy = completedBy;
    }

    public Date getCompletedTime()
    {
        return mCompletedTime;
    }

    public void setCompletedTime( Date completedTime )
    {
        mCompletedTime = completedTime;
    }

    public boolean isAcknowledged()
    {
        return mAcknowledged;
    }

    public void setAcknowledged( boolean acknowledged )
    {
        mAcknowledged = acknowledged;
    }

    public String getFinding()
    {
        return mFinding;
    }

    public void setFinding( String finding )
    {
        mFinding = finding;
    }

    public GwtPriorityLevel getLevel()
    {
        return mLevel;
    }

    public void setLevel( GwtPriorityLevel level )
    {
        this.mLevel = level;
    }

    public String getCommunicatedTo()
    {
        return mCommunicatedTo;
    }

    public void setCommunicatedTo( String communicatedTo )
    {
        this.mCommunicatedTo = communicatedTo;
    }

    public String getComment()
    {
        return mComment;
    }

    public void setComment( String comment )
    {
        this.mComment = comment;
    }

    public String getAccessionNumber()
    {
        return mAccessionNumber;
    }

    public void setAccessionNumber( String accessionNumber )
    {
        mAccessionNumber = accessionNumber;
    }

    public Date getLastModifiedTime()
    {
        return mLastModifiedTime;
    }

    public void setLastModifiedTime( Date lastModifiedTime )
    {
        mLastModifiedTime = lastModifiedTime;
    }

}
