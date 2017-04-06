package com.comp3161.placidandroid.models;

/**
 * Created by stone on 4/3/17.
 */

public class Patient {

    String id;
    String first_name;
    String last_name;
    String phone_number;
    String dob;
    String address;


    public Patient(String id,String first_name, String last_name, String phone_number, String dob, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.dob = dob;
        this.address = address;
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
