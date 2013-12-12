package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.Map;

import com.intelerad.datamodels.imagerendering.ImageQuality;
//import com.intelerad.datamodels.imagerendering.VolumeRenderParameters;

/**
 * This class is responsible for encoding an image rendering request as a URL.
 * 
 * <p>The uniqueness of the URL allows web browsers to cache the image.</p>
 * 
 * <p>TODO: Create separate parameters for rotation and azimuth (for volumes).</p>
 */
@SuppressWarnings("serial")
public class DefaultImageUrlEncoder implements ImageUrlEncoder, Serializable
{
    public static final String STANDARD_URL = "/Portal/jpeg";
    
    public static final String PARAM_STUDY_INSTANCE_UID         = "studyInstanceUid";
    public static final String PARAM_SERIES_INSTANCE_UID        = "seriesInstanceUid";
    public static final String PARAM_IMAGE_UID                  = "imageUid"; 
    public static final String PARAM_IMAGE_WIDTH                = "imageWidth";
    public static final String PARAM_IMAGE_HEIGHT               = "imageHeight";
    public static final String PARAM_DELTA_SCREEN_PAN_X         = "deltaScreenPanX";
    public static final String PARAM_DELTA_SCREEN_PAN_Y         = "deltaScreenPanY";
    public static final String PARAM_ZOOM_FACTOR                = "zoomFactor";
    public static final String PARAM_PS_SOP_INSTANCE_UID        = "psSopInstanceUid";
    public static final String PARAM_PS_SERIES_INSTANCE_UID     = "psSeriesInstanceUid";
    public static final String PARAM_IS_KEY_IMAGE               = "isKeyImage";
    public static final String PARAM_IS_VOLUME                  = "isVolume";
    public static final String PARAM_IMAGE_QUALITY              = "imageQuality";
    public static final String PARAM_WINDOW_CENTER              = "windowCenter";
    public static final String PARAM_WINDOW_WIDTH               = "windowWidth";
    public static final String PARAM_DATASET_INSTANCE_ID        = "datasetInsId";
    
    @Override
    public String getUrl( ImageRequest request )
    {
        StringBuffer url = new StringBuffer( STANDARD_URL );

        url.append( "?" );
        url.append( PARAM_STUDY_INSTANCE_UID );
        url.append( "=" );
        url.append( request.getStudyInstanceUid() );
        
        appendUrlParameter( url, PARAM_SERIES_INSTANCE_UID, request.getSeriesInstanceUid() );
        appendUrlParameter( url, PARAM_IMAGE_UID, String.valueOf( request.getImageUid() ) );
        appendUrlParameter( url, PARAM_IS_VOLUME, String.valueOf( request.isVolume() ) );
        
        if ( request.getImageWidth() > 0 )
            appendUrlParameter( url, PARAM_IMAGE_WIDTH, String.valueOf( request.getImageWidth() ) );
        
        if ( request.getImageHeight() > 0 )
            appendUrlParameter( url, PARAM_IMAGE_HEIGHT, String.valueOf( request.getImageHeight() ) );
        
        if ( request.getPsSopInstanceUid() != null )
            appendUrlParameter( url, PARAM_PS_SOP_INSTANCE_UID, request.getPsSopInstanceUid() );
        
        if ( request.getPsSeriesInstanceUid() != null )
            appendUrlParameter( url, PARAM_PS_SERIES_INSTANCE_UID, request.getPsSeriesInstanceUid() );
        
//        if ( request.isVolume() )
//            get3dUrl( url, (Image3dRequest)request );
//        else
            get2dUrl( url, (Image2dRequest)request );
        
        return url.toString();
    }
    
    // Encode the 2D-specific parameters by appending them to the URL,
    private void get2dUrl( StringBuffer url, Image2dRequest request )
    {    
        appendUrlParameter( url, PARAM_DELTA_SCREEN_PAN_X, String.valueOf( request.getDeltaScreenPanX() ) );
        appendUrlParameter( url, PARAM_DELTA_SCREEN_PAN_Y, String.valueOf( request.getDeltaScreenPanY()  ) );
        appendUrlParameter( url, PARAM_IMAGE_QUALITY, request.getImageQuality().name() );        
        if ( request.getZoomFactor() >= 1 )
            appendUrlParameter( url, PARAM_ZOOM_FACTOR, String.valueOf( request.getZoomFactor() ) );
        
        appendUrlParameter( url, PARAM_WINDOW_CENTER, String.valueOf( request.getWindowCenter() ) );
        appendUrlParameter( url, PARAM_WINDOW_WIDTH, String.valueOf( request.getWindowWidth() ) );
        appendUrlParameter( url, PARAM_IS_KEY_IMAGE, String.valueOf( request.isKeyImage() ) );
        appendUrlParameter( url, PARAM_DATASET_INSTANCE_ID, request.getDatasetInstanceId() );
    }

    // Encode the 3D-specific parameters by appending them to the URL.
//    private void get3dUrl( StringBuffer url, Image3dRequest request )
//    {
//        VolumeRenderParameters params = request.getVolumeRenderParameters();
//        appendUrlParameter( url, PARAM_DELTA_SCREEN_PAN_X, String.valueOf( request.getPanX() ) );
//        appendUrlParameter( url, PARAM_DELTA_SCREEN_PAN_Y, String.valueOf( request.getPanY() ) );
//        appendUrlParameter( url, PARAM_IMAGE_QUALITY, params.getImageQuality().name() );
//        if ( params.getZoomFactor() >= 1 )
//            appendUrlParameter( url, PARAM_ZOOM_FACTOR, String.valueOf( params.getZoomFactor() ) );
//    }
    
    @Override
    public ImageRequest decode( Map<String, String[]> map )
    {
        ImageRequest renderRequest = null;
        
//        Boolean isVolume = Boolean.valueOf( map.get( PARAM_IS_VOLUME )[0] );
//        
//        if ( isVolume )
//        {
//            renderRequest = new Image3dRequest();
//            decode3d( (Image3dRequest) renderRequest, map );
//        }
//        else
//        {
            renderRequest = new Image2dRequest();
            decode2d( (Image2dRequest) renderRequest, map );
//        }
       
        renderRequest.setStudyInstanceUid( getStringValue( PARAM_STUDY_INSTANCE_UID, map, "" ) );
        renderRequest.setSeriesInstanceUid( getStringValue( PARAM_SERIES_INSTANCE_UID, map, "" ) );
        renderRequest.setImageUid( getStringValue( PARAM_IMAGE_UID, map, "" ) );
        renderRequest.setImageWidth( getIntValue( PARAM_IMAGE_WIDTH, map, -1 ) );
        renderRequest.setImageHeight( getIntValue( PARAM_IMAGE_HEIGHT, map, -1 ) );
        renderRequest.setPsSopInstanceUid( getStringValue( PARAM_PS_SOP_INSTANCE_UID, map, "" ) );        
        renderRequest.setPsSeriesInstanceUid( getStringValue( PARAM_PS_SERIES_INSTANCE_UID, map, "" ) );
         
        return renderRequest;
    }
    
    // Decode the 2D-specific parameters and set the request's ImageRenderParameters object.
    private void decode2d( Image2dRequest request, Map<String, String[]> map )
    {
        request.setKeyImage( getBoolValue( PARAM_IS_KEY_IMAGE, map, false ) );
                
        request.setDeltaScreenPanX( getDoubleValue( PARAM_DELTA_SCREEN_PAN_X, map, 0 ) );
        request.setDeltaScreenPanY( getDoubleValue( PARAM_DELTA_SCREEN_PAN_Y, map, 0 ) );
        request.setZoomFactor( getDoubleValue( PARAM_ZOOM_FACTOR, map, 1 ) );
        
        String imageQualityName = getStringValue( PARAM_IMAGE_QUALITY, map, ImageQuality.UNKNOWN.name() );
        request.setImageQuality( ImageQuality.valueOf( imageQualityName ) );
        
        request.setWindowCenter( getDoubleValue( PARAM_WINDOW_CENTER, map, Double.MAX_VALUE ) );
        request.setWindowWidth( getDoubleValue( PARAM_WINDOW_WIDTH, map, Double.MAX_VALUE ) );
        request.setDatasetInstanceId( getStringValue( PARAM_DATASET_INSTANCE_ID, map, "" ) );
    }

    // Decode the 3D-specific parameters and set the request's VolumeRenderParameters object.
//    private void decode3d( Image3dRequest request, Map<String, String[]> map )
//    { 
//        VolumeRenderParameters volumeRenderParameters = new VolumeRenderParameters();
//        volumeRenderParameters.setZoomFactor( getDoubleValue( PARAM_ZOOM_FACTOR, map, 1 ) );
//        request.setPanX( getDoubleValue( PARAM_DELTA_SCREEN_PAN_X, map, 0 ) );
//        request.setPanY( getDoubleValue( PARAM_DELTA_SCREEN_PAN_Y, map, 0 ) );
//        
//        String imageQualityName = getStringValue( PARAM_IMAGE_QUALITY, map, ImageQuality.UNKNOWN.name() );
//        volumeRenderParameters.setImageQuality( ImageQuality.valueOf( imageQualityName ) );
//        
//        request.setVolumeRenderParameters( volumeRenderParameters );
//    }
    
    private void appendUrlParameter( StringBuffer url, String key, String value )
    {
        url.append( "&" );
        url.append( key );
        url.append( "=" );
        url.append( value );
    }
    
    /*
     * Get a String value from the given Map. If the key maps to multiple values, then the first value
     * is returned. See {@link ServletRequest.getParameterMap()}
     * 
     * @returns The default value if the map is empty, or if the map does not contain the key.
     */
    private static String getStringValue( String key, Map<String, String[]> map, String defaultValue )
    {
        try
        {
            return getValue( key, map );
        }
        catch ( Throwable t )
        {
            return defaultValue;
        }
    }
    
    private static String getValue( String key, Map<String, String[]> map ) throws IllegalArgumentException
    {
        if ( ( map == null ) || ( !map.containsKey( key ) ) )
            throw new IllegalArgumentException( "The value could not be found." );
        String[] values = map.get( key );
        return values[0];
    }
    
    /*
     * Get a Integer value from the map. If the key maps to multiple values, then the first value
     * is returned. See {@link ServletRequest.getParameterMap()}
     * 
     * @returns The default value if the map is empty, or if the map does not contain the key.
     */    
    private static int getIntValue( String key, Map<String, String[]> map, int defaultValue )
    {
        try
        {
            return Integer.parseInt( getValue( key, map ) );
        }
        catch ( Throwable t )
        {
            return defaultValue;
        }
    }
    
    /*
     * Get a Boolean value from the map. If the key maps to multiple values, then the first value
     * is returned. See {@link ServletRequest.getParameterMap()}
     * 
     * @returns The default value if the map is empty, or if the map does not contain the key.
     */    
    private static boolean getBoolValue( String key, Map<String, String[]> map, boolean defaultValue )
    {
        try
        {
            return Boolean.parseBoolean( getValue( key, map ) );
        }
        catch ( Throwable t )
        {
            return defaultValue;
        }
    }
    
    /*
     * Get a Double value from the map. If the key maps to multiple values, then the first value
     * is returned. See {@link ServletRequest.getParameterMap()}
     * 
     * @returns The default value if the map is empty, or if the map does not contain the key.
     */    
    private static double getDoubleValue( String key, Map<String, String[]> map, double defaultValue )
    {
        try
        {
            return Double.parseDouble( getValue( key, map ) );
        }
        catch ( Throwable t )
        {
            return defaultValue;
        }
    }
}
