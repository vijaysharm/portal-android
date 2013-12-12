package com.intelerad.android.portal;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity extends SherlockFragmentActivity
{
    @Override
    protected void onCreate( Bundle savedInstance )
    {
        super.onCreate( savedInstance );
        
        if ( ! AccountUtility.isAuthenticated( this ) )
        {
            AccountUtility.startAuthenticationFlow( this, getIntent() );
            finish();
        }
    }
}
