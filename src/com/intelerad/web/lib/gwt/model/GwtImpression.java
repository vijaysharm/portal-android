package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class GwtImpression implements Serializable
{
    private String mText;
    private Date mDate;
    private GwtBasicUser mAuthor;
    private boolean mDiscrepancy;

    public GwtImpression()
    {
        mText = "";
        mDate = new Date();
        mAuthor = null;
        mDiscrepancy = false;
    }
    
    public GwtImpression( String text, Date date, GwtBasicUser author, boolean discrepancy )
    {
        mText = text;
        mDate = date;
        mAuthor = author;
        mDiscrepancy = discrepancy;
    }
    
    public String getText()
    {
        return mText;
    }
    
    public void setText( String text )
    {
        mText = text;
    }
    
    public Date getDate()
    {
        return mDate;
    }
    
    public void setDate( Date date )
    {
        mDate = date;
    }

    public GwtBasicUser getAuthor()
    {
        return mAuthor;
    }

    public void setAuthor( GwtBasicUser author )
    {
        mAuthor = author;
    }

    public boolean isDiscrepancy()
    {
        return mDiscrepancy;
    }

    public void setDiscrepancy( boolean discrepancy )
    {
        mDiscrepancy = discrepancy;
    }
}
