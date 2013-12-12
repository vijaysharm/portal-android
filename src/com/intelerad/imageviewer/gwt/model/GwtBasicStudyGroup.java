package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;

/**
 * A GwtBasicGroupedStudies is a group of studies, where we are 
 * agnostic about which kinds of studies are in the group.  
 * So this class basically implements the operations on groups of 
 * studies that don't require knowledge of the kinds of studies 
 * in the group 
 * 
 */
@SuppressWarnings("serial")
public class GwtBasicStudyGroup<S extends GwtBasicStudy> implements Serializable
{
    protected SortedSet<S> mStudies; // Sorted by GwtStudyComparator
    
    public GwtBasicStudyGroup()
    {
        mStudies = new TreeSet<S>( GwtBasicStudy.getDefaultComparator() );
    }
    
    public String getAccessionNumber()
    {
        return getGroupAccessionNumber();
    }
    
    public SortedSet<S> getStudies()
    {
        return mStudies;
    }

    /**
     * Sets all studies in the group. Also sets the group accession number.
     * 
     * <p>This method will throw an IllegalArgumentException if any of the studies are null, 
     * if a study is missing an accession number, or if the group does not share the same 
     * accession number.</p>
     */
    public void setStudies( Collection<S> studies )
    {
        if ( studies == null )
            throw new IllegalArgumentException( "Studies are missing." );
        
        for ( S study : studies )
            addStudy( study );
    }
    
    /**
     * Add a study to the group.
     * 
     * <p>This method will throw an IllegalArgumentException if the study is null, 
     * if a study is missing an accession number, or if the group does not share the same 
     * accession number.</p>
     */
    public void addStudy( S study )
    {
        if ( study == null )
            throw new IllegalArgumentException( "Study is missing." );

        if ( GwtStringUtils.isBlank( study.getAccessionNumber() ) )
            throw new IllegalArgumentException( "Study's accession number is missing." );
        
        String groupAccessionNumber = getGroupAccessionNumber();
        
        if ( !mStudies.isEmpty() && ( !study.getAccessionNumber().equals( groupAccessionNumber ) ) )
            throw new IllegalArgumentException( "Study's accession number does not match the rest of the group." );
        
        mStudies.add( study );
    }
    
    public boolean hasStudies()
    {
        return !mStudies.isEmpty();
    }
    
    /*
     * Returns the accession number of the first study in the collection.
     * 
     * @return null if the collection is empty.
     */
    private String getGroupAccessionNumber( )
    {
        if ( mStudies == null || mStudies.isEmpty() )
            return null;
        else
            return mStudies.iterator().next().getAccessionNumber(); 
    } 
    
    public String getStudyDescriptionString()
    {
        StringBuffer buf = new StringBuffer();
        
        for ( GwtBasicStudy s : mStudies )
        {
            if ( s.getStudyDescription() != null )
            {
                if ( buf.length() > 0 )
                {
                    buf.append( ", " );
                }
                buf.append( s.getStudyDescription() );
            }
        }
        return buf.toString();
    }
}
