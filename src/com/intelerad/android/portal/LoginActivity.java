package com.intelerad.android.portal;

import java.lang.ref.WeakReference;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;

public class LoginActivity extends AccountAuthenticatorActivity
{
    private AccountManager mAccountManager;
    private Button mLoginButton;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        
        mAccountManager = AccountManager.get( this );
        
        final EditText pacsHost = (EditText) findViewById( R.id.pacs_host );
        final EditText pacsUsername = (EditText) findViewById( R.id.pacs_username );
        final EditText pacsPassword = (EditText) findViewById( R.id.pacs_password );
        mLoginButton = (Button) findViewById( R.id.login_button );
        
        mLoginButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                hideKeyboard( pacsHost, pacsUsername, pacsPassword );
                mLoginButton.setEnabled( false );
                
                String spec = "http";
                String hostname = pacsHost.getText().toString();
                String username = pacsUsername.getText().toString();
                String password = pacsPassword.getText().toString();
                
                new AccountTask( LoginActivity.this, spec, hostname, username, password ).execute();
            }
        } );
    }
    
    public void onAuthenticationResult( Session result )
    {
        mLoginButton.setEnabled( true );
        if ( result == null )
        {
            // TODO: There was some kind of failure to login
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putString( getString( R.string.account_hostname_key ), result.getHost() );
            bundle.putString( getString( R.string.account_spec_key ), result.getSpec() );
            bundle.putString( getString( R.string.account_permutation_key ), result.getPermutation() );
            bundle.putString( getString( R.string.account_strong_name_key ), result.getStrongName() );
            bundle.putString( getString( R.string.account_sesssion_id_key ), TextUtils.join( "|", result.getCookieIds() ) );
            
            Account account = new Account( result.getUsername(), getString( R.string.account_type ) );
            mAccountManager.addAccountExplicitly( account, result.getPassword(), bundle );
            
            Intent intent = new Intent();
            intent.putExtra( AccountManager.KEY_ACCOUNT_NAME, result.getUsername() );
            intent.putExtra( AccountManager.KEY_ACCOUNT_TYPE, getString( R.string.account_type ) );
            intent.putExtra( AccountManager.KEY_USERDATA, account );
            
            setAccountAuthenticatorResult( intent.getExtras() );
            setResult( RESULT_OK, intent );
            finish();            
        }
    }
    
    private void hideKeyboard( EditText...editTexts )
    {
        if ( editTexts == null )
            return;
        
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( inputMethodManager == null )
            return;
        
        for ( EditText editText : editTexts )
        {
            if ( ! editText.hasFocus() )
                continue;
            
            inputMethodManager.hideSoftInputFromWindow( editText.getWindowToken(), 0 );
            return;
        }
    }
    
    private static class AccountTask extends AsyncTask<Void, Void, Session>
    {
        private final WeakReference<LoginActivity> mContext;
        private final String mPassword;
        private final String mSpec;
        private final String mHost;
        private final String mUsername;
        
        public AccountTask( LoginActivity activity, String spec, String host, String username, String password )
        {
            mSpec = spec;
            mHost = host;
            mUsername = username;
            mPassword = password;
            mContext = new WeakReference<LoginActivity>( activity );
        }
        
        @Override
        protected Session doInBackground( Void... params )
        {
            try
            {
                Session session = PortalApi.login( mUsername, mPassword, mSpec, mHost );
                
                return session;
            }
            catch( Exception exception )
            {
                exception.printStackTrace();
                return null;
            }
        }
        
        @Override
        protected void onPostExecute( Session result )
        {
            // The activity was throw away 
            LoginActivity activity = mContext.get();
            if ( activity == null )
                return;
            
            activity.onAuthenticationResult( result );
        }
        
        @Override
        protected void onCancelled()
        {
            LoginActivity activity = mContext.get();
            if ( activity == null )
                return;
            // TODO: Do something
        }
    }
}
