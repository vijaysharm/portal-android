package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GwtCriticalFinding implements ModificationSubject, Comparable<GwtCriticalFinding>, Serializable
{
    private Long mFindingId;
    private String mLabel;
    private String mLanguage;
    private Long mLevelId;

    /**
     * A default constructor required by GWT.
     */
    private GwtCriticalFinding()
    {
    }

    /**
     * Creates a new <code>GwtCriticalFinding</code> instance.
     * <p/>
     * To simplify modification tracking all critical results are required to have a unique
     * identifier and this constructor will generate a temporary (negative) id for that purpose.
     * 
     * @param label the critical finding title
     * @param levelId the id of the parent priority level
     * @see ModificationTracker
     * @see ModificationSubject
     */
    public GwtCriticalFinding( String label, Long levelId )
    {
        // generate a temporary ID
        mFindingId = ( -1 ) * System.currentTimeMillis();
        mLabel = label;
        mLevelId = levelId;
    }

    /**
     * Creates a new <code>GwtCriticalFinding</code> instance.
     * <p/>
     * The constructor ensures that the provided finding id is not <code>null</code> by throwing an
     * {@link IllegalArgumentException}
     * 
     * @param findingId the unique identifier
     * @param label the critical finding title
     * @param levelId the id of the parent priority level
     * @throws IllegalArgumentException if the <code>findingId</code> is <code>null</code>
     */
    public GwtCriticalFinding( Long findingId, String label, Long levelId )
    {
        if ( findingId == null )
            throw new IllegalArgumentException( "The 'findingId' parameter cannot be 'null'" );

        mFindingId = findingId;
        mLabel = label;
        mLevelId = levelId;
    }

    @Override
    public Long getKey()
    {
        return getFindingId();
    }

    public Long getFindingId()
    {
        return mFindingId;
    }

    public void setFindingId( Long id )
    {
        mFindingId = id;
    }

    public String getLabel()
    {
        return mLabel;
    }

    public void setLabel( String label )
    {
        mLabel = label;
    }

    public String getLanguage()
    {
        return mLanguage;
    }

    public void setLanguage( String language )
    {
        mLanguage = language;
    }

    public Long getLevelId()
    {
        return mLevelId;
    }

    public void setLevelId( Long levelId )
    {
        mLevelId = levelId;
    }

    public GwtCriticalFinding deepCopy()
    {
        GwtCriticalFinding criticalFindingCopy = new GwtCriticalFinding();
        criticalFindingCopy.setFindingId( mFindingId );
        criticalFindingCopy.setLabel( mLabel );
        criticalFindingCopy.setLanguage( mLanguage );
        criticalFindingCopy.setLevelId( mLevelId );

        return criticalFindingCopy;
    }
    
    public boolean exactEquals( GwtCriticalFinding that )
    {
        if ( mFindingId != null ? !mFindingId.equals( that.mFindingId ) : that.mFindingId != null ) return false;
        if ( mLabel != null ? !mLabel.equals( that.mLabel ) : that.mLabel != null ) return false;
        if ( mLanguage != null ? !mLanguage.equals( that.mLanguage ) : that.mLanguage != null ) return false;
        if ( mLevelId != null ? !mLevelId.equals( that.mLevelId ) : that.mLevelId != null ) return false;
        
        return true;
    }

    @Override
    public int compareTo( GwtCriticalFinding other )
    {
        return mLabel.compareToIgnoreCase( other.mLabel );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;
        GwtCriticalFinding that = (GwtCriticalFinding) o;
        if ( !mLabel.equalsIgnoreCase( that.mLabel ) )
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        return mLabel.hashCode();
    }

    @Override
    public String toString()
    {
        return "GwtCriticalFinding [findingId=" + mFindingId + ", label=" + mLabel + ", languge=" + mLanguage + "]";
    }

}
