package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.intelerad.web.lib.gwt.model.hl7.GwtBasicOrder;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

@SuppressWarnings("serial")
public class GwtPatientActivity implements Serializable
{
    private GwtPatient mPatient;
    private Map<String, ImpressionStatus> mAccessionToImpressionMap;
   
    public GwtPatientActivity()
    {
    }

    public GwtPatientActivity( GwtPatient patient )
    {
        mPatient = patient;
    }

    public GwtPatient getPatient()
    {
        return mPatient;
    }

    public void setPatient( GwtPatient patient )
    {
        mPatient = patient;
    }
    
    public Date getLatestOrderDate()
    {
        Date date = null;
        for ( GwtBasicOrder order : getPatient().getOrders() )
        {
            if ( date == null || date.getTime() > order.getStudyDate().getTime() )
            {
                date = order.getStudyDate();
            }
        }
        return date;
    }
    
    public GwtOrder getOrder( String accessionNumber )
    {
        for( GwtBasicOrder o : mPatient.getOrders() )
        {
            if ( o.getAccessionNumber().equals( accessionNumber ) )
            {
                return (GwtOrder)o;
            }
        }
        return null;
    }

    public Map<String, ImpressionStatus> getAccessionToImpressionMap()
    {
        return mAccessionToImpressionMap;
    }

    public void setAccessionToImpressionMap( Map<String, ImpressionStatus> accessionToImpressionMap )
    {
        mAccessionToImpressionMap = accessionToImpressionMap;
    }    
}
