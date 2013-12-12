package com.intelerad.datamodels.reportworkflow;

// this class is used by GWT apps and must stay GWT compatible
public enum TimeUnit
{
    MINUTES,
    HOURS,
    DAYS;

    public static TimeUnit parseTimeUnit( String timeUnit )
    {
        return TimeUnit.valueOf( timeUnit.toUpperCase() );
    }
}
