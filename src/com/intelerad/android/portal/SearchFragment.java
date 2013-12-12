package com.intelerad.android.portal;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

public class SearchFragment extends ItemListFragment<GwtPatient>
{
    private static final Callback DUMMY = new Callback()
    {
        @Override public void selectPatient( GwtPatient patient ){}
    };
    
    private String mSearch;
    private Callback mCallback = DUMMY;
    
    public void reload( Bundle bundle )
    {
        mSearch = Utils.extractSearch( bundle );
        refresh( bundle );
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
    public Loader<List<GwtPatient>> onCreateLoader( int id, Bundle args )
    {
        Session[] sessions = AccountUtility.getSessions( getActivity() );
        final Session session = sessions[0];
        
        return new ThrowableLoader<List<GwtPatient>>( getActivity(), mItems )
        {
            @Override
            public List<GwtPatient> loadData() throws Exception
            {
                if ( session == null )
                    return Collections.EMPTY_LIST;

                if ( TextUtils.isEmpty( mSearch ) )
                    return Collections.EMPTY_LIST;

                return PortalApi.getSearchResults( session, mSearch ).getPatients();
            }
        };
    }

    @Override
    public void onListItemClick( ListView listView, View view, int position, long id )
    {
        GwtPatient patient = (GwtPatient) listView.getItemAtPosition( position );
        mCallback.selectPatient( patient );
    }
    
    @Override
    protected SingleTypeAdapter<GwtPatient> createAdapter()
    {
        return new SearchAdapter( getActivity() );
    }
    
    interface Callback
    {
        void selectPatient( GwtPatient patient );
    }
    
    private static class SearchAdapter extends SingleTypeAdapter<GwtPatient>
    {
        private final Context mContext;

        public SearchAdapter( Context context )
        {
            super( context, R.layout.item_search );
            mContext = context;
        }

        @Override
        protected int[] getChildViewIds()
        {
            return new int[]
            { 
                R.id.search_patient_name,
                R.id.search_demographics,
                R.id.search_patient_id,
                R.id.search_patient_order_count
            };
        }

        @Override
        protected void update( int position, GwtPatient item )
        {
            setText( 0, item.getPrintableName() );
            setText( 1, item.getGender() );
            
            CharSequence patientId =
                Html.fromHtml( String.format( mContext.getString( R.string.patient_id_label ),
                                              item.getPatientId() ) );
            
            setText( 2, patientId );
            setText( 3, item.getOrderCount() + " orders" );
        }
    }
}
