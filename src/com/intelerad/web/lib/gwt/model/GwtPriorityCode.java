package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtPriorityCode implements Serializable
{
    private int mIndex;
    private String mCode;
    private String mLabel;
    
    public GwtPriorityCode()
    {
    }

    public int getIndex()
    {
        return mIndex;
    }

    public void setIndex( int index )
    {
        mIndex = index;
    }

    public String getCode()
    {
        return mCode;
    }

    public void setCode( String code )
    {
        mCode = code;
    }

    public String getLabel()
    {
        return mLabel;
    }

    public void setLabel( String label )
    {
        mLabel = label;
    }
    
    public boolean isHighestPriority()
    {
        return mIndex == 0;
    }
}
