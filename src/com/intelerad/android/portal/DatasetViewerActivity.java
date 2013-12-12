package com.intelerad.android.portal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DatasetViewerActivity extends Activity implements OnClickListener
{

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dataset_viewer );

        // /create DicomSeries asynchronously?

        // /FIXME: create study/series objects correctly, use UIDs
        Bundle parameterBundle = getIntent().getExtras();
        String studyInstanceUid = parameterBundle.getString( "StudyInstanceUID" ); // FIXME
                                                                                   // un-hardcode!
        // /
        Log.i( LOG_TAG, "study instance UID=" + studyInstanceUid );
        // /
        /*
         * DicomSeries dicomSeries = new DicomSeries(this);
         * DicomSeriesView dicomSeriesView = (DicomSeriesView)findViewById(R.id.dicom_series_view);
         * dicomSeriesView.setDicomSeries(dicomSeries);
         */

        LinearLayout dicomSeriesScrollLayout = (LinearLayout) findViewById( R.id.datasetScrollLayout );
        // /FIXME add correct content (thumbnails of series):
        dicomSeriesScrollLayout.addView( new DatasetThumbnail( getApplicationContext() ) );
        dicomSeriesScrollLayout.addView( new DatasetThumbnail( getApplicationContext() ) );
        dicomSeriesScrollLayout.addView( new DatasetThumbnail( getApplicationContext() ) );

        // / dicomSeriesScrollLayout.setOnItemClickListener

        GridView gridview = (GridView) findViewById( R.id.datasetGridView );
        gridview.setAdapter( new DatasetThumbnailAdapter( this, null, null, null ) ); // /FIXME TMP

        gridview.setOnItemClickListener( new OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id )
            {
                Toast.makeText( DatasetViewerActivity.this, "" + position, Toast.LENGTH_SHORT )
                    .show();
            }
        } );

        // /FIXME TMP!
        // ((ImageButton)findViewById(R.id.imageButton1)).setOnClickListener(this);
        // ((ImageButton)findViewById(R.id.imageButton2)).setOnClickListener(this);
        // ((ImageButton)findViewById(R.id.imageButton3)).setOnClickListener(this);
        // ((ImageButton)findViewById(R.id.imageButton4)).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.activity_dataset_viewer, menu );

        return true;
    }

    @Override
    public void onClick( View v )
    {

        // /
        Log.i( LOG_TAG, "clicked on view " + v.getId() );
        // /
    }
}

/*
 * package com.intelerad.android.portal;
 * import android.content.Context;
 * import android.graphics.Canvas;
 * import android.graphics.Bitmap;
 * import android.graphics.Paint;
 * import android.util.AttributeSet;
 * import android.util.Log;
 * import android.view.MotionEvent;
 * import android.view.ScaleGestureDetector;
 * import android.view.ScaleGestureDetector.OnScaleGestureListener;
 * import android.view.View;
 * public class DicomSeriesView extends View {
 * private final String LOG_TAG = getClass().getSimpleName();
 * DicomSeries dicomSeries = null;
 * int currentObjectIndex = -1;
 * Bitmap bitmap = null;
 * Bitmap scaledBitmap = null;
 * float bitmapX = 0.0f;
 * float bitmapY = 0.0f;
 * Paint paint = new Paint();
 * public DicomSeriesView(Context context, AttributeSet attributeSet) {
 * super(context, attributeSet);///TODO REMEMBER THIS (don't forget both parameters)!
 * OnScaleGestureListener scaleGestureListener = new OnScaleGestureListener() {
 * @Override
 * public void onScaleEnd(ScaleGestureDetector detector) {
 * ///
 * Log.i(LOG_TAG, "pinch zoom end");
 * ///
 * }
 * @Override
 * public boolean onScaleBegin(ScaleGestureDetector detector) {
 * ///
 * Log.i(LOG_TAG, "pinch zoom start");
 * ///
 * return true;
 * }
 * @Override
 * public boolean onScale(ScaleGestureDetector detector) {
 * float scaleFactor = detector.getScaleFactor();
 * scaleImage(scaleFactor);
 * // do factor filtering?
 * if (scaleFactor > 0.01f) {
 * return true;
 * } else {
 * return false;
 * }
 * return true;
 * }
 * };
 * final ScaleGestureDetector scaleGestureDetector
 * = new ScaleGestureDetector(context, scaleGestureListener);
 * // center the image initially
 * bitmapX = getWidth() / 2;
 * bitmapY = getHeight() / 2;
 * setOnTouchListener(new OnTouchListener() {
 * @Override
 * public boolean onTouch(View v, MotionEvent event) {
 * scaleGestureDetector.onTouchEvent(event);
 * bitmapX = event.getX();
 * bitmapY = event.getY();
 * postInvalidate();
 * return true;
 * }
 * });
 * }
 * public void setDicomSeries(DicomSeries dicomSeries) {
 * this.dicomSeries = dicomSeries;
 * initializeSeries(dicomSeries);
 * }
 * private void initializeSeries(DicomSeries dicomSeries) {
 * ///FIXME! correct?
 * if (dicomSeries == null || dicomSeries.getDicomObjectCount() < 1) {
 * currentObjectIndex = -1;
 * bitmap = null;
 * scaledBitmap = null;
 * } else {
 * currentObjectIndex = 0;
 * bitmap = dicomSeries.getDicomObject(currentObjectIndex).getImageBitmap();
 * scaleImage(1.0f); // initial set-up: no zoom (1:1)
 * postInvalidate();
 * }
 * }
 * protected void scaleImage(float scaleFactor) {
 * if (bitmap != null) {
 * int scaledWidth = (int)(bitmap.getWidth() * scaleFactor);
 * int scaledHeight = (int)(bitmap.getHeight() * scaleFactor);
 * ///
 * Log.i(LOG_TAG, "zoom " + scaleFactor + " -> " + scaledWidth + "x" + scaledHeight);
 * ///
 * scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, false); // no
 * filtering
 * }
 * }
 * @Override
 * public void onDraw(Canvas canvas) {
 * if (scaledBitmap != null) {
 * canvas.drawBitmap(scaledBitmap,
 * bitmapX - scaledBitmap.getWidth() / 2,
 * bitmapY - scaledBitmap.getHeight() / 2,
 * paint);
 * }
 * }
 * @Override
 * protected void onSizeChanged(int w, int h, int oldw, int oldh) {
 * super.onSizeChanged(w, h, oldw, oldh);
 * ///do some logic on resizing here
 * postInvalidate(); /// need this?
 * }
 * }
 */
