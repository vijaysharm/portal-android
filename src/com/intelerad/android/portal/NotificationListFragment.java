package com.intelerad.android.portal;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;
import com.intelerad.datamodels.reportworkflow.PriorityLevelColor;
import com.intelerad.datamodels.reportworkflow.portal.EventType;
import com.intelerad.web.lib.gwt.model.GwtCriticalResult;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;
import com.intelerad.web.portal.gwt.model.GwtNotification;
import com.intelerad.web.portal.gwt.model.NotificationFilter;
import com.intelerad.web.portal.gwt.model.NotificationResults;

public class NotificationListFragment extends ItemListFragment<GwtNotification>
{
    private Session[] mSessions;
    private Session mCurrentSession;
    private Callback mCallback = DUMMY;
    
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        mSessions = AccountUtility.getSessions( getActivity() );
        mCurrentSession = mSessions[0];
    }

    @Override
    public Loader<List<GwtNotification>> onCreateLoader( int id, Bundle args )
    {
        return new ThrowableLoader<List<GwtNotification>>( getActivity(), mItems )
        {
            @Override
            public List<GwtNotification> loadData() throws Exception
            {
                NotificationResults notificationResults = 
                    PortalApi.getNotificationResults( mCurrentSession, 0, NotificationFilter.ALL );

                return notificationResults.getNotifications();
            }
        };
    }

    @Override
    protected SingleTypeAdapter<GwtNotification> createAdapter()
    {
        return new NotificationAdapter( getActivity() );
    }
    
    @Override
    public void onListItemClick( ListView listView, View view, int position, long id )
    {
        GwtNotification notification = (GwtNotification) listView.getItemAtPosition( position );
        mCallback.orderSelection( notification.getOrder() );
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
    
    interface Callback
    {
        void orderSelection( GwtOrder order );
    }
    
    private static final Callback DUMMY = new Callback()
    {
        @Override public void orderSelection( GwtOrder order ){}
    };
    
    private static class NotificationAdapter extends SingleTypeAdapter<GwtNotification>
    {
        private final Context mContext;

        public NotificationAdapter( Context context )
        {
            super( context, R.layout.item_notification );
            mContext = context;
        }

        @Override
        protected int[] getChildViewIds()
        {
            return new int[]
            { 
                R.id.notification_image,
                R.id.notification_name,
                R.id.notification_description,
                R.id.notification_date,
                R.id.notification_state
            };
        }

        @Override
        protected void update( int position, GwtNotification item )
        {
            imageView( 0 ).setImageResource( getImageResource( item ) );
            
            GwtOrder order = item.getOrder();
            String printableName = order.getPrintableName();
            setText( 1, printableName );
            
            String description = String.format( mContext.getString( R.string.notification_description_label ), order.getReasonForStudy() );
            setText( 2, description );
            
            String date = String.format( mContext.getString( R.string.notification_update_label ), item.getEventTime().toString() );
            setText( 3, date );
            
            GwtCriticalResult criticalResult = order.getCriticalResult();
            setGone( 4, criticalResult == null ? true : ! criticalResult.isPending() );
        }
        
        private int getImageResource( GwtNotification item )
        {
            GwtOrder order = item.getOrder();
            EventType eventType = item.getEventType();
            
            boolean hasPriorityLevel = ( order != null ) && ( order.getCriticalResultLevel() != null );
            boolean isCriticalNotification = hasPriorityLevel && ( eventType == EventType.CRITICAL_RESULT_ADDED || eventType == EventType.CRITICAL_RESULT_MODIFIED );
            
            if ( isCriticalNotification )
            {
                PriorityLevelColor color = order.getCriticalResultLevel().getColor();
                switch ( color )
                {
                    case ORANGE:
                        return R.drawable.flag_orange;
                    case RED:
                        return R.drawable.flag_red;
                    case YELLOW:
                        return R.drawable.flag_yellow;                        
                    default:
                        return R.drawable.flag_orange;
                }
            }
            else
            {
                switch ( eventType )
                {
                    case IMAGES_AVAILABLE:
                        return R.drawable.images_available;
                    case IMPRESSON_ADDED: 
                        return R.drawable.impressions;
                    case DICTATION_AVAILABLE:
                    case ADDENDUM_DICTATED:
                        return R.drawable.dictated;
                    case PRELIMINARY_REPORT_AVAILABLE:
                    case PRELIMINARY_REPORT_MODIFIED: 
                        return R.drawable.preliminary_report;
                    case ADDENDUM_TRANSCRIBED:
                        return R.drawable.addendum;
                    case FINAL_REPORT_AVAILABLE:
                    case FINAL_REPORT_CORRECTED:
                        return R.drawable.final_report;
                    case CRITICAL_RESULT_ADDED:
                        return R.drawable.flag_red;
                    case REPORT_DELETED:
                    case PRELIMINARY_REPORT_DELETED:
                    case FINAL_REPORT_DELETED:
                    case SIGNATURE_REVOKED:
                    case ORDER_CANCELLED:    
                    default:
                        return R.drawable.unknown;
                }
            }
        }
    }
}
