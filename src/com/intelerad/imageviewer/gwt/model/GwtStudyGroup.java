package com.intelerad.imageviewer.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a collection of GwtStudy objects that share the same accession number.
 */
@SuppressWarnings("serial")
public class GwtStudyGroup extends GwtBasicStudyGroup<GwtStudy> implements Serializable
{ 
    public GwtStudyGroup()
    {
        super();
    }

    public List<GwtPacsImage> getAllKeyImages()
    {
        List<GwtPacsImage> keyImages = new ArrayList<GwtPacsImage>();
        if ( mStudies != null )
        {
            for( GwtStudy s: mStudies )
            {   
                keyImages.addAll( s.getAllKeyImages() );
            }
        }
        return keyImages;
    }
    
    public List<GwtDataset> getKeyDatasets()
    {
        List<GwtDataset> datasets = new ArrayList<GwtDataset>();
        if ( mStudies != null )
        {
            for( GwtStudy s: mStudies )
                datasets.addAll( s.getKeyDatasets() );
        }
        return datasets;
    }
    
    public List<GwtDataset> getNonKeyDatasets()
    {
        List<GwtDataset> datasets = new ArrayList<GwtDataset>();
        if ( mStudies != null )
        {
            for( GwtStudy s: mStudies )
                datasets.addAll( s.getNonKeyDatasets() );
        }
        return datasets;
    }
    
    /**
     * 
     * @return list of images where each one represents a dataset
     */
    public List<GwtPacsImage> getDatasetImages()
    {
        List<GwtPacsImage> datasetImages = new ArrayList<GwtPacsImage>();
        if ( mStudies != null )
        {
            for( GwtStudy s: mStudies )
                datasetImages.addAll( s.getDatasetImages() );
        }
        return datasetImages;
    }

    public Set<DicomError> getDicomErrors()
    {
        final Set<DicomError> errors = new HashSet<DicomError>();
        if ( mStudies != null )
        {
            for( GwtStudy s: mStudies )
                errors.addAll( s.getDicomErros() );
        }
        return errors;
    }
    
    public List<GwtDataset> getAllDatasets( boolean includeNonKeyImages )
    {
        List<GwtDataset> allDatasets = new ArrayList<GwtDataset>( getKeyDatasets() );   
        if ( includeNonKeyImages )
        {
            allDatasets.addAll( getNonKeyDatasets() );
        }
        return allDatasets;
    }
    
    public GwtDataset getNextDataset( GwtDataset currentDataset, boolean includeNonKeyImages )
    {
        List<GwtDataset> allDatasets = getAllDatasets( includeNonKeyImages );
        int currentIndex = allDatasets.indexOf( currentDataset );
        int newIndex = 0;
        
        GwtDataset datasetToDisplay = null;
        
        do
        {
            if ( currentIndex == -1 )
            {
                throw new IllegalArgumentException( "The current dataset could not be found." );
            }
            else if ( currentIndex >= allDatasets.size() - 1 )
            {
                newIndex = 0; // wrap around
            }
            else
            {
                newIndex = currentIndex + 1;
            }

            datasetToDisplay = allDatasets.get( newIndex );
            currentIndex++;
        }
        while ( datasetToDisplay.getImages().size() == 0 );
        return datasetToDisplay;
    }
    
    public GwtDataset getPrevDataset( GwtDataset currentDataset, boolean includeNonKeyImages )
    {
        List<GwtDataset> allDatasets = getAllDatasets( includeNonKeyImages );
        int currentIndex = allDatasets.indexOf( currentDataset );
        int newIndex = 0;
        
        GwtDataset datasetToDisplay = null;
        
        do
        {
            if ( currentIndex == -1 )
            {
                throw new IllegalArgumentException( "The current dataset could not be found." );
            }
            else if ( currentIndex <= 0 )
            {
                newIndex = allDatasets.size() - 1; // wrap around
            }
            else
            {
                newIndex = currentIndex - 1;
            }

            datasetToDisplay = allDatasets.get( newIndex );
            currentIndex--;
        }
        while ( datasetToDisplay.getImages().size() == 0 );
        return datasetToDisplay;
    }
    
    public boolean isLastDataset( GwtDataset currentDataset, boolean includeNonKeyImages )
    {        
        List<GwtDataset> allDatasets = getAllDatasets( includeNonKeyImages);
        return ( allDatasets.indexOf( currentDataset ) == getLastDatasetIndex( allDatasets ) );
    }

    public boolean isFirstDataset( GwtDataset currentDataset, boolean includeNonKeyImages )
    {
        List<GwtDataset> allDatasets = getAllDatasets( includeNonKeyImages );
        return ( allDatasets.indexOf( currentDataset ) == getFirstDatasetIndex( allDatasets ) );
    } 
    
    private static int getLastDatasetIndex( List<GwtDataset> datasets )
    {
        int undefined = -1;
        
        for ( int i = datasets.size() - 1; i >= 0 ; i-- )
        {
            if ( datasets.get( i ).getImages().size() > 0 )
            {
                return i;
            }
        }
        
        return undefined;
    }
    
    /**
     * Returns the index of the first dataset that contains supported images. In the case
     * that the series contains MPEGs, there will be a dataset, but the image will be null. 
     * 
     * @param datasets
     * @return the index of the first dataset that contains supported images.
     */
    private static int getFirstDatasetIndex( List<GwtDataset> datasets )
    {
        int undefined = -1;
        
        for ( int i = 0; i < datasets.size(); i++ )
        {
            if ( datasets.get( i ).getImages().size() > 0 )
            {
                return i;
            }
        }
        
        return undefined;
    }
}
