package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.intelerad.web.lib.gwt.model.GwtFormSpecification;

@SuppressWarnings("serial")
public class GwtPortalSettings implements Serializable
{
    private boolean mIsAcknowledgeButtonDisplayed;
    private String mHeaderImageUrl;
    private String mHeaderBackgroundColour;
    private GwtFormSpecification mFormSpec;
    private GwtPortalUser mUser;
    private int mNumberOfImagesToPrefetch;
    private int mDefaultSessionTimeout;
    private boolean mIsVolumeRenderingEnabled;
    private Set<String> mModalitiesForVolumeRendering;
    private PortalOptions mPortalOptions;
    private String mSessionId;
    private String mSessionHost;
    private Map<Integer,String> mHelpTopics;
    private boolean mKeepMeSignedInEnabled ;
    private String mKeepMeSignedInCookieName;
    private String mXmppDomain;
    private boolean mInstantMessagingEnabled;
    private String mPacsIdentifier;
    private boolean mSelfServeEnabled;
    private String mSelfServeAgreementText;
    private LandingPage mLandingPage;
    
    public GwtPortalSettings()
    {
        mModalitiesForVolumeRendering = new HashSet<String>();
    }
    
    public String getHeaderImageUrl()
    {
        return mHeaderImageUrl;
    }

    public void setHeaderImageUrl( String headerImageUrl )
    {
        this.mHeaderImageUrl = headerImageUrl;
    }

    public String getHeaderBackgroundColour()
    {
        return mHeaderBackgroundColour;
    }

    public void setHeaderBackgroundColour( String headerBackgroundColour )
    {
        this.mHeaderBackgroundColour = headerBackgroundColour;
    }

    public GwtFormSpecification getCustomUserFormSpec()
    {
        return mFormSpec;
    }

    public void setCustomUserFormSpec( GwtFormSpecification formSpec )
    {
        mFormSpec = formSpec;
    }

    public void setUser( GwtPortalUser user )
    {
        mUser = user;
    }
    
    public GwtPortalUser getUser()
    {
        return mUser;
    }
    
    public int getNumberOfImagesToPrefetch()
    {
        return mNumberOfImagesToPrefetch;
    }

    public void setNumberOfImagesToPrefetch( int numberOfImagesToPrefetch )
    {
        mNumberOfImagesToPrefetch = numberOfImagesToPrefetch;
    }

    public void setDefaultSessionTimeoutInSecs( int defaultSessionTimeout )
    {
        mDefaultSessionTimeout = defaultSessionTimeout;
    }

    public int getDefaultSessionTimeout()
    {
        return mDefaultSessionTimeout;
    }

    public boolean isVolumeRenderingEnabled()
    {
        return mIsVolumeRenderingEnabled;
    }

    public void setVolumeRenderingEnabled( boolean isVolumeRenderingEnabled )
    {
        mIsVolumeRenderingEnabled = isVolumeRenderingEnabled;
    }

    public Set<String> getModalitiesForVolumeRendering()
    {
        return mModalitiesForVolumeRendering;
    }

    public void setModalitiesForVolumeRendering( Set<String> modalitiesForVolumeRendering )
    {
        mModalitiesForVolumeRendering = modalitiesForVolumeRendering;
    }

    public PortalOptions getPortalOptions()
    {
        return mPortalOptions;
    }

    public void setPortalOptions( PortalOptions portalOptions )
    {
        mPortalOptions = portalOptions;
    }

    public String getSessionId()
    {
        return mSessionId;
    }

    public void setSessionId( String sessionId )
    {
        mSessionId = sessionId;
    }

    public String getSessionHost()
    {
        return mSessionHost;
    }

    public void setSessionHost( String sessionHost )
    {
        mSessionHost = sessionHost;
    }

    public boolean isAcknowledgeButtonDisplayed()
    {
        return mIsAcknowledgeButtonDisplayed;
    }

    public void setAcknowledgeButtonDisplayed( boolean isAcknowledgeButtonDisplayed )
    {
        this.mIsAcknowledgeButtonDisplayed = isAcknowledgeButtonDisplayed;
    }

    public Map<Integer, String> getHelpTopics()
    {
        return mHelpTopics;
    }

    public void setHelpTopics( Map<Integer, String> helpTopics )
    {
        this.mHelpTopics = helpTopics;
    }

    public boolean isKeepMeSignedInEnabled()
    {
        return mKeepMeSignedInEnabled;
    }

    public void setKeepMeSignedInEnabled( boolean keepMeSignedInEnabled )
    {
        this.mKeepMeSignedInEnabled = keepMeSignedInEnabled;
    }

    public String getKeepMeSignedInCookieName()
    {
        return mKeepMeSignedInCookieName;
    }

    public void setKeepMeSignedInCookieName( String keepMeSignedInCookieName )
    {
        this.mKeepMeSignedInCookieName = keepMeSignedInCookieName;
    }

    public String getXmppDomain()
    {
        return mXmppDomain;
    }

    public void setXmppDomain( String xmppDomain )
    {
        mXmppDomain = xmppDomain;
    }

    public boolean isInstantMessagingEnabled()
    {
        return mInstantMessagingEnabled;
    }

    public void setInstantMessagingEnabled( boolean instantMessagingEnabled )
    {
        mInstantMessagingEnabled = instantMessagingEnabled;
    }

    public String getPacsIdentifier()
    {
        return mPacsIdentifier;
    }

    public void setPacsIdentifier( String pacsIdentifier )
    {
        mPacsIdentifier = pacsIdentifier;
    }

    public boolean isSelfServeEnabled()
    {
        return mSelfServeEnabled;
    }

    public void setSelfServeEnabled( boolean selfServeEnabled )
    {
        mSelfServeEnabled = selfServeEnabled;
    }

    public String getSelfServeAgreementText()
    {
        return mSelfServeAgreementText;
    }

    public void setSelfServeAgreementText( String selfServeAgreementText )
    {
        mSelfServeAgreementText = selfServeAgreementText;
    }

    public LandingPage getLandingPage()
    {
        return mLandingPage;
    }

    public void setLandingPage( LandingPage landingPage )
    {
        mLandingPage = landingPage;
    }  
}
