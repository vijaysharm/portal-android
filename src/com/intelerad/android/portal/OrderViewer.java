package com.intelerad.android.portal;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intelerad.web.lib.gwt.client.GwtStringUtils;
import com.intelerad.web.lib.gwt.model.hl7.GwtOrder;
import com.intelerad.web.portal.gwt.model.GwtPortalCase;

public class OrderViewer
{
    private final ViewGroup mPatientOrder;
    private final Context mContext;

    public OrderViewer( Context context, ViewGroup patientOrder )
    {
        mContext = context;
        mPatientOrder = patientOrder;
    }

    public void update( GwtOrder order )
    {
        TextView patientName = (TextView) mPatientOrder.findViewById( R.id.patient_name );
        patientName.setText( order.getPrintableName() );
        
        TextView demographics = (TextView) mPatientOrder.findViewById( R.id.patient_demographics );
        demographics.setText( order.getPatient().getGender() );
        
        TextView patientId = (TextView) mPatientOrder.findViewById( R.id.patient_id );
        setText( patientId, R.string.patient_id_label, order.getPatientId() );
        
        TextView orderDescription = (TextView) mPatientOrder.findViewById( R.id.case_viewer_order_description_textview );
        orderDescription.setText( Html.fromHtml( GwtStringUtils.listJoin( order.getProcedureDescriptions(), "<br/>" ) ) );
        
        TextView accessionNumber = (TextView) mPatientOrder.findViewById( R.id.case_viewer_accession_number_textview );
        setText( accessionNumber, R.string.accession_number_label , order.getAccessionNumber() );
        
        TextView studyDate = (TextView) mPatientOrder.findViewById( R.id.case_viewer_study_date_textview );
        setText( studyDate, R.string.study_date_label, order.getStudyDate().toString() );
        
        TextView physician = (TextView) mPatientOrder.findViewById( R.id.case_viewer_assigned_physician_textview );
        setText( physician, R.string.referring_physcian_label, order.getReferringPhysicians().get( 0 ).getPrintableName() );
        
        TextView radiologist = (TextView) mPatientOrder.findViewById( R.id.case_viewer_assigned_radiologist_textview );
        setText( radiologist, R.string.radiologist_label, order.getRadiologist().getPrintableName() );        
    }

    private void setText( TextView textView, int resourceId, String value )
    {
        String text = String.format( mContext.getString( resourceId ), value );
        CharSequence html = Html.fromHtml( text );
        textView.setText( html );
    }
}
