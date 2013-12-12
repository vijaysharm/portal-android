package com.intelerad.android.portal;

import android.content.Intent;

import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;


public class OrderListActivity extends SimpleSinglePaneActivity<OrderListFragment> implements OrderListFragment.Callback
{
    public OrderListActivity()
    {
        super( R.menu.order_list );
    }
    
    @Override
    protected OrderListFragment onCreateFragment()
    {
        return new OrderListFragment();
    }

    @Override
    public void orderSelection( GwtOrder order )
    {
        Intent intent = new Intent( this, CaseViewerActivity.class );
        Utils.addAccession( intent, order.getAccessionNumber() );
        startActivity( intent );
    }
}
