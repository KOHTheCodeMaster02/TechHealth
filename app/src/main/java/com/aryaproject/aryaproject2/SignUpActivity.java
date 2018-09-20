package com.aryaproject.aryaproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword, editTextPatientName, editTextPatientMobile;
    EditText editTextGuardianName, editTextGuardianMobile, editTextDOB;

    ProgressBar progressBar;

    DatabaseReference databaseUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseUser = FirebaseDatabase.getInstance().getReference("Users");
        progressBar = findViewById(R.id.idSignUpProgressBar);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.idEditTextEmail);
        editTextPassword = (EditText) findViewById(R.id.idEditTextPassword);
        editTextPatientName = (EditText) findViewById(R.id.idEditTextPatientName);
        editTextPatientMobile = (EditText) findViewById(R.id.idEditTextPatientMobile);
        editTextGuardianName = (EditText) findViewById(R.id.idEditTextGuardianName);
        editTextGuardianMobile = (EditText) findViewById(R.id.idEditTextGuardianMobile);
        editTextDOB = (EditText) findViewById(R.id.idEditTextDOB);

        findViewById(R.id.idSignUpBtn).setOnClickListener(this);
        findViewById(R.id.idTextViewLogin).setOnClickListener(this);

    }

    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String pName = editTextPatientName.getText().toString().trim();
        final String pMobile = editTextPatientMobile.getText().toString().trim();
        final String gName = editTextGuardianName.getText().toString().trim();
        final String gMobile = editTextGuardianMobile.getText().toString().trim();
        final String dob = editTextDOB.getText().toString().trim();



        if(email.isEmpty()){
            editTextEmail.setError("Email can not be Empty!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password can not be Empty!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password must contain at least 6 characters.");
            editTextPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        /*
        String id = databaseUser.push().getKey();

        User user = new User(id, pName, pMobile, email);
        databaseUser.child(id).setValue(user);
        */

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    //User user = new User(pName, pMobile, email, gName, gMobile, dob);

                    //progressBar.setVisibility(View.GONE);

                    String id = databaseUser.push().getKey();
                    User user = new User(pName, pMobile, email, gName, gMobile, dob);
                    databaseUser.child(id).setValue(user);

                    Toast.makeText(getApplicationContext(), "Registration Successful",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    progressBar.setVisibility(View.GONE);
                    startActivity(intent);

                    /*
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                progressBar.setVisibility(View.GONE);
                                startActivity(intent);
                            }
                        }
                    });
                    */
                } else {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Account with the Email already exists",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getApplicationContext(), "Error during Registration....", Toast.LENGTH_SHORT).show();
                }
                //if()

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idSignUpBtn:
                registerUser();
                break;
            case R.id.idTextViewLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}


/*
 * Status: Working
 * Task: Register New User into Firebase Database.
 * Next Activity: HomePageActivity.
 * Last Modified: 20th September, 0801,
 * Developed By,
 * ~K.O.H..!! ^__^
 *
 */