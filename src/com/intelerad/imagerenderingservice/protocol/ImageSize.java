package com.intelerad.imagerenderingservice.protocol;

/**
 * The dimensions of an image.
 */
// this class is used by GWT apps and must stay GWT compatible
public class ImageSize
{
    private final int mWidth;
    private final int mHeight;
    
    public ImageSize( int width, int height )
    {
        mWidth = width;
        mHeight = height;
    }

    public int getWidth()
    {
        return mWidth;
    }
    
    public int getHeight()
    {
        return mHeight;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + mHeight;
        result = prime * result + mWidth;
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        
        ImageSize other = (ImageSize) obj;
        
        if ( mHeight != other.mHeight )
            return false;
        if ( mWidth != other.mWidth )
            return false;
        
        return true;
    }
}
