package com.intelerad.datamodels.reportworkflow.portal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Type of event used by the RP portal.
 */
// this class is used by GWT apps and must stay GWT compatible
public enum EventType implements Serializable
{
    IMAGES_AVAILABLE( "IMG" ),
    DICTATION_AVAILABLE( "DA" ),
    PRELIMINARY_REPORT_AVAILABLE( "PRA" ),
    PRELIMINARY_REPORT_MODIFIED( "PRM" ),
    FINAL_REPORT_AVAILABLE( "FRA" ),
    FINAL_REPORT_CORRECTED( "FRC" ),
    REPORT_DELETED( "RD" ), // TODO: remove once the two events bellow are supported by HL7GS
    PRELIMINARY_REPORT_DELETED( "PRD" ),
    FINAL_REPORT_DELETED( "FRD" ),
    SIGNATURE_REVOKED( "SR" ),
    ADDENDUM_DICTATED( "AD" ),
    ADDENDUM_TRANSCRIBED( "AT" ),
    ORDER_CANCELLED( "OC" ),
    IMPRESSON_ADDED( "IMP" ),
    CRITICAL_RESULT_ADDED( "CRA" ),
    CRITICAL_RESULT_MODIFIED( "CRM" ),
    REFERRING_RIS_ID_CHANGED( "RRC" ), 
    NOTIFICATION_READ( "NRD" );
    
    private String mTypeCode;
    
    private EventType( String typeCode )
    {
        mTypeCode = typeCode;
    }
    
    public String getTypeCode()
    {
        return mTypeCode;
    }
    
    private static Map<String,EventType> mTypeCodeMap = null;
    private static void ensureTypeCodeMap()
    {
        if ( mTypeCodeMap == null )
        {
            mTypeCodeMap = new HashMap<String,EventType>();
            for ( EventType eventType : EventType.values() )
                mTypeCodeMap.put( eventType.getTypeCode(), eventType );
        }
    }
    
    public static EventType getEventType( String typeCode )
    {
        ensureTypeCodeMap();
        return mTypeCodeMap.get( typeCode );
    }
    
    public static Set<String> getAllTypeCodes()
    {
        ensureTypeCodeMap();
        return mTypeCodeMap.keySet();
    }
}
