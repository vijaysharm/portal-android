package com.intelerad.datamodels.reportworkflow.portal;

import java.io.Serializable;

// this class is used by GWT apps and must stay GWT compatible
@SuppressWarnings("serial")
public class NotificationCounts implements Serializable
{
    private int mTotalCount = 0;
    private int mUnviewedCount = 0;
    private int mCriticalCount = 0;
    private int mCriticalPendingCount = 0;
    
    public NotificationCounts()
    {   
    }
    
    public int getTotalCount()
    {
        return mTotalCount;
    }
    
    public void setTotalCount( int totalCount )
    {
        mTotalCount = totalCount;
    }

    public int getUnviewedCount()
    {
        return mUnviewedCount;
    }

    public void setUnviewedCount( int unviewedCount )
    {
        mUnviewedCount = unviewedCount;
    }
    
    public int getCriticalCount()
    {
        return mCriticalCount;
    }
    
    public void setCriticalCount( int criticalCount )
    {
        mCriticalCount = criticalCount;
    }
    
    public int getCriticalPendingCount()
    {
        return mCriticalPendingCount;
    }

    public void setCriticalPendingCount( int pendingCount )
    {
        mCriticalPendingCount = pendingCount;
    }

    @Override
    public String toString()
    {
        return "NotificationCounts [mCriticalCount=" + mCriticalCount + ", mCriticalPendingCount="
               + mCriticalPendingCount + ", mTotalCount=" + mTotalCount + ", mUnviewedCount="
               + mUnviewedCount + "]";
    }
}
