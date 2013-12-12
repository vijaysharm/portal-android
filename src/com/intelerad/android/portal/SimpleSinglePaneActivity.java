package com.intelerad.android.portal;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public abstract class SimpleSinglePaneActivity<T extends Fragment> extends BaseActivity
{
    private static final String FRAGMENT_TAG = "single_pane_fragment";

    private T mFragment;
    private final int mMenuResourceId;

    public SimpleSinglePaneActivity( int menuResourceId )
    {
        mMenuResourceId = menuResourceId;
    }

    protected abstract T onCreateFragment();

    @Override
    protected void onCreate( Bundle bundle )
    {
        super.onCreate( bundle );

        if ( isFinishing() )
            return;

        setContentView( R.layout.activity_singlepane_empty );

        final String customTitle = getIntent().getStringExtra( Intent.EXTRA_TITLE );
        setTitle( customTitle != null ? customTitle : getTitle() );

        if ( bundle == null )
        {
            mFragment = onCreateFragment();
            mFragment.setArguments( Utils.convertToFragmentArgument( getIntent() ) );

            getSupportFragmentManager().beginTransaction()
                                       .add( R.id.root_container, mFragment, FRAGMENT_TAG )
                                       .commit();
        }
        else
        {
            mFragment = (T) getSupportFragmentManager().findFragmentByTag( FRAGMENT_TAG );
        }
    }

    // @Override
    // public boolean onSearchRequested()
    // {
    // Bundle appData = new Bundle();
    // startSearch( null, false, appData, false );
    //
    // return true;
    // }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        super.onCreateOptionsMenu( menu );
        getSupportMenuInflater().inflate( mMenuResourceId, menu );
        setupSearchMenuItem( menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case R.id.menu_search:
                return onSearchRequested();
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void finish()
    {
        super.finish();

        if ( UiUtils.hasJellybean() )
            overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
    }

    protected T getFragment()
    {
        return mFragment;
    }

    /**
     * We do this for Honeycomb devices onwards because older devices have a dedicated search button
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupSearchMenuItem( Menu menu )
    {
        MenuItem searchItem = menu.findItem( R.id.menu_search );
        if ( searchItem != null && UiUtils.hasHoneycomb() )
        {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if ( searchView != null )
            {
                SearchManager searchManager = (SearchManager) getSystemService( SEARCH_SERVICE );
                searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
                searchView.setOnQueryTextListener( new OnQueryTextListener()
                {
                    @Override
                    public boolean onQueryTextSubmit( String query )
                    {
                        return true;
                    }
                    
                    @Override
                    public boolean onQueryTextChange( String newText )
                    {
                        return false;
                    }
                } );
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void animateIn( Intent intent )
    {
        Bundle bundle =
            ActivityOptions.makeCustomAnimation( this, R.anim.slide_in_left, R.anim.slide_out_left )
                           .toBundle();
        startActivity( intent, bundle );
    }

    @Override
    public void startActivity( Intent intent )
    {
        if ( UiUtils.hasJellybean() )
            animateIn( intent );
        else
            super.startActivity( intent );
    }
}
