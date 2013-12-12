package com.intelerad.android.portal;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;

/**
 * Displays the list of accounts. If there are no accounts on the device, users are prompted to
 * create one. If there are multiple accounts, users can log into multiple accounts.
 */
public class AccountsActivity extends SherlockActivity  
{
    private static final int ACCOUNT_CREATION_REQUEST_CODE = 0;
    private AccountManager mAccountManager;
    private ListView mAccountList;
    private Button mSubmitButton;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_accounts );
        
        mAccountList = (ListView) findViewById( R.id.account_list );
        mAccountList.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
        
        mSubmitButton = (Button) findViewById( R.id.account_login_button );
        mSubmitButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                SelectionAdapter listAdapter = (SelectionAdapter) mAccountList.getAdapter();
                ArrayList<Account> accounts = new ArrayList<Account>();
                for ( int index = 0; index < listAdapter.getCount(); index++ )
                {
                    SelectableAccount account = listAdapter.getItem( index );
                    if ( account.isSelected() )
                        accounts.add( account.mAccount );
                    
                    loginAllAndContinue( accounts.toArray( new Account[accounts.size()] ) );
                }
            }
        } );
        
        mAccountManager = AccountManager.get( this );
        Account[] accounts = mAccountManager.getAccountsByType( getString( R.string.account_type ) );

        if ( accounts == null || accounts.length == 0 )
        {
            // There are no accounts in the system, so we let the user go create one.
            Intent createAccountIntent = new Intent( this, LoginActivity.class );
            startActivityForResult( createAccountIntent, ACCOUNT_CREATION_REQUEST_CODE );
        }
        else
        {
            // There are multiple accounts, let the user choose one, login, and move on
            SelectableAccount[] acc = new SelectableAccount[ accounts.length ];
            String[] activeAccountIds = AccountUtility.getActiveAccountIds( this );
            if ( activeAccountIds == null )
                activeAccountIds = new String[0];
            
            int index = 0;
            for ( Account account : accounts )
            {
                SelectableAccount selectableAccount = new SelectableAccount( AccountsActivity.this, account );
                
                // TODO: Mark the account "selected"
//                for ( String activeId : activeAccountIds )
//                {
//                    if ( activeId.equals( selectableAccount )
//                }
                
                acc[ index++ ] = selectableAccount;
            }
            
            mAccountList.setAdapter( new SelectionAdapter( this, acc ) );

            if ( accounts.length == 1 )
            {
                // There's only one account, lets automatically login, and move on
                Account account = accounts[0];
                loginAllAndContinue( account );
            }
        }
    }
    
    private void loginAllAndContinue( final Account... accounts )
    {
        final ProgressDialog dialog = new ProgressDialog( this, R.style.DialogTheme );
        dialog.setMessage( getString( R.string.loging_in ) );
        dialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        
        AsyncTask<Void, Void, Session[]> task = new AsyncTask<Void, Void, Session[]>()
        {
            @Override
            protected Session[] doInBackground( Void... params )
            {
                Session[] sessions = new Session[ accounts.length ];
                int index = 0;

                try
                {
                    for ( Account account : accounts )
                    {
                        Session session = PortalApi.login( account.name,
                                                           AccountUtility.getPassword( AccountsActivity.this, account ),
                                                           AccountUtility.getSpec( AccountsActivity.this, account ),
                                                           AccountUtility.getHostname( AccountsActivity.this, account ) );

                        sessions[ index++ ] = session;
                    }
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }

                return sessions;
            }
            
            @Override
            protected void onPostExecute( Session[] result )
            {
                dialog.dismiss();
                mSubmitButton.setEnabled( true );
                String [] activeAccountIds = new String[ result.length ];
                
                int index = 0;
                for ( Session session : result )
                    activeAccountIds[ index++ ] = session.getId();
                
                AccountUtility.setActiveAccountIds( AccountsActivity.this, activeAccountIds );
///FIXME tmp
//Intent intent = new Intent( AccountsActivity.this, DatasetSelectorActivity.class );
//Utils.addAccession(intent, "1247859231");
///^TMP
                Intent intent = new Intent( AccountsActivity.this, NotificationActivity.class );
                if ( UiUtils.hasJellybean() )
                    animateIn( intent );
                else
                    startActivity( intent );
            }

        };
        
        mSubmitButton.setEnabled( false );
        dialog.show();
        task.execute();
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void animateIn( Intent intent )
    {
        Bundle bundle = ActivityOptions.makeCustomAnimation( AccountsActivity.this,
                                                             R.anim.slide_in_left,
                                                             R.anim.slide_out_left ).toBundle();
        startActivity( intent, bundle );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch ( requestCode )
        {
            case ACCOUNT_CREATION_REQUEST_CODE:
                if ( RESULT_OK == resultCode )
                {
                    Account account = data.getParcelableExtra( AccountManager.KEY_USERDATA );
                    if ( account == null )
                        return;
                    
                    SelectableAccount acc = new SelectableAccount( AccountsActivity.this, account );
                    mAccountList.setAdapter( new SelectionAdapter( this, new SelectableAccount[]{ acc } ) );
                }
                break;

            default:
                super.onActivityResult( requestCode, resultCode, data );
        }
    }
    
    private static class ViewHolder
    {
        private final CheckBox mCheckBox;
        private final TextView mHostnameTextView;
        private final TextView mUsernameTextView;
        
        public ViewHolder( CheckBox checkBox, TextView hostnameTextView, TextView usermameTextView )
        {
            mCheckBox = checkBox;
            mHostnameTextView = hostnameTextView;
            mUsernameTextView = usermameTextView;
        }
        
        public CheckBox getCheckBox()
        {
            return mCheckBox;
        }
        
        public TextView getHostnameTextView()
        {
            return mHostnameTextView;
        }
        
        public TextView getUsernameTextView()
        {
            return mUsernameTextView;
        }        
    }
    
    private static class SelectableAccount
    {
        private final Account mAccount;
        private boolean mSelected;
        private Context mContext;
        
        public SelectableAccount( Context context, Account account )
        {
            mContext = context;
            mAccount = account;
        }
        
        public boolean isSelected()
        {
            return mSelected;
        }
        
        public void setSelected( boolean selected )
        {
            mSelected = selected;
        }
        
        public String getUsername()
        {
            return mAccount.name;
        }
        
        public String getHostname()
        {
            return AccountUtility.getHostname( mContext, mAccount );
        }
    }
    
    private static class SelectionAdapter extends ArrayAdapter<SelectableAccount>
    {
        private final LayoutInflater mLayoutInflater;

        public SelectionAdapter( Context context, SelectableAccount[] items )
        {
            super( context, R.layout.item_account, R.id.account_hostname_textview, items );
            mLayoutInflater = LayoutInflater.from( context );
        }
        
        @Override
        public View getView( int position, View convertView, ViewGroup parent )
        {
            SelectableAccount item = getItem( position );
            
            CheckBox checkBox;
            TextView hostname;
            TextView username;
            
            if ( convertView == null )
            {
                convertView = mLayoutInflater.inflate( R.layout.item_account, null );
                checkBox = (CheckBox) convertView.findViewById( R.id.account_checkbox );
                hostname = (TextView) convertView.findViewById( R.id.account_hostname_textview );
                username = (TextView) convertView.findViewById( R.id.account_username_textview );
                convertView.setTag( new ViewHolder( checkBox, hostname, username ) );

                checkBox.setOnClickListener( new OnClickListener()
                {
                    @Override
                    public void onClick( View v )
                    {
                        CheckBox box = (CheckBox) v;
                        SelectableAccount account = (SelectableAccount) box.getTag();
                        account.setSelected( box.isChecked() );
                    }
                } );
            }
            else
            {
                ViewHolder holder = (ViewHolder) convertView.getTag();
                checkBox = holder.getCheckBox();
                hostname = holder.getHostnameTextView();
                username = holder.getUsernameTextView();
            }
            
            checkBox.setTag( item );
            checkBox.setChecked( item.isSelected() );
            hostname.setText( item.getHostname() );
            username.setText( item.getUsername() );
            
            return convertView;
        }
    }
}
