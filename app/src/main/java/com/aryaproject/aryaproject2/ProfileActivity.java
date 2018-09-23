package com.aryaproject.aryaproject2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    DatabaseReference firebaseRef;
    TextView textViewDisplayPatientName;
    TextView textViewDisplayPatientEmail;
    TextView textViewDisplayPatientMobile;
    TextView textViewDisplayGuardianName;
    TextView textViewDisplayGuardianEmail;
    TextView textViewDisplayGuardianMobile;
    TextView textViewDisplayAge;
    TextView textViewDisplayDoctorName;
    TextView textViewDisplayDoctorMobile;
    TextView textViewDisplayDoctorEmail;
    TextView textViewDisplayGender;
    TextView textViewDisplayBloodGroup;
    TextView textViewDisplayWeight;
    TextView textViewDisplayHeight;
    private User currentUserDetails;

    // Parameters:


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewDisplayPatientEmail = (TextView) findViewById(R.id.idDisplayPatientEmail);
        textViewDisplayPatientName = (TextView) findViewById(R.id.idDisplayPatientName);
        textViewDisplayPatientMobile = (TextView) findViewById(R.id.idDisplayPatientMobile);
        textViewDisplayGuardianEmail = (TextView) findViewById(R.id.idDisplayGuardianEmail);
        textViewDisplayGuardianName = (TextView) findViewById(R.id.idDisplayGuardianName);
        textViewDisplayGuardianMobile = (TextView) findViewById(R.id.idDisplayGuardianMobile);
        textViewDisplayDoctorName = (TextView) findViewById(R.id.idDisplayDoctorName);
        textViewDisplayDoctorMobile = (TextView) findViewById(R.id.idDisplayDoctorMobile);
        textViewDisplayDoctorEmail = (TextView) findViewById(R.id.idDisplayDoctorEmail);
        textViewDisplayGender = (TextView) findViewById(R.id.idDisplayGender);
        textViewDisplayAge = (TextView) findViewById(R.id.idDisplayAge);
//        textViewDisplayWeight = (TextView) findViewById(R.id.idDisplayWeight);
//        textViewDisplayHeight = (TextView) findViewById(R.id.idDisplayHeight);
//        textViewDisplayBloodGroup = (TextView) findViewById(R.id.idDisplayBloodGroup);

        firebaseRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://aryaproject2-7252e.firebaseio.com/Users/" +
                        HomePageActivity.currentUserHashId);

/*
        firebaseRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://aryaproject2-7252e.firebaseio.com/Users/" +
                        "" + "/email");
*/



        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserDetails = dataSnapshot.getValue(User.class);
                textViewDisplayPatientName.setText(currentUserDetails.getName());
                textViewDisplayPatientEmail.setText(currentUserDetails.getEmail());
                textViewDisplayPatientMobile.setText(currentUserDetails.getPatientMobile());

                textViewDisplayGuardianName.setText(currentUserDetails.getGuardianName());
                textViewDisplayGuardianEmail.setText(currentUserDetails.getGuardianEmail());
                textViewDisplayGuardianMobile.setText(currentUserDetails.getGuardianMobile());
                textViewDisplayDoctorName.setText(currentUserDetails.getDoctorName());
                textViewDisplayDoctorMobile.setText(currentUserDetails.getDoctorMobile());
                textViewDisplayDoctorEmail.setText(currentUserDetails.getDoctorEmail());
                textViewDisplayGender.setText(currentUserDetails.getGender());
                textViewDisplayAge.setText(currentUserDetails.getAge());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

/*
 * Status: Working
 * Task: Empty Activity.
 * Next Activity: None.
 * Last Modified: 20th September, 0806,
 * Developed By,
 * ~K.O.H..!! ^__^
 *
 */
