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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail, editTextPassword;

    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.idEditTextEmail);
        editTextPassword = (EditText) findViewById(R.id.idEditTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.idLoginProgressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.idTextViewSignUp).setOnClickListener(this);
        findViewById(R.id.idLoginBtn).setOnClickListener(this);

    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

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

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    progressBar.setVisibility(View.GONE);
                    startActivity(intent);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.idTextViewSignUp:

                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.idLoginBtn:
                userLogin();
                break;
        }

    }
}

/*
 * 18th September 2K18,
 * 22:57,
 * Working SignUp, Login, Saving all attributes in Database.
 *
 */
