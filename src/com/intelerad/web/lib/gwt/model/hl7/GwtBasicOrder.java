package com.intelerad.web.lib.gwt.model.hl7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.intelerad.web.lib.gwt.model.GwtPriorityCode;
import com.intelerad.web.lib.gwt.model.Validator;

@SuppressWarnings("serial")
public class GwtBasicOrder implements Serializable
{
    public enum OrderStatus 
    {
        UNKNOWN( "UNKNOWN" ),
        SCHEDULED( "SC" ),
        PATIENT_ARRIVED( "IP" ),
        VALIDATED( "OC" ),
        PENDING_COMPLETION( "PC" ),
        COMPLETED( "CM" ),
        DICTATED( "ZA" ),
        TRANSCRIBED( "ZD" ),
        REPORT_PENDING( "ZE" ),
        REPORT_AVAILABLE( "ZZ" ),
        CANCELLED( "CA" ),
        REPORT_PRELIM( "ZY" ),
        PRIOR_ORDER( "NG" );
        
        private String mCode;
        
        private OrderStatus( String code )
        {
            mCode = code;
        }
        
        public String getCode()
        {
            return mCode;
        }
        
        private static Map<String,OrderStatus> mCodeMap;
        
        public static OrderStatus getOrderStatus( String code )
        {
            if ( mCodeMap == null )
            {
                mCodeMap = new HashMap<String,OrderStatus>();
                for ( OrderStatus status : OrderStatus.values() )
                    mCodeMap.put( status.getCode(), status );
            }
            OrderStatus status =  mCodeMap.get( code );
            return status != null ? status : OrderStatus.UNKNOWN;
        }
    }
    
    /** TODO Make this configurable. Used in ATC */
    static public final String HIGH_PRIORITY_STATUS_CODE = "ST";
    // used in Portal
    // TODO: normalize this with ATC's idea of priority 
    protected GwtPriorityCode mPriorityCode;
    
    protected GwtPatient mPatient = new GwtPatient();
    
    protected String mAccessionNumber;
    protected String mStatus;
    protected String mPriority;
    protected String mOrganization;
    protected int mTotalImages;
    protected Date mStudyDate;
    
    // Required key fields
    protected String mVisitNumber;
    protected String mFillerOrderNumber;
    protected String mPlacerOrderNumber;
    
    protected List<GwtProcedure> mProcedures = new ArrayList<GwtProcedure>( 1 );
    
    protected OrderStatus mOrderStatus = OrderStatus.UNKNOWN;
    private String mLocation;
    
    public GwtBasicOrder()
    {
    }
    
    public GwtBasicOrder( GwtBasicOrder source )
    {
        mPatient = source.mPatient;
        
        mAccessionNumber = source.mAccessionNumber;
        mStatus = source.mStatus;
        mPriority = source.mPriority;
        mOrganization = source.mOrganization;
        mTotalImages = source.mTotalImages;
        mStudyDate = source.mStudyDate;
        mVisitNumber = source.mVisitNumber;
        mFillerOrderNumber = source.mFillerOrderNumber;
        mPlacerOrderNumber = source.mPlacerOrderNumber;
        
        mOrderStatus = source.mOrderStatus;
        
        mProcedures = new ArrayList<GwtProcedure>( 1 );
        if ( source.mProcedures != null )
        {
            for ( GwtProcedure p : source.mProcedures )
            {
                mProcedures.add( new GwtProcedure( p ) );
            }
        }
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof GwtBasicOrder )
        {
            GwtBasicOrder order = (GwtBasicOrder) obj;
            return mAccessionNumber.equals( order.mAccessionNumber );
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Copy the key fields and immutable fields from another instance.
     * @param sourceOrder the source order
     */
    public void copyKeyFields( GwtBasicOrder sourceOrder )
    {
        this.setPatientId( sourceOrder.getPatientId() );
        this.mAccessionNumber = sourceOrder.mAccessionNumber;
        this.mVisitNumber = sourceOrder.mVisitNumber;
        this.mFillerOrderNumber = sourceOrder.mFillerOrderNumber;
        this.mPlacerOrderNumber = sourceOrder.mPlacerOrderNumber;
   
        for ( GwtProcedure sourceProc : sourceOrder.mProcedures )
        {
            GwtProcedure newProc = new GwtProcedure();
            newProc.copyKeyFields( sourceProc );
            addProcedure( newProc );
        }
    }
    
    /**
     * Compare fields in this instance to another instance and set to null those that have not
     * changed. This only applies to fields modifiable by the user.
     * @param order the order to compare to
     */
    public void clearUnchangedFields( GwtBasicOrder order )
    {
        if ( this.getFirstName().equals( order.getFirstName() ) &&
             this.getMiddleName().equals( order.getMiddleName() ) &&
             this.getLastName().equals( order.getLastName() ) )
        {
            this.setFirstName( null );
            this.setMiddleName( null );
            this.setLastName( null );
        }
        
        if ( this.mPriority.equals( order.mPriority ) )
            this.mPriority = null;
        
        String dob = getDateOfBirth();
        if ( dob != null && dob.equals( order.getDateOfBirth() ) )
            setDateOfBirth( null );
        
        String gender = getGender();
        if ( gender.equals( order.getGender() ) )
            setGender( null );
        
        // For order completion, the procedure indexes should always match, since procedures
        // cannot be added or removed. This may not be the case later for order modification.
        for ( int i = 0; i < this.mProcedures.size(); i++ )
        {
            GwtProcedure proc = this.mProcedures.get( i );
            proc.clearUnchangedFields( order.mProcedures.get( i ) );
        }
    }
    
    public boolean validate()
    {
        if ( getLastName() != null && !Validator.isValid( getLastName(), true ) )
            return false;
        
        if ( getFirstName() != null && !Validator.isValid( getFirstName(), true ) )
            return false;
        
        if ( getMiddleName() != null && !Validator.isValid( getMiddleName(), false ) )
            return false;
        
        if ( mPriority != null && !Validator.isValid( mPriority, true ) )
            return false;
        
        if ( getDateOfBirth() != null && !Validator.isValid( getDateOfBirth(), true ) )
            return false;
        
        if ( getGender() != null && !Validator.isValid( getGender(), true ) )
            return false;
        
        for ( int i = 0; i < mProcedures.size(); i++ )
        {
            GwtProcedure proc = mProcedures.get( i );
            
            if ( !proc.validate() )
                return false;
        }
        
        return true;
    }
    
    public void addProcedure( GwtProcedure proc )
    {
        mProcedures.add( proc );
    }

    public boolean isPostImageStatus()
    {
        if ( mOrderStatus == null )
        {
            return false;
        }
        if ( mOrderStatus.equals( OrderStatus.DICTATED ) ||
             mOrderStatus.equals( OrderStatus.TRANSCRIBED ) ||
             mOrderStatus.equals( OrderStatus.REPORT_PENDING ) || 
             mOrderStatus.equals( OrderStatus.REPORT_AVAILABLE ) || 
             mOrderStatus.equals( OrderStatus.REPORT_PRELIM ) )
        {
            return true;
        }
        
        return false;
    }
    
    public boolean isReportStatus()
    {
        if ( mOrderStatus == null )
        {
            return false;
        }
        if ( mOrderStatus.equals( OrderStatus.TRANSCRIBED ) ||
             mOrderStatus.equals( OrderStatus.REPORT_PENDING ) || 
             mOrderStatus.equals( OrderStatus.REPORT_AVAILABLE ) || 
             mOrderStatus.equals( OrderStatus.REPORT_PRELIM ) )
        {
            return true;
        }
        
        return false;
    }
    
    public String getModalityString()
    {
        StringBuffer buf = new StringBuffer();
        
        for ( GwtProcedure proc : mProcedures )
        {
            if ( proc.getModality() != null )
            {
                if ( buf.length() > 0 )
                {
                    buf.append( ", " );
                }
                buf.append( proc.getModality() );
            }
        }
        return buf.toString();
    }
    
    public String getStudyDescString()
    {
        StringBuffer buf = new StringBuffer();
        
        for ( GwtProcedure proc : mProcedures )
        {
            if ( proc.getStudyDescription() != null )
            {
                if ( buf.length() > 0 )
                {
                    buf.append( ", " );
                }
                buf.append( proc.getStudyDescription() );
            }
        }
        return buf.toString();
    }
    
    public String getExamDescString()
    {
        StringBuffer buf = new StringBuffer();
        
        for ( GwtProcedure proc : mProcedures )
        {
            if ( proc.getProcedureDescription() != null )
            {
                if ( buf.length() > 0 )
                {
                    buf.append( ", " );
                }
                buf.append( proc.getProcedureDescription() );
            }
        }
        return buf.toString();
    }
    
    public String getRpidString()
    {
        StringBuffer buf = new StringBuffer();
        
        for ( GwtProcedure proc : mProcedures )
        {
            if ( proc.getRequestedProcId() != null )
            {
                if ( buf.length() > 0 )
                {
                    buf.append( ", " );
                }
                buf.append( proc.getRequestedProcId() );
            }
        }
        return buf.toString();
    }
    
    public List<String> getRpids()
    {
        List<String> rpids = new ArrayList<String>();
        for ( GwtProcedure p: getProcedures() )
        {
            rpids.add( p.getRequestedProcId() );
        }
        return rpids;
    }
    
    public List<String> getProcedureDescriptions()
    {
        List<String> descs = new ArrayList<String>();
        for ( GwtProcedure p: getProcedures() )
        {
            descs.add( p.getProcedureDescription() );
        }
        return descs;
    }
    
    public String getPatientId()
    {
        return mPatient.getPatientId();
    }
    
    public void setPatientId( String patientId )
    {
        mPatient.setPatientId( patientId );
    }
    
    public String getAccessionNumber()
    {
        return mAccessionNumber;
    }
    
    public void setAccessionNumber( String accessionNumber )
    {
        mAccessionNumber = accessionNumber;
    }
    
    public String getStatus()
    {
        return mStatus;
    }

    public void setStatus( String status )
    {
        mStatus = status;
    }
    
    public Date getStudyDate()
    {
        return mStudyDate;
    }

    public void setStudyDate( Date studyDate )
    {
        mStudyDate = studyDate;
    }
    
    public OrderStatus getOrderStatus()
    {
        return mOrderStatus;
    }

    public void setOrderStatus( OrderStatus orderStatus )
    {
        mOrderStatus = orderStatus;
    }
    
    public String getFirstName()
    {
        return mPatient.getFirstName();
    }

    public void setFirstName( String firstName )
    {
        mPatient.setFirstName( firstName );
    }

    public String getMiddleName()
    {
        return mPatient.getMiddleName();
    }

    public void setMiddleName( String middleName )
    {
        mPatient.setMiddleName( middleName );
    }

    public String getLastName()
    {
        return mPatient.getLastName();
    }

    public void setLastName( String lastName )
    {
        mPatient.setLastName( lastName );
    } 
    
    public String getGender()
    {
        return mPatient.getGender();
    }
    
    public void setGender( String gender )
    {
        mPatient.setGender( gender );
    }

    public String getDateOfBirth()
    {
        return mPatient.getDateOfBirth();
    }
    
    public void setDateOfBirth( String dateOfBirth )
    {
        mPatient.setDateOfBirth( dateOfBirth );
    }
    
    public String getPrintableName()
    {
        return mPatient.getPrintableName();
    }

    /**
     * 
     * @deprecated see {@link #getPriorityCode()}
     */
    public String getPriority()
    {
        return mPriority;
    }
    
    /**
     * 
     * @deprecated see {@link #setPriorityCode(GwtPriorityCode)}
     */
    public void setPriority( String priority )
    {
        mPriority = priority;
    }

    /**
     * Set the value of the priority if it is either not set or if it is set but the new one is
     * a high priority (can happen if procedures don't have all the same priority).
     * 
     * Used in ATC, not in Portal
     * @param priority
     * @deprecated see {@link #setPriorityCode(GwtPriorityCode)}
     */
    public void setPriorityIfHigher( String priority )
    {
        if ( mPriority == null || GwtBasicOrder.HIGH_PRIORITY_STATUS_CODE.equals( priority ) )
            mPriority = priority;
    }
    
    /**
     * 
     * These methods all work with "Priority codes".  
     * I didn't want to disturb ATC, so I made new concept of priority code here, even 
     * though ATC could reuse the same concept.
     * 
     *  TODO: WE NEED TO NORMALIZE PRIORITY MODELS BETWEEN ATC AND PORTAL 
     * 
     */
    public GwtPriorityCode getPriorityCode()
    {
        return mPriorityCode;
    }

    public void setPriorityCode( GwtPriorityCode priorityCode )
    {
        mPriorityCode = priorityCode;
    }
    
    public void setPriorityCodeIfHigher( GwtPriorityCode priorityCode )
    {
        if ( mPriorityCode == null || mPriorityCode.getIndex() > priorityCode.getIndex() )
            mPriorityCode = priorityCode;
    }
    
    public String getOrganization()
    {
        return mOrganization;
    }

    public void setOrganization( String organization )
    {
        mOrganization = organization;
    }

    public int getTotalImages()
    {
        return mTotalImages;
    }

    public void setTotalImages( int totalImages )
    {
        mTotalImages = totalImages;
    }

    public String getVisitNumber()
    {
        return mVisitNumber;
    }

    public void setVisitNumber( String visitNumber )
    {
        mVisitNumber = visitNumber;
    }

    public String getFillerOrderNumber()
    {
        return mFillerOrderNumber;
    }

    public void setFillerOrderNumber( String fillerOrderNumber )
    {
        mFillerOrderNumber = fillerOrderNumber;
    }

    public String getPlacerOrderNumber()
    {
        return mPlacerOrderNumber;
    }

    public void setPlacerOrderNumber( String placerOrderNumber )
    {
        mPlacerOrderNumber = placerOrderNumber;
    }
    
    public List<GwtProcedure> getProcedures()
    {
        return mProcedures;
    }

    public void setProcedures( List<GwtProcedure> procedures )
    {
        mProcedures = procedures;
    }
    
    public GwtPatient getPatient()
    {
        return mPatient;
    }
    
    public void setPatient( GwtPatient patient )
    {
        mPatient = patient;
    }

    public void setLocation( String location )
    {
        mLocation = location;
    }
    
    public String getLocation()
    {
        return mLocation;
    }
}
