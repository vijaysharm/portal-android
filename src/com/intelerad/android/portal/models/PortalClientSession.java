package com.intelerad.android.portal.models;

public class PortalClientSession
{
    private final String mSpec;
    private final String mHost;
    private final String mUsername;
    private final String mPassword;
    
    public PortalClientSession( String spec, String host, String username, String password )
    {
        mSpec = spec;
        mHost = host;
        mUsername = username;
        mPassword = password;
    }
}
