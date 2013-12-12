package com.intelerad.android.portal;

import java.util.ArrayList;

import com.intelerad.android.portal.models.Session;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class AccountUtility
{
    public static final String EXTRA_FINISH_INTENT = "com.intelerad.android.portal.extra.FINISH_INTENT";
    private static final String ACCOUNT_PREFERENCES_KEY = "intelerad_accounts";
    
    public static boolean isAuthenticated( Context context )
    {
        String[] accountIds = getActiveAccountIds( context );
        if ( accountIds == null || accountIds.length == 0 )
            return false;
        
        return true;
    }

    public static String[] getActiveAccountIds( Context context )
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( context );
        String accounts = preferences.getString( ACCOUNT_PREFERENCES_KEY, null );
        if ( TextUtils.isEmpty( accounts ) )
            return null;
        
        return TextUtils.split( accounts, "|" );
    }
    
    public static void setActiveAccountIds( Context context, String[] accounts )
    {
        String ids = TextUtils.join( "|", accounts );
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( context );
        preferences.edit().putString( ACCOUNT_PREFERENCES_KEY, ids ).commit();
    }
    
    public static Session[] getSessions( Context context )
    {
        AccountManager accountManager = AccountManager.get( context );
        Account[] accounts = accountManager.getAccountsByType( context.getString( R.string.account_type ) );
        
        if ( accounts == null || accounts.length == 0 )
            return null;
        
        String[] accountIds = getActiveAccountIds( context );
        if ( accountIds == null || accounts.length == 0 )
            return null;
        
        Session[] sessions = new Session[ accountIds.length ];
        
        int index = 0;
        for ( Account account : accounts )
        {
            String hostname = accountManager.getUserData( account, context.getString( R.string.account_hostname_key ) );
            String spec = accountManager.getUserData( account, context.getString( R.string.account_spec_key ) );
            String permutation = accountManager.getUserData( account, context.getString( R.string.account_permutation_key ) );
            String strongName = accountManager.getUserData( account, context.getString( R.string.account_strong_name_key ) );
            String password = accountManager.getPassword( account );
            String ids = accountManager.getUserData( account, context.getString( R.string.account_sesssion_id_key ) );
            ArrayList<String> array = new ArrayList<String>();
            for ( String id : TextUtils.split( ids, "|" ) )
                array.add( id );
            
            Session session =
                new Session( spec, hostname, account.name, password, permutation, strongName, array );
            
            sessions[index++] = session;
        }
        
        return sessions;
    }
    
    public static void startAuthenticationFlow( Context context, Intent intent )
    {
        Intent loginIntent = new Intent( context, AccountsActivity.class );
//        loginIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        loginIntent.putExtra( EXTRA_FINISH_INTENT, intent );
        context.startActivity( intent );
    }
    
    public static String getHostname( Context context, Account account )
    {
        AccountManager accountManager = AccountManager.get( context );
        return accountManager.getUserData( account,
                                           context.getString( R.string.account_hostname_key ) ); 
    }
    
    public static void setHostname( Context context, Account account, String hostname )
    {
        AccountManager accountManager = AccountManager.get( context );
        accountManager.setUserData( account,
                                    context.getString( R.string.account_hostname_key ),
                                    hostname );
    }
    
    public static String getSpec( Context context, Account account )
    {
        AccountManager accountManager = AccountManager.get( context );
        return accountManager.getUserData( account,
                                           context.getString( R.string.account_spec_key ) ); 
    }
    
    public static void setSpec( Context context, Account account, String spec )
    {
        AccountManager accountManager = AccountManager.get( context );
        accountManager.setUserData( account,
                                    context.getString( R.string.account_spec_key ),
                                    spec );
    }

    public static String getPassword( Context context, Account account )
    {
        AccountManager accountManager = AccountManager.get( context );
        return accountManager.getPassword( account );
    }
}
