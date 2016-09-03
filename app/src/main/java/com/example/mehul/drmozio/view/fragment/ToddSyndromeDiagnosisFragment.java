package com.example.mehul.drmozio.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.example.mehul.drmozio.R;
import com.example.mehul.drmozio.bean.Patient;
import com.example.mehul.drmozio.constant.DrMozioConstant;
import com.example.mehul.drmozio.database.PatientDatabase;
import com.example.mehul.drmozio.model.ToddSyndromeProbabilityCalculator;
import com.example.mehul.drmozio.utils.Utility;
import com.example.mehul.drmozio.view.activity.DashoardActivity;

/**
 * Created by Mehul on 25/06/16.
 */
public class ToddSyndromeDiagnosisFragment extends Fragment implements View.OnClickListener {

    // Content View Elements
    private EditText nameET;
    private RadioButton migraneYesRB;
    private RadioButton migraneNoRB;
    private RadioButton halluYesRB;
    private RadioButton halluNoRB;
    private RadioButton maleRB;
    private RadioButton femaleRB;
    private TextView ageTV;
    private com.appyvet.rangebar.RangeBar ageRB;
    private Button submitB;
    private Button resetB;
    private Context context;

    //Variable to store age value
    int ageRangeBarValue = 0 ;

    //Default Constructor
    public ToddSyndromeDiagnosisFragment(){
    }

    //View is created and initialized
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_todd_syndrome_diagnosis, container, false);
        context = getContext();
        bindViews(rootView);
        defaultValues();
        enablingListeners();
        return  rootView;
    }

    //Binding View to the references of the class
    private void bindViews(View view) {
        nameET = (EditText) view.findViewById(R.id.nameET);
        migraneYesRB = (RadioButton) view.findViewById(R.id.migraneYesRB);
        migraneNoRB = (RadioButton) view.findViewById(R.id.migraneNoRB);
        halluYesRB = (RadioButton) view.findViewById(R.id.halluYesRB);
        halluNoRB = (RadioButton) view.findViewById(R.id.halluNoRB);
        maleRB = (RadioButton) view.findViewById(R.id.maleRB);
        femaleRB = (RadioButton) view.findViewById(R.id.femaleRB);
        ageTV = (TextView) view.findViewById(R.id.ageTV);
        ageRB = (com.appyvet.rangebar.RangeBar) view.findViewById(R.id.ageRB);
        submitB = (Button) view.findViewById(R.id.submitB);
        resetB = (Button) view.findViewById(R.id.resetB);
    }


    //Default or Initial Values of the View Elements
    private void defaultValues(){
        nameET.setText("");
        migraneYesRB.setChecked(false);
        migraneNoRB.setChecked(true);
        halluYesRB.setChecked(false);
        halluNoRB.setChecked(true);
        maleRB.setChecked(true);
        femaleRB.setChecked(false);
        ageRB.setSeekPinByValue(DrMozioConstant.DEFAULT_AGE);
        setRangeValuesToDisplayText(DrMozioConstant.DEFAULT_AGE);
    }

    //Method to enlist all the listeners of the Views
    private void enablingListeners(){
        submitB.setOnClickListener(this);
        resetB.setOnClickListener(this);
        ageRB.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i1, String s, String s1) {
                int rangeValue = Integer.valueOf(s1);
                setRangeValuesToDisplayText(rangeValue);
            }
        });
    }

    // This method is use to change the Text of the Age depending on RangeBar View
    private void setRangeValuesToDisplayText(int range){
        ageTV.setText(range + " years");
        ageRangeBarValue = range;
    }

    //Calls when the screen is bout to be displayed to the user
    @Override
    public void onResume() {
        super.onResume();
        if(getContext() instanceof DashoardActivity){
            ((DashoardActivity) getContext()).setHeader(getString(R.string.toddsSyndromeDiagnosis));
        }
    }

    //Called when user clicks on the registered element
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitB:
                if(checkProperFormIsFilled()) {
                    storePatientInformationToDatabase();
                    defaultValues();
                }
                break;
            case R.id.resetB:
                defaultValues();
                break;
        }
    }

    //Check whether all the necessary and compulsory fields are properly entered
    private boolean checkProperFormIsFilled() {
        if(TextUtils.isEmpty(nameET.getText().toString())){
            Utility.showToast(context,getString(R.string.please_enter_name));
            return false;
        }
        return true;
    }

    //This method stores patiensts information to the database
    private void storePatientInformationToDatabase() {
        Patient patient = new Patient();
        if(!TextUtils.isEmpty(nameET.getText().toString())){
            patient.setName(nameET.getText().toString());
            if(migraneYesRB.isChecked()){
                patient.setHasMigrane(true);
            }
            if(halluYesRB.isChecked()){
                patient.setConsumesHallucinogenicDrug(true);
            }
            if(maleRB.isChecked()){
                patient.setMale(true);
            }
            patient.setAge(ageRangeBarValue);
            PatientDatabase patientDatabase = PatientDatabase.getInstance(context);
            patientDatabase.addPatientDetails(patient);
            ToddSyndromeProbabilityCalculator toddSyndromeProbabilityCalculator = new ToddSyndromeProbabilityCalculator();
            Utility.showToast(context,"Probability of Todd's Syndrome: " + toddSyndromeProbabilityCalculator.getPercentageOfToddSyndrome(patient) + "%");
            Utility.showLongToast(context, "Patient's data has been stored locally and can be viewed in Patient's Record");
        }
    }
}
