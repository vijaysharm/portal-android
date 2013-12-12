package com.intelerad.web.portal.gwt.model;

import java.io.Serializable;
import java.util.List;

import com.intelerad.web.lib.gwt.model.hl7.GwtPatient;

@SuppressWarnings("serial")
public class CachedResults implements Serializable
{
    private String mSearchText;
    private PatientTableModel mPatientTableModel;
    
    public CachedResults( String searchText, List<GwtPatient> patients )
    {
        mSearchText = searchText;
        mPatientTableModel = new PatientTableModel();
        mPatientTableModel.setColumns( PatientTableModel.Column.values() );
        mPatientTableModel.setRows( patients );
    }
    
    public PatientTableModel getPatientTableModel()
    {
        return mPatientTableModel;
    }
    
    public String getSearchText()
    {
        return mSearchText;
    }
    
    public void setSearchText( String searchText )
    {
        mSearchText = searchText;
    }
}
