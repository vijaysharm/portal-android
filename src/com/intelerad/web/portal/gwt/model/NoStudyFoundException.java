package com.intelerad.web.portal.gwt.model;

public class NoStudyFoundException extends PortalException
{
    private static final long serialVersionUID = 967099168040945407L;

    public NoStudyFoundException()
    {
        super();
    }

    public NoStudyFoundException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public NoStudyFoundException( String message )
    {
        super( message );
    }

    public NoStudyFoundException( Throwable cause )
    {
        super( cause );
    }
}
