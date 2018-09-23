package com.aryaproject.aryaproject2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseStorage storage;
    StorageReference storageReference;
    InputStream stream;
    UploadTask uploadTask;
    private String currentEmail = "asi@afa.no";
    private String folderName = "abc";
    static String currentFileName = "null";

    static String eEmail = "no email";
    static String currentUserHashId = "not assigned yet.";
    static String DOB = "not assigned yet.";
    static String Gender = "not assigned yet.";
    static String Age = "not assigned yet.";
    static User currentUser = null;
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
        findViewById(R.id.idButtonViewProfile).setOnClickListener(this);
        findViewById(R.id.idButtonViewHealthReport).setOnClickListener(this);

//        koh2();
        //  Upload Gender.
        DatabaseReference firebaseRef;
        firebaseRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://aryaproject2-7252e.firebaseio.com/Users/" +
                        HomePageActivity.currentUserHashId);

        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                HomePageActivity.DOB = currentUser.getDob();
                HomePageActivity.Gender = currentUser.getGender();

//                textViewDisplayPatientName.setText(currentUserDetails.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void sync1() throws IOException {
        storage = FirebaseStorage.getInstance();

        currentEmail = HomePageActivity.eEmail;
//        Log.d("CurrentEmail", currentEmail);
        folderName = ashwin(currentEmail);
//        Log.d("Ashwin", folderName);

        currentFileName = ashwinDate();

        koh2();

//        edit_ml_run();
//        upload_ml_run();

        update4MajorFiles();
        //perfectTesterByKoh();



    }

    private void koh2() throws IOException {

        writeToAnyFile(HomePageActivity.Age, "gender.txt");
        readFromAnyFile("gender.txt");
        uploadAnyFile("temp", "gender", ".txt");

    }

    // age
    private int getAge(int year, int month, int day){

        Date now = new Date();
        int nowMonth = now.getMonth() + 1;
        int nowYear = now.getYear() + 1900;
        int result = nowYear - year;

        if( month > nowMonth )
            result--;
        else if( month == nowMonth ){
            int nowDay = now.getDate();
            if(day > nowDay)
                result--;
        }
        return result;
    }

    //  4 Major Important Functions!!!

    private void updateUsersTxt() throws IOException {

        String data = "healthy";
        String localFileName = currentFileName;
        String extension = ".txt";
        String firebaseFolder = "users/" + ashwin(eEmail) ;

        writeToAnyFile(data, localFileName + extension);
        uploadAnyFile(firebaseFolder, localFileName, extension);

    }


    private void updateUsersCsv(String firebaseFolder) throws IOException {

        int a=0;
        double b=0.0;
        int c=0,d=0;        File dir = getFilesDir();
        File file = new File(dir, HomePageActivity.currentFileName + ".csv");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        Random r = new Random();

        int low = 60, high = 100, heartrate;
        double low1 = 36.5, high1 = 37.2, bodytemperature, bt;
        bodytemperature = high1 - low1;
        int low2 = 90, high2 = 120;
        int low3 = 60, high3 = 80;
        int systolicpressure, diastolicpressure;

        try {
            String str = "";
            str +="HeartRate," + "Temperature," + "SystolicBloodPressure," + "DiastolicBloodPressure\n";

            for (int i = 0; i <= 100; i++) {
                heartrate = r.nextInt(high - low) + low;
                a = a + heartrate;
                bodytemperature = r.nextDouble() + low1;
                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);
                bt = Double.parseDouble(df.format(bodytemperature));
                b = b + bt;
                systolicpressure = r.nextInt(high2 - low2) + low2;
                c = c + systolicpressure;
                diastolicpressure = r.nextInt(high3 - low3) + low3;
                d = d + diastolicpressure;

                if(i == 100)
                    str += heartrate + "," + df.format(bodytemperature) + "," + systolicpressure + ","
                            + diastolicpressure ;
                else
                    str += heartrate + "," + df.format(bodytemperature) + "," + systolicpressure + ","
                            + diastolicpressure + "\n";
            }
            a = a / 100;
            c = c / 100;
            d = d / 100;
            b = b / 100;
            String hrate = a + "", temperature = b + "", spressure = c + "", dpressure = d + "";

            Log.d("status", str);

            writeToAnyFile(str, HomePageActivity.currentFileName + ".csv");
            readFromAnyFile( HomePageActivity.currentFileName + ".csv");
            uploadParticularUserCsv(firebaseFolder,HomePageActivity.currentFileName, ".csv" );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void uploadParticularUserCsv(String firebaseFolder, String sourceFileName, String extension){
        //readAndWriteFiles(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, sourceFileName + extension);
        /*if(file.exists())
            Log.d("status", "exists");
        else
            Log.d("status", "No!");*/
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();

        StorageReference riv = storageRef.child(firebaseFolder + "/" + sourceFileName + extension);
        UploadTask uploadTask = riv.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                try {
                    edit_ml_run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    upload_ml_run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


/*
    Empty Csv.
    private void updateUsersCsv() throws IOException {

        String data = currentFileName;  //  Get Simulated Data here.
        String localFileName = currentFileName;
        String extension = ".csv";
        String firebaseFolder = "users/" + ashwin(eEmail) ;

        // wrtieSimulatedCsvFile !!
        writeToAnyFile(data, localFileName + extension);
        uploadAnyFile(firebaseFolder, localFileName, extension);

    }
*/

    private void updateRecent_CsvDotTxt() throws IOException {

        String csvName = currentFileName;   //  To Upload currentTime with ".txt" as content in recent_csv.txt
        String localFileName = "recent_csv";
        String extension = ".txt";
        String firebaseFolder = "temp";

        writeToAnyFile(csvName + ".csv", localFileName + extension);
        uploadAnyFile(firebaseFolder, localFileName, extension);

    }

    private void updateUseridDotText() throws IOException {
        String userid = ashwin(eEmail); // Data to write in "userid.txt" is user's email + ".txt"
        String localFileName = "userid";
        String extension = ".txt";
        String firebaseFolder = "temp";

        writeToAnyFile(userid, localFileName + extension);
        uploadAnyFile(firebaseFolder, localFileName, extension);

    }

    private void update4MajorFiles() throws IOException {

        updateUseridDotText();
        updateRecent_CsvDotTxt();
        updateUsersCsv("users/" + ashwin(eEmail));
        updateUsersTxt();



    }

    //  Testing All 4 tasks working successfully.
    //  Write, Read, Upload & Downnload.
    private void perfectTesterByKoh() throws IOException {

        String fullFileName1 = "recent_csv.txt";
        String data = "run";
        String firebaseFolder = "temp";
        String soureFileName = "recent_csv";
        String extension = ".txt";

        writeToAnyFile(data, fullFileName1);
        readFromAnyFile(fullFileName1);

        uploadAnyFile(firebaseFolder, soureFileName, extension);
        downloadAnyFile(firebaseFolder, soureFileName, extension);
        readFromAnyFile(soureFileName + extension);
        data = "stop";

        writeToAnyFile(data, soureFileName + extension);
        readFromAnyFile(soureFileName + extension);

    }



    private void msg1(){
        Toast.makeText(getApplicationContext(), "Data Synced Successfully!",
                Toast.LENGTH_SHORT).show();
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

    // -----------------------------------------
    //             Upload Any File.
    //  firebaseFolder: users, temp, users/a1, etc... (do not use '/' at the end of the firebaseFolderName.
    //  sourceFileName: source file which exists in mysterious place (with content) which needs to be uploaded on firebase.
    //  extension: .txt, .csv

    private void uploadAnyFile(String firebaseFolder, String sourceFileName, String extension){
        //readAndWriteFiles(tempFileName);

        File dir = getFilesDir();
        File file = new File(dir, sourceFileName + extension);
        /*if(file.exists())
            Log.d("status", "exists");
        else
            Log.d("status", "No!");*/
        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();

        StorageReference riv = storageRef.child(firebaseFolder + "/" + sourceFileName + extension);
        UploadTask uploadTask = riv.putFile(file2);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                msg1();
            }
        });
    }


    // ------------------------------------------
    //  Download File Working.
    //  firebaseFolder: users, temp, users/a1, etc... (do not use '/' at the end of the firebaseFolderName.
    //  sourceFileName: source file which exists in mysterious place (with content) which needs to be uploaded on firebase.
    //  extension: .txt, .csv
    private void downloadAnyFile(String firebaseFolder, String sourceFileName, String extension) throws IOException {

        StorageReference storageRef = storage.getReference();
        final StorageReference firebaseFileRef = storageRef.child( firebaseFolder + "/" + sourceFileName + extension );

        final File localFile = File.createTempFile(sourceFileName, extension);
        firebaseFileRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

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

    //  Write data to any file.
    //  data: content to write.
    //  localFileName: file name with extension in which data needs to be written & this file will be at Mysterious Place.
    private void writeToAnyFile(String data, String localFileName) throws IOException {

        //  localFileName is with the file with extension which needs the content to be written on it.
        File dir = getFilesDir();
        File file = new File(dir, localFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(data);
        bufferedWriter.close();
    }

    // Read:
    // sourceFileNameWExt: source file name with extension from which data needs to be read line by line
    // and printed on LogCat with tag "status".
    private void readFromAnyFile(String sourceFileNameWExt){
        File dir = getFilesDir();
        File file = new File(dir, sourceFileNameWExt);
        String readData = "";
        //File f = new File()
        try {
            // Handle Csv file properly ASAP!!!.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufferedReader.readLine() ) != null ){
//                Log.d("status", line);
                readData += line;
            }
            bufferedReader.close();
            Log.d("status", readData);
/*

            if(readData.equals("stop"))
                Log.d("status", "Machine has predicted its results successfully.");
            if(readData.equals("run"))
                Log.d("status", "Machine is Running its Algo. (Your System is in Busy Waiting)");
*/


        } catch (IOException e){
            e.printStackTrace();
            Log.d("status", "error!");
            Log.d("status", file.getPath());
        }

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
            case R.id.idButtonViewProfile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.idButtonViewHealthReport:
                startActivity(new Intent(this, HealthProfile.class));
                break;
        }
    }


}

/*
 * Pending:
 * 1. App Drawer
 *      a. Default Home Page -> Health Report.
 *      b. Next Page -> View Profile.
 *      c. Next Page -> About Us.
 *      d. Next Page => Help.
 * 2. Realtime Sync Health Report [every 2 seconds]
 * 3. Run Stop ML Handle.
 * 4. Doctor UI.
 */

/*
 * Status: Working
 * Verified Before Sleep.
 * Working: Reading, Writing, Uploading, Downloading Files from firebase.
 * Last Modified: 22th September, 0040,
 * Developed By,
 * ~K.O.H..!! ^__^
 *
 */


/*
    Aims:
    1. Upload CSV File. [Partially Done] [Simulation left]
    2. Health State File Upload.    [Done]
    3. Upload Name of CSV File & Userid in text files.  [Done]
    4. Download Health State file.  [Done]
*/