package com.intelerad.imageviewer.gwt.model;

/**
 * 
 * This is just a class which saves typing.  Any time you want a 
 * GwtBasicStudy<GwtBasicSeries>, now all you need is a GwtSimpleStudy.
 * 
 * It turns an expression like 
 * 
 * List<GwtBasicStudy<GwtBasicSeries>>
 * 
 * into
 * 
 * List<GwtSimpleStudy>
 * 
 * which is marginally easier to read at a glance.
 *
 */
@SuppressWarnings("serial")
public class GwtSimpleStudy extends GwtBasicStudy<GwtBasicSeries>
{
    public GwtSimpleStudy()
    {
        super();
    }
    
    public GwtSimpleStudy( String studyInstanceUid )
    {
        super( studyInstanceUid );
    }
    
    public GwtSimpleStudy( GwtBasicStudy<GwtBasicSeries> basicStudy )
    {
        super( basicStudy );
    }
}
