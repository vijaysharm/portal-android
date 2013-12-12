package com.intelerad.imagerenderingservice.protocol;

/**
 * The set of parameters used to specify the characteristics of a rendered
 * output image.
 * 
 * @see OutputImage
 */
// this class is used by GWT apps and must stay GWT compatible
public class OutputImageParameters
{
    private final ImageEncodingFormat mEncodingFormat;
    private final ImageSize mImageSize;
    private final double mCompressionQuality;
    
    public OutputImageParameters( ImageEncodingFormat imageEncodingFormat,
                                  ImageSize imageSize,
                                  double compressionQuality )
    {
        mEncodingFormat = imageEncodingFormat;
        mImageSize = imageSize;
        mCompressionQuality = compressionQuality;
    }

    /**
     * Return the format in which the image data shall be encoded to. See
     * {@link ImageEncodingFormat} for the list of possible formats.
     * 
     * @return the requested image data encoding format
     */
    public ImageEncodingFormat getEncodingFormat()
    {
        return mEncodingFormat;
    }
    
    /**
     * @return the requested target image size
     */
    public ImageSize getImageSize()
    {
        return mImageSize;
    }
    
    /**
     * @return the requested image compression quality
     */
    public double getCompressionQuality()
    {
        return mCompressionQuality;
    }
}
