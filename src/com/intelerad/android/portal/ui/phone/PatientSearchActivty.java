package com.intelerad.android.portal.ui.phone;

import android.os.Bundle;
import android.view.MenuItem;

import com.actionbarsherlock.R;
import com.actionbarsherlock.view.Menu;
import com.intelerad.android.portal.ui.SimpleSinglePaneActivity;
import com.intelerad.android.portal.ui.fragment.PatientSearchFragment;

public class PatientSearchActivty extends SimpleSinglePaneActivity<PatientSearchFragment> 
                                  implements PatientSearchFragment.Callback
{
    @Override
    public void showCase( String accession )
    {
        // Launch a new activity that will show
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        menu.add( "search" )
            .setIcon( R.drawable.ic_action_search )
            .setActionView( R.layout.collapsible_edittext )
            .setShowAsAction( MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW );
        
        return true;
    }
    
    @Override
    protected void onCreate( Bundle bundle )
    {
        super.onCreate( bundle );
        
        getSupportActionBar().show();
    }
    
    @Override
    protected PatientSearchFragment onCreateFragment()
    {
        return new PatientSearchFragment();
    }
}
