package com.intelerad.imageviewer.gwt.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class GwtStudy extends GwtBasicStudy<GwtSeries>
{
    protected Set<DicomError> mDicomErrors = new HashSet<DicomError>();

    public GwtStudy()
    {
        super();
    }
    
    public GwtStudy( GwtBasicStudy<GwtSeries> basicStudy )
    {
        super( basicStudy );
    }
    
    /**
     * 
     * @return all the key images in the study
     */
    public List<GwtPacsImage> getAllKeyImages()
    {
        List<GwtPacsImage> keyImages = new ArrayList<GwtPacsImage>();
        if ( mSeriesList != null )
        {
            for( GwtSeries s: mSeriesList )
            {
                if ( s.isKeyImageSeries() && s.isLoadable() )
                {
                    keyImages.addAll( s.getAllImages() );
                }
            }
        }
        
        return keyImages;
    }
   
    /**
     * 
     * @return list of images where each one represents a dataset
     */
    public List<GwtPacsImage> getDatasetImages()
    {
        List<GwtPacsImage> datasetImages = new ArrayList<GwtPacsImage>();
        if ( mSeriesList != null )
        {
            for( GwtSeries s: mSeriesList )
            {
                if ( s.isLoadable() )
                    datasetImages.addAll( s.getDatasetImages() );
            }
        }
        
        return datasetImages;
    }
    
    /**
     * Get all of the datasets for a given study.
     */
    public List<GwtDataset> getDatasets()
    {
        List<GwtDataset> datasets = new ArrayList<GwtDataset>();

        if ( getSeriesList() == null || getSeriesList().isEmpty() )
            return datasets;

        for ( GwtSeries series : getSeriesList() )
            datasets.addAll( series.getDatasets() );

        return datasets;
    }

    /**
     * Get all of the datasets for a given study.
     * 
     * @param isKeyImages Should the returned datasets be key image datasets?
     */
    private List<GwtDataset> getFilteredDatasets( boolean isKeyImages )
    {
        List<GwtDataset> datasets = new ArrayList<GwtDataset>();

        if ( getSeriesList() == null || getSeriesList().isEmpty() )
            return datasets;

        for ( GwtSeries series : getSeriesList() )
        {
            if ( series.isKeyImageSeries() == isKeyImages )
                datasets.addAll( series.getDatasets() );
        }

        return datasets;
    }
    
    public List<GwtDataset> getKeyDatasets()
    {
        return getFilteredDatasets( true );
    }
    
    public List<GwtDataset> getNonKeyDatasets()
    {
        return getFilteredDatasets( false );
    }
    
    /**
     * @return Is there an image series that is unavailable or offline?
     */
    public boolean hasUnloadableImages()
    {
        for ( GwtSeries series : getSeriesList() )
        {
            if ( !series.isPresentationStateSeries() && !series.isLoadable() )
                return true;
        }
        return false;
    }
    
    public boolean hasOfflineSeries()
    {
        for ( GwtSeries series : getSeriesList() )
        {
            if ( !series.isOnline() )
                return true;
        }
        return false;
    }
    
    public GwtSeries getSeries( String seriesInsUid )
    {
        for ( GwtSeries series: getSeriesList() )
        {
            if ( series.getSeriesInstanceUid().equals( seriesInsUid ) )
                return series;
        }
        return null;
    }    

    /**
     * @return The series that are offline
     */
    public Set<GwtSeries> getOfflineSeries()
    {
    	Set<GwtSeries> offlineSeries = new HashSet<GwtSeries>();
        for ( GwtSeries series : getSeriesList() )
        {
            if ( !series.isOnline() )
                offlineSeries.add( series );
        }
        return offlineSeries;
    }
    
    public void addDicomError( DicomError error )
    {
        mDicomErrors.add( error );
    }

    public Set<DicomError> getDicomErros()
    {
        return mDicomErrors;
    }

    private void checkForUnloadableImages()
    {
        for ( GwtSeries series : mSeriesList )
        {
            if ( !series.isPresentationStateSeries() && !series.isLoadable() )
                addDicomError( DicomError.UNLOADABLE_IMAGE );
        }
    }

    public void setSeriesList( List<GwtSeries> seriesList )
    {
        super.setSeriesList( seriesList );
        checkForUnloadableImages();
    }

    /**
     * Used to create an instance of GwtStudy using basic study information.
     * 
     * @param basicStudy A study without image data (lightweight)
     * 
     * @return A empty GwtStudy, without image information
     */
    public static GwtStudy convertBasicStudy( GwtSimpleStudy basicStudy )
    {
        GwtBasicStudy<GwtSeries> studyNoImageInfo = new GwtBasicStudy<GwtSeries>();
        studyNoImageInfo.copyStudyInfo( basicStudy );
        
        List<GwtSeries> seriesList = new ArrayList<GwtSeries>();
        for ( GwtBasicSeries basicSeries : basicStudy.getSeriesList() )
            seriesList.add( new GwtSeries( basicSeries ) );
        studyNoImageInfo.setSeriesList( seriesList );

        return new GwtStudy( studyNoImageInfo );
    }
}
