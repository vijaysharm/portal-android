package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;

import com.intelerad.datamodels.user.FieldMode;


/**
 * 
 * Somewhat simplified version of com.intelerad.datamodels.user.FieldSpecification.  
 * Describes one field of a user form.
 *
 */
@SuppressWarnings("serial")
public class GwtFieldSpecification implements Serializable
{
    private FieldMode mFieldMode;
    
    private boolean mMasked = false; // should field be masked, like for passwords
    
    // only useful for String fields
    private int mMinimumLength = -1;
    private int mMaximumLength = -1;
    
    // these come from ModelProperty.  Not sure if we should bother making these into
    // a GwtModelProperty class.
    //private String mValueClass;
    private String mName;
    private String mDescription;
    
    public GwtFieldSpecification()
    {
    }
    
    /**
     * Constructor for String fields
     */
    public GwtFieldSpecification( FieldMode fieldMode, 
                                  String name,
                                  String description,
                                  int maxLength,
                                  int minLength )
    {
        mMaximumLength = maxLength;
        mMinimumLength = minLength;
        mName = name;
        mDescription = description;
        mFieldMode = fieldMode;
    }
    
    public FieldMode getFieldMode()
    {
        return mFieldMode;
    }
    
    public void setFieldMode( FieldMode fieldMode )
    {
        mFieldMode = fieldMode;
    }
    
    public boolean isMasked()
    {
        return mMasked;
    }
    
    public void setMasked( boolean masked )
    {
        mMasked = masked;
    }
    
    public int getMinimumLength()
    {
        return mMinimumLength;
    }
    
    public void setMinimumLength( int minimumLength )
    {
        mMinimumLength = minimumLength;
    }
    
    public int getMaximumLength()
    {
        return mMaximumLength;
    }
    
    public void setMaximumLength( int maximumLength )
    {
        mMaximumLength = maximumLength;
    }
    
    public String getName()
    {
        return mName;
    }
    
    public void setName( String name )
    {
        mName = name;
    }
    
    public String getDescription()
    {
        return mDescription;
    }
    
    public void setDescription( String description )
    {
        mDescription = description;
    }
    
    public boolean isRequired()
    {
        return mFieldMode == FieldMode.REQUIRED;
    }

    public boolean isEditable()
    {
        return mFieldMode == FieldMode.REQUIRED || mFieldMode == FieldMode.OPTIONAL;
    }
}
