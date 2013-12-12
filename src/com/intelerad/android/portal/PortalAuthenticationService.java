package com.intelerad.android.portal;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class PortalAuthenticationService extends Service
{
    private static Authenticator AUTHENTICATOR;
    
    @Override
    public IBinder onBind( Intent intent )
    {
        return intent.getAction().equals( AccountManager.ACTION_AUTHENTICATOR_INTENT ) ? getAuthenticator().getIBinder() : null;
    }
    
    private Authenticator getAuthenticator()
    {
        if ( AUTHENTICATOR == null )
            AUTHENTICATOR = new Authenticator( this );
        
        return AUTHENTICATOR;
    }
    
    private static class Authenticator extends AbstractAccountAuthenticator
    {
        private final Context mContext;

        public Authenticator( Context context )
        {
            super( context );
            mContext = context;
        }

        @Override
        public Bundle addAccount( AccountAuthenticatorResponse response,
                                  String accountType,
                                  String authTokenType,
                                  String[] requiredFeatures,
                                  Bundle options ) throws NetworkErrorException
        {
            Intent intent = new Intent( mContext, LoginActivity.class ); 
            intent.putExtra( AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response );
            
            Bundle reply = new Bundle();
            reply.putParcelable( AccountManager.KEY_INTENT, intent );
            return reply;
        }
        
        @Override
        public Bundle hasFeatures( AccountAuthenticatorResponse response,
                                   Account account,
                                   String[] features ) throws NetworkErrorException
        {
            Bundle bundle = new Bundle();
            bundle.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, false );
            
            return bundle;
        }
        
        @Override
        public String getAuthTokenLabel( String authTokenType )
        {
            if ( mContext.getString( R.string.account_type ).equals( authTokenType ) )
                return authTokenType;
            
            return null;
        }
        
        @Override
        public Bundle editProperties( AccountAuthenticatorResponse response, String accountType )
        {
            return null;
        }

        @Override
        public Bundle confirmCredentials( AccountAuthenticatorResponse response,
                                          Account account,
                                          Bundle options ) throws NetworkErrorException
        {
            return null;
        }

        @Override
        public Bundle getAuthToken( AccountAuthenticatorResponse response,
                                    Account account,
                                    String authTokenType,
                                    Bundle options ) throws NetworkErrorException
        {
            return null;
        }

        @Override
        public Bundle updateCredentials( AccountAuthenticatorResponse response,
                                         Account account,
                                         String authTokenType,
                                         Bundle options ) throws NetworkErrorException
        {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
