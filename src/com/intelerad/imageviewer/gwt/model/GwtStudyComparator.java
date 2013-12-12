package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This comparator will compare the accession numbers of the two studies. If they are equal, then
 * it will compare the study instance UIDs.
 */
@SuppressWarnings("serial")
public class GwtStudyComparator implements Comparator<GwtBasicStudy>, Serializable
{
    public GwtStudyComparator() {}
    
    @Override
    public int compare( GwtBasicStudy o1, GwtBasicStudy o2 )
    {
        if ( !o1.getAccessionNumber().equals( o2.getAccessionNumber() ) )
            return o1.getAccessionNumber().compareToIgnoreCase( o2.getAccessionNumber() );
        else
            return o1.getStudyInstanceUid().compareToIgnoreCase( o2.getStudyInstanceUid() );
    }    
}
