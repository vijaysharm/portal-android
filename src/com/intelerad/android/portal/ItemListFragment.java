package com.intelerad.android.portal;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Base fragment for displaying a list of items that loads with a progress bar visible
 */
public abstract class ItemListFragment<E> extends SherlockFragment implements LoaderCallbacks<List<E>>
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

    /**
     * List items provided to {@link #onLoadFinished(Loader, List)}
     */
    protected List<E> mItems = Collections.emptyList();

    /**
     * List view
     */
    protected ListView mListView;

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
    protected boolean mListShown;

    @Override
    public void onActivityCreated( Bundle savedInstanceState )
    {
        super.onActivityCreated( savedInstanceState );

        if ( !mItems.isEmpty() )
            setListShown( true, false );

        getLoaderManager().initLoader( 0, null, this );
    }

    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState )
    {
        return inflater.inflate( R.layout.activity_itemlist, null );
    }

    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView()
    {
        mListShown = false;
        mEmptyView = null;
        mProgressBar = null;
        mListView = null;

        super.onDestroyView();
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState )
    {
        super.onViewCreated( view, savedInstanceState );

        mListView = (ListView) view.findViewById( android.R.id.list );
        mListView.setOnItemClickListener( new OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id )
            {
                onListItemClick( (ListView) parent, view, position, id );
            }
        } );
        
        mProgressBar = (ProgressBar) view.findViewById( R.id.loading_progressbar );
        mEmptyView = (TextView) view.findViewById( android.R.id.empty );
        mListView.setAdapter( createAdapter() );
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

    protected void refresh( final Bundle args )
    {
        if ( !isUsable() )
            return;

        getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
        getLoaderManager().restartLoader( 0, args, this );
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

    @Override
    public void onLoadFinished( Loader<List<E>> loader, List<E> items )
    {
        if ( getActivity() == null )
            return;

        getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );

        Exception exception = getException( loader );
        if ( exception != null )
        {
            showError( getErrorMessage( exception ) );
            showList();
            return;
        }

        mItems = items;
        getListAdapter().setItems( items.toArray() );
        showList();
    }

    /**
     * Create adapter to display items
     * 
     * @param items
     * @return adapter
     */
    protected abstract SingleTypeAdapter<E> createAdapter();

    /**
     * Set the list to be shown
     */
    protected void showList()
    {
        setListShown( true, isResumed() );
    }

    @Override
    public void onLoaderReset( Loader<List<E>> loader )
    {
        // Intentionally left blank
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
     * Get exception from loader if it provides one by being a {@link ThrowableLoader}
     * 
     * @param loader
     * @return exception or null if none provided
     */
    protected Exception getException( final Loader<List<E>> loader )
    {
        if ( loader instanceof ThrowableLoader )
            return ( (ThrowableLoader<List<E>>) loader ).clearException();
        else
            return null;
    }

    /**
     * Refresh the list with the progress bar showing
     */
    protected void refreshWithProgress()
    {
        mItems.clear();
        setListShown( false );
        refresh();
    }

    /**
     * Get {@link ListView}
     * 
     * @return listView
     */
    public ListView getListView()
    {
        return mListView;
    }

    /**
     * Get list adapter
     * 
     * @return list adapter
     */
    @SuppressWarnings("unchecked")
    protected SingleTypeAdapter<E> getListAdapter()
    {
        if ( mListView != null )
            return (SingleTypeAdapter<E>) mListView.getAdapter();
        else
            return null;
    }

    /**
     * Set list adapter to use on list view
     * 
     * @param adapter
     * @return this fragment
     */
    protected ItemListFragment<E> setListAdapter( final ListAdapter adapter )
    {
        if ( mListView != null )
            mListView.setAdapter( adapter );
        return this;
    }

    private ItemListFragment<E> fadeIn( final View view, final boolean animate )
    {
        if ( view != null )
            if ( animate )
                view.startAnimation( AnimationUtils.loadAnimation( getActivity(),
                                                                   android.R.anim.fade_in ) );
            else
                view.clearAnimation();
        return this;
    }

    private ItemListFragment<E> show( final View view )
    {
        ViewUtils.setGone( view, false );
        return this;
    }

    private ItemListFragment<E> hide( final View view )
    {
        ViewUtils.setGone( view, true );
        return this;
    }

    /**
     * Set list shown or progress bar show
     * 
     * @param shown
     * @return this fragment
     */
    public ItemListFragment<E> setListShown( final boolean shown )
    {
        return setListShown( shown, true );
    }

    /**
     * Set list shown or progress bar show
     * 
     * @param shown
     * @param animate
     * @return this fragment
     */
    public ItemListFragment<E> setListShown( final boolean shown, final boolean animate )
    {
        if ( !isUsable() )
            return this;

        if ( shown == mListShown )
        {
            if ( shown )
                // List has already been shown so hide/show the empty view with
                // no fade effect
                if ( mItems.isEmpty() )
                    hide( mListView ).show( mEmptyView );
                else
                    hide( mEmptyView ).show( mListView );
            return this;
        }

        mListShown = shown;

        if ( shown )
            if ( !mItems.isEmpty() )
                hide( mProgressBar ).hide( mEmptyView ).fadeIn( mListView, animate ).show( mListView );
            else
                hide( mProgressBar ).hide( mListView ).fadeIn( mEmptyView, animate ).show( mEmptyView );
        else
            hide( mListView ).hide( mEmptyView ).fadeIn( mProgressBar, animate ).show( mProgressBar );

        return this;
    }

    /**
     * Set empty text on list fragment
     * 
     * @param message
     * @return this fragment
     */
    protected ItemListFragment<E> setEmptyText( final String message )
    {
        if ( mEmptyView != null )
            mEmptyView.setText( message );
        return this;
    }

    /**
     * Set empty text on list fragment
     * 
     * @param resId
     * @return this fragment
     */
    protected ItemListFragment<E> setEmptyText( final int resId )
    {
        if ( mEmptyView != null )
            mEmptyView.setText( resId );
        return this;
    }

    /**
     * Callback when a list view item is clicked
     * 
     * @param listView
     * @param view
     * @param position
     * @param id
     */
    public void onListItemClick( ListView listView, View view, int position, long id )
    {
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
