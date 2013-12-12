package com.intelerad.imageviewer.gwt.model;

import com.intelerad.datamodels.imagerendering.ImageQuality;


/**
 * This represents a request from the viewer to render a 2D image.
 */
@SuppressWarnings("serial")
public class Image2dRequest extends ImageRequest
{
    // just used for the zoom, pan, etc. Not used for presentation state stuff.
    private double mDeltaScreenPanX = 0;
    private double mDeltaScreenPanY = 0;
    private double mZoomFactor = 1.0;
    private double mWindowCenter = Double.MAX_VALUE;
    private double mWindowWidth = Double.MAX_VALUE;
    private ImageQuality mImageQuality = ImageQuality.BEST;
    private boolean mIsKeyImage;
    
    private String mDatasetInstanceId = "";
    
    public Image2dRequest() {}
    public Image2dRequest( GwtPacsImage image,
                           int imageWidth,
                           int imageHeight, 
                           boolean ignorePs )
    {
        super( image, imageWidth, imageHeight, ignorePs );
       
        setKeyImage( image.isKeyImage() );
        initializeRenderParameters( image.isKeyImage() || !ignorePs ? image.getActiveRenderParams()
                                                                    : image.getDefaultRenderParams() );
    }
    
    public Image2dRequest( GwtPacsImage image, int imageWidth, int imageHeight )
    {
        this( image, imageWidth, imageHeight, false );
    }
    
    public double getDeltaScreenPanX()
    {
        return mDeltaScreenPanX;
    }
    
    public double getDeltaScreenPanY()
    {
        return mDeltaScreenPanY;
    }
    
    public double getZoomFactor()
    {
        return mZoomFactor;
    }
    
    public void setDeltaScreenPanX( double deltaScreenPanX )
    {
        mDeltaScreenPanX = deltaScreenPanX;
    } 
    
    public void setDeltaScreenPanY( double deltaScreenPanY )
    {
        mDeltaScreenPanY = deltaScreenPanY;
    }
    
    public void setZoomFactor( double zoomFactor )
    {
        mZoomFactor = zoomFactor;
    }
    
    public ImageQuality getImageQuality()
    {
        return mImageQuality;
    }
    
    public void setImageQuality( ImageQuality imageQuality )
    {
        mImageQuality = imageQuality;
    }
    
    public boolean isPanned()
    {
        return ( ( Double.compare( 0.0, getDeltaScreenPanX() ) != 0 ) ||
                 ( Double.compare( 0.0, getDeltaScreenPanY() ) != 0 ) );
    }
    
    public boolean isZoomed()
    {
        return Double.compare( 1.0, getZoomFactor() ) != 0;
    }
    
    public boolean isKeyImage()
    {
        return mIsKeyImage;
    }

    public void setKeyImage( boolean isKeyImage )
    {
        mIsKeyImage = isKeyImage;
    }
    
    @Override
    public boolean isVolume()
    {
        return false;
    }
    
    public void setWindowCenter( double windowCenter )
    {
        mWindowCenter = windowCenter;
    }

    public double getWindowCenter()
    {
        return mWindowCenter;
    }
    
    public void setWindowWidth( double windowWidth )
    {
        mWindowWidth = windowWidth;
    }
    
    public double getWindowWidth()
    {
        return mWindowWidth;
    }

    public String getDatasetInstanceId()
    {
        return mDatasetInstanceId;
    }
    
    public void setDatasetInstanceId( String datasetInstanceId )
    {
        mDatasetInstanceId = datasetInstanceId;
    }
    
    private void initializeRenderParameters( GwtImageRenderParams gwtImageRenderParams )
    {
        setWindowCenter( gwtImageRenderParams.getWindowCenter() );
        setWindowWidth( gwtImageRenderParams.getWindowWidth() );
    }    
}
