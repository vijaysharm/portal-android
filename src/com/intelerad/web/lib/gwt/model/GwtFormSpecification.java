package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Somewhat simplified version of com.intelerad.datamodels.user.UserFormSpecification.  
 * Basically a list of GwtFieldSpecifications.
 * 
 * TODO: verify if we can get away with just a List<GwtFieldSpecification> instead of this object
 */
@SuppressWarnings("serial")
public class GwtFormSpecification implements Serializable
{
    private List<GwtFieldSpecification> mFields = new ArrayList<GwtFieldSpecification>();

    public GwtFormSpecification()
    {    
    }
    
    public GwtFormSpecification( List<GwtFieldSpecification> fields )
    {
        mFields = fields;
    }
    
    public List<GwtFieldSpecification> getFields()
    {
        return mFields;
    }

    public int size()
    {
        return mFields.size();
    }
}
