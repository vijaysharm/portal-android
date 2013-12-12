package com.intelerad.android.portal;

import android.content.Intent;

import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;


public class NotificationActivity extends SimpleSinglePaneActivity<NotificationListFragment> implements NotificationListFragment.Callback
{
    public NotificationActivity()
    {
        super( R.menu.notification );
    }
    
    @Override
    protected NotificationListFragment onCreateFragment()
    {
        return new NotificationListFragment();
    }
    
    @Override
    public void orderSelection( GwtOrder order )
    {
        Intent intent = new Intent( this, CaseViewerActivity.class );
        Utils.addAccession( intent, order.getAccessionNumber() );
        startActivity( intent );
    }
}
