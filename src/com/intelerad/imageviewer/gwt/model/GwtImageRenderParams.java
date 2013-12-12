package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtImageRenderParams implements Serializable
{ 
    private String mActivePsSopInsUid = null;
    private String mActivePsSeriesInsUid = null;
     
    private double mPanX = 0;
    private double mPanY = 0;
    
    private double mZoomFactor = 1;

    private double mWindowWidth = Double.MAX_VALUE;
    private double mWindowCenter = Double.MAX_VALUE;
    
    public GwtImageRenderParams()
    {
    }

    public double getPanX()
    {
        return mPanX;
    }
    
    public void setPanX( double panX )
    {
        mPanX = panX;
    }

    public double getPanY()
    {
        return mPanY;
    }

    public void setPanY( double panY )
    {
        mPanY = panY;
    }

    public double getZoomFactor()
    {
        return mZoomFactor;
    }

    public void setZoomFactor( double zoomFactor )
    {
        mZoomFactor = zoomFactor;
    }

    public String getActivePsSopInsUid()
    {
        return mActivePsSopInsUid;
    }

    public void setActivePsSopInsUid( String activePsSopInsUid )
    {
        mActivePsSopInsUid = activePsSopInsUid;
    }   
    
    public String getActivePsSeriesInsUid()
    {
        return mActivePsSeriesInsUid;
    }

    public void setActivePsSeriesInsUid( String activePsSeriesInsUid )
    {
        mActivePsSeriesInsUid = activePsSeriesInsUid;
    }

    public void setWindowWidth( double windowWidth )
    {
        mWindowWidth = windowWidth;
    }

    public double getWindowWidth()
    {
        return mWindowWidth;
    }
    
    public void setWindowCenter( double windowCenter )
    {
        mWindowCenter = windowCenter;
    }
    
    public double getWindowCenter()
    {
        return mWindowCenter;
    }
}
