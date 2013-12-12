package com.intelerad.android.portal.rpc;

import com.intelerad.android.portal.models.Session;
import com.intelerad.imageviewer.gwt.model.GwtStudyGroup;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;
import com.intelerad.web.portal.gwt.model.GwtPortalCase;
import com.intelerad.web.portal.gwt.model.NotificationFilter;
import com.intelerad.web.portal.gwt.model.NotificationResults;
import com.intelerad.web.portal.gwt.model.SearchResults;

public interface DataSource
{
    Session login( String username, String password, String spec, String host ) throws Exception;
    GwtStudyGroup getStudies( Session session, String accessionNumber ) throws Exception;
    SearchResults getSearchResults( Session session, String searchString ) throws Exception;
    GwtPatient getPatient( Session session, String patientId ) throws Exception;
    GwtPortalCase getCase( Session session, String accessionNumber ) throws Exception;
    NotificationResults getNotificationResults( Session session,
                                                int itemCount,
                                                NotificationFilter filter ) throws Exception;
    byte[] getImage( Session session, String url ) throws Exception;
}
