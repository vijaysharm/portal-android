package com.intelerad.web.portal.gwt.model;

/**
 * 
 * This enum is used for a couple of things:
 * 
 * - it defines the index in the notification view filter pull down list corresponding 
 *   to the filter object
 * - it defines the URL slug used to identify the filter object
 */
public enum NotificationFilter
{
    ALL ( 0, "all" ),
    PREFERRED( 1, "preferred" ),
    CRITICAL ( 2, "critical" ), 
    CRITICIAL_PENDING ( 3, "criticalpending" );
    
    private NotificationFilter( int index, String slug )
    {
        mIndex = index;
        mSlug = slug;
    }
    
    /**
     * The "index" here refers to this filters place in the pull down list on 
     * the notification page 
     */
    public int getIndex()
    {
        return mIndex;
    }
    
    /**
     * The "slug" is Internet jargon for "part of a URL".  This is the URL
     * representation of the filter; it appears in the URL as a way to tell
     * which filter is currently being applied. 
     */
    public String getSlug()
    {
        return mSlug;
    }
    
    public static NotificationFilter getFilterByIndex( int index )
    {
        for( NotificationFilter filter : NotificationFilter.values() )
        {
            if ( filter.getIndex() == index )
                return filter;
        }
        return ALL;
    }
    
    public static NotificationFilter getFilterBySlug( String slug )
    {
        for( NotificationFilter filter : NotificationFilter.values() )
        {
            if ( filter.getSlug().equals( slug ) )
                return filter;
        }
        return ALL;
    }
    
    private String mSlug;
    private int mIndex;
}