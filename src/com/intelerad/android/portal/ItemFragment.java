package com.intelerad.android.portal;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class ItemFragment<E> extends SherlockFragment implements LoaderCallbacks<E>
{
    private static final String FORCE_REFRESH = "forceRefresh";

    /**
     * @param args bundle passed to the loader by the LoaderManager
     * @return true if the bundle indicates a requested forced refresh of the items
     */
    protected static boolean isForceRefresh( Bundle args )
    {
        return args != null && args.getBoolean( FORCE_REFRESH, false );
    }
    
    protected E mItem = null;
    
    protected View mView;

    /**
     * Empty view
     */
    protected TextView mEmptyView;

    /**
     * Progress bar
     */
    protected ProgressBar mProgressBar;

    /**
     * Is the list currently shown?
     */
    protected boolean mViewShown;
    
    @Override
    public void onActivityCreated( Bundle savedInstanceState )
    {
        super.onActivityCreated( savedInstanceState );
        
        if ( mItem == null )
            setViewShow( true, false );
        
        getLoaderManager().initLoader( 0, null, this );
    }
    
    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView()
    {
        mViewShown = false;
        mEmptyView = null;
        mProgressBar = null;
        mView = null;

        super.onDestroyView();
    }

    protected abstract void handleItemLoaded( E result );
    protected abstract View getShowableView();
    
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState )
    {
        super.onViewCreated( view, savedInstanceState );

        mView = getShowableView();
        mProgressBar = (ProgressBar) view.findViewById( R.id.loading_progressbar );
        mEmptyView = (TextView) view.findViewById( android.R.id.empty );
    }
    
    @Override
    public void onLoadFinished( Loader<E> loader, E result )
    {
        if ( ! isUsable() )
            return;
        
        getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );

        Exception exception = getException( loader );
        if ( exception != null )
        {
            showError( getErrorMessage( exception ) );
            showView();
            return;
        }
        
        mItem = result;
        handleItemLoaded( result );
        showView();
    }

    @Override
    public void onLoaderReset( Loader<E> loader )
    {
        // do nothing
    }
    
    /**
     * Set the list to be shown
     */
    protected void showView()
    {
        setViewShow( true, isResumed() );
    }
    
    /**
     * Get error message to display for exception
     * 
     * @param exception
     * @return string resource id
     */
    protected int getErrorMessage( Exception exception )
    {
        return R.string.error;
    }
    
    /**
     * Force a refresh of the items displayed ignoring any cached items
     */
    protected void forceRefresh()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean( FORCE_REFRESH, true );
        refresh( bundle );
    }
    
    /**
     * Refresh the fragment's list
     */
    public void refresh()
    {
        refresh( null );
    }

    private void refresh( final Bundle args )
    {
        if ( !isUsable() )
            return;

        getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
        getLoaderManager().restartLoader( 0, args, this );
    }
    
    /**
     * Get exception from loader if it provides one by being a {@link ThrowableLoader}
     * 
     * @param loader
     * @return exception or null if none provided
     */
    protected Exception getException( final Loader<E> loader )
    {
        if ( loader instanceof ThrowableLoader )
            return ( (ThrowableLoader<E>) loader ).clearException();
        else
            return null;
    }
    
    /**
     * Show exception in a Toast
     * 
     * @param message
     */
    protected void showError( final int message )
    {
//        Toaster.showLong( getActivity(), message );
    }
    
    /**
     * Refresh the view with the progress bar showing
     */
    protected void refreshWithProgress()
    {
        mItem = null;
        setListShown( false );
        refresh();
    }
    
    /**
     * Set view shown or progress bar show
     * 
     * @param shown
     * @return this fragment
     */
    public ItemFragment<E> setListShown( final boolean shown )
    {
        return setViewShow( shown, true );
    }
    
    public ItemFragment<E> setViewShow( final boolean shown, final boolean animate )
    {
        if ( ! isUsable() )
            return this;
        
        if ( shown == mViewShown )
        {
            if ( shown )
                // View has already been shown so hide/show the empty view with
                // no fade effect
                if ( mItem == null )
                    hide( mView ).show( mEmptyView );
                else
                    hide( mEmptyView ).show( mView );
            
            return this;
        }
        
        mViewShown = shown;
        
        if ( shown )
        {
            if ( mItem == null )
                hide( mProgressBar ).hide( mEmptyView ).fadeIn( mView, animate ).show( mView );
            else
                hide( mProgressBar ).hide( mView ).fadeIn( mEmptyView, animate ).show( mEmptyView );
        }
        else
        {
            hide( mView ).hide( mEmptyView ).fadeIn( mProgressBar, animate ).show( mProgressBar );
        }
        
        return this;
    }
    
    private ItemFragment<E> fadeIn( final View view, final boolean animate )
    {
        if ( view != null )
        {
            if ( animate )
                view.startAnimation( AnimationUtils.loadAnimation( getActivity(),
                                                                   android.R.anim.fade_in ) );
            else
                view.clearAnimation();
        }
        
        return this;
    }
    
    private ItemFragment<E> show( final View view )
    {
        ViewUtils.setGone( view, false );
        return this;
    }

    private ItemFragment<E> hide( final View view )
    {
        ViewUtils.setGone( view, true );
        return this;
    }
    
    protected ItemFragment<E> setEmptyText( final int resId )
    {
        if ( mEmptyView != null )
            mEmptyView.setText( resId );

        return this;
    }
    
    /**
     * Is this fragment still part of an activity and usable from the UI-thread?
     * 
     * @return true if usable on the UI-thread, false otherwise
     */
    protected boolean isUsable()
    {
        return getActivity() != null;
    }
}
