package com.intelerad.web.lib.gwt.client.ui;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;


public interface SortableTableModel<T,C> extends Serializable
{
    /**
     * @return the list of objects (of type T) representing rows
     */
    public List<T> getRows();
    
    /**
     * @param rows the list of row objects (of type T) to set
     */
    public void setRows( List<T> rows );
    
    /**
     * @return the list of objects (can be any type) representing columns
     */
    public List<C> getColumns();
    
    /**
     * @param column
     * @return the label displayed as column header
     */
    public String getColumnLabel( C column );    
    
    /**
     * @param column
     * @return the tooltip displayed when hovering over the column header
     */
    public String getColumnTooltip( C column );    
    
    /**
     * @param row the object (of type T) representing the row
     * @param column
     * @return the comparable value for the specified column (used for sorting)
     */
    public Object getValue( T row, C column );
    
    /**
     * @param row the object (of type T) representing the row
     * @param column
     * @return the value to display for the specified column
     */
    public String getDisplayValue( T row, C column );
    
    /**
     * @param column
     * @return the Comparator to use when sorting by the specified column
     */
    public Comparator<T> getComparator( C column );
    
    /**
     * Returns an InputFilter that knows how to filter by a given column.
     * Only needs to be implemented if you table has filtering enabled.
     * Returning null for a particular column will make that field
     * non-filterable.
     * 
     * @param column
     * @return an InputFilter to filter by the specified column.
     */
    public InputFilter<T> getInputFilter( C column );
    
    /**
     * Sort the rows by the specified column. They will be sorted in descending order if the
     * "descending" flag is true, otherwise they will be sorted by ascending order.
     * @param column      The column to sort by
     * @param descending  Whether or not to sort by descending order
     */
    public void sort( C column, boolean descending );
    
    /**
     * Sort the rows by multiple columns, using the sortingMap provided as parameter. Rows will
     * sorted multiple times, in the iteration order of the entries in the map. Therefore the
     * primary sort column i
     * @param sortingMap  A linked map of Column to "descending" flag.
     */
    public void sortMultiLevels( LinkedHashMap<C,Boolean> sortingMap );
}
