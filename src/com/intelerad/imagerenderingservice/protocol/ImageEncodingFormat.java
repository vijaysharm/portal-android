package com.intelerad.imagerenderingservice.protocol;

/**
 * Enumerations of possible image encoding formats.
 */
// this class is used by GWT apps and must stay GWT compatible
public enum ImageEncodingFormat
{
    JPEG,
    PNG,
    UNKNOWN;

    /**
     * @param name
     *            the name of the image encoding format
     * @return the corresponding image encoding format or {@link #UNKNOWN} if
     *         the encoding format is not recognized.
     */
    public static ImageEncodingFormat lookup( String name )
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
