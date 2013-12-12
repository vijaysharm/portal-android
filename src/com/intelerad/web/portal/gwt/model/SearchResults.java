package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

@SuppressWarnings("serial")
public class SearchResults implements Serializable
{
    private List<GwtPatient> mPatients;
    private int mTotalResultCount;

    public SearchResults()
    {
        mPatients = new ArrayList<GwtPatient>();
    }
    
    public List<GwtPatient> getPatients()
    {
        return mPatients;
    }
    
    public void setPatients( List<GwtPatient> patients )
    {
        mPatients = patients;
    }
    
    public int getTotalResultCount()
    {
        return mTotalResultCount;
    }

    public void setTotalResultCount( int totalResultCount )
    {
        mTotalResultCount = totalResultCount;
    }
}
