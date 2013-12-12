package com.intelerad.web.lib.gwt.model.hl7;

import java.io.Serializable;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;
import com.intelerad.web.lib.gwt.model.Validator;

@SuppressWarnings("serial")
public class GwtProcedure implements Serializable
{
    private String mRequestedProcId;
    private String mSchedProcStepId;
    private String mStudyDescription;
    private String mModality;
    //private String mStatus;
    //private int mTotalImages;
    private boolean mNew;
    
    // this exists for the Portal.  ATC uses priority as well, but it's dealt with in a different manner
    // TODO: normalize priority handling for Portal and ATC
    private String mPriority;
    
    public GwtProcedure()
    {
    }
    
    public GwtProcedure( GwtProcedure proc )
    {
        mRequestedProcId = proc.mRequestedProcId;
        mSchedProcStepId = proc.mSchedProcStepId;
        mStudyDescription = proc.mStudyDescription;
        mModality = proc.mModality;
        mNew = proc.mNew;
        mPriority = proc.mPriority;
    }

    /**
     * Copy the key fields and immutable fields from another instance.
     * @param sourceProc the source procedure
     */
    public void copyKeyFields( GwtProcedure sourceProc )
    {
        this.mRequestedProcId = sourceProc.mRequestedProcId;
        this.mSchedProcStepId = sourceProc.mSchedProcStepId;
        this.mNew = sourceProc.mNew;
    }
    
    public void clearUnchangedFields( GwtProcedure proc )
    {
        if ( this.mStudyDescription.equals( proc.mStudyDescription ) )
            this.mStudyDescription = null;
        
        if ( this.mModality.equals( proc.mModality ) )
            this.mModality = null;
    }
    
    public boolean validate()
    {
        if ( mStudyDescription != null && !Validator.isValid( mStudyDescription, true ) )
            return false;
        
        return true;
    }
    
    public String getRequestedProcId()
    {
        return mRequestedProcId;
    }

    public void setRequestedProcId( String requestedProcId )
    {
        mRequestedProcId = requestedProcId;
    }

    public String getStudyDescription()
    {
        return mStudyDescription;
    }

    public void setStudyDescription( String studyDescription )
    {
        mStudyDescription = studyDescription;
    }

    public String getModality()
    {
        return mModality;
    }

    public void setModality( String modality )
    {
        mModality = modality;
    }

    public String getSchedProcStepId()
    {
        return mSchedProcStepId;
    }

    public void setSchedProcStepId( String schedProcStepId )
    {
        mSchedProcStepId = schedProcStepId;
    }

    public boolean isNew()
    {
        return mNew;
    }

    public void setNew( boolean new1 )
    {
        mNew = new1;
    }
    
    /**
     * 
     * @return a displayable string consisting of the modality and the study description, with some 
     * care taken to remove duplicate information
     */
    public String getProcedureDescription()
    {
        String studyDesc = !GwtStringUtils.isBlank( mStudyDescription ) ? mStudyDescription : "";
        studyDesc = trimModality( studyDesc, mModality );
        
        String procDesc = mModality;
        if ( !GwtStringUtils.isBlank( studyDesc ) )
            procDesc = procDesc + " " + studyDesc;
        
        return procDesc;
    }
    
    /**
     * Removes the modality prefix from the description if present
     * COPIED THIS DIRECTLY FROM com.intelerad.rwl.util.extractors.ModalityStudyDescriptionExtractor
     * @param desc
     * @param mod
     * @return trimmed description (without the modality prefix)
     */
    private String trimModality( String desc, String mod )
    {
        if ( desc.length() <= 2 )
            return desc;
        
        String potentialMod = desc.trim().substring( 0, 2 );
        if ( potentialMod.equalsIgnoreCase( mod ) )
            desc = desc.substring( desc.indexOf( mod ) + mod.length() ).trim();
        
        return desc;
    }

    public String getPriority()
    {
        return mPriority;
    }

    public void setPriority( String priority )
    {
        mPriority = priority;
    }
}
