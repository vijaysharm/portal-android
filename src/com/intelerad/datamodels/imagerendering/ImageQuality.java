package com.intelerad.datamodels.imagerendering;

/**
 * A hint on the image quality to promote when rendering images. 
 */
// this class is used by GWT apps and must stay GWT compatible
public enum ImageQuality
{
    BEST,
    FASTEST,
    UNKNOWN;
    
    public static ImageQuality lookup( String name )
    {
        try
        {
            return valueOf( name );
        } 
        catch ( IllegalArgumentException ex )
        {
            return UNKNOWN;
        }
    }
    
    
}
