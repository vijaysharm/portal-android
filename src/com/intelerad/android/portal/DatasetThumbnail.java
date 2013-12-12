package com.intelerad.android.portal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.intelerad.imageviewer.gwt.model.GwtDataset;
import com.intelerad.imageviewer.gwt.model.GwtSeries;

public class DatasetThumbnail extends ImageView implements BitmapReceiver
{
    private GwtDataset mDataset = null;
    
    private Paint mDescriptionBackgroundFillPaint = new Paint();

    private String mDatasetDescription = null;
    private Paint mPaint = new Paint();
    private float mFontSpacing;
    private float mDescriptionWidth;
    
    private String mDatasetDescription2 = null;
    private Paint mPaint2 = new Paint();
    private float mFontSpacing2;
    private float mDescriptionWidth2;
    
    private static Bitmap sKeyBitmap = null; // one bitmap for all thumbnails
    
    private static Bitmap getKeyDatasetBitmap( Context context ) // lazy bitmap loader
    {
        if ( sKeyBitmap == null )
        {
            sKeyBitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.key_image );
        }
        
        return sKeyBitmap;
    }
    
    public DatasetThumbnail( Context context )
    {
        super( context );
        initialize();
    }

    public DatasetThumbnail( Context context, AttributeSet attrs )
    {
        super( context, attrs );
        initialize();
    }

    private void initialize()
    {
    }

    public void setDataset( GwtDataset dataset )
    {
        mDataset = dataset;
        
        if ( mDataset != null )
        {
            GwtSeries parentSeries = mDataset.getParentSeries();
            
/// FIXME use resources for colors, strings and dimensions
            
            mDescriptionBackgroundFillPaint.setColor( Color.GRAY );
            mDescriptionBackgroundFillPaint.setAlpha( 0xA0 );
            mDescriptionBackgroundFillPaint.setStyle( Style.FILL );
            
            // line 1: modality and series description
            
            String modality = parentSeries.getModality();
            String seriesDescription = parentSeries.getSeriesDescription();
            mDatasetDescription  = modality + " " + seriesDescription;
            
            mPaint.setColor(Color.YELLOW); // FIXME use resources
            mPaint.setTextSize( 12.0f ); // FIXME use resources
            mPaint.setTypeface( Typeface.DEFAULT_BOLD ); // FIXME use resources
            mPaint.setAntiAlias(true);

            mDescriptionWidth = mPaint.measureText( mDatasetDescription );
            mFontSpacing = mPaint.getFontSpacing();
            
            // line 2: image count
            
            int imageCount = mDataset.getImageCount();
            mDatasetDescription2 = "" + imageCount + " " + (imageCount == 1? "image" : "images");
            
            mPaint2.setColor(Color.GREEN); // FIXME use resources
            mPaint2.setTextSize( 10.0f ); // FIXME use resources
            mPaint2.setTypeface( Typeface.DEFAULT ); // FIXME use resources
            mPaint2.setAntiAlias(true);

            mDescriptionWidth2 = mPaint2.measureText( mDatasetDescription2 );
            mFontSpacing2 = mPaint2.getFontSpacing();
        }
    }

    public GwtDataset getDataset()
    {
        return mDataset;
    }

    @Override
    protected void onDraw( Canvas canvas )
    {
        super.onDraw( canvas );

        if (mDataset == null)
        {
            return; // nothing to do here
        }
        
        // depending on whether this is a key or a non-key image, overlay a key bitmap
        if ( mDataset.isKeyImageDataset() )
        {
            canvas.drawBitmap( getKeyDatasetBitmap( getContext() ), 0.0f, 0.0f, mPaint );
        }
        
        float x = (getWidth() - mDescriptionWidth) / 2;
        float y = getHeight() - mFontSpacing - mFontSpacing2; // line 1 above line 2
        
        float x2 = (getWidth() - mDescriptionWidth2) / 2;
        float y2 = getHeight() - mFontSpacing2;
        
        canvas.drawRoundRect( new RectF( 0.0f, y - mFontSpacing, getRight(), getBottom() ),
                              3, 3, mDescriptionBackgroundFillPaint );
        
        canvas.drawText( mDatasetDescription, x, y, mPaint );
        canvas.drawText( mDatasetDescription2, x2, y2, mPaint2 );
    }

    @Override
    public void onBitmapDelivery( Bitmap bitmap, Object sourceObject )
    {
        setImageBitmap( bitmap );
        postInvalidate();
    }
}
