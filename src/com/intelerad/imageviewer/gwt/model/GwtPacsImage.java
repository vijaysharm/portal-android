package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.net.URLEncoder;

/**
 * A reference to an image in the pacs. The image is part of a dataset. A GwtPacsImage object can
 * reference the JPEG version of itself with a URL that can be used to download the image.
 */
@SuppressWarnings("serial")
public class GwtPacsImage implements Serializable
{
    /** The parent dataset. */
    private GwtDataset mParentDataset;
    
    /** The encoder for generating a URL. */
    private ImageUrlEncoder mUrlEncoder;

    /** A unique identifier for the image. 
     *  An image consists of a single frame of pixel data inside a DICOM SOP instance, for regular images
     *  OR 
     *  A Presentation State node Id (SOPInsUid), if this is a key image. 
     *  */
    private String mImageNodeUniqueIdentifier;
    
    /** The image's SOP instance UID. */
    private String mImageNodeSopInstanceUid;
    
    /** For regular images, this is the same as mImageNodeUniqueId
     *  For key images, this is the imageUid of the referenced image 
     * */
    private String mRefImageNodeUniqueIdentifier;

    /** For regular images, this the seriesInsUid of the parent dataset/series
     *  For key images, this is the seriesInsUid of the referenced images.
     * */
    private String mRefSeriesInstanceUid;
    
    public GwtPacsImage()
    {
    }    
    
    // for regular images
    public GwtPacsImage( GwtDataset parent, 
                         String imageNodeUniqueIdentifier,
                         String sopInstanceUid,
                         ImageUrlEncoder urlEncoder )
    {
        this();
        setParentDataset( parent );
        mImageNodeUniqueIdentifier = imageNodeUniqueIdentifier;
        mRefImageNodeUniqueIdentifier = imageNodeUniqueIdentifier;
        mRefSeriesInstanceUid = parent.getParentSeries().getSeriesInstanceUid(); 
        mImageNodeSopInstanceUid = sopInstanceUid;
        mUrlEncoder = urlEncoder;
    }
    
    // for key images
    public GwtPacsImage( GwtDataset parent, 
                         String imageNodeUniqueIdentifier,
                         String refImageNodeUniqueIdentifer,
                         String refSeriesInstanceUid,
                         String sopInstanceUid,
                         ImageUrlEncoder urlEncoder )
    {
        this();
        setParentDataset( parent );
        mImageNodeUniqueIdentifier = imageNodeUniqueIdentifier;
        mRefImageNodeUniqueIdentifier = refImageNodeUniqueIdentifer;
        mRefSeriesInstanceUid = refSeriesInstanceUid;
        mImageNodeSopInstanceUid = sopInstanceUid;
        mUrlEncoder = urlEncoder;
    }
     
    /**
     * Encode the ImageRequest object into a URL.
     * @param renderRequest A request to render an image.
     */
    public String getUrl( ImageRequest renderRequest )
    {
//        return URL.encode( mUrlEncoder.getUrl( renderRequest ) );
        try
        {
            return URLEncoder.encode( mUrlEncoder.getUrl( renderRequest ), "UTF-8" );
        }
        catch( Exception exception )
        {
            throw new RuntimeException( exception );
        }
    }

    public String getImageNodeSopInstanceUid()
    {
        return mImageNodeSopInstanceUid;
    }

    public void setParentDataset( GwtDataset parentDataset )
    {
        mParentDataset = parentDataset;
    }

    public GwtDataset getParentDataset()
    {
        return mParentDataset;
    }
 
    public int hashCode()
    {
        return mImageNodeUniqueIdentifier.hashCode();
    }
    
    public String getImageNodeUniqueIdentifier()
    {
        return mImageNodeUniqueIdentifier;
    }
    
    public String getRefImageNodeUniqueIdentifier()
    {
        return mRefImageNodeUniqueIdentifier;
    }
    
    public String getRefSeriesInstanceUid()
    {
        return mRefSeriesInstanceUid;
    }
 
    public ImageUrlEncoder getUrlEncoder()
    {
        return mUrlEncoder;
    }

    public void setUrlEncoder( ImageUrlEncoder urlEncoder )
    {
        mUrlEncoder = urlEncoder;
    }
    
    public boolean isKeyImage()
    {
        return mParentDataset.isKeyImageDataset();
    }
    
    public String getAccessionNumber()
    {
        return mParentDataset.getAccessionNumber();
    }
    
    public String getStudyInstanceUid()
    {
        return mParentDataset.getParentSeries().getParentStudy().getStudyInstanceUid();
    }
    
    public String getSeriesInstanceUid()
    {
        return mParentDataset.getParentSeries().getSeriesInstanceUid();
    }
    
    public GwtImageRenderParams getActiveRenderParams()
    {
        return mParentDataset.getActiveRenderParams();
    }
    
    public GwtImageRenderParams getDefaultRenderParams()
    {
        return mParentDataset.getDefaultRenderParams();
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
      
        GwtPacsImage other = (GwtPacsImage) obj;
        return safeEquals( mImageNodeUniqueIdentifier, other.mImageNodeUniqueIdentifier );
    } 
    
    private static boolean safeEquals( Object obj1, Object obj2 )
    {
        return obj1 == obj2 || ( obj1 != null && obj1.equals( obj2 ) );
    }
}
