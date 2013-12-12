package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;

/**
 * Creates ImageUrlEncoder objects.
 * 
 * Extend this class to inject a different type of url encoder into the GwtPacsImage object.
 */
@SuppressWarnings("serial")
public class ImageUrlEncoderFactory implements Serializable
{
    public ImageUrlEncoder getEncoder()
    {
        return new DefaultImageUrlEncoder();
    }
}
