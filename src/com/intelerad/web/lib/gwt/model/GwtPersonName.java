package com.intelerad.web.lib.gwt.model;

import java.io.Serializable;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;

@SuppressWarnings("serial")
public class GwtPersonName implements Serializable
{ 
    protected String mLastName;
    protected String mFirstName;
    protected String mMiddleName;
    
    public GwtPersonName()
    {
        mLastName = "";
        mFirstName = "";
        mMiddleName = "";
    }
    
    public GwtPersonName( String lastName, String firstName, String middleName )
    {
        mFirstName = firstName;
        mLastName = lastName;
        mMiddleName = middleName;
    }
    
    public GwtPersonName( GwtPersonName name )
    {
        mFirstName = new String( name.getFirstName() );
        mLastName = new String( name.getLastName() );
        mMiddleName = new String( name.getMiddleName() );
    }
    
    public String getLastName()
    {
        return mLastName;
    }
    
    public void setLastName( String lastName )
    {
        mLastName = lastName;
    }
    
    public String getFirstName()
    {
        return mFirstName;
    }
    
    public void setFirstName( String firstName )
    {
        mFirstName = firstName;
    }
    
    public String getMiddleName()
    {
        return mMiddleName;
    }
    
    public void setMiddleName( String middleName )
    {
        mMiddleName = middleName;
    }
    
    public boolean hasName()
    {
        return !GwtStringUtils.isBlank( mLastName ) || 
               !GwtStringUtils.isBlank( mMiddleName ) ||
               !GwtStringUtils.isBlank( mFirstName );
    }
    
    public String getPrintableName()
    {
        return mLastName + ", " + mFirstName + " " + mMiddleName;
    }
    
    public String getInformalName( boolean includeMiddle )
    {
        StringBuffer result = new StringBuffer();
        appendIfNotBlank( result, mFirstName );
     
        if ( includeMiddle )
        {
            appendIfNotBlank( result, mMiddleName );
        }
        
        appendIfNotBlank( result, mLastName );
        return GwtStringUtils.trimToEmpty( result.toString() );
    }
    
    private void appendIfNotBlank( StringBuffer result, String name )
    {
        String trimmedName = GwtStringUtils.trimToEmpty( name );
        if ( !GwtStringUtils.isBlank( trimmedName ) )
        {
            result.append( trimmedName );
            result.append( " " );
        }
    }
    
    public boolean equals( Object obj )
    {
        if ( obj instanceof GwtPersonName )
        {
            GwtPersonName p = (GwtPersonName)obj;
            return safeEquals( this.mFirstName, p.mFirstName ) &&
                   safeEquals( this.mLastName, p.mLastName ) &&
                   safeEquals( this.mMiddleName, p.mMiddleName );
        }
        return false;
    }
    
    private static boolean safeEquals( Object obj1, Object obj2 )
    {
        return obj1 == obj2 || ( obj1 != null && obj1.equals( obj2 ) ); 
    }

    /**
     * @return the user's full name, in the format: "Last, First [Middle]", and
     * properly handles cases where any combination of the three names are empty.
     */ 
    public String getSortableFullName( boolean includeMiddle )
    {
        StringBuffer result = new StringBuffer();
        
        // Add last name
        String lastName = GwtStringUtils.trimToEmpty( mLastName );
        result.append( lastName );
        
        // Add first name
        String firstName = GwtStringUtils.trimToEmpty( mFirstName );
        if ( !GwtStringUtils.isBlank( firstName ) )
        {
            if ( !GwtStringUtils.isBlank( lastName ) )
                result.append( ", " );
            
            result.append( firstName );
        }
        
        // Optionally add middle name
        String middleName = GwtStringUtils.trimToEmpty( mMiddleName );
        if ( includeMiddle && !GwtStringUtils.isBlank( middleName ) )
        {
            if ( !GwtStringUtils.isBlank( firstName ) )
                result.append( " " );
            else if ( !GwtStringUtils.isBlank( lastName ) )
                result.append( ", " );
            // else both firstName and lastName are empty: nothing to prepend
            
            result.append( middleName );
        }
        
        return result.toString();
    }
}
