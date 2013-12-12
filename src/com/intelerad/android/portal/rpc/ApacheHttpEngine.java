package com.intelerad.android.portal.rpc;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class ApacheHttpEngine
{
    public static Response post( String uri,
                                 Map<String, List<String>> headers,
                                 byte[] encodedPostArgs )
    {
        HttpParams httpClientParams = new BasicHttpParams();
        HttpClientParams.setRedirecting( httpClientParams, false );
        
        HttpPost post = new HttpPost( uri );
        for ( Entry<String, List<String>> entry : headers.entrySet() )
        {
            String header = entry.getKey();
            for ( String value : entry.getValue() )
                post.addHeader( new BasicHeader( header, value ) );
        }
        post.setEntity( new ByteArrayEntity( encodedPostArgs ) );

        HttpClient httpClient = new DefaultHttpClient( httpClientParams );
        try
        {
            HttpResponse response = httpClient.execute( post );
            HttpEntity entity = response.getEntity();

            int status = response.getStatusLine().getStatusCode();
            byte[] body = readStream( entity.getContent() );
            Map<String, List<String>> responseHeaders = parseHeaders( response.getAllHeaders() );
            
            return new Response( status, responseHeaders, body );
        }
        catch ( Exception exception )
        {
            throw new RuntimeException( exception );
        }
    }    

    public static Response get( String uri, Map<String, List<String>> headers )
    {
        HttpGet get = new HttpGet( uri );
        
        for ( Entry<String, List<String>> entry : headers.entrySet() )
        {
            String header = entry.getKey();
            for ( String value : entry.getValue() )
                get.addHeader( new BasicHeader( header, value ) );
        }
        
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            HttpResponse response = httpClient.execute( get );
            HttpEntity entity = response.getEntity();

            int status = response.getStatusLine().getStatusCode();
            byte[] body = readStream( entity.getContent() );
            Map<String, List<String>> responseHeaders = parseHeaders( response.getAllHeaders() );
            
            return new Response( status, responseHeaders, body );
        }
        catch ( Exception exception )
        {
            throw new RuntimeException( exception );
        }
    }
    
    private static Map<String, List<String>> parseHeaders( Header[] allHeaders )
    {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        
        for ( Header header : allHeaders )
        {
            if ( ! headers.containsKey( header.getName()) )
                headers.put( header.getName(), new ArrayList<String>() );
            
            headers.get( header.getName() ).add( header.getValue() );
        }
        
        return headers;
    }

    // utilities
    private static byte[] readStream( InputStream stream ) throws IOException
    {
        BufferedInputStream in = new BufferedInputStream( stream );
        byte[] buf = new byte[ 1024 ];
        int count = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream( 1024 );
        while ( ( count = in.read( buf ) ) != -1 )
            out.write( buf, 0, count );
        return out.toByteArray();
    }
}
