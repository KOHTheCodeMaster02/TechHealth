package com.aryaproject.aryaproject2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/test1").child("test.txt");
        stream = getResources().openRawResource(R.raw.ab);
        uploadTask = storageReference.putStream(stream);
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
}

/*
 * Status: Working
 * Uploading "ab.txt" file at Firebase Storage under "test1" directory with file name "test.txt"
 * Last Modified: 20th September, 0743,
 * Developed By,
 * ~K.O.H..!! ^__^
 *
 */