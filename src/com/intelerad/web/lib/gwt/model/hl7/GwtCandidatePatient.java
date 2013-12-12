package com.intelerad.web.lib.gwt.model.hl7;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtCandidatePatient implements Serializable
{
    private double mConfidenceLevel;
    private GwtPatient mPatient;

    public GwtCandidatePatient()
    {
    }

    public double getConfidenceLevel()
    {
        return mConfidenceLevel;
    }

    public void setConfidenceLevel( double confidenceLevel )
    {
        mConfidenceLevel = confidenceLevel;
    }

    public GwtPatient getPatient()
    {
        return mPatient;
    }

    public void setPatient( GwtPatient patient )
    {
        mPatient = patient;
    }
}
