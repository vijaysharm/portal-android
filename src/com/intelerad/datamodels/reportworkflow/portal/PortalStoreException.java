package com.intelerad.datamodels.reportworkflow.portal;

/**
 * Exception for implementations of PortalStore to throw when there is 
 * a problem accessing or saving information.
 */
@SuppressWarnings("serial")
public class PortalStoreException extends Exception
{
    public PortalStoreException()
    {
        super();
    }

    public PortalStoreException( String message )
    {
        super( message );
    }

    public PortalStoreException( Throwable cause )
    {
        super( cause );
    }

    public PortalStoreException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
