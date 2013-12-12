package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtDictation implements Serializable
{ 
    private String mDictationUrl = null; 
    
    public GwtDictation()
    {
        mDictationUrl = "";
    }
    
    public GwtDictation( String dictationUrl )
    {
        mDictationUrl = dictationUrl;
    }
    
    public String getDictationUrl()
    {
        return mDictationUrl;
    }

    public void setDictationUrl( String dictationUrl )
    {
        mDictationUrl = dictationUrl;
    }
    
    @Override
    public String toString()
    {
        return "GwtDictation url: " + mDictationUrl;
    }
}
