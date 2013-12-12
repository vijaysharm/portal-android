package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A value class that represents a patient's age as a Dicom Age String (AS), a value of days, months or years.  (Generally, a number and date unit, ex
 * 5 days, 6 months, or 35 years).
 * <P><code>DateUtilities.calculateAge</code> returns an instance of the Age class calculated based
 * on hard-coded rules. <b>Warning:</b> this implements comparable but please see {@link #compareTo compareTo} for 
 * details of the surprising ordering behavior.  
 * @see DateUtilities
 */
@SuppressWarnings("serial")
public class GwtAge implements Comparable<GwtAge>, Serializable
{
    /**
     * This enum represents the unit of measurement for the {@link DicomAgeString}, one of
     * Day ("D"), Month ("M") or Year;
     */
    public enum GwtAgeUnit implements Serializable
    { 
        DAY("D"), 
        WEEK("W"),
        MONTH("M"), 
        YEAR("Y");
        
        private String mDisplayCode;
        
        GwtAgeUnit( String displayCode )
        {
            mDisplayCode = displayCode;
        }
        
        public String toString() 
        {
            return mDisplayCode;
        }
        
        public String getDisplayCode()
        {
            return mDisplayCode;
        } 
        
        private static Map<String,GwtAgeUnit> mTypeCodeMap;
        
        public static GwtAgeUnit getAgeUnit( String displayCode )
        {
            if ( mTypeCodeMap == null )
            {
                mTypeCodeMap = new HashMap<String,GwtAgeUnit>();
                for ( GwtAgeUnit ageUnit : GwtAgeUnit.values() )
                    mTypeCodeMap.put( ageUnit.getDisplayCode(), ageUnit );
            }
            return mTypeCodeMap.get( displayCode );
        }
    }
    
    private GwtAgeUnit mUnit;
    private long mAge;
    
    /**
     * Default constructor needed to keep GWT happy.
     */
    public GwtAge()
    {    
    }
    
    public GwtAge( long age, GwtAgeUnit unit ) 
    {
        mAge = age;
        mUnit = unit;
    }
    
    /**
     * <B>Warning, this works counter-intuitively</b>. An Age in days is always less than an Age months (regardless of the value), similarly, an Age in months
     * is always less than an Age is years.  If the AgeUnit of the objects are the same, then the value is compared.
     */
    @Override
    public int compareTo( GwtAge other )
    {
        if ( mUnit != other.mUnit )
            return mUnit.compareTo( other.mUnit );
        
        long ageDiff = mAge - other.mAge;
        if ( ageDiff == 0 )
            return 0;
        
        return ageDiff > 0 ? 1 : -1;
    }
    

    /** 
     * @return string composed of age number and the unit identifier, e.g. 5D, 3M, 2Y, etc... 
     * */
    @Override
    public String toString()
    {
        return Long.toString( mAge ) + mUnit;
    }

    public GwtAgeUnit getUnit()
    {
        return mUnit;
    }
    
    public long getAge()
    {
        return mAge;
    }

    @Override
    public int hashCode()
    {
        final long prime = 31;
        long result = 1;
        
        result = prime * result + mAge;
        result = prime * result + mUnit.hashCode();
        
        return (int)result;
    }
    
    /**
     * Two Age objects are equal when the number and the type of units are the same 
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        
        if ( obj == null )
            return false;
        
        if ( getClass() != obj.getClass() )
            return false;
        
        final GwtAge other = (GwtAge) obj;
        
        if ( mUnit != other.mUnit )
            return false;
        
        if ( mAge != other.mAge )
            return false;
        
        return true;
    }
}
