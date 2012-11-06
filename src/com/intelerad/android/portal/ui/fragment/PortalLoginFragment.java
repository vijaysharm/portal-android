package com.intelerad.android.portal.ui.fragment;

import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.intelerad.android.portal.R;
import com.intelerad.android.portal.models.PortalClientSession;
import com.intelerad.android.portal.utils.Logs;

public class PortalLoginFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<PortalClientSession>
{
    private static final Logger L = Logs.create( PortalLoginFragment.class );
    private static final Callback DUMMY = new Callback()
    {
        @Override public void onLogin(){}
    };
    
    private Callback mCallback = DUMMY;
    
    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.portal_login, null );
        final EditText pacsHost = (EditText) view.findViewById( R.id.pacs_host );
        final EditText pacsUsername = (EditText) view.findViewById( R.id.pacs_username );
        final EditText pacsPassword = (EditText)view.findViewById( R.id.pacs_password );
        final Button loginButton = (Button) view.findViewById( R.id.login_button );
        loginButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                L.info( "Host: " + pacsHost.getText() );
                L.info( "Name: " + pacsUsername.getText() );
                
                hideKeyboard( pacsHost, pacsUsername, pacsPassword );
                loginButton.setEnabled( false );
                
                String spec = "http";
                String host = pacsHost.getText().toString();
                String username = pacsUsername.getText().toString();
                String password = pacsPassword.getText().toString();
                
                login( LoginBundle.pack( spec, host, username, password ) );
            }
        } );
        
        return view;
    }

    private void hideKeyboard( EditText...editTexts )
    {
        if ( editTexts == null )
            return;
        
        for ( EditText editText : editTexts )
        {
            if ( ! editText.hasFocus() )
                continue;
            
            InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow( editText.getWindowToken(), 0 );
            
            return;
        }
    }

    @Override
    public Loader<PortalClientSession> onCreateLoader( int id, Bundle bundle )
    {
        L.info( "Creating loader " + id );
        return new SessionLoader( getActivity(), LoginBundle.unpack( bundle ) );
    }

    @Override
    public void onLoadFinished( Loader<PortalClientSession> loader, PortalClientSession result )
    {
        L.info( "Finished loading " + result );
        getView().findViewById( R.id.login_button ).setEnabled( true );
        
        if ( mCallback != null )
            mCallback.onLogin();
    }

    @Override
    public void onLoaderReset( Loader<PortalClientSession> loader )
    {
        
    }

    @Override
    public void onAttach( Activity activity )
    {
        super.onAttach( activity );
        mCallback = (Callback) activity;
    }
    
    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallback = DUMMY;
    }
    
    
    private void login( Bundle pack )
    {
        getLoaderManager().initLoader( 0, pack, this ).forceLoad();
    }
    
    private static class SessionLoader extends AsyncTaskLoader<PortalClientSession>
    {
        private final LoginBundle mLoginBundle;

        public SessionLoader( Context context, LoginBundle loginBundle )
        {
            super( context );
            mLoginBundle = loginBundle;
        }

        @Override
        public PortalClientSession loadInBackground()
        {
            
            try
            {
                Thread.sleep( 500 );
            }
            catch ( InterruptedException exception )
            {

            }
            
            L.info( "Logged in with: " + mLoginBundle.mUsername + " @ " + mLoginBundle.mHost );
            
            return new PortalClientSession( mLoginBundle.mSpec, mLoginBundle.mHost, mLoginBundle.mUsername, mLoginBundle.mPassword );
        }
    }
    
    private static class LoginBundle
    {
        private static final String SPEC_KEY = "spec";
        private static final String HOST_KEY = "host";
        private static final String USERNAME_KEY = "username";
        private static final String PASSWORD_KEY = "password";
        
        public static Bundle pack( String spec, String host, String username, String password )
        {
            Bundle bundle = new Bundle();
            bundle.putString( SPEC_KEY, spec );
            bundle.putString( HOST_KEY, host );
            bundle.putString( USERNAME_KEY, username );
            bundle.putString( PASSWORD_KEY, password );
            
            return bundle;
        }

        public static LoginBundle unpack( Bundle bundle )
        {
            return new LoginBundle( bundle.getString( SPEC_KEY ),
                                    bundle.getString( HOST_KEY ),
                                    bundle.getString( USERNAME_KEY ),
                                    bundle.getString( PASSWORD_KEY ) );
        }
        
        private final String mSpec;
        private final String mHost;
        private final String mUsername;
        private final String mPassword;
        
        public LoginBundle( String spec, String host, String username, String password )
        {
            mSpec = spec;
            mHost = host;
            mUsername = username;
            mPassword = password;
        }
    }
    
    public interface Callback
    {
        void onLogin();
    }
}
