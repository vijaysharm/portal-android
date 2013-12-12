package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;

@SuppressWarnings("serial")
public class GwtAccountPreferences implements Serializable
{
    // non custom field names
    public static final String FIELD_PASSWORD1 = "Password1";
    public static final String FIELD_PASSWORD2 = "Password2";
    public static final String FIELD_CURRENT_PASSWORD = "CurrentPassword";
    public static final String FIELD_SESSION_TIMEOUT = "SessionTimeout";
    
    public static final int UNDEFINED_SESSION_TIMEOUT = -1;
    
    private int mSessionTimeoutInSecs;
    private String mCurrentPassword;
    private String mPassword1;
    private String mPassword2;
    private Map<String,String> mNameToValues = new HashMap<String,String>();
    
    public GwtAccountPreferences()
    {
    }

    public Set<String> getPropertyNames()
    {
        return mNameToValues.keySet();
    }
    
    public String getValue( String name )
    {
        return mNameToValues.get( name );
    }

    public void setValue( String name, String value )
    {
        mNameToValues.put( name, value );
    }
    
    public void removeName( String name )
    {
        mNameToValues.remove( name );
    }

    public int getSessionTimeout()
    {
        return mSessionTimeoutInSecs;
    }

    public void setSessionTimeout( int sessionTimeout )
    {
        mSessionTimeoutInSecs = sessionTimeout;
    }

    public boolean isCustomSessionTimeout()
    {
        return mSessionTimeoutInSecs != UNDEFINED_SESSION_TIMEOUT;
    }

    public String getPassword1()
    {
        return mPassword1;
    }

    public void setPassword1( String password1 )
    {
        mPassword1 = password1;
    }

    public String getPassword2()
    {
        return mPassword2;
    }

    public void setPassword2( String password2 )
    {
        mPassword2 = password2;
    }

    public String getCurrentPassword()
    {
        return mCurrentPassword;
    }

    public void setCurrentPassword( String currentPassword )
    {
        mCurrentPassword = currentPassword;
    }

    public boolean isChangePassword()
    {
        return !GwtStringUtils.isBlank( mPassword1 ) && 
               !GwtStringUtils.isBlank( mPassword2 ) &&
               !GwtStringUtils.isBlank( mCurrentPassword );
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( mNameToValues == null ) ? 0
                                                             : mNameToValues.hashCode() );
        result = prime * result + mSessionTimeoutInSecs;
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        GwtAccountPreferences other = (GwtAccountPreferences) obj;
        if ( mNameToValues == null )
        {
            if ( other.mNameToValues != null )
                return false;
        }
        else if ( !mNameToValues.equals( other.mNameToValues ) )
            return false;
        if ( mSessionTimeoutInSecs != other.mSessionTimeoutInSecs )
            return false;
        return true;
    }
}
