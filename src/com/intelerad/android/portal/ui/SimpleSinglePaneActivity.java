package com.intelerad.android.portal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.intelerad.android.portal.R;

public abstract class SimpleSinglePaneActivity<T extends Fragment> extends SherlockFragmentActivity
{
    private static final String FRAGMENT_TAG = "single_pane_fragment";
    
    private T mFragment;
    
    protected abstract T onCreateFragment();
    
    @Override
    protected void onCreate( Bundle bundle )
    {
        super.onCreate( bundle );
        setContentView( R.layout.activity_singlepane_empty );
        
        final String customTitle = getIntent().getStringExtra( Intent.EXTRA_TITLE );
        setTitle( customTitle != null ? customTitle : getTitle() );
        
        if ( bundle == null )
        {
            mFragment = onCreateFragment();
            // TODO: convert intent into arguments for the fragment
            // mFragment.setArguments( args )
            getSupportFragmentManager().beginTransaction()
                                       .add( R.id.root_container, mFragment, FRAGMENT_TAG )
                                       .commit();
        }
        else
        {
            mFragment = (T) getSupportFragmentManager().findFragmentByTag( FRAGMENT_TAG );
        }
    }
    
    protected T getFragment()
    {
        return mFragment;
    }
}
