package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intelerad.datamodels.reportworkflow.PriorityLevelColor;
import com.intelerad.datamodels.reportworkflow.TimeUnit;

@SuppressWarnings("serial")
public class GwtPriorityLevel implements ModificationSubject, Comparable<GwtPriorityLevel>, Serializable
{
    private Long mLevelId;
    private String mLabel;
    private String mLanguage;
    private Integer mDueInTime;
    private TimeUnit mDisplayTimeUnit;
    private Integer mExigency;
    private PriorityLevelColor mColor;
    private Map<Long, GwtCriticalFinding> mCriticalFindings = new HashMap<Long, GwtCriticalFinding>();

    /**
     * A default constructor required by GWT.
     */
    private GwtPriorityLevel()
    {
    }

    /**
     * Creates a new <code>GwtPriorityLevel</code> instance.
     * <p/>
     * To simplify modification tracking all priority levels are required to have a unique
     * identifier and this constructor will generate a temporary (negative) id for that purpose.
     * 
     * @param label the priority level title
     * @param color the default color for this level
     * @param dueInTime the due in time for this level
     * @see ModificationTracker
     * @see ModificationSubject
     */
    public GwtPriorityLevel( String label, PriorityLevelColor color, Integer dueInTime )
    {
        // generate a temporary ID
        mLevelId = ( -1 ) * System.currentTimeMillis();
        mLabel = label;
        mColor = color;
        mDueInTime = dueInTime;
    }

    /**
     * Creates a new <code>GwtPriorityLevel</code> instance.
     * <p/>
     * The constructor ensures that the provided level id is not <code>null</code> by throwing an
     * {@link IllegalArgumentException}
     * 
     * @param levelId the unique identifier
     * @param label the priority level title
     * @param color the default color for this level
     * @param dueInTime the due in time for this level
     */
    public GwtPriorityLevel( Long levelId, String label, PriorityLevelColor color, Integer dueInTime )
    {
        if ( levelId == null )
            throw new IllegalArgumentException( "The 'levelId' parameter cannot be 'null'" );

        mLevelId = levelId;
        mLabel = label;
        mColor = color;
        mDueInTime = dueInTime;
    }

    @Override
    public Long getKey()
    {
        return getLevelId();
    }

    public Long getLevelId()
    {
        return mLevelId;
    }

    public void setLevelId( Long levelId )
    {
        mLevelId = levelId;
    }

    public String getLabel()
    {
        return mLabel;
    }

    public void setLabel( String label )
    {
        mLabel = label;
    }

    public PriorityLevelColor getColor()
    {
        return mColor;
    }

    public void setColor( PriorityLevelColor color )
    {
        mColor = color;
    }

    public void setColor( String color )
    {
        mColor = PriorityLevelColor.parseColor( color );
    }

    public Integer getDueInTime()
    {
        return mDueInTime;
    }

    /**
     * Converts the due-in time into the specified time unit.
     * 
     * @param timeUnit the time unit
     * @return the due-in time in the requested time unit
     */
    public Integer getDueInTime( TimeUnit timeUnit )
    {
        switch ( timeUnit )
        {
            case MINUTES:
                return mDueInTime;
            case HOURS:
                return mDueInTime / 60;
            case DAYS:
                return mDueInTime / 24 / 60;
        }
        return 0;
    }

    public void setDueInTime( Integer dueInTime )
    {
        this.mDueInTime = dueInTime;
    }

    public TimeUnit getDisplayTimeUnit()
    {
        return mDisplayTimeUnit;
    }

    public void setDisplayTimeUnit( TimeUnit displayTimeUnit )
    {
        mDisplayTimeUnit = displayTimeUnit;
    }

    public String getLanguage()
    {
        return mLanguage;
    }

    public void setLanguage( String language )
    {
        mLanguage = language;
    }

    public Integer getExigency()
    {
        return mExigency;
    }

    public void setExigency( Integer exigency )
    {
        mExigency = exigency;
    }

    public List<GwtCriticalFinding> getCriticalFindings()
    {
        return new ArrayList<GwtCriticalFinding>( mCriticalFindings.values() );
    }

    public GwtCriticalFinding getCriticalFinding( String label )
    {
        return getCriticalFinding( label, true );
    }

    public GwtCriticalFinding getCriticalFinding( String label, boolean caseSensitive )
    {
        return getCriticalFinding( mCriticalFindings, label, caseSensitive );
    }
    
    public boolean isLabelDuplicatedInAnotherCriticalFinding( Long criticalFindId, String label )
    {
        Map<Long, GwtCriticalFinding> otherCriticalFindings = new HashMap<Long, GwtCriticalFinding>();
        otherCriticalFindings.putAll( mCriticalFindings );
        if ( criticalFindId != null )
            otherCriticalFindings.remove( criticalFindId );

        return getCriticalFinding( otherCriticalFindings, label, false ) != null;
    }

    private GwtCriticalFinding getCriticalFinding(  Map<Long, GwtCriticalFinding> criticalFindings , String label, boolean caseSensitive )
    {
        for ( GwtCriticalFinding finding : criticalFindings.values() )
        {
            if ( finding.getLabel().equals( label ) )
                return finding;

            if ( !caseSensitive && finding.getLabel().equalsIgnoreCase( label ) )
            {
                return finding;
            }
        }
        return null;
    }

    public GwtCriticalFinding getCriticalFinding( Long findingId )
    {
        return mCriticalFindings.get( findingId );
    }

    public void addCriticalFinding( GwtCriticalFinding criticalFinding )
    {
        if ( criticalFinding.getFindingId() == null )
            throw new IllegalArgumentException( "The 'findingId' is requied." );

        mCriticalFindings.put( criticalFinding.getFindingId(), criticalFinding );
    }

    public void removeCriticalFinding( GwtCriticalFinding criticalFinding )
    {
        if ( criticalFinding.getFindingId() == null )
            throw new IllegalArgumentException( "The 'findingId' is requied." );

        mCriticalFindings.remove( criticalFinding.getFindingId() );
    }

    public GwtPriorityLevel deepCopy()
    {
        GwtPriorityLevel priorityLevelCopy = new GwtPriorityLevel();
        priorityLevelCopy.setLevelId( mLevelId );
        priorityLevelCopy.setLabel( mLabel );
        priorityLevelCopy.setLanguage( mLanguage );
        priorityLevelCopy.setDueInTime( mDueInTime );
        priorityLevelCopy.setDisplayTimeUnit( mDisplayTimeUnit );
        priorityLevelCopy.setExigency( mExigency );
        priorityLevelCopy.setColor( mColor );

        for ( GwtCriticalFinding finding : mCriticalFindings.values() )
        {
            priorityLevelCopy.addCriticalFinding( finding.deepCopy() );
        }

        return priorityLevelCopy;
    }
    
    public boolean exactEquals( GwtPriorityLevel that )
    {
        if ( mLevelId != null ? !mLevelId.equals( that.mLevelId ) : that.mLevelId != null ) return false;
        if ( mLabel != null ? !mLabel.equals( that.mLabel ) : that.mLabel != null ) return false;
        if ( mLanguage != null ? !mLanguage.equals( that.mLanguage ) : that.mLanguage != null ) return false;
        if ( mDueInTime != null ? !mDueInTime.equals( that.mDueInTime ) : that.mDueInTime != null ) return false;
        if ( mDisplayTimeUnit != null ? !mDisplayTimeUnit.equals( that.mDisplayTimeUnit ) : that.mDisplayTimeUnit != null ) return false;
        if ( mExigency != null ? !mExigency.equals( that.mExigency ) : that.mExigency != null ) return false;
        if ( mColor != null ? !mColor.equals( that.mColor ) : that.mColor != null ) return false;
        if ( mCriticalFindings.size() != that.mCriticalFindings.size() ) return false;
        
        for ( Long findingId : mCriticalFindings.keySet() )
        {
            GwtCriticalFinding thisFinding = this.mCriticalFindings.get( findingId );
            GwtCriticalFinding thatFinding = that.getCriticalFinding( findingId );
            
            if ( !thisFinding.exactEquals( thatFinding ) ) return false;
        }
        return true;
    }

    @Override
    public int compareTo( GwtPriorityLevel other )
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
        GwtPriorityLevel that = (GwtPriorityLevel) o;
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
        return "GwtPriorityLevel{" +
                "levelId=" + mLevelId +
                ", label='" + mLabel + '\'' +
                ", language='" + mLanguage + '\'' +
                ", dueInTime=" + mDueInTime +
                ", displayTimeUnit=" + mDisplayTimeUnit +
                ", exigency=" + mExigency +
                ", color=" + mColor +
                ", criticalFindings=" + mCriticalFindings +
                '}';
    }
}
