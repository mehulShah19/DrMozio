package com.example.mehul.drmozio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mehul.drmozio.bean.Patient;

import java.util.ArrayList;

/**
 * Created by Mehul on 03/09/16.
 */

/*
   PatientDatabase is used to store all their diagnoses which can be refer to doctor
 */
public class PatientDatabase extends SQLiteOpenHelper {

    //Constants which define table Column Name and Database Names


    public static final String DATABASE_NAME = "patient.db";
    public static final String TABLE_NAME = "PatientTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATIENT_NAME = "PATIENT_NAME";
    public static final String COLUMN_HAS_MIGRANE = "PATIENT_HAS_MIGRANE";
    public static final String COLUMN_PATIENT_AGE = "PATIENT_AGE";
    public static final String COLUMN_PATIENT_GENDER = "PATIENT_GENDER";
    public static final String COLUMN_CONSUME_HALLUCINOGENIC_DRUG = "PATIENT_CONSUME_HALLUCINOGENIC_DRUG";
    public static final String ColUMN_DATE = "DATE";

    static PatientDatabase patientDatabase;
    Context context;

    /*
        Singleton Pattern is used to create one instance for this Database
     */
    public static PatientDatabase getInstance(Context context){
        if(patientDatabase == null){
            patientDatabase = new PatientDatabase(context);
        }
        return patientDatabase;
    }

    //Private Constructor is made so the instance cannot be created
    private PatientDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // THis is called for the first time when the database is not created
        db.execSQL(
                "create table " + TABLE_NAME +
                        "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PATIENT_NAME + " TEXT, " +
                        COLUMN_HAS_MIGRANE + " INTEGER, " +
                        COLUMN_PATIENT_AGE + " INTEGER, " +
                        COLUMN_PATIENT_GENDER + " INTEGER, " +
                        ColUMN_DATE + " INTEGER, " +
                        COLUMN_CONSUME_HALLUCINOGENIC_DRUG + " INTEGER" + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //If new Database version is present we will delete the whole previous database
        db.execSQL("DROP TABLE IF EXISTS  " + DATABASE_NAME);
        onCreate(db);
    }

    /**
     * THis method is used to add Patient data in the database
     *
     * @param patient
     * @return long rowId in the database
     */
    public long addPatientDetails(Patient patient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_NAME, patient.getName());
        contentValues.put(COLUMN_HAS_MIGRANE, patient.isHasMigrane());
        contentValues.put(COLUMN_PATIENT_AGE, patient.getAge());
        contentValues.put(COLUMN_PATIENT_GENDER, patient.isMale());
        contentValues.put(COLUMN_CONSUME_HALLUCINOGENIC_DRUG, patient.isConsumesHallucinogenicDrug());

        long date = patient.getDate();
        if(date == 0){
            date = System.currentTimeMillis();
        }
        contentValues.put(ColUMN_DATE, date);
        return(db.insert(TABLE_NAME, null, contentValues));
       }

    /**
     * This method will provide all patient data ordered by the most recent one
     * @return patientList
     */
    public ArrayList<Patient> getAllPatientDetails(){
           return query(null, null,null, null, ColUMN_DATE + " Desc");
    }

    /**
     * Standard readable query is implemented
     * @param selection - where condition to execute the query null means whole database
     * @param selectionArgs - condition values to avoid leakage
     * @param groupBy - condition by which the group will be formed
     * @param having - used to perform aggregate function
     * @param orderBy - arranging the data depending on orderBy value
     * @return
     */

    private  ArrayList<Patient> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.query(TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy);
        ArrayList<Patient> patientArrayList = cursorToList(res);
        return patientArrayList;
    }

    /***
     * Method is use to convert the cursor in the arrayList
     * So, the cursor is converted with respect to the patient
     * @param res
     * @return
     */
    private ArrayList<Patient> cursorToList(Cursor res){
        ArrayList patientArrayList = new ArrayList<Patient>();
        if(res!=null && res.getCount()>0){
            res.moveToFirst();
            do{
                Patient patient = new Patient();
                patient.setId(res.getLong(res.getColumnIndex(COLUMN_ID)));
                patient.setName(res.getString(res.getColumnIndex(COLUMN_PATIENT_NAME)));

                int patientHasMigrane = res.getInt(res.getColumnIndex(COLUMN_HAS_MIGRANE));
                if(patientHasMigrane == 1){
                    patient.setHasMigrane(true);
                }
                patient.setAge(res.getInt(res.getColumnIndex(COLUMN_PATIENT_AGE)));
                int isMale = res.getInt(res.getColumnIndex(COLUMN_PATIENT_GENDER));
                if(isMale == 1){
                    patient.setMale(true);
                }
                int patientConsumesHalluccinogenicDrug = res.getInt(res.getColumnIndex(COLUMN_CONSUME_HALLUCINOGENIC_DRUG));
                if(patientConsumesHalluccinogenicDrug == 1){
                    patient.setConsumesHallucinogenicDrug(true);
                }

                patient.setDate(res.getLong(res.getColumnIndex(ColUMN_DATE)));

                patientArrayList.add(patient);
            }while(res.moveToNext());
        res.close();
        }
        return patientArrayList;
    }
}