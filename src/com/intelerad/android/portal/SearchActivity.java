package com.intelerad.android.portal;

import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.intelerad.web.lib.gwt.model.hl7.GwtBasicOrder;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

public class SearchActivity extends SimpleSinglePaneActivity<SearchFragment> implements SearchFragment.Callback
{
    public SearchActivity()
    {
        super( R.menu.search );
    }
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        handleNewIntent( getIntent() );
    }
    
    @Override
    public void onNewIntent( Intent intent )
    {
        setIntent( intent );
        handleNewIntent( intent ); 
    }

    private void handleNewIntent( Intent intent )
    {
        if ( isFinishing() )
            return;

        if ( ! Intent.ACTION_SEARCH.equals( intent.getAction() ) )
            return;
        
        String query = intent.getStringExtra( SearchManager.QUERY );
        setTitle( Html.fromHtml( getString( R.string.title_search_query, query ) ) );
        
        Bundle bundle = new Bundle();
        Utils.addSearch( bundle, query );
        getFragment().reload( bundle );
    }
    
    @Override
    public void selectPatient( GwtPatient patient )
    {
        Intent intent = null;
        
        List<GwtBasicOrder> orders = patient.getOrders();
        if ( orders.size() == 1 )
        {
            GwtBasicOrder order = orders.get( 0 );
            intent = new Intent( this, CaseViewerActivity.class );
            Utils.addAccession( intent, order.getAccessionNumber() );
        }
        else
        {
            intent = new Intent( this, OrderListActivity.class );
            Utils.addPatientId( intent, patient.getPatientId() );
        }
        
        startActivity( intent );
    }
    
    @Override
    protected SearchFragment onCreateFragment()
    {
        return new SearchFragment();
    }
}
