package com.intelerad.android.portal.rpc;

/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Performs GET and POST operations against a Google App Engine app, authenticating with a Google
 * account on the device.
 */
public class HttpClientEngine
{

    /**
     * Performs an HTTP GET request. The request is performed inline and this method must not be
     * called from the UI thread.
     * 
     * @param uri The URI you're sending the GET to
     * @param headers Any extra HTTP headers you want to send; may be null
     * @return a Response structure containing the status, headers, and body. Returns null if the
     *         request could not be launched due to an IO error or authentication failure; in which
     *         case use errorMessage() to retrieve diagnostic information.
     */
    public static Response get( URL uri, Map<String, List<String>> headers )
    {
        GET get = new GET( uri, headers );
        return getOrPost( get );
    }

    /**
     * Performs an HTTP POST request. The request is performed inline and this method must not be
     * called from the UI thread.
     * 
     * @param uri The URI you're sending the POST to
     * @param headers Any extra HTTP headers you want to send; may be null
     * @param body The request body to transmit
     * @return a Response structure containing the status, headers, and body. Returns null if the
     *         request could not be launched due to an IO error or authentication failure; in which
     *         case use errorMessage() to retrieve diagnostic information.
     */
    public static Response post( URL uri, Map<String, List<String>> headers, byte[] body )
    {
        POST post = new POST( uri, headers, body );
        return getOrPost( post );
    }

    private static Response getOrPost( Request request )
    {
        disableConnectionReuseIfNecessary();
        HttpURLConnection conn = null;
        Response response = null;
        try
        {
            conn = (HttpURLConnection) request.uri.openConnection();
            if ( request.headers != null )
            {
                for ( String header : request.headers.keySet() )
                {
                    for ( String value : request.headers.get( header ) )
                        conn.addRequestProperty( header, value );
                }
            }
            if ( request instanceof POST )
            {
                byte[] payload = ( (POST) request ).body;
                conn.setDoOutput( true );
                conn.setFixedLengthStreamingMode( payload.length );
//                conn.setUseCaches( false );
//                conn.setConnectTimeout( 60000 );
//                conn.setReadTimeout( 60000 );
                
//                conn.getOutputStream().write( payload );
                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write( payload );
                out.flush();
                conn.connect();

                int status = conn.getResponseCode();
                if ( status / 100 != 2 )
                {
                    Map<String, List<String>> headers = conn.getHeaderFields();
                    headers =  headers == null ? new Hashtable<String, List<String>>() : headers;
                    response = new Response( status,
                                             headers,
                                             conn.getResponseMessage().getBytes() );
                }
            }
            if ( response == null )
            {
                BufferedInputStream in = new BufferedInputStream( conn.getInputStream() );
                byte[] body = readStream( in );
                response = new Response( conn.getResponseCode(), conn.getHeaderFields(), body );
            }
        }
        catch ( IOException exception )
        {
            String errorMessage = ( ( request instanceof POST ) ? "POST " : "GET " ) + ": " + exception.getMessage();
            throw new RuntimeException( errorMessage, exception );
        }
        finally
        {
            if ( conn != null )
                conn.disconnect();
        }
        return response;
    }

    // request structs
    private static class Request
    {
        public URL uri;
        public Map<String, List<String>> headers;

        public Request( URL uri, Map<String, List<String>> headers )
        {
            this.uri = uri;
            this.headers = headers;
        }
    }
    
    private static class POST extends Request
    {
        public byte[] body;

        public POST( URL uri, Map<String, List<String>> headers, byte[] body )
        {
            super( uri, headers );
            this.body = body;
        }
    }
    
    private static class GET extends Request
    {
        public GET( URL uri, Map<String, List<String>> headers )
        {
            super( uri, headers );
        }
    }

    // utilities
    private static byte[] readStream( InputStream in ) throws IOException
    {
        byte[] buf = new byte[ 1024 ];
        int count = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream( 1024 );
        while ( ( count = in.read( buf ) ) != -1 )
            out.write( buf, 0, count );
        return out.toByteArray();
    }
    
    private static void disableConnectionReuseIfNecessary()
    {
        // HTTP connection reuse which was buggy pre-froyo
//        if ( Integer.parseInt( Build.VERSION.SDK ) < Build.VERSION_CODES.FROYO )
            System.setProperty( "http.keepAlive", "false" );
    }
}
