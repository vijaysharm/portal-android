package com.intelerad.android.portal.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.intelerad.android.portal.R;

public class PatientSearchFragment extends SherlockListFragment
{
//    private static final Logger L = Logs.create( PatientSearchFragment.class );
    private static final Callback DUMMY = new Callback()
    {
        @Override public void showCase( String string ){}
    };
    
    
    private Callback mCallback = DUMMY; 
    
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        // TODO: Should be replaced by a cursor adapter attached to an HTTP cursor loader
        setListAdapter( new ArrayAdapter<String>( getActivity(),
                                                  android.R.layout.simple_expandable_list_item_1,
                                                  new String[]{ getString( R.string.accession ) } ) );
    }

    @Override
    public void onListItemClick( ListView listView, View view, int position, long id )
    {
        super.onListItemClick( listView, view, position, id );

        if ( mCallback != null )
            mCallback.showCase( getString( R.string.accession ) );
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
        super.onDetach();
        mCallback = DUMMY;
    }
    
    public interface Callback
    {
        // TODO: Should probably be an order or case object
        void showCase( String string );
        
    }
}
