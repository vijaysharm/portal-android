package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.Date;

/**
 * A basic series class containing the search result info at the series level.
 */
public class GwtBasicSeries implements Serializable
{
    protected GwtBasicStudy mParentStudy;
    protected String mSeriesInstanceUid;
    protected Date   mSeriesDate;
    protected String mModality;
    protected String mSeriesNumber;
    protected int    mImageCount;
    protected String mSeriesDescription;
    protected String mSourceAe;
    protected String mOwner;
    protected boolean mIsKeyImageSeries;
    protected boolean mIsPresentationStateSeries;
    protected String mDestination;
    protected boolean mMoveResult;
    
    public GwtBasicSeries()
    {
    }

    public GwtBasicSeries( GwtBasicSeries other )
    {
        mParentStudy = other.getParentStudy();
        mSeriesInstanceUid = other.getSeriesInstanceUid();
        mSeriesDate = other.getSeriesDate();
        mModality = other.getModality();
        mSeriesNumber = other.getSeriesNumber();
        mImageCount = other.getImageCount();
        mSeriesDescription = other.getSeriesDescription();
        mSourceAe = other.getSourceAe();
        mOwner = other.getOwner();
        mIsKeyImageSeries = other.isKeyImageSeries();
        mIsPresentationStateSeries = other.isPresentationStateSeries();
        mDestination = other.getDestination();
        mMoveResult = other.getMoveResult();
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
        
        GwtBasicSeries other = (GwtBasicSeries) obj;
        return mSeriesInstanceUid.equals( other.mSeriesInstanceUid );
    }
    
    @Override
    public int hashCode()
    {
        return mSeriesInstanceUid.hashCode();
    }
    
    public String getSeriesInstanceUid()
    {
        return mSeriesInstanceUid;
    }

    public void setSeriesInstanceUid( String seriesInstanceUid )
    {
        mSeriesInstanceUid = seriesInstanceUid;
    }
    
    public String getStudyInstanceUid()
    {
        return mParentStudy.getStudyInstanceUid();
    }

    public Date getSeriesDate()
    {
        return mSeriesDate;
    }

    public void setSeriesDate( Date seriesDate )
    {
        mSeriesDate = seriesDate;
    }

    public GwtBasicStudy getParentStudy()
    {
        return mParentStudy;
    }

    public void setParentStudy( GwtBasicStudy parentStudy )
    {
        mParentStudy = parentStudy;
    }

    public String getModality()
    {
        return mModality;
    }

    public void setModality( String modality )
    {
        mModality = modality;
    }

    public String getSeriesNumber()
    {
        return mSeriesNumber;
    }

    public void setSeriesNumber( String seriesNumber )
    {
        mSeriesNumber = seriesNumber;
    }

    public String getSeriesDescription()
    {
        return mSeriesDescription;
    }

    public void setSeriesDescription( String seriesDescription )
    {
        mSeriesDescription = seriesDescription;
    }

    public int getImageCount()
    {
        return mImageCount;
    }

    public void setImageCount( int imageCount )
    {
        mImageCount = imageCount;
    }

    public String getSourceAe()
    {
        return mSourceAe;
    }

    public void setSourceAe( String sourceAe )
    {
        mSourceAe = sourceAe;
    }

    public String getOwner()
    {
        return mOwner;
    }

    public void setOwner( String owner )
    {
        mOwner = owner;
    }
    
    public boolean isKeyImageSeries()
    {
        return mIsKeyImageSeries;
    }

    public void setKeyImageSeries( boolean isKeyImageSeries )
    {
        mIsKeyImageSeries = isKeyImageSeries;
    }
    
    /**
     * 
     * @return true if this is a presentation state series but not a key image series
     */
    public boolean isPresentationStateSeries()
    {
        return mIsPresentationStateSeries;
    }

    public void setPresentationStateSeries( boolean isPresentationStateSeries )
    {
        mIsPresentationStateSeries = isPresentationStateSeries;
    }

    public void setDestination( String destination )
    {
    	mDestination = destination;
    }
    
    public String getDestination()
    {
    	return mDestination;
    }
    
    public void setMoveResult( boolean success )
    {
    	mMoveResult = success;
    }
    
    public boolean getMoveResult()
    {
    	return mMoveResult;
    }
    
    private static final long serialVersionUID = -2537900794972283426L;
}
