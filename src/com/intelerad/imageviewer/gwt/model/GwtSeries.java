package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Should contain the information that will be used in the user interface.
 */
@SuppressWarnings("serial")
public class GwtSeries extends GwtBasicSeries implements Serializable
{
    private List<GwtDataset> mDatasets;
    private Set<DicomError> mDicomErrors;
    private boolean         mOnline    = true;         // offline: in an archive
    private boolean         mAvailable = true;         // unavailable: host is down
    private GwtOverlayData mMetadata;
    
    public GwtSeries()
    {
        super();
        mDicomErrors = new HashSet<DicomError>();
        mDatasets = new ArrayList<GwtDataset>();
    }

    public GwtSeries( GwtBasicSeries series )
    {
        super( series );
        mDatasets = new ArrayList<GwtDataset>();
    }    
    
    public GwtSeries( String seriesInstanceUid, boolean online )
    {
        this();
        mSeriesInstanceUid = seriesInstanceUid;
        mOnline = online;
    }

    public List<GwtDataset> getDatasets()
    {
        return mDatasets;
    }

    public void setOverlayData( GwtOverlayData metadata )
    {
        mMetadata = metadata;
    }
    
    public GwtOverlayData getOverlayData()
    {
        return mMetadata;
    }
    
    public void setDatasets( List<GwtDataset> datasets )
    {
        if ( datasets == null )
            return;
        
        for ( GwtDataset dataset : datasets )
            addDataset( dataset );
    }
    
    public void addDataset( GwtDataset dataset )
    {
        if ( dataset != null )
        {
            mDatasets.add( dataset );
            dataset.setParentSeries( this );
        }
    }
    
    /**
     * 
     * @return image representing this series
     */
    public GwtPacsImage getRepresentativeImage()
    {
        GwtPacsImage image = null;
        
        List<GwtPacsImage> allImages = getAllImages();
        if ( allImages.size() > 0 )
        {
            image = allImages.get( allImages.size() / 2 );
        }
        
        return image;
    }
    /**
     * 
     * @return list of images, where each image represents a dataset
     */
    public List<GwtPacsImage> getDatasetImages()
    { 
        List<GwtPacsImage> datasetImages = new ArrayList<GwtPacsImage>();
        if ( mDatasets != null )
        {
            for( GwtDataset d: mDatasets )
            {       
                datasetImages.add( d.getRepresentativeImage() );
            }
        }
        return datasetImages;
    }
    
    /**
     * 
     * @return list of all images in the series
     */
    public List<GwtPacsImage> getAllImages()
    {
        List<GwtPacsImage> keyImages = new ArrayList<GwtPacsImage>();
        if ( mDatasets != null )
        {
            for ( GwtDataset dataset : mDatasets )
            {
                keyImages.addAll( dataset.getImages() );   
            }
        }
        return keyImages;
    }

    public String getAccessionNumber()
    {
        return mParentStudy.getAccessionNumber();
    }
     
    public void addDicomError( DicomError error )
    {
        mDicomErrors.add( error );
    }

    public Set<DicomError> getDicomErros()
    {
        return mDicomErrors;
    }

    public boolean isOnline()
    {
        return mOnline;
    }

    public void setOnline( boolean isOnline )
    {
        mOnline = isOnline;
    }

    public boolean isAvailable()
    {
        return mAvailable;
    }

    public void setAvailable( boolean available )
    {
        mAvailable = available;
    }
    
    public boolean isLoadable()
    {
        return isAvailable() && isOnline();
    }
}
