package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

@SuppressWarnings("serial")
public class SearchParameters implements Serializable
{
    private String                                           mSearchText;
    private boolean                                          mNew;
    private int                                              mPage;
    private int                                              mItemsPerPage;
    private LinkedHashMap<PatientTableModel.Column, Boolean> mSortingMap;

    public SearchParameters()
    {
        mNew = true;
        mPage = 0;
        mSortingMap = new LinkedHashMap<PatientTableModel.Column, Boolean>();
        mSortingMap.put( PatientTableModel.Column.PATIENT_NAME,
                         true );
    }

    public SearchParameters( SearchParameters params )
    {
        mNew = params.mNew;
        mPage = params.mPage;
        mSortingMap = new LinkedHashMap<PatientTableModel.Column, Boolean>( params.mSortingMap );
        mItemsPerPage = params.mItemsPerPage;
        mSearchText = params.mSearchText; 
    }

    public boolean isNew()
    {
        return mNew;
    }

    public void setNew( boolean isNew )
    {
        mNew = isNew;
    }

    public int getPage()
    {
        return mPage;
    }

    public void setPage( int page )
    {
        mPage = page;
    }

    public LinkedHashMap<PatientTableModel.Column, Boolean> getSortingMap()
    {
        return mSortingMap;
    }

    public void setSortingMap( LinkedHashMap<PatientTableModel.Column, Boolean> sortingMap )
    {
        mSortingMap = sortingMap;
    }

    public int getItemsPerPage()
    {
        return mItemsPerPage;
    }

    public void setItemsPerPage( int itemsPerPage )
    {
        mItemsPerPage = itemsPerPage;
    }

    public String getSearchText()
    {
        return mSearchText;
    }

    public void setSearchText( String searchText )
    {
        mSearchText = searchText;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof SearchParameters )
        {
            SearchParameters sp = (SearchParameters) obj;
            return safeEquals( mNew, sp.mNew ) && 
                   safeEquals( mSearchText, sp.mSearchText ) && 
                   safeEquals( mPage, sp.mPage ) && 
                   safeEquals( mItemsPerPage, sp.mItemsPerPage ) &&
                   safeEquals( mSortingMap, sp.mSortingMap );
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Mostly for debugging
     */
    @Override
    public String toString()
    {
        StringBuffer result = new StringBuffer();
        result.append( "SearchParameters[" );
        result.append( mNew + "," );
        result.append( mSearchText + "," );
        result.append( mPage + "," );
        result.append( mItemsPerPage + "," );
        result.append( mSortingMap );
        result.append( "]" );
        return result.toString();
    }

    private static boolean safeEquals( Object obj1, Object obj2 )
    {
        return obj1 == obj2 || ( obj1 != null && obj1.equals( obj2 ) );
    }
    
    public static String[] getSearchTokens( String searchText )
    {
        return searchText.toUpperCase().split( "[\\s\\,]+" );
    }
    
    public static Set<String> getSearchTokensSet( String searchText )
    {
        Set<String> searchTokensSet = new HashSet<String>();
        searchTokensSet.addAll( Arrays.asList( getSearchTokens( searchText ) ) );
        return searchTokensSet;
    }
}
