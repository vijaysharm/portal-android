package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PatientActivityDataRequest implements Serializable
{
    public enum Action { QUERY, ADD, EDIT, DELETE, RESULT };
    
    /*
     * If null, this means that we read the list preferences from the DB.  
     * We'll apply the preferred list parameters to the query results (which 
     * may be cached), and we'll return both collections of list preferences 
     * in the results.  This is the case when we load the patient activity 
     * list for the first time
     * 
     * If not null, this means save the list parameters first, then apply them  
     * do the query results (which may be cached).  We won't return any list 
     * preferences in the results.  This is the case once we load the patient 
     * activity at least once.
     * 
     */
    private PatientActivityListParameters mListParams = null;
    
    /*
     * If this is true, we do a query and set the cache.
     * If false, we take the query results from cache if set
     */
    private boolean mFullRefresh = true;
    private int mStart = 0;
    private int mLength = 10;
    private Action mAction = Action.QUERY;
    
    public PatientActivityDataRequest()
    {
    }
    
    public PatientActivityDataRequest( PatientActivityDataRequest request )
    {
        mStart = request.getStart();
        mLength = request.getLength();
        mFullRefresh = request.isFullRefresh();
        if ( request.getListParams() != null )
        {
            mListParams = new PatientActivityListParameters( request.getListParams() );
        }
    }
        
    public int getStart()
    {
        return mStart;
    }

    public void setStart( int start )
    {
        mStart = start;
    }

    public int getLength()
    {
        return mLength;
    }

    public void setLength( int length )
    {
        mLength = length;
    }

    public Action getAction()
    {
        return mAction;
    }
    
    public void setAction( Action action )
    {
        mAction = action;
    }
    
    /**
     * @return the {@link PatientActivityListParameters}. If null, it means the parameters should be
     *         read from DB. else, they should be saved.
     */
    public PatientActivityListParameters getListParams()
    {
        return mListParams;
    }
    
    public void setListParams( PatientActivityListParameters listParams )
    {
        mListParams = listParams;
    }

    public boolean isFullRefresh()
    {
        return mFullRefresh;
    }

    public void setFullRefresh( boolean fullRefresh )
    {
        mFullRefresh = fullRefresh;
    }
}
