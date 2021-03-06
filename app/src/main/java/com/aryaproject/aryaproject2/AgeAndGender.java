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

public class AgeAndGender extends AppCompatActivity {

    DatabaseReference firebaseRef;
    TextView textViewDisplayDOB;
    TextView textViewDisplayGender;
    private User currentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewDisplayGender = (TextView) findViewById(R.id.idDisplayGender);
        textViewDisplayDOB = (TextView) findViewById(R.id.idDisplayAge);

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
                HomePageActivity.DOB = currentUserDetails.getDob();
                HomePageActivity.Gender = currentUserDetails.getGender();

//                textViewDisplayPatientName.setText(currentUserDetails.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
