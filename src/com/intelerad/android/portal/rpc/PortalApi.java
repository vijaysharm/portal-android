package com.intelerad.android.portal.rpc;

import com.intelerad.android.portal.models.Session;
import com.intelerad.imageviewer.gwt.model.GwtStudyGroup;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;
import com.intelerad.web.portal.gwt.model.GwtPortalCase;
import com.intelerad.web.portal.gwt.model.NotificationFilter;
import com.intelerad.web.portal.gwt.model.NotificationResults;
import com.intelerad.web.portal.gwt.model.SearchResults;

public class PortalApi
{
//    private static DataSource DATA_SOURCE = new PacsSource();
    private static DataSource DATA_SOURCE = new FakeSource();
    
    public static Session login( String username, String password, String spec, String host ) throws Exception
    {
        return DATA_SOURCE.login( username, password, spec, host );
    }
    
    public static GwtStudyGroup getStudies( Session session, String accessionNumber ) throws Exception
    {
        return DATA_SOURCE.getStudies( session, accessionNumber );
    }

    /**
     * TODO: This one is a bit incomplete. We can actually control the search parameters, but
     * they've been defaulted.
     */
    public static SearchResults getSearchResults( Session session, String searchString ) throws Exception
    {
        return DATA_SOURCE.getSearchResults( session, searchString );
    }
    
    public static GwtPatient getPatient( Session session, String patientId ) throws Exception
    {
        return DATA_SOURCE.getPatient( session, patientId );
    }
    
    public static GwtPortalCase getCase( Session session, String accessionNumber ) throws Exception
    {
        return DATA_SOURCE.getCase( session, accessionNumber );
    }
    
    /**
     * There is a bug in this API call. We're not making use of the itemCount. I think in the portal, its value is 10. 
     */
    public static NotificationResults getNotificationResults( Session session, int itemCount, NotificationFilter filter ) throws Exception
    {
        return DATA_SOURCE.getNotificationResults( session, itemCount, filter );
    }
    
    public static byte[] getImageBytes( Session session, String url ) throws Exception {
    	return DATA_SOURCE.getImage( session, url );
    }
}
