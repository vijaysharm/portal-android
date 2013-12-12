package com.intelerad.android.portal.models;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Parcel;
import android.os.Parcelable;

public class Session
{
    private final Collection <String> mSessionIds;
    private final String mSpec;
    private final String mHost;
    private final String mUsername;
    private final String mPassword;
    private final String mPermutation;
    private final String mStrongName;
    private final String mId;
    
    public Session( String spec,
                    String host,
                    String username,
                    String password,
                    String permutation,
                    String strongName,
                    Collection<String> sessionIds )
    {
        mSpec = spec;
        mHost = host;
        mUsername = username;
        mPassword = password;
        mPermutation = permutation;
        mStrongName = strongName;
        mSessionIds = sessionIds;
        mId = mHost + "-" + mUsername;
    }

    public String getId()
    {
        return mId;
    }

    public String getUsername()
    {
        return mUsername;
    }
    
    public String getPassword()
    {
        return mPassword;
    }
    
    public String getHost()
    {
        return mHost;
    }
    
    public String getSpec()
    {
        return mSpec;
    }
    
    public String getPermutation()
    {
        return mPermutation;
    }
    
    public String getStrongName()
    {
        return mStrongName;
    }
    
    public ArrayList<String> getCookieIds()
    {
        return new ArrayList<String>( mSessionIds );
    }
    
    public static class SerializedForAndroid implements Parcelable
    {
        private final Session mSession;

        public SerializedForAndroid( Parcel parcel )
        {
            mSession = readFromParcel( parcel );
        }
        
        // Serialize it to for intents
        public SerializedForAndroid( Session session )
        {
            mSession = session;
        }
        
        public Session getSession()
        {
            return mSession;
        }
        
        @Override
        public void writeToParcel( Parcel dest, int flags )
        {
            dest.writeString( mSession.mSpec );
            dest.writeString( mSession.mHost );
            dest.writeString( mSession.mUsername );
            dest.writeString( mSession.mPassword );
            dest.writeString( mSession.mPermutation );
            dest.writeString( mSession.mStrongName );
            
            int size = mSession.mSessionIds.size();
            dest.writeInt( size );
            for ( String id : mSession.mSessionIds )
                dest.writeString( id );
        }

        private Session readFromParcel( Parcel parcel )
        {
            String spec = parcel.readString();
            String host = parcel.readString();
            String username = parcel.readString();
            String password = parcel.readString();
            String permutation = parcel.readString();
            String strongname = parcel.readString();
            
            Collection<String> sessionsIds = new ArrayList<String>();
            int size = parcel.readInt();
            for ( int index = 0; index < size; index++ )
            {
                String value = parcel.readString();
                sessionsIds.add( value );
            }
            
            return new Session( spec, host, username, password, permutation, strongname, sessionsIds );
        }
        
        @Override
        public int describeContents()
        {
            return 0;
        }
        
        public static final Parcelable.Creator<SerializedForAndroid> CREATOR = new Parcelable.Creator<SerializedForAndroid>()
        {
            @Override
            public SerializedForAndroid createFromParcel( Parcel source )
            {
                return new SerializedForAndroid( source );
            }

            @Override
            public SerializedForAndroid[] newArray( int size )
            {
                return new SerializedForAndroid[ size ];
            }
        };
    }
    
    public static class Builder
    {
        private final ArrayList<String> mSessionIds = new ArrayList<String>();
        private final String mSpec;
        private final String mHost;
        private final String mUsername;
        private final String mPassword;
        private final String mPermutation;
        private final String mStrongName;
        
        public Builder( String spec,
                        String host,
                        String username,
                        String password,
                        String permutation,
                        String strongName,
                        Collection<String> parsedCookies )
        {
            mSpec = spec;
            mHost = host;
            mUsername = username;
            mPassword = password;
            mPermutation = permutation;
            mStrongName = strongName;
            mSessionIds.addAll( parsedCookies );
        }

        public Session build()
        {
            return new Session( mSpec, mHost, mUsername, mPassword, mPermutation, mStrongName, mSessionIds );
        }
    }
}
