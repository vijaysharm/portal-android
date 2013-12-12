package com.intelerad.web.lib.gwt.model.hl7;

import java.io.Serializable;

public class GwtReport implements Serializable
{
    private String mObservationValue;
    
    public String getObservationValue()
    {
        return mObservationValue;
    }
    
    public void setObservationValue( String observationValue )
    {
        mObservationValue = observationValue;
    }
}
