package com.intelerad.android.portal;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;
import com.intelerad.imageviewer.gwt.model.GwtDataset;
import com.intelerad.imageviewer.gwt.model.GwtStudy;
import com.intelerad.imageviewer.gwt.model.GwtStudyGroup;

public class DatasetSelectorActivity extends Activity
{
    private static final String LOG_TAG = DatasetSelectorActivity.class.getSimpleName();

    class AsyncStudyGroupDownloadTask extends AsyncTask<Intent, Void, GwtStudyGroup>
    {

        Session mSession = null;

        @Override
        protected GwtStudyGroup doInBackground( Intent... params )
        {
            Intent intent = params[ 0 ];

            String accessionId = Utils.extractAccession( intent );
            //
            Log.i( LOG_TAG, "accession ID: " + accessionId );
            //

            Session[] sessions = AccountUtility.getSessions( DatasetSelectorActivity.this );

            if ( sessions != null && sessions.length > 0 )
            {
                mSession = sessions[ 0 ];
                //
                Log.i( LOG_TAG, "number of sessions: " + sessions.length );
                //
                for ( Session session : sessions )
                {
                    Log.i( LOG_TAG, "session: " + session );
                }
            }

            if ( mSession != null )
            {
                try
                {
                    return PortalApi.getStudies( mSession, accessionId );
                }
                catch ( Exception e )
                {

                    Log.e( LOG_TAG, e.toString() );
                    e.printStackTrace();

                    return null; // retrieval failure
                }
            }
            else
            { // no PACS session object available
                Log.e( LOG_TAG, "No PACS session object available" );
                return null;
            }
        }

        @Override
        protected void onPostExecute( GwtStudyGroup studyGroup )
        {
            if ( studyGroup != null && studyGroup.hasStudies() )
            {

                SortedSet<GwtStudy> studies = studyGroup.getStudies();
                //
                Log.i( LOG_TAG, "number of studies: " + studies.size() );
                //

                Iterator<GwtStudy> studyIterator = studies.iterator();

                List<GwtDataset> keyDataSets = null;
                List<GwtDataset> nonKeyDataSets = null;

                while ( studyIterator.hasNext() )
                {
                    GwtStudy study = studyIterator.next();
                    //
                    Log.i( LOG_TAG, "Study: " + study.toString() );
                    //
                    keyDataSets = study.getKeyDatasets();
                    nonKeyDataSets = study.getNonKeyDatasets();

                    //
                    Log.i( LOG_TAG, "number of key datasets: " + keyDataSets.size() );
                    //
                    for ( GwtDataset dataset : keyDataSets )
                    {
                        //
                        Log.i( LOG_TAG, "key dataset " + dataset.getDatasetId() + " contains " +
                                        dataset.getImageCount() + " image(s)" );
                        //
                    }
                    //
                    Log.i( LOG_TAG, "number of non-key datasets: " + nonKeyDataSets.size() );
                    //
                    for ( GwtDataset dataset : nonKeyDataSets )
                    {
                        //
                        Log.i( LOG_TAG, "non-key dataset " + dataset.getDatasetId() + " contains " +
                                        dataset.getImageCount() + " image(s)" );
                        //

                    }
                }

                if ( mSession != null )
                {
                    GridView gridview = (GridView) findViewById( R.id.datasetGridView );
                    final DatasetThumbnailAdapter datasetThumbnailAdapter =
                        new DatasetThumbnailAdapter( DatasetSelectorActivity.this,
                                                     mSession,
                                                     keyDataSets,
                                                     nonKeyDataSets );
                    gridview.setAdapter( datasetThumbnailAdapter );

                    gridview.setOnItemClickListener( new OnItemClickListener()
                    {
                        @Override
                        public void onItemClick( AdapterView<?> parent,
                                                 View view,
                                                 int position,
                                                 long id )
                        {
                            Toast.makeText( DatasetSelectorActivity.this,
                                            "Selection = " + position + ", dataset " +
                                            datasetThumbnailAdapter.getItem( position ).toString(),
                                            Toast.LENGTH_LONG ).show();
                        }
                    } );
                }

            }
            else
            {
                // /TODO no studies in this group: reflect that in UI somehow
                Log.e( LOG_TAG, "No studies here!" );
                Toast.makeText( DatasetSelectorActivity.this,
                                "No studies were retrieved!",
                                Toast.LENGTH_LONG ).show();
            }
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dataset_selector );

        AsyncStudyGroupDownloadTask task = new AsyncStudyGroupDownloadTask();
        task.execute( getIntent() );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.activity_dataset_selector, menu );
        return true;
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ImageLoadingService.stopService( this );
    }
}
