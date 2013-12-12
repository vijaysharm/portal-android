package com.intelerad.web.portal.gwt.model;

import java.util.List;

@SuppressWarnings("serial")
public class PortalValidationException extends PortalException
{
    private List<GwtValidationError> mErrors;
    
    public PortalValidationException()
    {    
    }
    
    public PortalValidationException( List<GwtValidationError> errors )
    {
        super();
        mErrors = errors;
    }

    public List<GwtValidationError> getErrors()
    {
        return mErrors;
    }

    public void setErrors( List<GwtValidationError> errors )
    {
        mErrors = errors;
    }
}
