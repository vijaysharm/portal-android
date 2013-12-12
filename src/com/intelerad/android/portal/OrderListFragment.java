package com.intelerad.android.portal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;
import com.intelerad.web.lib.gwt.client.GwtStringUtils;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder.ImageAvailability;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

public class OrderListFragment extends ItemFragment<GwtPatient> implements OnItemClickListener
{
    private static final Callback DUMMY = new Callback()
    {
        @Override public void orderSelection( GwtOrder order ) {}
    };
    
    private Session[] mSessions;
    private Session mCurrentSession;
    private String mPatientId;
    private ViewGroup mRootView;
    private ViewGroup mContainer;
    private OrderAdapter mOrderAdapter;
    private Callback mCallback = DUMMY;
    
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        Intent intent = Utils.convertToIntent( getArguments() );
        mSessions = AccountUtility.getSessions( getActivity() );
        mCurrentSession = mSessions[0];
        mPatientId = Utils.extractPatientId( intent );
        mOrderAdapter = new OrderAdapter( getActivity() );
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
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState )
    {
        mRootView = (ViewGroup) inflater.inflate( R.layout.activity_order_list, null );
        mContainer = (ViewGroup) mRootView.findViewById( R.id.patient_orders );
        
        ListView orderList = (ListView) mContainer.findViewById( R.id.patient_orders_list );
        orderList.setAdapter( mOrderAdapter );
        orderList.setOnItemClickListener( this );
        
        return mRootView;
    }
    
    @Override
    public Loader<GwtPatient> onCreateLoader( int id, Bundle bundle )
    {
        return new ThrowableLoader<GwtPatient>( getActivity(), mItem )
        {
            @Override
            public GwtPatient loadData() throws Exception
            {
                return PortalApi.getPatient( mCurrentSession, mPatientId );
            }
        };
    }

    @Override
    protected void handleItemLoaded( GwtPatient patient )
    {
        TextView patientName = (TextView) mContainer.findViewById( R.id.patient_name );
        patientName.setText( patient.getPrintableName() );
        
        TextView demographics = (TextView) mContainer.findViewById( R.id.patient_demographics );
        demographics.setText( patient.getGender() );
        
        TextView patientId = (TextView) mContainer.findViewById( R.id.patient_id );
        setText( patientId, R.string.patient_id_label, patient.getPatientId() );
        
        TextView orders = (TextView) mContainer.findViewById( R.id.orders_patient_count );
        orders.setText( "Orders: " + patient.getOrderCount() );

        mOrderAdapter.setItems( patient.getOrders() );
    }

    @Override
    protected View getShowableView()
    {
        return mContainer;
    }
    
    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id )
    {
        ListView listView = (ListView) parent;
        GwtOrder order = (GwtOrder) listView.getItemAtPosition( position );
        mCallback.orderSelection( order );
    }
    
    private void setText( TextView textView, int resourceId, String value )
    {
        String text = String.format( getString( resourceId ), value );
        CharSequence html = Html.fromHtml( text );
        textView.setText( html );
    }
    
    interface Callback
    {
        void orderSelection( GwtOrder order );
    }
    
    private static class OrderAdapter extends SingleTypeAdapter<GwtOrder>
    {
        private final Context mContext;

        public OrderAdapter( Context context )
        {
            super( context, R.layout.item_order );
            mContext = context;
        }

        @Override
        protected int[] getChildViewIds()
        {
            return new int[]
            { 
                R.id.order_image,
                R.id.order_name,
                R.id.order_description,
                R.id.order_date,
                R.id.order_state
            };
        }

        @Override
        protected void update( int position, GwtOrder order )
        {
            String html = GwtStringUtils.listJoin( order.getProcedureDescriptions(), "<br/>" );
            setText( 1, Html.fromHtml( html ) );
            
            String description = String.format( mContext.getString( R.string.order_list_accesion_label ), order.getAccessionNumber() );
            setText( 2, description );
            
            setText( 3, order.getStudyDate().toString() );
            
            String imageAvailability = getImageAvailability( order );
            if ( imageAvailability == null )
                setGone( 4, true );
            else
                setText( 4, imageAvailability );
        }

        private String getImageAvailability( GwtOrder order )
        {
            ImageAvailability availability = order.getImageAvailability();
            switch ( availability )
            {
                case IN_PROGRESS:
                    return mContext.getString( R.string.in_progress );
                case NO_IMAGES:
                    return mContext.getString( R.string.no_images );
                case OFFLINE:
                    return mContext.getString( R.string.offline );
                case ONLINE:
                    return mContext.getString( R.string.online );
            }
            
            return null;
        }
    }
}
