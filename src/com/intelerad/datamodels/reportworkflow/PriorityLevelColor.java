package com.intelerad.datamodels.reportworkflow;

// this class is used by GWT apps and must stay GWT compatible
public enum PriorityLevelColor
{
    RED,
    ORANGE,
    YELLOW;

    public static PriorityLevelColor parseColor( String color )
    {
        return PriorityLevelColor.valueOf( color.toUpperCase() );
    }
}
