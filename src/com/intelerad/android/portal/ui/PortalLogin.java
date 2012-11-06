package com.intelerad.android.portal.ui;

import android.content.Intent;
import android.os.Bundle;

import com.intelerad.android.portal.ui.fragment.PortalLoginFragment;
import com.intelerad.android.portal.ui.phone.NotificationActivity;

public class PortalLogin extends SimpleSinglePaneActivity<PortalLoginFragment> 
                         implements PortalLoginFragment.Callback
{
    @Override
    public void onLogin()
    {
        // TODO: Should be selected based on platform, also, should add session object
        Intent intent = new Intent( this, NotificationActivity.class );
        startActivity( intent );
        finish();
    }
    
    @Override
    protected void onCreate( Bundle bundle )
    {
        super.onCreate( bundle );
        getSupportActionBar().hide();
    }

    @Override
    protected PortalLoginFragment onCreateFragment()
    {
        return new PortalLoginFragment();
    }
}
