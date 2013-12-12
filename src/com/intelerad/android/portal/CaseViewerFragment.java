package com.intelerad.android.portal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;
import com.intelerad.imageviewer.gwt.model.GwtDataset;
import com.intelerad.imageviewer.gwt.model.GwtPacsImage;
import com.intelerad.imageviewer.gwt.model.GwtStudy;
import com.intelerad.imageviewer.gwt.model.GwtStudyGroup;
import com.intelerad.imageviewer.gwt.model.Image2dRequest;
import com.intelerad.web.portal.gwt.model.GwtPortalCase;

public class CaseViewerFragment extends ItemFragment<CaseViewerFragment.CaseResult> implements OnItemClickListener
{
    private static final Callback DUMMY = new Callback()
    {
        @Override public void selectDataset( GwtDataset dataset ){}
    };
    
    private Session[] mSessions;
    private Session mCurrentSession;
    private Callback mCallback;
    private String mAccession;
    private ViewGroup mRootView;
    private ViewGroup mPatientOrder;
    private ImageFetcher mImageFetcher;
    private OrderViewer mOrderViewer;

    private GridAdapter mImageListAdapter;
    
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        mSessions = AccountUtility.getSessions( getActivity() );
        mCurrentSession = mSessions[0];
        
        Intent intent = Utils.convertToIntent( getArguments() );
        mAccession = Utils.extractAccession( intent );
        
        mImageFetcher = UiUtils.getImageFetcher( getActivity() );
        mImageFetcher.setImageFadeIn( true );
        mImageListAdapter = new GridAdapter( mCurrentSession, getActivity(), mImageFetcher );
    }
    
    @Override
    public void onStop()
    {
        super.onStop();

        if ( mImageFetcher != null )
            mImageFetcher.closeCache();
    }
    
    @Override
    protected void handleItemLoaded( CaseResult result )
    {
        mOrderViewer.update( result.getPortalCase().getOrder() );
        
        GwtStudyGroup studygroup = result.getStudy();
        if ( ! studygroup.hasStudies() )
            return;
        
        GwtStudy study = studygroup.getStudies().iterator().next();
        mImageListAdapter.setItems( study.getDatasets() );
    }
    
    @Override
    protected View getShowableView()
    {
        return mPatientOrder;
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState )
    {
        mRootView = (ViewGroup) inflater.inflate( R.layout.activity_case_viewer, null );
        mPatientOrder = (ViewGroup) mRootView.findViewById( R.id.patient_order );
        mOrderViewer = new OrderViewer( getActivity(), mPatientOrder );
        
        GridView view = (GridView) mPatientOrder.findViewById( R.id.case_viewer_thumbnails );
        view.setAdapter( mImageListAdapter );
        view.setOnItemClickListener( this );
        
        return mRootView;
    }
    
    @Override
    public void onAttach( Activity activity )
    {
        super.onAttach( activity );
        mCallback = (Callback) activity;
    }
    
    @Override
    public void onDetach()
    {
        mCallback = DUMMY;
        super.onDetach();
    }

    @Override
    public Loader<CaseResult> onCreateLoader( int id, Bundle args )
    {
        return new ThrowableLoader<CaseResult>( getActivity(), mItem )
        {
            @Override
            public CaseResult loadData() throws Exception
            {
                GwtPortalCase portalCase = PortalApi.getCase( mCurrentSession, mAccession );
                GwtStudyGroup studies = PortalApi.getStudies( mCurrentSession, mAccession );

                return new CaseResult( portalCase, studies );
            }
        };
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id )
    {
        GwtDataset dataset = mImageListAdapter.getItem( position );
        mCallback.selectDataset( dataset );
    }
    
    interface Callback
    {
        void selectDataset( GwtDataset dataset );
    }
    
    static class CaseResult
    {
        private final GwtStudyGroup mGroup;
        private final GwtPortalCase mPortalCase;

        public CaseResult( GwtPortalCase portalCase, GwtStudyGroup group )
        {
            mPortalCase = portalCase;
            mGroup = group;
        }
        
        public GwtStudyGroup getStudy()
        {
            return mGroup;
        }
        
        public GwtPortalCase getPortalCase()
        {
            return mPortalCase;
        }
    }
    
    private static class GridAdapter extends SingleTypeAdapter<GwtDataset>
    {
        private final ImageFetcher mImageFetcher;
        private final Session mCurrentSession;
        private final Context mContext;

        public GridAdapter( Session currentSession,
                            Context context,
                            ImageFetcher imageFetcher )
        {
            super( context, R.layout.item_dataset_thumbnail );
            mCurrentSession = currentSession;
            mContext = context;
            mImageFetcher = imageFetcher;
        }
        
        @Override
        protected int[] getChildViewIds()
        {
            return new int[]{ R.id.grid_item_image };
        }

        @Override
        protected void update( int position, GwtDataset dataset )
        {
            int height = mContext.getResources().getDimensionPixelSize( R.dimen.case_viewer_thumbnail_height );
            int width = mContext.getResources().getDimensionPixelSize( R.dimen.case_viewer_thumbnail_width );
            
            ImageView imageView = imageView( 0 );
            GwtPacsImage image = dataset.getRepresentativeImage();
            Image2dRequest request = new Image2dRequest( image, width, height );
            mImageFetcher.loadThumbnailImage( mCurrentSession, image.getUrl( request ), imageView );
        }
    }
}
