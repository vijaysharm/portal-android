package com.intelerad.imageviewer.gwt.model;

import java.util.Map;

/**
 * Classes that implement this interface are responsible for encoding image rendering
 * parameters into a URL. The URL refers to the servlet that will interpret the image
 * rendering parameters and produce an image.
 */
public interface ImageUrlEncoder
{
    /**
     * Get the URL for the given image request for a 2D or 3D image.
     */
    public String getUrl( ImageRequest request );
    
    /**
     * Decode the map of parameters and reconstruct the original image request.
     * @param uriParameterMap A map of key-value pairs.
     * @return The original image request.
     */
    public ImageRequest decode( Map<String, String[]> uriParameterMap );
}
