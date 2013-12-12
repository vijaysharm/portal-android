package com.intelerad.web.portal.gwt.model;

import java.util.Set;
import com.intelerad.web.lib.gwt.model.GwtBasicUser;

@SuppressWarnings("serial")
public class GwtPortalUser extends GwtBasicUser
{
    private Integer mUserId;
    private String mEmailAddress;
    private String mAddress;
    protected Set<String> mSpecialties;
    private boolean mIsInteleradUser;

    public GwtPortalUser()
    {
        super();
    }
    
    public GwtPortalUser(  String username, String lastName, String firstName,
                           String middleName, String risId, String phoneNumber,
                           String emailAddress )
    {
        super( username, lastName, firstName, middleName, risId, phoneNumber );
        
        mUserId = 0;
        mEmailAddress = emailAddress;
        mAddress = "";
        mSpecialties = null;
        mIsInteleradUser = false;
    }
    
    public Integer getUserId()
    {
        return mUserId;
    }

    public void setUserId( Integer userId )
    {
        mUserId = userId;
    }  

    public String getEmailAddress()
    {
        return mEmailAddress;
    }

    public void setEmailAddress( String emailAddress )
    {
        mEmailAddress = emailAddress;
    }    
    
    public String getAddress()
    {
        return mAddress;
    }

    public void setAddress( String address )
    {
        mAddress = address;
    }    
    
    public Set<String> getSpecialties()
    {
        return mSpecialties;
    }

    public void setSpecialties( Set<String> specialties )
    {
        mSpecialties = specialties;
    }
    
    public void setIsInteleradUser( boolean isInteleradUser )
    {
        mIsInteleradUser = isInteleradUser;
    }
    
    public boolean getIsInteleradUser()
    {
        return mIsInteleradUser;
    }
}
