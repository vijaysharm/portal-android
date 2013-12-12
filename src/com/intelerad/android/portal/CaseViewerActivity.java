package com.intelerad.android.portal;

import android.widget.Toast;

import com.intelerad.imageviewer.gwt.model.GwtDataset;

public class CaseViewerActivity extends SimpleSinglePaneActivity<CaseViewerFragment> implements CaseViewerFragment.Callback 
{
    public CaseViewerActivity()
    {
        super( R.menu.notification );
    }
    
    @Override
    protected CaseViewerFragment onCreateFragment()
    {
        return new CaseViewerFragment();
    }

    @Override
    public void selectDataset( GwtDataset dataset )
    {
        Toast.makeText( this,
                        "Clicked on dataset: " + dataset.getAccessionNumber(),
                        Toast.LENGTH_LONG ).show();
    }
}
