package com.aryaproject.aryaproject2;

public class User {

    String id, name, dob, patientMobile, guardianName, guardianMobile, email;

    public User() {

    }

    public User(String id, String name, String patientMobile, String email) {
        this.id = id;
        this.name = name;
        this.patientMobile = patientMobile;
        this.email = email;
    }
    public User(String name, String patientMobile, String email) {
        this.name = name;
        this.patientMobile = patientMobile;
        this.email = email;
    }
    public User(String name, String patientMobile, String email, String guardianName, String guardianMobile, String dob) {
        this.name = name;
        this.patientMobile = patientMobile;
        this.email = email;
        this.guardianName = guardianName;
        this.guardianMobile = guardianMobile;
        this.dob = dob;

    }

    public User(String name, String dob, String patientMobile, String guardianName, String guardianMobile) {
        this.name = name;
        this.dob = dob;
        this.patientMobile = patientMobile;
        this.guardianName = guardianName;
        this.guardianMobile = guardianMobile;
        //this.email = email;
    }
}
