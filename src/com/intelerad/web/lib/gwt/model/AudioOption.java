package com.intelerad.web.lib.gwt.model;

public enum AudioOption 
{
    NEVER("never"), 
    IF_NO_REPORT("if_no_report"),
    IF_NO_REPORT_AND_CRITICAL("if_no_report_and_critical");
    
    private String mName;

    private AudioOption()
    {    
    }

    private AudioOption( String name )
    {
        mName = name;
    }

    public String getName()
    {
        return mName;
    }

    public static AudioOption getAudioOption( String name )
    {
        for ( AudioOption type : AudioOption.values() )
        {
            if ( type.getName().equals( name ) )
            {
                return type;
            }
        }
            
        throw new IllegalArgumentException( "Unknown AudioOption: " + name );
    }
}