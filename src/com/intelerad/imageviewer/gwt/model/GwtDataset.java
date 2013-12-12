package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Series can be split into datasets. A dataset is a convenient way for user to view groups of images.
 * 
 * <p>DX, CR, and MG modalities are single-image-set modalities, whose series are divided
 * into datasets containing one single image each.</p>
 * 
 * <p>Datasets are also created for series that contain multi-frame images such as multi-echo
 * MR. Each sequence is separated into a dataset containing multiple images.</p>
 * 
 * <p>Additionally, if a series contains images that vary in size, they may be grouped, based
 * on size, into multiple datasets within the series.</p>
 * 
 * <p>Each dataset behaves in the same way as a series.</p>
 */
public class GwtDataset implements Serializable
{
    private GwtSeries mParentSeries;
    
    /** Ordered for navigation by the user. */
    private List<GwtPacsImage> mImages;
    
    /** alphabetically ordered for comparison purposes */
    private Set<String> mImageIds;
    
    // render parameters for rendering non-key thumbnails
    private GwtImageRenderParams mDefaultRenderParams = null;

    private GwtImageRenderParams mPsRenderParams;
    
    public GwtDataset()
    {
        mImages = new ArrayList<GwtPacsImage>();
        mImageIds = new HashSet<String>();
    }    
    
    public GwtPacsImage getRepresentativeImage()
    {
        GwtPacsImage image = null;
        if ( mImages.size() > 0 )
        {
            image = mImages.get( mImages.size() / 2 );
        }
        return image;
    }
    
    public List<GwtPacsImage> getImages()
    {
        return mImages;
    } 
    
    public int getImageCount()
    {
        return mImages != null ? mImages.size() : 0;
    }
    
    public void setImages( List<GwtPacsImage> images )
    {
        if ( images == null )
            return;
        
        for ( GwtPacsImage image : images )
            addImage( image );
    }
    
    public boolean hasImages()
    {
        return mImages != null && mImages.size() > 0;
    }
    
    public void addImage( GwtPacsImage image )
    {
        if ( image != null )
        {
            mImages.add( image );
            image.setParentDataset( this );
            mImageIds.add( image.getImageNodeUniqueIdentifier() );
        }
    }
    
    public boolean isKeyImageDataset()
    {
        return mParentSeries.isKeyImageSeries();
    }    

    public GwtSeries getParentSeries()
    {
        return mParentSeries;
    }

    public void setParentSeries( GwtSeries parentSeries )
    {
        mParentSeries = parentSeries;
    }

    public String getAccessionNumber()
    {
        return mParentSeries.getAccessionNumber();
    }
    
    // render parameters for rendering a full image and possibly the 
    // thumbnails as well
    public GwtImageRenderParams getActiveRenderParams()
    {
        return mPsRenderParams != null ? mPsRenderParams : mDefaultRenderParams;
    }
    
    public GwtImageRenderParams getDefaultRenderParams()
    {
        return mDefaultRenderParams;
    }

    public void setDefaultRenderParams( GwtImageRenderParams defaultRenderParams )
    {
        mDefaultRenderParams = defaultRenderParams;
    }

    public void setPsRenderParams( GwtImageRenderParams psRenderParams )
    {
        mPsRenderParams = psRenderParams;
    }
    
    public GwtImageRenderParams getPsRenderParams()
    {
        return mPsRenderParams;
    }
    
    public boolean fromSameStudy( GwtDataset dataset )
    {
        return dataset != null && getAccessionNumber().equals( dataset.getAccessionNumber() );
    }
    
    public String getDatasetId()
    {
        return mParentSeries.getSeriesInstanceUid() + "_" + mImages.get( 0 ).getImageNodeSopInstanceUid();
    }
     
    public String toString() {
    	return "[GwtDataset: " + getDatasetId() + "]";
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( mImages == null ) ? 0
                                                       : mImages.hashCode() );
        result = prime * result + ( ( mParentSeries == null ) ? 0
                                                             : mParentSeries.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
      
        GwtDataset other = (GwtDataset) obj;
        return safeEquals( mImageIds, other.mImageIds );
    }
    
    private static boolean safeEquals( Object obj1, Object obj2 )
    {
        return obj1 == obj2 || ( obj1 != null && obj1.equals( obj2 ) );
    }
    
    private static final long serialVersionUID = -1365054766747362289L;
}
