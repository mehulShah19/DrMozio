package com.example.mehul.drmozio.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.mehul.drmozio.R;
import com.example.mehul.drmozio.utils.Utility;
import com.example.mehul.drmozio.view.fragment.PatientRecordFragment;
import com.example.mehul.drmozio.view.fragment.ToddSyndromeDiagnosisFragment;

public class DashoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * Fiest method is called to bind View to the Window and also to initiate values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashoard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        showToddSyndromeDiagnosisFragment();

    }

    /**
     * On back Pressed check whether drawer is open or not
     * If open then close the drawer
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * This method is called when user clicks on any item of NavigationDrawer
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.patientDiagnosis:
                    showToddSyndromeDiagnosisFragment();
                    break;
            case R.id.doctorConsultancy:
                    showPatientsRecordFragment();
                    break;
        }
        Utility.dismisskeyboard(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads the container with PatientRecordFragment
     */

    public void showPatientsRecordFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PatientRecordFragment patientRecordFragment = new PatientRecordFragment();
        fragmentTransaction.replace(R.id.fragment_container, patientRecordFragment);
        fragmentTransaction.commit();

    }

    /**
     * Loads the container with ToddSyndromeDiagnosisFragment
     */
    public void showToddSyndromeDiagnosisFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ToddSyndromeDiagnosisFragment toddSyndromeDiagnosisFragment = new ToddSyndromeDiagnosisFragment();
        fragmentTransaction.replace(R.id.fragment_container, toddSyndromeDiagnosisFragment);
        fragmentTransaction.commit();
    }


    /**
     * Use to set title of the screen
     * @param header
     */
    public void setHeader(String header){
        if(TextUtils.isEmpty(header)){
            return;
        }
        getSupportActionBar().setTitle(header);
    }

}
