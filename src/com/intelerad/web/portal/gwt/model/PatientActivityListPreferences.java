package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents all the information stored about the Patient Activity list.
 * It has a list of PatientActivityListPreferences, each of which can populate the 
 * list, and an indication of which of these parameter objects is the "preferred" one 
 * so we can use it by default. 
 */
@SuppressWarnings("serial")
public class PatientActivityListPreferences implements Serializable
{
    private Map<String, String> mAllLocations = new HashMap<String, String>();
    private Map<String, String> mAllOrganizations = new HashMap<String, String>();
    private Map<String, String> mAllModalities = new HashMap<String, String>();
    private Map<String, String> mAllOrderStatuses = new HashMap<String, String>();
    
    private PatientActivityListParameters mPreferredListParam = null;
    private List<PatientActivityListParameters> mListParams = new ArrayList<PatientActivityListParameters>();
   
    public PatientActivityListPreferences()
    {   
    }
    
    public PatientActivityListPreferences( PatientActivityListPreferences other )
    {
        mAllLocations = new HashMap<String, String>( other.mAllLocations );
        mAllOrganizations = new HashMap<String, String>( other.mAllOrganizations );
        mAllModalities = new HashMap<String, String>( other.mAllModalities );
        mAllOrderStatuses = new HashMap<String, String>( other.mAllOrderStatuses );
        
        mPreferredListParam = other.mPreferredListParam;
        mListParams = new ArrayList<PatientActivityListParameters>( other.mListParams );
    }
    
    public void setPreferredListParameter( PatientActivityListParameters name )
    {
        mPreferredListParam = name;
    }
    
    /**
     * @return the list of all {@link PatientActivityListParameters} cannot be null.
     */
    public List<PatientActivityListParameters> getListParams()
    {
        return mListParams;
    }
    
    public void setListParams( List<PatientActivityListParameters> listParams )
    {
        if ( listParams == null )
            throw new IllegalArgumentException( "list parameters cannot be null" );
        
        mListParams = listParams;
    }

    public Map<String, String> getAllLocations()
    {
        return mAllLocations;
    }

    public void setAllLocations( Map<String, String> allLocations )
    {
        mAllLocations = allLocations;
    }

    public Map<String, String> getAllOrganizations()
    {
        return mAllOrganizations;
    }

    public void setAllOrganizations( Map<String, String> allOrganizations )
    {
        mAllOrganizations = allOrganizations;
    }

    /**
     * Returns the preferred filter, or the first one in the list if none was set exists.
     */
    public PatientActivityListParameters getPreferredListParameter()
    {
        if ( mListParams.isEmpty() )
            return null;
        
        PatientActivityListParameters parameter = findListParameter( mPreferredListParam );
        return parameter == null ? mListParams.get( 0 ) : parameter;
    }

    public Map<String, String> getAllModalities()
    {
        return mAllModalities;
    }

    public void setAllModalities( Map<String, String> allModalities )
    {
        mAllModalities = allModalities;
    }
    
    public Map<String, String> getAllOrderStatuses()
    {
        return mAllOrderStatuses;
    }
    
    public void setAllOrderStatuses( Map<String, String> allOrderStatuses )
    {
        mAllOrderStatuses = allOrderStatuses;
    }
    
    private PatientActivityListParameters findListParameter( PatientActivityListParameters parameters )
    {
        if ( parameters == null )
            return null;
        
        for ( PatientActivityListParameters params : mListParams )
        {
            if ( parameters.getId().equals( params.getId() ) )
                return params;
        }
        
        return null;
    }
}
