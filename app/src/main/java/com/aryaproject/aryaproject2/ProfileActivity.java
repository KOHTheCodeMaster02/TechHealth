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
    TextView textViewDisplayEmail;
    private User currentUserDetails;

    // Parameters:


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewDisplayEmail = (TextView) findViewById(R.id.idTextViewDisplayEmail);

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
                textViewDisplayEmail.setText(currentUserDetails.getEmail());
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
