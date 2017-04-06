package com.comp3161.placidandroid.models;

/**
 * Created by stone on 4/5/17.
 */

public class MedicalData {

    String procedure;
    String testResults;
    String diagnosis;
    String treatment;


    public MedicalData(String procedure, String testResults, String diagnosis, String treatment) {
        this.procedure = procedure;
        this.testResults = testResults;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
