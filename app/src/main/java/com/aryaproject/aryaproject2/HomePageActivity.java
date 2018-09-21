package com.aryaproject.aryaproject2;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseStorage storage;
    StorageReference storageReference;
    InputStream stream;
    UploadTask uploadTask;
    private String currentEmail = "asi@afa.no";
    private String folderName = "abc";
    private String currentFileName = "null";

    static String eEmail = "no email";

    File ml_run_File;

    {
        try {
            ml_run_File = File.createTempFile("ml_run", "txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        findViewById(R.id.idButtonSync).setOnClickListener(this);

    }

    private void sync1() throws IOException {

        storage = FirebaseStorage.getInstance();

        currentEmail = HomePageActivity.eEmail;
        Log.d("CurrentEmail", currentEmail);
        folderName = ashwin(currentEmail);
        Log.d("Ashwin", folderName);

        currentFileName = ashwinDate();

            //koh7();
        uploadFile("recent_csv.txt");   //  working.
        uploadFile("userid.txt");       //  working.

        uploadFileUserCsv(ashwin(HomePageActivity.eEmail));
        uploadFileUserTxt(ashwin(HomePageActivity.eEmail), currentFileName);

        // https://github.com/KOHTheCodeMaster02/AryaProject2
        updateRecent_CSVFile(currentFileName);

        //downloadFile(currentFileName, "users", ".txt");
        koh2();
//        download_ml_run();
        edit_ml_run();
        upload_ml_run();

        //

        File dir = getFilesDir();
        File file = new File(dir, "k.txt");
        if(file.exists())
            Log.d("status", "exists");
        else
            Log.d("status", "not exists");

        //

//        asishUploadFileUserTxt("chocho.txt", currentFileName);
        //readDownloadedFile(currentFileName + ".txt");
        //asishUploadFileUserTxt(ashwin(HomePageActivity.eEmail), currentFileName);

        //readDownloadedFile(currentFileName, ".txt");


        //downloadFile();
        //uploadFile("c.txt");
          //downloadFile();   //  working.


    }

    private void updateRecent_CSVFile(String currentFileName){
        koh4(currentFileName);

        asishUploadFileUserTxt("temp", currentFileName, ".csv");

    }

    //  Write Current File name in recent_csv.txt which is present at mysterious place.
    private void koh4(String currentFileName) {

        File dir = getFilesDir();
        File file = new File(dir, "recent_csv.txt");
        //File f = new File()
        try {
            // Handle Csv file properly ASAP!!!.

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(currentFileName + ".csv");
            bufferedWriter.close();

        } catch (IOException e){
            e.printStackTrace();
            Log.d("status", "error!");
            Log.d("status", file.getPath());
        }


    }

    private void readDownloadedFile(String currentFileName, String extension) {

        File dir = getFilesDir();
        File file = new File(dir, currentFileName + extension );
        try {
            Log.d("aq2", "entered\n File: "+ currentFileName + extension);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            line = bufferedReader.readLine();
            Log.d("aq2", line);
            /*
            while((line = bufferedReader.readLine() ) != null ){
                Log.d("aq2", line);
            }*/
            bufferedReader.close();
            Log.d("aq2", "exit\n");

        } catch (IOException e){
            e.printStackTrace();
            Log.d("aq1", "error!");
            Log.d("aq1", file.getPath());
        }
        Log.d("aq2", "unknown\n");

    }


    // Kaam kar raha hai jo wahi hai ye. ~Asish Dada.
    private File readAndWriteFilesWithExtension(String tempFileName){
        //koh6();
        File dir = getFilesDir();
        File file = new File(dir, tempFileName);
        //File f = new File()
        try {

            String str = ashwin( HomePageActivity.eEmail);
            if(tempFileName.equals("recent_csv.txt"))
                str += ".csv";

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(str);
            bufferedWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufferedReader.readLine() ) != null ){
                Log.d("aq1", line);
            }
            bufferedReader.close();

        } catch (IOException e){
            e.printStackTrace();
            Log.d("aq1", "error!");
            Log.d("aq1", file.getPath());
        }

        return file;
    }


    private void msg1(){
        Toast.makeText(getApplicationContext(), "Data Synced Successfully!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idButtonSync:
                try {
                    sync1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.idButtonViewHealthReport:
                //chiku();
                break;
        }
    }

    private String ashwin(String email) {

        //String str1 = email.substring(0, email.length())
        //String str = "@";
        int a = email.indexOf('@');
        String str = email.substring(0, a);

        return str;
    }

    private String ashwinDate(){

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD_HH-mm-SS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //  Working.    for Temp folder.
    private void uploadFile(String tempFileName){

        readAndWriteFilesWithExtension(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, tempFileName);
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();


        StorageReference river = storageRef.child("temp/" + tempFileName);
        UploadTask uploadTask = river.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });
    }


    private void uploadFileUserCsv(String tempFileName){

        readAndWriteFiles(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, tempFileName);
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();

        StorageReference river = storageRef.child("users/" + tempFileName + "/" + currentFileName + ".csv");
        UploadTask uploadTask = river.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });
    }

    //  Called when uploading for users folder.
    private void readAndWriteFiles(String tempFileName) {
        //koh6();
        File dir = getFilesDir();
        File file = new File(dir, tempFileName);
        //File f = new File()
        try {
            // Handle Csv file properly ASAP!!!.

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("healthy");
            bufferedWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufferedReader.readLine() ) != null ){
                Log.d("status", line);
            }
            bufferedReader.close();

        } catch (IOException e){
            e.printStackTrace();
            Log.d("status", "error!");
            Log.d("status", file.getPath());
        }

    }

    // asish upload.

    private void asishUploadFileUserTxt(String firebaseFolder, String currentTime, String extension){
        //  tempFileName hume Ashwin bhaiya de rahe hai vo bhi ek dum MUFT!!!
        //readAndWriteFiles(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, "d.txt");
        if(file.exists())
            Log.d("status", "exists");
        else
            Log.d("status", "No!");
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();

        StorageReference river = storageRef.child(firebaseFolder + "/" + currentTime + extension);
        UploadTask uploadTask = river.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });
    }


    // Upload under "users/" + ashwin(eEmail) + "/" + currentFileName + ".txt";

    private void uploadFileUserTxt(String tempFileName, String currentTime){
        //  tempFileName hume Ashwin bhaiya de rahe hai vo bhi ek dum MUFT!!!
        readAndWriteFiles(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, tempFileName);
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();

        StorageReference river = storageRef.child("users/" + tempFileName + "/" + currentFileName + ".txt");
        UploadTask uploadTask = river.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });
    }


    //  Download File Working.
    private void koh2() throws IOException {

        StorageReference storageRef = storage.getReference();
        final StorageReference riversRef = storageRef.child("temp/userid.txt");

        final File localFile = File.createTempFile("userid", "txt");
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        Log.d("status" ,"downloaded");

                        Log.d("status" , riversRef.getDownloadUrl().toString());
                        Log.d("status" , localFile.getAbsolutePath());

                        if(localFile.exists())
                            Log.d("status" ,"Exists");

                        if(!localFile.exists())
                            Log.d("status" , "No");
                        // ...
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                Log.d("status" ,"not downloaded");
            }
        });


    }

//    we are downloading the ml_run file to check if there is already some syncing going on
//    private void download_ml_run() throws IOException {
//        StorageReference storageRef = storage.getReference();
//        final StorageReference riversRef = storageRef.child("temp/ml_run.txt");
//
//        File localFile = File.createTempFile("ml_run", "txt");
//        riversRef.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        // Successfully downloaded data to local file
//                        // ...
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                // ...
//            }
//        });
//    }
//

//  on every sync we tell the ml algo to run on the latest data
    private void edit_ml_run() throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ml_run_File));
        bufferedWriter.write("run");
        bufferedWriter.close();
    }

//    we need to upload the run_ml file with content = run or stop
    private void upload_ml_run() throws IOException {

        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File(ml_run_File.toURI()));
        StorageReference riversRef = storageRef.child("temp/ml_run.txt");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }




    //  Download Working.
    private void downloadFile(String currentFileName, String firebaseFolder, String extension){
        Log.d("status" ,"entered");
        StorageReference storageRef = storage.getReference();
        final StorageReference isLandRef = storageRef.child(firebaseFolder + "/" + ashwin(eEmail) + "/" + currentFileName + ".txt");

        File dir = getFilesDir();
        final File file = new File(dir, "k.txt");

        Log.d("status" ,"got file");
        isLandRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                Log.d("status" , isLandRef.getDownloadUrl().toString());
                Log.d("status" , file.getAbsolutePath());

                if(file.exists())
                    Log.d("status" ,"Exists");

                if(!file.exists())
                    Log.d("status" , "No");

            }



        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(file.exists())
                    Log.d("status" ,"Failed");

            }
        });

        Log.d("status" ,"exit");
    }

    private void readDownloadedFile(String fullFileName){
        //koh6();
        File dir = getFilesDir();
        File file = new File(dir, "chocho.txt");
        //File f = new File()
        try {
            // Handle Csv file properly ASAP!!!.

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufferedReader.readLine() ) != null ){
                Log.d("chocho", line);
            }
            bufferedReader.close();

        } catch (IOException e){
            e.printStackTrace();
            Log.d("chocho", "error!");
            Log.d("chocho", file.getPath());
        }

    }





    // -----------------------------------------
    //             Upload Any File.
    //  firebaseFolder: users, temp, users/a1, etc...
    //  currentTime: current date & time [YY-MM-DD_HH-MM-SS]
    //  extension: .txt, .csv

    private void uploadAnyFile(String firebaseFolder, String currentTime, String extension){
        //  tempFileName hume Ashwin bhaiya de rahe hai vo bhi ek dum MUFT!!!
        //readAndWriteFiles(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, "d.txt");
        if(file.exists())
            Log.d("status", "exists");
        else
            Log.d("status", "No!");
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();

        StorageReference river = storageRef.child(firebaseFolder + "/" + currentTime + extension);
        UploadTask uploadTask = river.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });
    }


    // ------------------------------------------
    //  Download File Working.
    private void downloadAnyFile(String firebaseFolder, String fileName, String extension) throws IOException {

        StorageReference storageRef = storage.getReference();
        final StorageReference riversRef = storageRef.child( firebaseFolder + "/" + fileName + extension );

        final File localFile = File.createTempFile(fileName, extension);
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        /*                        *//*Log.d("status" ,"downloaded");

                        Log.d("status" , riversRef.getDownloadUrl().toString());
                        Log.d("status" , localFile.getAbsolutePath());*//*

                        if(localFile.exists())
                            Log.d("status" ,"Exists");

                        if(!localFile.exists())
                            Log.d("status" , "No");
                        // ...*/
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                Log.d("status" ,"not downloaded");
            }
        });
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


/*
    Aims:
    1. Upload CSV File.
    2. Health State File Upload.
    3. Upload Name of CSV File & Userid in text files.

    4. Download Health State file.
*/