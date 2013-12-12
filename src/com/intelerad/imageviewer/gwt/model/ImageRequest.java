package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;

import com.intelerad.imagerenderingservice.protocol.ImageEncodingFormat;
import com.intelerad.imagerenderingservice.protocol.ImageSize;
import com.intelerad.imagerenderingservice.protocol.OutputImageParameters;

/**
 * A request to render a 2D or 3D image.
 */
public abstract class ImageRequest implements Serializable
{
    // Image identification...
    private String mStudyInstanceUid;
    private String mSeriesInstanceUid;
    private String mImageUid;

    // The desired image dimensions
    protected int mImageWidth;
    protected int mImageHeight;
    
    private String mPsSopInstanceUid;
    private String mPsSeriesInstanceUid;

    public ImageRequest() {}
    
    @SuppressWarnings("unchecked")
    public ImageRequest( GwtPacsImage image, int imageWidth, int imageHeight, boolean ignorePs )
    {
        mStudyInstanceUid = image.getStudyInstanceUid();
        mSeriesInstanceUid = image.getRefSeriesInstanceUid();    
        mImageUid = image.getRefImageNodeUniqueIdentifier();
        
        // Key images (thumbnail or not) always have a presentation state applied.  
        // Normal images have a presentation state if not a thumbnail
        if ( image.isKeyImage() || !ignorePs )
        {
            mPsSopInstanceUid = image.getActiveRenderParams().getActivePsSopInsUid();
            mPsSeriesInstanceUid = image.getActiveRenderParams().getActivePsSeriesInsUid();
        }
        
        mImageWidth = imageWidth;
        mImageHeight = imageHeight;
    }
    
    public ImageRequest( GwtPacsImage image, int imageWidth, int imageHeight )
    {
        this( image, imageWidth, imageHeight, false );
    }
    
    public OutputImageParameters getOutputImageParameters( double compressionQuality )
    {
        OutputImageParameters outputImageParameters = 
            new OutputImageParameters( ImageEncodingFormat.JPEG, 
                                       new ImageSize( mImageWidth, mImageHeight ),
                                       compressionQuality );
        return outputImageParameters;
    }
    
    public String getStudyInstanceUid()
    {
        return mStudyInstanceUid;
    }

    public void setStudyInstanceUid( String studyInstanceUid )
    {
        mStudyInstanceUid = studyInstanceUid;
    }

    public String getSeriesInstanceUid()
    {
        return mSeriesInstanceUid;
    }

    public void setSeriesInstanceUid( String seriesInstanceUid )
    {
        mSeriesInstanceUid = seriesInstanceUid;
    }
  
    public String getImageUid()
    {
        return mImageUid;
    }

    public void setImageUid( String imageUid )
    {
        this.mImageUid = imageUid;
    }

    public int getImageWidth()
    {
        return mImageWidth;
    }
    
    public void setImageWidth( int imageWidth )
    {
        mImageWidth = imageWidth;
    }
    
    public int getImageHeight()
    {
        return mImageHeight;
    }
    
    public void setImageHeight( int imageHeight )
    {
        mImageHeight = imageHeight;
    }
    
    public void setPsSopInstanceUid( String psSopInsUid )
    {
        mPsSopInstanceUid = psSopInsUid;
    }

    public String getPsSopInstanceUid()
    {
        return mPsSopInstanceUid;
    }
    
    public void setPsSeriesInstanceUid( String psSeriesInsUid )
    {
        mPsSeriesInstanceUid = psSeriesInsUid;
    }

    public String getPsSeriesInstanceUid()
    {
        return mPsSeriesInstanceUid;
    }
    /** Is this a request for a volume? */
    public abstract boolean isVolume();
    
    private static final long serialVersionUID = 3630211663284089184L;
}
