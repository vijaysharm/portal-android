package com.intelerad.datamodels.user;

import java.util.Arrays;

// this class is used by GWT apps and must stay GWT compatible
public enum FailureType
{
    // The first string in each of these is the name; it is used as part of the
    // authentication protocol between XmlDataSerlvet and InteleViewer, so
    // *don't change them*.  The second string is a human-readable form that
    // should only be used in log files; they might disappear someday, but in
    // the meanwhile, feel free to modify them for readability.
    BAD_SESSION_ID( "bad_session_id", "Bad session ID" ),
    BAD_USERNAME( "bad_username", "Bad username" ),
    BAD_PASSWORD( "bad_password", "Bad password" ),
    LDAP_CREDENTIALS( "ldap_credentials", "LDAP credentials rejected (bad username or password)" ),
    LDAP_ERROR( "ldap_error", "Error connecting to LDAP server" ),
    ACCOUNT_DELETED( "account_deleted", "Account deleted" ),
    ACCOUNT_EXPIRED( "account_expired", "Expired user account" ),
    SESSION_EXPIRED ( "session_expired", "Session expired" ),
    USER_MISMATCH( "username_mismatch", "Username/session mismatch" ),
    NEW_USER( "new_account", "New user account" ),
    MAX_FAILURES( "max_failures", "Too many failed login attempts" ),
    LOCKED_USER ( "locked_account", "Locked user account" ),
    
    /** The user's password has expired *and* all grace logins have been used up */
    PASSWORD_EXPIRED( "password_expired", "Password expired" ),
    BAD_HOST( "bad_host", "Invalid client host" ),
    OTHER( "other", "Unknown failure" );
    
    private static final FailureType[] LOGIN_FAILURES = 
    {
        FailureType.BAD_USERNAME,
        FailureType.BAD_PASSWORD,
        FailureType.LDAP_CREDENTIALS,
        FailureType.LDAP_ERROR,
        FailureType.ACCOUNT_DELETED,
        FailureType.NEW_USER    ,
        FailureType.MAX_FAILURES,
        FailureType.LOCKED_USER ,
        FailureType.ACCOUNT_EXPIRED,
    };
    
    private String mDescription;
    private String mName;
    
    FailureType( String name, String description )
    {
        mName = name;
        mDescription = description;
    }
    
    public String toString()
    {
        return getName();
    }

    public String getDescription()
    {
       return mDescription;
    }
        
    public String getName()
    {
        return mName;
    }
    
    public static boolean isLoginFailure( FailureType ft )
    {
        return Arrays.asList( LOGIN_FAILURES ).contains( ft );
    }
    
    public static FailureType getFailureType( String name )
    { 
        for ( FailureType ft : FailureType.values() )
        {
            if ( ft.getName().equals( name ) )
            {
                return ft;
            }
        }
        return null;
    }                                                 
 }
