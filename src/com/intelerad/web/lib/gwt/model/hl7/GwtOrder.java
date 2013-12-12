package com.intelerad.web.lib.gwt.model.hl7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;
import com.intelerad.web.lib.gwt.model.GwtBasicUser;
import com.intelerad.web.lib.gwt.model.GwtCriticalResult;
import com.intelerad.web.lib.gwt.model.GwtImpression;
import com.intelerad.web.lib.gwt.model.GwtPriorityLevel;

@SuppressWarnings("serial")
public class GwtOrder extends GwtBasicOrder
{
    protected GwtBasicUser mRadiologist;
    protected List<GwtBasicUser> mReferringPhysicians = new ArrayList<GwtBasicUser>();
    protected Set<String> mReasonForStudy;
    protected List<GwtImpression> mImpressions = new ArrayList<GwtImpression>();
    protected GwtCriticalResult mCriticalResult;
    private ImageAvailability mImageAvailability;
    
    public enum ImageAvailability
    {
        ONLINE, OFFLINE, IN_PROGRESS, NO_IMAGES;
    }
    
    public GwtOrder()
    {
        super();
    }
    
    public String getReasonForStudy()
    {
        if ( mReasonForStudy == null )
            return null;
        
        StringBuffer buf = new StringBuffer();
        
        for ( String reason : mReasonForStudy )
        {
            if ( buf.length() > 0 )
                buf.append( "\n" );
            
            buf.append( reason );
        }
        return buf.toString();
    }

    public void addReasonForStudy( String reasonForStudy )
    {
        if ( mReasonForStudy == null )
            mReasonForStudy = new HashSet<String>( 1 );
        
        if ( !GwtStringUtils.isBlank( reasonForStudy ) )
            mReasonForStudy.add( reasonForStudy );
    }

    public GwtBasicUser getRadiologist()
    {
        return mRadiologist;
    }

    public void setRadiologist( GwtBasicUser radiologist )
    {
        mRadiologist = radiologist;
    }

    public GwtBasicUser getPrimaryReferringPhysician()
    {
        return mReferringPhysicians != null && 
           mReferringPhysicians.size() > 0 ? mReferringPhysicians.get( 0 ) : null;
    }
    
    public void setPrimaryReferringPhysician( GwtBasicUser refPhys )
    {
        if ( mReferringPhysicians == null )
            mReferringPhysicians = new ArrayList<GwtBasicUser>();
        else
            mReferringPhysicians.clear();
        
        mReferringPhysicians.add( refPhys );
        
    }
    
    public List<GwtBasicUser> getReferringPhysicians()
    {
        return mReferringPhysicians;
    }

    public void setReferringPhysicians( List<GwtBasicUser> referringPhysicians )
    {
        mReferringPhysicians = referringPhysicians;
    }

    public boolean isCriticalResult()
    {
        return mCriticalResult != null;
    }
    
    public GwtPriorityLevel getCriticalResultLevel()
    {
        GwtPriorityLevel level = null;
        if ( mCriticalResult != null )
        {
            level = mCriticalResult.getLevel();
        }
        return level;
    }

    public void setCriticalResult( GwtCriticalResult criticalResult )
    {
        mCriticalResult = criticalResult;
    }

    public List<GwtImpression> getImpressions()
    {
        return mImpressions;
    }

    public void setImpressions( List<GwtImpression> impressions )
    {
        mImpressions = impressions;
    }
    
    public void addImpression( GwtImpression imp )
    {
        if ( mImpressions == null )
            mImpressions = new ArrayList<GwtImpression>();
        
        mImpressions.add( imp );
    }

    public GwtCriticalResult getCriticalResult()
    {
        return mCriticalResult;
    }

    public boolean isReferringPhysician( String primaryRisId )
    {
        boolean isReferringPhysician = false;
        
        for ( GwtBasicUser referringPhysician : getReferringPhysicians() )
        {
            if ( !GwtStringUtils.isBlank( referringPhysician.getRisId() )
                 && referringPhysician.getRisId().equals( primaryRisId ) )
            {
                isReferringPhysician = true;
                break;
            }
        }
        
        return isReferringPhysician;
    }

    public ImageAvailability getImageAvailability()
    {
        return mImageAvailability;
    }

    public void setImageAvailability( ImageAvailability imageAvailability )
    {
        mImageAvailability = imageAvailability;
    }
}
