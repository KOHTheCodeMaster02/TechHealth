package com.aryaproject.aryaproject2;

public class User {


    String name, dob, gender, bloodGroup, height, weight, patientMobile, email, guardianName, guardianMobile, guardianEmail;
    String doctorName, doctorMobile, doctorEmail;

    public User(String name, String dob, String gender, String bloodGroup, String height, String weight,
                String patientMobile, String email, String guardianName, String guardianMobile,
                String guardianEmail, String doctorName, String doctorMobile, String doctorEmail) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.height = height;
        this.weight = weight;
        this.patientMobile = patientMobile;
        this.email = email;
        this.guardianName = guardianName;
        this.guardianMobile = guardianMobile;
        this.guardianEmail = guardianEmail;
        this.doctorName = doctorName;
        this.doctorMobile = doctorMobile;
        this.doctorEmail = doctorEmail;
    }

    public User() {

    }

    public User(String name, String patientMobile, String email, String guardianName, String guardianMobile, String dob, String gender) {
        this.name = name;
        this.patientMobile = patientMobile;
        this.email = email;
        this.guardianName = guardianName;
        this.guardianMobile = guardianMobile;
        this.dob = dob;
        this.gender = gender;

    }


/*    public String getId() {
        return id;
    }*/

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getGuardianMobile() {
        return guardianMobile;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }
}

/*
 * Status: Working
 * Task: Collect User Details in a User Class object.
 * Next Activity: None.
 * Last Modified: 20th September, 0805,
 * Developed By,
 * ~K.O.H..!! ^__^
 *
 */
