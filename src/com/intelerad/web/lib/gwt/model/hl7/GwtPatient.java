package com.intelerad.web.lib.gwt.model.hl7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.intelerad.web.lib.gwt.model.GwtAge;
import com.intelerad.web.lib.gwt.model.GwtPersonName;

@SuppressWarnings("serial")
public class GwtPatient implements Serializable
{ 
    private String mPatientId;
    
    private GwtPersonName mName = new GwtPersonName();
    
    private String mGender;
    private String mDateofBirth;
    private GwtAge mAge;

    private List<GwtBasicOrder> mOrders = new ArrayList<GwtBasicOrder>();
    
    public GwtPatient()
    {
    }
    
    public GwtPatient( GwtPatient source )
    {
        mPatientId = source.getPatientId();
        mDateofBirth = source.getDateOfBirth();
        
        mName = source.getName();
      
        mGender = source.getGender();
        
        mOrders = new ArrayList<GwtBasicOrder>();
        if ( source.mOrders != null )
        {
            for ( GwtBasicOrder o : source.mOrders )
            {
                GwtBasicOrder newOrder = new GwtBasicOrder( o );
                newOrder.setPatient( this );
                mOrders.add( newOrder );
            }
        }
    }
    
    public void setGender( String gender )
    {
        mGender = gender;
    }
    
    public String getGender()
    {
        return mGender;
    }
    
    public String getPatientId()
    {
        return mPatientId;
    }
    
    public void setPatientId( String patientId )
    {
        mPatientId = patientId;
    }

    public String getDateOfBirth()
    {
        return mDateofBirth;
    }
    
    public void setDateOfBirth( String dateOfBirth )
    {
        mDateofBirth = dateOfBirth;
    }
    
    public GwtPersonName getName()
    {
        return mName;
    }
    
    public String getFirstName()
    {
        return mName.getFirstName();
    }

    public void setFirstName( String firstName )
    {
        mName.setFirstName( firstName );
    }

    public String getMiddleName()
    {
        return mName.getMiddleName();
    }

    public void setMiddleName( String middleName )
    {
        mName.setMiddleName( middleName );
    }

    public String getLastName()
    {
        return mName.getLastName();
    }

    public void setLastName( String lastName )
    {
        mName.setLastName( lastName );
    }
    
    public String getPrintableName()
    {
        return mName.getPrintableName();
    }
    
    public String getInformalName( boolean includeMiddle)
    {
        return mName.getInformalName( includeMiddle );
    }
    
    /// XXX ATC doesn't use these yet
    
    public void setOrders( List<GwtBasicOrder> orders )
    {
        mOrders = orders;
    }
    
    public List<GwtBasicOrder> getOrders()
    {
        return mOrders;
    }
    
    public boolean hasOrders()
    {
        return mOrders != null && mOrders.size() > 0;
    } 
    
    public int getOrderCount()
    {
        return mOrders != null ? mOrders.size() : 0;
    }

    public GwtAge getAge()
    {
        return mAge;
    }

    public void setAge( GwtAge age )
    {
        mAge = age;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof GwtPatient )
        {
            GwtPatient p = (GwtPatient)obj;
            return safeEquals( this.mPatientId, p.mPatientId ) && demographicsEqual( p );
        }
        else
        {
            return false;
        }
    }
    
    public boolean demographicsEqual( GwtPatient p )
    {
        return this.mName.equals( p.mName ) &&
               safeEquals( this.mGender, p.mGender ) &&
               safeEquals( this.mDateofBirth, p.mDateofBirth );
    }
    
    private static boolean safeEquals( Object obj1, Object obj2 )
    {
        return obj1 == obj2 || ( obj1 != null && obj1.equals( obj2 ) ); 
    }
}
