package com.intelerad.web.portal.gwt.model;

//WARNING: these enums are persisted
public enum DateOption
{
    TWO_HOURS( 2 ),
    SIX_HOURS( 6 ),
    TWELVE_HOURS( 12 ),
    DAY( 24 ),
    TWO_DAYS( 48 ),
    THREE_DAYS( 72 ),
    SEVEN_DAYS( 168 ),
    FIFTEEN_DAYS( 360 ),
    THIRTY_ONE_DAYS( 744 );
    
    private int mHours;

    private DateOption( int hours )
    {
        mHours = hours;
    }
    
    public int getHours()
    {
        return mHours;
    }
}
