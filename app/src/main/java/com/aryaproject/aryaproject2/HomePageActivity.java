package com.aryaproject.aryaproject2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseStorage storage;
    StorageReference storageReference;
    InputStream stream;
    UploadTask uploadTask;
    private String currentEmail = "asa@afa.no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        findViewById(R.id.idButtonSync).setOnClickListener(this);

    }
/*
    private void temp1() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/test1").child("test.txt");
        stream = getResources().openRawResource(R.raw.ab);
        uploadTask = storageReference.putStream(stream);

        Toast.makeText(getApplicationContext(), "Data Synced Successfully!",
                Toast.LENGTH_SHORT).show();
    }*/

    private void sync1() {

        storage = FirebaseStorage.getInstance();


        String folderName = ashwin(currentEmail);
        Log.d("Ashwin", folderName);

        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/users/" + folderName + "/").child(folderName + ".txt");
        stream = getResources().openRawResource(R.raw.ab);
        uploadTask = storageReference.putStream(stream);
//
//        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/users/" + currentEmail + "/").child(folderName + ".csv");
//        stream = getResources().openRawResource(R.raw.ab);
//        uploadTask = storageReference.putStream(stream);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });



    }

    private void msg1(){
        Toast.makeText(getApplicationContext(), "Data Synced Successfully!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idButtonSync:
                sync1();
                break;
        }
    }

    private String obtaintCurrentEmail(String str){
        return str;
    }

    public void a11(String str){
        currentEmail = str;
    }
    private String ashwin(String email) {

        //String str1 = email.substring(0, email.length())
        //String str = "@";
        int a = email.indexOf('@');
        String str = email.substring(0, a);

        return str;
    }
}

/*
 * Status: Working
 * Uploading "ab.txt" file at Firebase Storage under "test1" directory with file name "test.txt"
 * Last Modified: 20th September, 0743,
 * Developed By,
 * ~K.O.H..!! ^__^
 *
 */