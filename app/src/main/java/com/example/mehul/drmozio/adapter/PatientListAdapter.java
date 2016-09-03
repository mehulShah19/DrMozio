package com.example.mehul.drmozio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehul.drmozio.R;
import com.example.mehul.drmozio.bean.Patient;
import com.example.mehul.drmozio.model.ToddSyndromeProbabilityCalculator;

import java.util.ArrayList;


/**
 * Created by mehulshah on 24/07/15.
 */

/**
 * This class is used to display Patients List on PatientsListFragment
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {


    Context context;
    ToddSyndromeProbabilityCalculator toddSyndromeProbabilityCalculator;
    ArrayList<Patient> patientArrayList;

    /**
     * Constructor
     * @param context - COntext
     * @param patientArrayList - PatientArrayList which will be displayed on the screen
     */

    public PatientListAdapter(Context context, ArrayList<Patient> patientArrayList) {
        this.context = context;
        this.patientArrayList = patientArrayList;
        toddSyndromeProbabilityCalculator = new ToddSyndromeProbabilityCalculator();
    }


    /**
     * Called few times to draw view till the time it covers the screen and then bindingViewHolder is called no new view is created even
     * if scrolling is performed
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_patient_single_record, parent, false);
        PatientViewHolder holder = new PatientViewHolder(view);
        return holder;
    }

    /**
     * Method which reuses the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PatientViewHolder holder, final int position) {
        Patient patient = patientArrayList.get(position);
        String gender = context.getString(R.string.Female);
        if (patient.isMale()) {
            gender = context.getString(R.string.Male);
        }
        String personalInformation = patient.getName() + ", " + gender + ", " + patient.getAge() + " years";
        holder.perosnalInformationTV.setText(personalInformation);
        String otherInformation = context.getString(R.string.migrane);
        if (patient.isHasMigrane()) {
            otherInformation += " " + context.getString(R.string.yes);
        } else {
            otherInformation += " " + context.getString(R.string.no);
        }
        otherInformation += ", Halluciganic Drug";
        if (patient.isConsumesHallucinogenicDrug()) {
            otherInformation += " " + context.getString(R.string.yes);
        } else {
            otherInformation += " " + context.getString(R.string.no);
        }
        holder.otherInformationTV.setText(otherInformation);

        holder.probabilityTV.setText("Probability of Todd's Syndrome: " + toddSyndromeProbabilityCalculator.getPercentageOfToddSyndrome(patient) + "%");
    }

    /**
     * get Number of items which needs to be processed
     * @return
     */
    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    /**
     * Single View Holder is made to make Views to it's corresponding elements
     */
    public class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView perosnalInformationTV;
        TextView otherInformationTV;
        TextView probabilityTV;
        View itemView;

        public PatientViewHolder(View itemView) {
            super(itemView);
            perosnalInformationTV = (TextView) itemView.findViewById(R.id.personalInformation);
            otherInformationTV = (TextView) itemView.findViewById(R.id.otherInformation);
            probabilityTV = (TextView) itemView.findViewById(R.id.probability);
            this.itemView = itemView;
        }
    }
}

