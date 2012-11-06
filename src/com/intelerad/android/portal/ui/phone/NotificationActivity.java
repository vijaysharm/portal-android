package com.intelerad.android.portal.ui.phone;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.actionbarsherlock.R;
import com.actionbarsherlock.view.Menu;
import com.intelerad.android.portal.ui.SimpleSinglePaneActivity;
import com.intelerad.android.portal.ui.fragment.NotificationFragment;

public class NotificationActivity extends SimpleSinglePaneActivity<NotificationFragment> 
                                  implements NotificationFragment.Callback
{
    @Override
    public void showCase( String accession )
    {
        // Launch a new activity that will show
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        LayoutInflater inflater = LayoutInflater.from( this );
        EditText searchField = (EditText) inflater.inflate( R.layout.collapsible_edittext, null );
        
        menu.add( "search" )
            .setIcon( R.drawable.ic_action_search )
            .setActionView( searchField )
            .setShowAsAction( MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW );
        
        searchField.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                System.err.println( "Got text from search field: " + s );        
            }
            
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after )
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void afterTextChanged( Editable s )
            {
                // TODO Auto-generated method stub
                
            }
        } );
        
        return true;
    }
    
    @Override
    protected void onCreate( Bundle bundle )
    {
        super.onCreate( bundle );
        
        getSupportActionBar().show();
    }
    
    @Override
    protected NotificationFragment onCreateFragment()
    {
        return new NotificationFragment();
    }
}
