package com.intelerad.web.portal.gwt.model;

@SuppressWarnings("serial")
public class PortalException extends Exception
{
    public PortalException()
    {
        super();
    }
    
    public PortalException( String message, Throwable cause )
    {
        super( message, cause );
    }
    
    public PortalException( String message )
    {
        super( message );
    }
    
    public PortalException( Throwable cause )
    {
        super( cause );
    }
}
