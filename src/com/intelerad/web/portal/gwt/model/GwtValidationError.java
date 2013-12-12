package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class GwtValidationError implements Serializable
{
    private String mMessage;
    private List<String> mFieldNames;
    
    public GwtValidationError()
    {    
    }
    
    public GwtValidationError( String message, String... fieldNames )
    {
        super();
        mMessage = message;
        mFieldNames = new ArrayList<String>( Arrays.asList( fieldNames ) );
    }

    public String getMessage()
    {
        return mMessage;
    }
    
    public void setMessage( String message )
    {
        mMessage = message;
    }
    
    public List<String> getFieldNames()
    {
        return mFieldNames;
    }

    public void setFieldNames( List<String> fieldNames )
    {
        mFieldNames = fieldNames;
    }
}
