package com.intelerad.web.portal.gwt.model;

import com.intelerad.datamodels.user.FailureType;

public class SessionException extends Exception
{
    private FailureType mFailureType;

    public SessionException()
    {    
    }
    
    public SessionException( FailureType failureType, String message )
    {
        super( message );
        mFailureType = failureType;
    }
    
    public String getMessage()
    {
        String realMessage = super.getMessage();
        if ( realMessage == null )
            return mFailureType.getDescription();
        else
            return mFailureType + ": " + realMessage;
    }

    public boolean isLoginFailure()
    {
        return FailureType.isLoginFailure( mFailureType );
    }

    public FailureType getFailureType()
    {
        return mFailureType;
    }  
}
