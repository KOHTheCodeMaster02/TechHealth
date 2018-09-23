package com.aryaproject.aryaproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChoChoActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editTextEmail, editTextPassword, editTextPatientName, editTextPatientMobile;
    EditText editTextGuardianName, editTextGuardianMobile, editTextGuardianEmail ,editTextAge;
    EditText editTextDoctorName, editTextDoctorMobile, editTextDoctorEmail, editTextGender;

//    ProgressBar progressBar;

    DatabaseReference databaseUser;
    HomePageActivity homePageActivity = new HomePageActivity();

    private FirebaseAuth mAuth;
    private String tempEmail = "abc@a.com";
    static String gender = "F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_cho);

        databaseUser = FirebaseDatabase.getInstance().getReference("Users");
//        progressBar = findViewById(R.id.idSignUpProgressBar);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.idPEmail);
        editTextPassword = (EditText) findViewById(R.id.idPassword);
        editTextPatientName = (EditText) findViewById(R.id.idPName);
        editTextPatientMobile = (EditText) findViewById(R.id.idPMobile);
        editTextGuardianName = (EditText) findViewById(R.id.idGName);
        editTextGuardianMobile = (EditText) findViewById(R.id.idGMobile);
        editTextGuardianEmail = (EditText) findViewById(R.id.idGEmail);
        editTextAge = (EditText) findViewById(R.id.idAge);
        editTextGender = (EditText) findViewById(R.id.idGender);
        editTextDoctorEmail = (EditText) findViewById(R.id.idDoctorEmail);
        editTextDoctorMobile = (EditText) findViewById(R.id.idDoctorMobile);
        editTextDoctorName = (EditText) findViewById(R.id.idDoctorName);

        findViewById(R.id.idSubmitBtn).setOnClickListener(this);
        findViewById(R.id.idAlreadyHaveAccount).setOnClickListener(this);

    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String pName = editTextPatientName.getText().toString().trim();
        final String pMobile = editTextPatientMobile.getText().toString().trim();
        final String gName = editTextGuardianName.getText().toString().trim();
        final String gMobile = editTextGuardianMobile.getText().toString().trim();
        final String gEmail = editTextGuardianEmail.getText().toString().trim();
        final String dName = editTextDoctorName.getText().toString().trim();
        final String dEmail = editTextDoctorEmail.getText().toString().trim();
        final String dMobile = editTextDoctorMobile.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();

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



        if(!Patterns.EMAIL_ADDRESS.matcher(gEmail).matches()){
            editTextGuardianEmail.setError("Please Enter a valid Email!");
            editTextGuardianEmail.requestFocus();
            return;
        }
        if(gEmail.isEmpty()){
            editTextGuardianEmail.setError("Email can not be Empty!");
            editTextGuardianEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(dEmail).matches()){
            editTextDoctorEmail.setError("Please Enter a valid Email!");
            editTextDoctorEmail.requestFocus();
            return;
        }
        if(dEmail.isEmpty()){
            editTextDoctorEmail.setError("Email can not be Empty!");
            editTextDoctorEmail.requestFocus();
            return;
        }


        if(pMobile.length() < 10){
            editTextPatientMobile.setError("Number can't be less than 10 Digits!");
            editTextPatientMobile.requestFocus();
            return;
        }
        if(dMobile.length() < 10){
            editTextDoctorMobile.setError("Number can't be less than 10 Digits!");
            editTextDoctorMobile.requestFocus();
            return;
        }
        if(gMobile.length() < 10){
            editTextGuardianMobile.setError("Number can't be less than 10 Digits!");
            editTextGuardianMobile.requestFocus();
            return;
        }



//        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//                    progressBar.setVisibility(View.GONE);

//                    String id = databaseUser.push().getKey();
//                    HomePageActivity.currentUserHashId = id;

//                    Log.d("status", "Current User ID: | " + id + " |");

                    User user = new User(pName, age, gender, pMobile, email, gName, gMobile, gEmail, dName,
                            dMobile, dEmail);
                    databaseUser.child(ashwin(email)).setValue(user);

                    HomePageActivity.currentUserHashId = ashwin(email);

                    Toast.makeText(getApplicationContext(), "Registration Successful",
                            Toast.LENGTH_SHORT).show();

                    HomePageActivity.eEmail = email;
                    HomePageActivity.Gender = gender;
                    HomePageActivity.Age = age;

                    Intent intent = new Intent(ChoChoActivity.this, HomePageActivity.class);
                    //intent.putExtra("currentUserHashId", currentUserHashID);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

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
            }
        });
    }

    private String ashwin(String email) {

        int a = email.indexOf('@');
        String str = email.substring(0, a);

        return str;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idSubmitBtn:
                registerUser();
                break;
            case R.id.idAlreadyHaveAccount:
                startActivity(new Intent(ChoChoActivity.this, MainActivity.class));
                break;
        }
    }

    public void a11(String str){
        tempEmail = str;
    }
}
