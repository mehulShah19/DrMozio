package com.example.mehul.drmozio.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehul.drmozio.R;
import com.example.mehul.drmozio.adapter.PatientListAdapter;
import com.example.mehul.drmozio.bean.Patient;
import com.example.mehul.drmozio.database.PatientDatabase;
import com.example.mehul.drmozio.view.activity.DashoardActivity;

import java.util.ArrayList;


/**
 * Created by Mehul on 25/06/16.
 */
public class PatientRecordFragment extends Fragment {

    //Content Views
    RecyclerView patientListRecyclerView;
    PatientListAdapter patientListAdapter;
    TextView noDataAvailable;
    private ArrayList<Patient> patientArrayList;
    private Context context;

    //Default Constructor
    public PatientRecordFragment(){
    }

    // This method is called after on Attached. All the one time initializstion apart from view is done here
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        PatientDatabase patientDatabase = PatientDatabase.getInstance(context);
        patientArrayList = patientDatabase.getAllPatientDetails();
    }

    // Binds Window with this View
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_patient_record_fragment, container, false);
        patientListRecyclerView = (RecyclerView) rootView.findViewById(R.id.patientListRecyclerView);
        noDataAvailable = (TextView) rootView.findViewById(R.id.noDataAvailable);
        if(patientArrayList == null || patientArrayList.size() == 0){
            showNoDataAvailableText();
        }else{
            initPatientAdapter();
        }
        return rootView;
    }

    //Initializes the patientsAdapter
    private void initPatientAdapter() {
        patientListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        patientListAdapter = new PatientListAdapter(getActivity(), patientArrayList);
        patientListRecyclerView.setAdapter(patientListAdapter);
    }

    //This method is called when there is no data present in database
    private void showNoDataAvailableText(){
        if(patientArrayList == null || patientArrayList.size() == 0){
            noDataAvailable.setVisibility(View.VISIBLE);
            patientListRecyclerView.setVisibility(View.GONE);
        }else{
            noDataAvailable.setVisibility(View.GONE);
            patientListRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    // This is called when the user is about to see the screen
    @Override
    public void onResume() {
        super.onResume();
        if(getContext() instanceof DashoardActivity){
            ((DashoardActivity) getContext()).setHeader(getString(R.string.patientsRecord));
        }
    }
}
