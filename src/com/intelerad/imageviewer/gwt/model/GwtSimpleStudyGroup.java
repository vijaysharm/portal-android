package com.intelerad.imageviewer.gwt.model;

/**
 * 
 * This is just a class which saves typing.  Any time you want a 
 * GwtBasicGroupedStudies<GwtSimpleStudy>, now all you need is a 
 * GwtSimpleStudyGroup.
 * 
 * It turns an expression like 
 * 
 * List<GwtBasicGroupedStudies<GwtSimpleStudy>>
 * 
 * into
 * 
 * List<GwtSimpleStudyGroup>
 * 
 * which is marginally easier to read at a glance.
 *
 */
@SuppressWarnings("serial")
public class GwtSimpleStudyGroup extends GwtBasicStudyGroup<GwtSimpleStudy>
{
    public GwtSimpleStudyGroup()
    {
        super();
    }
}
