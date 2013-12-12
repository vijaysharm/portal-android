package com.intelerad.datamodels.user;

// this class is used by GWT apps and must stay GWT compatible
public enum FieldMode
{
    REQUIRED( "required" ),
    OPTIONAL( "optional" ),
    READONLY( "readonly" ), // aka "display only"
    HIDDEN( "hidden" ); // aka "not shown"

    private String mName;
    
    private FieldMode()
    {    
    }
    
    private FieldMode( String name )
    {
        mName = name;
    }
    
    public String getName()
    {
        return mName;
    }

    public static FieldMode getMode( String name )
    {
        for ( FieldMode type : FieldMode.values() )
        {
            if ( type.getName().equals( name ) )
            {
                return type;
            }
        }
                
        throw new IllegalArgumentException( "Unknown FieldMode: " + name );
    }
}
