/**
 * 
 */
package com.intelerad.android.portal;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.intelerad.android.portal.models.Session;
import com.intelerad.imageviewer.gwt.model.GwtDataset;
import com.intelerad.imageviewer.gwt.model.GwtPacsImage;

public class DatasetThumbnailAdapter extends BaseAdapter
{

    private static final String LOG_TAG = DatasetThumbnailAdapter.class.getSimpleName();

    private Context mContext;

    private Session mPacsSession;

    private List<GwtDataset> mKeyDataSets;
    private List<GwtDataset> mNonKeyDataSets;

    public DatasetThumbnailAdapter( Context context,
                                    Session pacsSession,
                                    List<GwtDataset> keyDataSets,
                                    List<GwtDataset> nonKeyDataSets )
    {
        mContext = context;
        mPacsSession = pacsSession;
        mKeyDataSets = keyDataSets;
        mNonKeyDataSets = nonKeyDataSets;
    }

    public int getCount()
    {
        return mKeyDataSets.size() + mNonKeyDataSets.size();
    }

    public Object getItem( int position )
    {
        return getDataSet( position );
    }

    private GwtDataset getDataSet( int position )
    {
        GwtDataset dataset = null;
        // key datasets are followed by non-key ones, continuing sequential numbering
        if ( position < mKeyDataSets.size() )
        {
            dataset = mKeyDataSets.get( position );
        }
        else
        {
            dataset = mNonKeyDataSets.get( position - mKeyDataSets.size() );
        }
        return dataset;
    }

    public long getItemId( int position )
    {
        return 0;
    }

    public View getView( int position, View convertView, ViewGroup parent )
    {
        DatasetThumbnail thumbnail;
        if ( convertView == null )
        {
            // if it's not recycled, initialize some attributes
            thumbnail = new DatasetThumbnail( mContext );
            
            int width = mContext.getResources().getDimensionPixelSize( R.dimen.dataset_thumbnail_width );         
            int height = mContext.getResources().getDimensionPixelSize( R.dimen.dataset_thumbnail_height );         
            thumbnail.setLayoutParams( new GridView.LayoutParams( width, height ) );
            thumbnail.setPadding( 4, 4, 4, 4 );
            // thumbnail.setScaleType(ImageView.ScaleType.FIT_CENTER);

            GwtDataset dataset = getDataSet( position );
            thumbnail.setDataset( dataset );

///FIXME should we force this image to be used anyway? or only if convertView is null?

            thumbnail.setImageResource( R.drawable.image_loading_animated ); // temporary, until loaded

/// FIXME should we better use some other image than the first? like here:
//mDataset.getParentSeries().getRepresentativeImage();
///
            GwtPacsImage pacsImage = dataset.getImages().get( 0 ); // first in the dataset
///FIXME tmp
Log.i( LOG_TAG, "* * * NEED TO GET IMAGE for position " + position );
////
            // schedule the loading of real image from PACS
            ImageLoadingService.scheduleImageLoadingTask( mContext, new ImageLoadingTask(
                thumbnail, mPacsSession, pacsImage, width, height ) );// /FIXME hardcoded
        }
        else
        {
///FIXME tmp
Log.i( LOG_TAG, "* * * RE-USING VIEW for position " + position );
///
            thumbnail = (DatasetThumbnail) convertView; ///FIXME what is this? 
                                                        // should we re-initialize it again?
        }

        return thumbnail;
    }
}
