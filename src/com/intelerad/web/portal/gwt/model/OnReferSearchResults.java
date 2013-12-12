package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OnReferSearchResults implements Serializable
{
    private List<GwtPortalUser> mUsers;
    private int mTotalResultCount;
    
    public OnReferSearchResults() 
    {
        mUsers = new ArrayList<GwtPortalUser>();
    }
    
    public List<GwtPortalUser> getUsers()
    {
        return mUsers;
    }

    public void setUsers( List<GwtPortalUser> users )
    {
        mUsers = users;
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
