package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * This class holds everything you need to populate a patient activity list.
 * This eventually gets translated into a HL7 query.
 *
 */
@SuppressWarnings("serial")
public class PatientActivityListParameters implements Serializable
{
    private String mId = null;
    
    private String mName = "";
    private boolean mAutoRefresh = false;
    private boolean mDeletable = true;
    
    private boolean mReferrer = true;
    private boolean mShowImpressionDiscrepancy = false;
    private Set<String> mLocations = new HashSet<String>();
    private Set<String> mOrganizations = new HashSet<String>();
    private Set<String> mModalities = new HashSet<String>();
    private Set<String> mOrderStatus = new HashSet<String>();
    
    private SortOption mSortOption = SortOption.ALPHABETICAL;
    private DateOption mDateOption = DateOption.SEVEN_DAYS;
    private Set<CriticalResultsOption> mCriticalResultOption = new HashSet<CriticalResultsOption>();

    public PatientActivityListParameters()
    {
    }
    
    public PatientActivityListParameters( PatientActivityListParameters params )
    {
        mId = params.getId();
        mName = params.getName();
        mSortOption = params.getSortOption();
        mDateOption = params.getDateOption();
        mReferrer = params.isReferrer();
        mLocations = new HashSet<String>( params.getLocations() );
        mOrganizations = new HashSet<String>( params.getOrganizations() );
        mModalities = new HashSet<String>( params.getModalities() );
        mOrderStatus = new HashSet<String>( params.getOrderStatus() );
        mCriticalResultOption = new HashSet<CriticalResultsOption>( params.getCriticalResultOption() );
        mShowImpressionDiscrepancy = params.showImpressionDiscrepancy();
        mDeletable = params.isDeletable();    
    }

    public String getId()
    {
        return mId;
    }

    public void setId( String id )
    {
        mId = id;
    }
    
    public boolean isDeletable()
    {
        return mDeletable;
    }
    
    public void setDeletable( boolean isDeletable )
    {
        mDeletable = isDeletable;
    }
    
    public boolean autoRefresh()
    {
        return mAutoRefresh;
    }
    
    public void setAutoRefresh( boolean refresh )
    {
        mAutoRefresh = refresh;
    }
    
    public Set<CriticalResultsOption> getCriticalResultOption()
    {
        return mCriticalResultOption;
    }
    
    public boolean showImpressionDiscrepancy()
    {
        return mShowImpressionDiscrepancy;
    }
    
    public void setShowImpressionDiscrepancy( boolean impressionDiscrepancy )
    {
        mShowImpressionDiscrepancy = impressionDiscrepancy;
    }
    
    public void setCriticalResultOption( Set<CriticalResultsOption> criticalResultOption )
    {
        if ( criticalResultOption == null )
            throw new IllegalArgumentException( "Arguement cannot be null" );
        
        mCriticalResultOption = criticalResultOption;
    }
    
    public Set<String> getModalities()
    {
        return mModalities;
    }
    
    public void setModalities( Set<String> modalities )
    {
        mModalities = modalities;
    }
    
    public Set<String> getOrderStatus()
    {
        return mOrderStatus;
    }
    
    public void setOrderStatus( Set<String> orderStatus )
    {
        mOrderStatus = orderStatus;
    }

    public SortOption getSortOption()
    {
        return mSortOption;
    }

    public void setSortOption( SortOption sortOption )
    {
        mSortOption = sortOption;
    }

    public DateOption getDateOption()
    {
        return mDateOption;
    }

    public void setDateOption( DateOption dateOption )
    {
        mDateOption = dateOption;
    }

    public boolean isReferrer()
    {
        return mReferrer;
    }

    public void setReferrer( boolean referrer )
    {
        mReferrer = referrer;
    }

    public Set<String> getLocations()
    {
        return mLocations;
    }

    public void setLocations( Set<String> locations )
    {
        mLocations = locations;
    }
   
    public Set<String> getOrganizations()
    {
        return mOrganizations;
    }
    
    public void setOrganizations( Set<String> organizations )
    {
        mOrganizations = organizations;
    }

    public String getName()
    {
        return mName;
    }
    
    public void setName( String name )
    {
        mName = name;
    }
    
    public static PatientActivityListParameters createDefaultReferrerParams()
    {
        PatientActivityListParameters params = new PatientActivityListParameters();
        params.setId( "0" );
        params.setName( "My patients" );
        params.setReferrer( true );
        params.setAutoRefresh( false );
        params.setLocations( new HashSet<String>() );
        params.setOrganizations( new HashSet<String>() );
        params.setCriticalResultOption( new HashSet<CriticalResultsOption>() );
        params.setModalities( new HashSet<String>() );
        params.setShowImpressionDiscrepancy( false );
        params.setOrderStatus( new HashSet<String>() );
        params.setDateOption( DateOption.SEVEN_DAYS );
        params.setSortOption( SortOption.ALPHABETICAL );
        params.setDeletable( false );

        return params;
    }
    
    public static PatientActivityListParameters createDefaultEmergencyParams()
    {
        PatientActivityListParameters params = new PatientActivityListParameters();
        params.setId( "1" );
        params.setName( "Patients by location" );
        params.setReferrer( false );
        params.setAutoRefresh( true );
        params.setLocations( new HashSet<String>() );
        params.setOrganizations( new HashSet<String>() );
        params.setCriticalResultOption( new HashSet<CriticalResultsOption>() );
        params.setModalities( new HashSet<String>() );
        params.setShowImpressionDiscrepancy( false );
        params.setOrderStatus( new HashSet<String>() );        
        params.setDateOption( DateOption.DAY );
        params.setSortOption( SortOption.MOST_RECENT );
        params.setDeletable( false );

        return params;
    }
}
