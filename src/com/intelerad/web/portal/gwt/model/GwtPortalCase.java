package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;
import com.intelerad.web.lib.gwt.model.hl7.GwtReport;

/**
 * The HL7 and DICOM information related to a particular accession number.
 * 
 * This class supports studies with multiple procedures.
 */
@SuppressWarnings("serial")
public class GwtPortalCase implements Serializable
{
    private GwtOrder mOrder;
    private GwtReport mReport;
    private List<GwtDictation> mDictations;
    private boolean mDisplayDiagnosticWarning;
    private List<String> mCannedImpressions;
    private int mPatientOrderCount;

    public GwtPortalCase()
    {
    }
    
    public GwtOrder getOrder()
    {
        return mOrder;
    }
    
    public void setOrder( GwtOrder order )
    {
        mOrder = order;
    }
    
    public GwtReport getReport()
    {
        return mReport;
    }
    
    public void setReport( GwtReport report )
    {
        mReport = report;
    }
    
    public boolean hasReport()
    {
        return mReport != null && !GwtStringUtils.isBlank( mReport.getObservationValue() );
    }

    public List<GwtDictation> getDictations()
    {
        return mDictations;
    }

    public void setDictations( List<GwtDictation> dictations )
    {
        mDictations = dictations != null ? new ArrayList<GwtDictation>( dictations ) : 
                                           new ArrayList<GwtDictation>();
    }
    
    public String getAccessionNumber()
    {
        return mOrder != null ? mOrder.getAccessionNumber() : null;
    }
    
    public boolean isDiagnosticWarningDisplayed()
    {
        return mDisplayDiagnosticWarning;
    }

    public void setDiagnosticWarningDisplayed( boolean displayDiagnosticWarning )
    {
        mDisplayDiagnosticWarning = displayDiagnosticWarning;
    }

    public List<String> getCannedImpressions()
    {
        return mCannedImpressions;
    }

    public void setCannedImpressions( List<String> cannedImpression )
    {
        mCannedImpressions = cannedImpression;
    }
    
    public int getPatientOrderCount()
    {
        return mPatientOrderCount;
    }
    
    public void setPatientOrderCount( int patientOrderCount )
    {
        mPatientOrderCount = patientOrderCount;
    }
}
