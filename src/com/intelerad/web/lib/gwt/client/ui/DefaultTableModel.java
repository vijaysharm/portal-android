package com.intelerad.web.lib.gwt.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.intelerad.web.lib.gwt.client.GwtStringUtils;

@SuppressWarnings("serial")
public abstract class DefaultTableModel<T,C> implements SortableTableModel<T,C>
{
    protected List<C> mColumns;
    protected List<T> mRows;
    
    public DefaultTableModel()
    {
        super();
        mColumns = new ArrayList<C>();
        mRows = new ArrayList<T>();
    }
    
    @Override
    public List<T> getRows()
    {
        return mRows;
    }
    
    @Override
    public void setRows( List<T> rows )
    {
        mRows = rows;
    }
    
    @Override
    public List<C> getColumns()
    {
        return mColumns;
    }
    
    public void setColumns( C... columns )
    {
        mColumns = Arrays.asList( columns );
    }
    
    public void setColumns( List<C> columns )
    {
        mColumns = columns;
    }
    
    @Override
    public String getColumnLabel( C column )
    {
        return column.toString();
    }
    
    @Override
    public String getColumnTooltip( C column )
    {
        return null;
    }
    
    @Override
    public String getDisplayValue( T rowObject, C column )
    {
        Object value = getValue( rowObject, column );
        
        if ( value == null )
            return "";
        
        return String.valueOf( value );
    }
    
    @Override
    public Comparator<T> getComparator( final C column )
    {
        // Default is to compare the string representation (display value)
        return new Comparator<T>()
        {
            @Override
            public int compare( T o1, T o2 )
            {
                Object value1 = getValue( o1, column );
                
                if ( value1 instanceof Date )
                {
                    Date date1 = (Date) value1;
                    Date date2 = (Date) getValue( o2, column );
                    
                    if ( date1 == null && date2 == null )
                        return 0;
                    else if ( date1 == null )
                        return -1;
                    else if ( date2 == null )
                        return 1;
                    else
                        return date1.compareTo( date2 );
                }
                else if ( value1 instanceof Integer )
                {
                    Integer int1 = ( Integer ) value1;
                    Integer int2 = ( Integer ) getValue( o2, column );
                    return int1.compareTo( int2 );
                }
                else
                {
                    // String comparison - ignore case, accents, apostrophes
                    String s1 = getDisplayValue( o1, column );
                    String s2 = getDisplayValue( o2, column );
                    
                    s1 = GwtStringUtils.normalizeString( s1.replaceAll( "[\'\"]", "" ) );
                    s2 = GwtStringUtils.normalizeString( s2.replaceAll( "[\'\"]", "" ) );
                    return s1.compareToIgnoreCase( s2 );
                }
            }
        };
    }
    
    @Override
    public InputFilter<T> getInputFilter( C column )
    {
        throw new UnsupportedOperationException( "Filtering is not implemented for this table" );
    }
    
    @Override
    public void sort( C column, boolean descending )
    {
        Comparator<T> comparator = getComparator( column );
        
        // Inverse the comparator order if the sorting is descending
        if ( descending )
        {
            comparator = Collections.reverseOrder( comparator );
        }
        Collections.sort( getRows(), comparator );
    }
    
    @Override
    public void sortMultiLevels( LinkedHashMap<C,Boolean> sortingMap )
    {
        for ( C column : sortingMap.keySet() )
        {
            sort( column, sortingMap.get( column ) );
        }
    }
}
