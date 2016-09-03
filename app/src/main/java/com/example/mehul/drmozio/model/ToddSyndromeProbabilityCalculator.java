package com.example.mehul.drmozio.model;

import com.example.mehul.drmozio.bean.Patient;

/**
 * Created by Mehul on 04/09/16.
 */
public class ToddSyndromeProbabilityCalculator {

    private final int NumberOfFactorsCOnsiderWhileDiagnosis = 4;
    private final float increaseinPercentageWhenOneFactorIsAccepted = 100/NumberOfFactorsCOnsiderWhileDiagnosis;

    /**Depending on various factors it can be decided the percentage of todd's syndrome
     *
     * @param patient
     * @return
     */
    public int getPercentageOfToddSyndrome(Patient patient){
            int numberOfConditionsSatisfied = 0;
        if(patient.isHasMigrane()){numberOfConditionsSatisfied ++;}
            if(patient.isConsumesHallucinogenicDrug()){numberOfConditionsSatisfied ++;}
            if(patient.getAge() <=15){ numberOfConditionsSatisfied ++;}
        if (patient.isMale()) {numberOfConditionsSatisfied ++;}

        return (int)(numberOfConditionsSatisfied * increaseinPercentageWhenOneFactorIsAccepted);
    }
}
