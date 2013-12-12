package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;

public class GwtOverlayData implements Serializable
{
    private String mPatientName;
    private String mDescription;
    private String mStudyDate;
    private String mPatientBirthdateSexAge;
    
    public GwtOverlayData()
    {
    }
    
    public String getPatientName()
    {
        return mPatientName;
    }

    public void setPatientName( String patientName )
    {
        mPatientName = patientName;
    }

    public void setStudyDescription( String description )
    {
        mDescription = description;
    }
    
    public String getDescription()
    {
        return mDescription;
    }

    public void setStudyDate( String formattedStudyDate )
    {
        mStudyDate = formattedStudyDate;
    }
    
    /**
     * @return an already formatted study date ready for display
     */
    public String getStudyDate()
    {
        return mStudyDate;
    }

    public void setPatientBirthdateSexAge( String formatPatientBirthdateSexAge )
    {
        mPatientBirthdateSexAge = formatPatientBirthdateSexAge;
    }

    /**
     * @return an already formatted patient birth date, sex and age ready for display
     */
    public String getPatientBirthdateSexAge()
    {
        return mPatientBirthdateSexAge;
    }
}
