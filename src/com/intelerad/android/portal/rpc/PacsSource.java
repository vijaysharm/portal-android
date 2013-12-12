package com.intelerad.android.portal.rpc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import com.gdevelop.gwt.syncrpc.SyncClientSerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.intelerad.android.portal.models.Session;
import com.intelerad.imageviewer.gwt.model.GwtStudyGroup;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;
import com.intelerad.web.portal.gwt.model.GwtPortalCase;
import com.intelerad.web.portal.gwt.model.NotificationFilter;
import com.intelerad.web.portal.gwt.model.NotificationResults;
import com.intelerad.web.portal.gwt.model.SearchResults;

public class PacsSource implements DataSource
{
    private static final String CONNECTION_TYPE = "keep-alive";
    private static final String AGENT_TYPE =
          "Mozilla/5.0 (Linux; U; Android 1.6; en-us; GenericAndroidDevice) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1";    
    
    // All entity strong names in GWT are 32 characters 
    private static Pattern ENTITY_NAME_PATTERN = Pattern.compile( "\'([A-Z0-9]){32}\'" );

    @Override
    public Session login( String username, String password, String spec, String host ) throws Exception
    {
        // Create an initial JSESSION ID
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "*/*" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        
        headers.put( "Host", Arrays.asList( host ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );

        Response response = get( createUri( spec, host, "/Portal/app" ), headers );
        if ( 200 != response.getStatus() )
            throw new RuntimeException( "GET of JSESSIONID Status returned: " + response.getStatus() );
        
        String cookieId = extractCookieId( response.getHeaders().get( "Set-Cookie" ).get( 0 ) );
        String cookiePath = extractCookiePath( response.getHeaders().get( "Set-Cookie" ).get( 0 ) );

        // POST the login creditials
        headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Cache-Control", Arrays.asList( "max-age=0" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "application/x-www-form-urlencoded" ) );
        headers.put( "Cookie", Arrays.asList( cookieId ) );
        headers.put( "Host", Arrays.asList( host ) );
        headers.put( "Origin", Arrays.asList( createUri( spec, host, "" ) ) );
        headers.put( "Referer", Arrays.asList( createUri( spec, host, "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        
        Map<String, String> postArgs = new LinkedHashMap<String, String>();
        postArgs.put( "service", "direct/1/Login/loginForm" );
        postArgs.put( "sp", "S0" );
        postArgs.put( "Form0", "$Hidden,$Hidden$0,j_username,j_password,redirectUrl" );
        postArgs.put( "$Hidden", "X" );
        postArgs.put( "$Hidden$0", "X" );
        postArgs.put( "redirectUrl", "/Portal/app" );
        postArgs.put( "j_username", username );
        postArgs.put( "j_password", password );
        
        byte[] encodedPostArgs = convertPostArgs( postArgs ).getBytes();
        
        response = post( createUri( spec, host, "/Portal/j_security_check" ), headers, encodedPostArgs );
        if ( 302 != response.getStatus() )
            throw new RuntimeException( "POST of login Status returned: " + response.getStatus() + " instead of expected 302" );
        
        // Go to new location
        String movedLocation = response.getHeaders().get( "Location" ).get( 0 );
        headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Cache-Control", Arrays.asList( "max-age=0" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "application/x-www-form-urlencoded" ) );
        headers.put( "Cookie", Arrays.asList( cookieId ) );
        headers.put( "Host", Arrays.asList( host ) );
        headers.put( "Referer", Arrays.asList( createUri( spec, host, "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        
        response = get( movedLocation, headers );
        if ( 200 != response.getStatus() )
            throw new RuntimeException( "GET of moved location Status returned: " + response.getStatus() );

        List<String> cookies = response.getHeaders().get( "Set-Cookie" );
        Map<String, String> parsedCookies = new HashMap<String, String>();
        for ( String cookie : cookies )
            parsedCookies.put( extractCookiePath( cookie ), extractCookieId( cookie ) );
        
        // Go get the permutation and rpc manifest name
        headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "*/*" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Host", Arrays.asList( host ) );
        headers.put( "Referer", Arrays.asList( createUri( spec, host, "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        
        // http://v441a/Portal/gwt/portal/portal.nocache.js
        response = get( createUri( spec, host, "/Portal/gwt/portal/portal.nocache.js" ), headers );
        if ( 200 != response.getStatus() )
            throw new RuntimeException( "GET portal.nocache.js Status returned: " + response.getStatus() );
        
        String portalNoCacheJs = new String( response.getBody() );
        Matcher cacheMatcher = ENTITY_NAME_PATTERN.matcher( portalNoCacheJs );
        String permutation = ( cacheMatcher.find() ? cacheMatcher.group().replace( "\'", "" ) : null );
        
        // http://qa441mdb1/Portal/gwt/WEB-INF/deploy/portal/rpcPolicyManifest/manifest.txt
        String manifest = createUri( spec, host, "/Portal/gwt/WEB-INF/deploy/portal/rpcPolicyManifest/manifest.txt" );
        response = get( manifest, headers );
        if ( 200 != response.getStatus() )
            System.err.println( "GET manifest.txt Status returned: " + response.getStatus() );
        
        String strongName = parseStrongName( response );
        if ( strongName == null )
            throw new RuntimeException( "Got null strong name. Failed to read: " + manifest );
        
        System.err.println( "portal strong name: " + strongName );
        
        return new Session.Builder( spec, host, username, password, permutation, strongName, parsedCookies.values() ).build();
    }

    @Override
    public GwtStudyGroup getStudies( Session session, String accessionNumber ) throws Exception
    {
        String responseString = getStudy( session, accessionNumber );
        
        SyncClientSerializationStreamReader reader = new SyncClientSerializationStreamReader( DUMMY_POLICY );
        reader.prepareToRead( responseString );
        Object value = reader.deserializeValue( GwtStudyGroup.class );

        if ( GwtStudyGroup.class.equals( value.getClass() ) )
            return (GwtStudyGroup) value;
        else if ( value instanceof Exception )
            throw (Exception) value;
        else
            throw new RuntimeException( "Unexpected class type received: " + value.getClass() );
    }
    
    @Override
    public SearchResults getSearchResults( Session session, String searchString ) throws Exception
    {
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "*/*" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "text/x-gwt-rpc; charset=UTF-8" ) );
        headers.put( "Cookie", session.getCookieIds() );
        headers.put( "DNT", Arrays.asList( "1" ) );
        headers.put( "Host", Arrays.asList( session.getHost() ) );
        headers.put( "Origin", Arrays.asList( createUri( session.getSpec(), session.getHost(), "" ) ) );
        headers.put( "Referer", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        headers.put( "X-GWT-Module-Base", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) ) );
        headers.put( "X-GWT-Permutation", Arrays.asList( session.getPermutation() ) );
        
        StringBuilder postArgs = new StringBuilder();
        postArgs.append( "7|0|7|" + createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) + "|" );
        postArgs.append( session.getStrongName() );
        postArgs.append( "|com.intelerad.web.portal.gwt.client.PortalService|getSearchResults|com.intelerad.web.portal.gwt.model.SearchParameters/1005726375|" );
        postArgs.append( searchString.toLowerCase() );
        postArgs.append( "|java.util.LinkedHashMap/3008245022|1|2|3|4|1|5|5|10|1|0|6|7|0|0|" );
        
        String post = postArgs.toString();
        String uri = createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/portalService" );
        Response response = post( uri, headers, post.getBytes() );

        if ( 200 != response.getStatus() )
            throw new RuntimeException( "Get Studies Status returned: " + response.getStatus() );

        String responseString = unZipResponse( response );
        
        SyncClientSerializationStreamReader reader = new SyncClientSerializationStreamReader( DUMMY_POLICY );
        reader.prepareToRead( responseString );
        Object value = reader.deserializeValue( SearchResults.class );
        
        if ( SearchResults.class.equals( value.getClass() ) )
            return (SearchResults) value;
        else if ( value instanceof Exception )
            throw (Exception) value;
        else
            throw new RuntimeException( "Unexpected class type received: " + value.getClass() );
    }
    
    @Override
    public GwtPatient getPatient( Session session, String patientId ) throws Exception
    {
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "*/*" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "text/x-gwt-rpc; charset=UTF-8" ) );
        headers.put( "Cookie", session.getCookieIds() );
        headers.put( "DNT", Arrays.asList( "1" ) );
        headers.put( "Host", Arrays.asList( session.getHost() ) );
        headers.put( "Origin", Arrays.asList( createUri( session.getSpec(), session.getHost(), "" ) ) );
        headers.put( "Referer", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        headers.put( "X-GWT-Module-Base", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) ) );
        headers.put( "X-GWT-Permutation", Arrays.asList( session.getPermutation() ) );
        
        StringBuilder postArgs = new StringBuilder();
        postArgs.append( "7|0|6|" + createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) + "|" );
        postArgs.append( session.getStrongName() );
        postArgs.append( "|com.intelerad.web.portal.gwt.client.PortalService|getPatient|java.lang.String/2004016611|" );
        postArgs.append( patientId );
        postArgs.append( "|1|2|3|4|1|5|6|" );
        
        String post = postArgs.toString();
        String uri = createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/portalService" );
        Response response = post( uri, headers, post.getBytes() );

        if ( 200 != response.getStatus() )
            throw new RuntimeException( "Get Studies Status returned: " + response.getStatus() );

        String responseString = unZipResponse( response );
        
        SyncClientSerializationStreamReader reader = new SyncClientSerializationStreamReader( DUMMY_POLICY );
        reader.prepareToRead( responseString );
        Object value = reader.deserializeValue( GwtPatient.class );
        
        if ( GwtPatient.class.equals( value.getClass() ) )
            return (GwtPatient) value;
        else if ( value instanceof Exception )
            throw (Exception) value;
        else
            throw new RuntimeException( "Unexpected class type received: " + value.getClass() );
    }
    
    @Override
    public GwtPortalCase getCase( Session session, String accessionNumber ) throws Exception
    {
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "*/*" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "text/x-gwt-rpc; charset=UTF-8" ) );
        headers.put( "Cookie", session.getCookieIds() );
        headers.put( "DNT", Arrays.asList( "1" ) );
        headers.put( "Host", Arrays.asList( session.getHost() ) );
        headers.put( "Origin", Arrays.asList( createUri( session.getSpec(), session.getHost(), "" ) ) );
        headers.put( "Referer", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        headers.put( "X-GWT-Module-Base", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) ) );
        headers.put( "X-GWT-Permutation", Arrays.asList( session.getPermutation() ) );
        
        StringBuilder postArgs = new StringBuilder();
        postArgs.append( "7|0|6|" + createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) + "|" );
        postArgs.append( session.getStrongName() );
        postArgs.append( "|com.intelerad.web.portal.gwt.client.PortalService|getCase|java.lang.String/2004016611|" );
        postArgs.append( accessionNumber );
        postArgs.append( "|1|2|3|4|1|5|6|" );
        
        String post = postArgs.toString();
        String uri = createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/portalService" );
        Response response = post( uri, headers, post.getBytes() );

        if ( 200 != response.getStatus() )
            throw new RuntimeException( "Get Studies Status returned: " + response.getStatus() );

        String responseString = unZipResponse( response );
        
        SyncClientSerializationStreamReader reader = new SyncClientSerializationStreamReader( DUMMY_POLICY );
        reader.prepareToRead( responseString );
        Object value = reader.deserializeValue( GwtPortalCase.class );
        
        if ( GwtPortalCase.class.equals( value.getClass() ) )
            return (GwtPortalCase) value;
        else if ( value instanceof Exception )
            throw (Exception) value;
        else
            throw new RuntimeException( "Unexpected class type received: " + value.getClass() );
    }
    
    @Override
    public NotificationResults getNotificationResults( Session session,
                                                       int itemCount,
                                                       NotificationFilter filter ) throws Exception
    {
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "*/*" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "text/x-gwt-rpc; charset=UTF-8" ) );
        headers.put( "Cookie", session.getCookieIds() );
        headers.put( "DNT", Arrays.asList( "1" ) );
        headers.put( "Host", Arrays.asList( session.getHost() ) );
        headers.put( "Origin", Arrays.asList( createUri( session.getSpec(), session.getHost(), "" ) ) );
        headers.put( "Referer", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        headers.put( "X-GWT-Module-Base", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) ) );
        headers.put( "X-GWT-Permutation", Arrays.asList( session.getPermutation() ) );
        
        StringBuilder postArgs = new StringBuilder();
        postArgs.append( "7|0|6|http://v451a/Portal/gwt/portal/|85B7775BD619C487A54D73E88786CD6C|" +
                        "com.intelerad.web.portal.gwt.client.PortalService|getNotificationResults|I|" +
                        "com.intelerad.web.portal.gwt.model.NotificationFilter/1054540051|1|2|3|4|2|5|6|10|6|" );
        postArgs.append( filter.getIndex() + "|" );
        
        String post = postArgs.toString();
        String uri = createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/portalService" );
        Response response = post( uri, headers, post.getBytes() );

        if ( 200 != response.getStatus() )
            throw new RuntimeException( "Get Studies Status returned: " + response.getStatus() );

        String responseString = unZipResponse( response );
        
        SyncClientSerializationStreamReader reader = new SyncClientSerializationStreamReader( DUMMY_POLICY );
        reader.prepareToRead( responseString );
        Object value = reader.deserializeValue( NotificationResults.class );
        
        if ( NotificationResults.class.equals( value.getClass() ) )
            return (NotificationResults) value;
        else if ( value instanceof Exception )
            throw (Exception) value;
        else
            throw new RuntimeException( "Unexpected class type received: " + value.getClass() );
    }
    
    @Override
    public byte[] getImage( Session session, String url ) throws Exception
    {
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "image/png,image/*;q=0.8,*/*;q=0.5" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Cookie", session.getCookieIds() );
        headers.put( "Host", Arrays.asList( session.getHost() ) );
        headers.put( "Referer", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        
        Response response = get( createUri( session.getSpec(), session.getHost(), url ), headers );
        if ( 200 != response.getStatus() )
            throw new RuntimeException( "Failed to get image. Got response " + response.getStatus() + ": to " + url );
        
//        writeOutImage( response, "/tmp/irs-output.jpg" );
        return response.getBody();
    }
    
//    private static byte[] getFirstImage( Session session, String accession, int width, int height ) throws Exception
//    {
//        GwtStudyGroup studies = getStudies( session, accession );
//        List<GwtDataset> nonKeyDatasets = studies.getNonKeyDatasets();
//        GwtDataset gwtDataset = nonKeyDatasets.get( 1 );
//        List<GwtPacsImage> images = gwtDataset.getImages();
//        System.err.println( "Total # of images: " + images.size() );
//        long start = System.currentTimeMillis();
//        
//        for ( GwtPacsImage image : images )
//        {
//            ImageRequest request = new Image2dRequest( image, width, height, true );
//            String url = image.getUrlEncoder().getUrl( request );
//            
//            return getImage( session, url );
//        }
//
//        long stop = System.currentTimeMillis();
//        System.err.println( "Total time to get " + images.size() + " images: " + ( stop - start ) + " ms" );
//        return null;
//    }
    
    private static String getStudy( Session session, String accessionNumber ) throws Exception
    {
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put( "Accept", Arrays.asList( "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" ) );
        headers.put( "Accept-Charset", Arrays.asList( "ISO-8859-1,utf-8;q=0.7,*;q=0.3" ) );
        headers.put( "Accept-Encoding", Arrays.asList( "gzip,deflate,sdch" ) );
        headers.put( "Accept-Language", Arrays.asList( "en-US,en;q=0.8" ) );
        headers.put( "Connection", Arrays.asList( CONNECTION_TYPE ) );
        headers.put( "Content-Type", Arrays.asList( "text/x-gwt-rpc; charset=UTF-8" ) );
        headers.put( "Cookie", session.getCookieIds() );
        headers.put( "Host", Arrays.asList( session.getHost() ) );
        headers.put( "Origin", Arrays.asList( createUri( session.getSpec(), session.getHost(), "" ) ) );
        headers.put( "Referer", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/app" ) ) );
        headers.put( "User-Agent", Arrays.asList( AGENT_TYPE ) );
        headers.put( "X-GWT-Module-Base", Arrays.asList( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) ) );
        headers.put( "X-GWT-Permutation", Arrays.asList( session.getPermutation() ) );
        
//        SyncClientSerializationStreamWriter writer =
//            new SyncClientSerializationStreamWriter( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ),
//                                                     WHITELIST_HASH,
//                                                     retrieveSerializationPolicy( createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ), WHITELIST_HASH ) );
//        writer.prepareToWrite();
//        writer.writeString( "com.intelerad.web.portal.gwt.client.PortalService" );
//        writer.writeString( "getStudies" );
//        writer.writeString( "java.lang.String/2004016611" ); // you can get this from the .rpc file
//        writeParam( writer, String.class, accessionNumber );
//        System.err.println( writer.toString() );
        
        StringBuilder postArgs = new StringBuilder();
        postArgs.append( "7|0|6|" + createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/" ) + "|" );
        postArgs.append( session.getStrongName() );
        postArgs.append( "|com.intelerad.web.portal.gwt.client.PortalService|getStudies|java.lang.String/2004016611|" );
        postArgs.append( accessionNumber );
        postArgs.append( "|1|2|3|4|1|5|6|" );
        
        String post = postArgs.toString();
        String uri = createUri( session.getSpec(), session.getHost(), "/Portal/gwt/portal/portalService" );
        Response response = post( uri, headers, post.getBytes() );

        if ( 200 != response.getStatus() )
            throw new RuntimeException( "Get Studies Status returned: " + response.getStatus() );

        return unZipResponse( response );
    }
    
    private static String unZipResponse( Response response ) throws IOException
    {
        InputStream gzipStream =
            new GZIPInputStream( new ByteArrayInputStream( response.getBody() ) );
        Reader decoder = new InputStreamReader( gzipStream );
        BufferedReader buffered = new BufferedReader( decoder );

        String readed = "";
        while ( ( readed = buffered.readLine() ) != null )
            return readed.substring( 4 );

        return "//EX";
    }

    private static void writeOutImage( Response response, String location ) throws IOException
    {
//        ByteArrayInputStream bis = new ByteArrayInputStream( response.getBody() );
//        Iterator<?> readers = ImageIO.getImageReadersByFormatName( "jpg" );
//        // ImageIO is a class containing static convenience methods for locating ImageReaders
//        // and ImageWriters, and performing simple encoding and decoding.
//
//        ImageReader reader = (ImageReader) readers.next();
//        Object source = bis; // File or InputStream, it seems file is OK
//
//        ImageInputStream iis = ImageIO.createImageInputStream( source );
//        // Returns an ImageInputStream that will take its input from the given Object
//
//        reader.setInput( iis, true );
//        ImageReadParam param = reader.getDefaultReadParam();
//
//        Image image = reader.read( 0, param );
//        // got an image file
//
//        BufferedImage bufferedImage =
//            new BufferedImage( image.getWidth( null ),
//                               image.getHeight( null ),
//                               BufferedImage.TYPE_INT_RGB );
//        // bufferedImage is the RenderedImage to be written
//        Graphics2D g2 = bufferedImage.createGraphics();
//        g2.drawImage( image, null, null );
//        File imageFile = new File( location );
//
//        // "jpg" is the format of the image
//        // imageFile is the file to be written to.
//        ImageIO.write( bufferedImage, "jpg", imageFile );
    }

    private static Response get( String uri, Map<String, List<String>> headers ) throws MalformedURLException
    {
//        return HttpClientEngine.get( new URL( uri ), headers );
        return ApacheHttpEngine.get( uri, headers );
    }
    
    private static Response post( String uri, Map<String, List<String>> headers, byte[] encodedPostArgs ) throws MalformedURLException
    {
        return ApacheHttpEngine.post( uri, headers, encodedPostArgs );
//        return HttpClientEngine.post( new URL( uri ), headers, encodedPostArgs );
    }

    private static String parseStrongName( Response response ) throws IOException
    {
        BufferedReader br = new BufferedReader( new InputStreamReader( new ByteArrayInputStream( response.getBody() ) ) );
        String line = br.readLine();
        while ( line != null )
        {
            if ( ! line.startsWith( "#" ) )
            {
                String[] split = line.split( "," );
                if ( "com.intelerad.web.portal.gwt.client.PortalService".equals( split[0].trim() ) )
                {
                    int strongName = split[1].trim().indexOf( ".gwt.rpc" );
                    return split[1].trim().substring( 0, strongName );
                }
            }
            
            line = br.readLine();
        }
        
        return null;
    }

    private static String extractCookiePath( String cookie )
    {
        return cookie.split( ";" )[1].trim();
    }

    private static String extractCookieId( String cookie )
    {
        return cookie.split( ";" )[0].trim();
    }
    
    private static String encode( String postArgs, String encoding )
    {
        try
        {
            return URLEncoder.encode( postArgs, encoding );
        }
        catch ( UnsupportedEncodingException exeption )
        {
            throw new RuntimeException( exeption );
        }
    }

    private static String convertPostArgs( Map<String, String> postArgs )
    {
        StringBuilder args = new StringBuilder();
        
        int count = 0;
        for ( Entry<String, String> entry : postArgs.entrySet() )
        {
            args.append( encode( entry.getKey(), "UTF-8" ) + "=" + encode( entry.getValue(), "UTF-8" ) );
            if ( count != ( postArgs.size() - 1 ) )
                args.append( "&" );
            
            count++;
        }
        
        return args.toString();
    }

    private static String createUri( String spec, String host, String path )
    {
        return spec + "://" + host + path;
    }
    
    private static final SerializationPolicy DUMMY_POLICY = new SerializationPolicy()
    {
        @Override
        public void validateSerialize( Class<?> clazz ) throws SerializationException {}
        
        @Override
        public void validateDeserialize( Class<?> clazz ) throws SerializationException {}
        
        @Override
        public boolean shouldSerializeFields( Class<?> clazz )
        {
            return clazz == null ? false : true;
        }
        
        @Override
        public boolean shouldDeserializeFields( Class<?> clazz )
        {
            return clazz == null ? false : true;
        }        
    };    
}
