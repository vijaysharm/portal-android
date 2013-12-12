package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;
import java.util.List;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;

@SuppressWarnings("serial")
public class GwtBasicUser implements Serializable
{
    protected String mUsername;
    protected GwtPersonName mName;
    protected String mRisId;
    protected String mPhoneNumber;
    protected String mEmailAddress;
    protected List<String> mRoleNames;
    
    public GwtBasicUser()
    {
        mName = new GwtPersonName();
    }
    
    public GwtBasicUser( String username, String lastName, String firstName,
                         String middleName, String risId, String phoneNumber )
    {
        mUsername = username;
        mName = new GwtPersonName( lastName, firstName, middleName );
        mRisId = risId;
        mPhoneNumber = phoneNumber;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        
        GwtBasicUser user = (GwtBasicUser) obj;
        return GwtStringUtils.equals( mRisId, user.getRisId() );
    }
    
    public boolean hasName()
    {
        return mName.hasName();
    }
    
    public String getSortableFullName( boolean includeMiddle )
    {
        return mName.getSortableFullName( includeMiddle );
    }
    
    /**
     * @return a String representation of the user's name in the format:<br/>
     * "lastName, firstName (username)" if "lastName, firstName" is not blank, or<br/>
     * "username" otherwise.
     */
    public String getSortableUiString()
    {
        String fullName = mName.getSortableFullName( false );
        
        if ( GwtStringUtils.isBlank( fullName ) )
            return GwtStringUtils.isBlank( mUsername ) ? "" : mUsername;
        else
            return fullName + ( GwtStringUtils.isBlank( mUsername ) ? "" : " (" + mUsername + ")" );
    }
    
    public String getInformalName( boolean includeMiddle )
    {
        return mName.getInformalName( includeMiddle );
    }
    
    public GwtPersonName getName()
    {
        return mName;
    }
    
    public String getUsername()
    {
        return mUsername;
    }

    public void setUsername( String username )
    {
        mUsername = username;
    }

    public String getFirstName()
    {
        return mName.getFirstName();
    }

    public void setFirstName( String firstName )
    {
        mName.setFirstName( firstName );
    }

    public String getMiddleName()
    {
        return mName.getMiddleName();
    }

    public void setMiddleName( String middleName )
    {
        mName.setMiddleName( middleName );
    }

    public String getLastName()
    {
        return mName.getLastName();
    }

    public void setLastName( String lastName )
    {
        mName.setLastName( lastName );
    }

    public String getRisId()
    {
        return mRisId;
    }

    public void setRisId( String risId )
    {
        mRisId = risId;
    }

    public String getPhoneNumber()
    {
        return mPhoneNumber;
    }

    public void setPhoneNumber( String phoneNumber )
    {
        mPhoneNumber = phoneNumber;
    }
    
    public String getPrintableName()
    {
        return mName.getPrintableName();
    }

    public String getEmailAddress()
    {
        return mEmailAddress;
    }

    public void setEmailAddress( String emailAddress )
    {
        mEmailAddress = emailAddress;
    }

    public List<String> getRoleNames()
    {
        return mRoleNames;
    }

    public void setRoleNames( List<String> roleNames )
    {
        mRoleNames = roleNames;
    }
    
}
