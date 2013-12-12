package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.Comparator;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;
import com.intelerad.web.lib.gwt.client.ui.DefaultTableModel;
import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

@SuppressWarnings("serial")
public class PatientTableModel extends DefaultTableModel<GwtPatient, PatientTableModel.Column>
                               implements Serializable
{ 
    public enum Column
    { 
        PATIENT_NAME,
        DOB,
        AGE,
        PATIENT_ID,
        GENDER,
        NB_OF_ORDERS
    }
    
    public PatientTableModel()
    {
        setColumns( Column.values() );
    }
    
    @Override
    public String getColumnLabel( Column column )
    {
        return column.name();
    }

    @Override
    public Object getValue( GwtPatient patient, Column column )
    {
        switch ( column )
        {
            case PATIENT_NAME:
                return GwtStringUtils.capitalizeName( patient.getPrintableName() );
            case DOB:
                return patient.getDateOfBirth();
            case AGE:
                return patient.getAge();
            case PATIENT_ID:
                return patient.getPatientId();   
            case GENDER:
                return patient.getGender();
            case NB_OF_ORDERS:
                return patient.getOrderCount();
            default:
                return null;
        }
    }  
    
    // TODO: this needs to be rethought
    @Override
    public Comparator<GwtPatient> getComparator( final Column column )
    {
        // Default is to compare the string representation (display value)
        return new Comparator<GwtPatient>()
        {
            @Override
            public int compare( GwtPatient o1, GwtPatient o2 )
            {
                Object value1 = getValue( o1, column );
                
                if ( value1 instanceof String )
                { 
                    // String comparison - ignore case, accents, apostrophes
                    String s1 = (String)getValue( o1, column );
                    String s2 = (String)getValue( o2, column );
                    
                    // If the code is running on the server side (Java only),
                    // make sure that we aren't calling a native JavaScript function
                    s1 = GwtStringUtils.normalizeString( s1.replaceAll( "[\'\"]", "" ) );
                    s2 = GwtStringUtils.normalizeString( s2.replaceAll( "[\'\"]", "" ) );
                    return s1.compareToIgnoreCase( s2 );
                }
                else
                {
                    Comparable cmp1 = (Comparable)value1;
                    Comparable cmp2 = (Comparable)getValue( o2, column );
                    
                    if ( value1 == null && cmp2 == null )
                        return 0;
                    else if ( value1 == null )
                        return -1;
                    else if ( cmp2 == null )
                        return 1;
                    else
                        return cmp1.compareTo( cmp2 );
                }
            }
        };
    }
}
