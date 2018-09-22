package com.aryaproject.aryaproject2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
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
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class HealthProfile extends AppCompatActivity {

    static TextView pulse;
    static TextView temp;
    static TextView sys;
    static TextView dia;
    public int a=0;
    double b=0.0;
    int c=0,d=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_profile);

        pulse = (TextView) findViewById(R.id.ht);
        temp = (TextView) findViewById(R.id.utemp);
        sys = (TextView) findViewById(R.id.bp);
        dia = (TextView) findViewById(R.id.ubp);

        try {
            chocho1("users/" + ashwin( HomePageActivity.eEmail) ) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chocho1(String firebaseFolder) throws IOException {

        File dir = getFilesDir();
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

            temp.setText(temperature);
            dia.setText(dpressure);
            sys.setText(spressure);
            pulse.setText(hrate);
            Log.d("status", str);

            writeToAnyFile(str, HomePageActivity.currentFileName + ".csv");
            readFromAnyFile( HomePageActivity.currentFileName + ".csv");
            uploadAnyFile(firebaseFolder,HomePageActivity.currentFileName, ".csv" );

//            bufferedWriter.write(str);

//            bufferedWriter.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        //readFromAnyFile("a.csv");
        //uploadAnyFile("temp", "a", ".csv");


    }
    private void writeToAnyFile(String data, String localFileName) throws IOException {

        //  localFileName is with the file with extension which needs the content to be written on it.
        File dir = getFilesDir();
        File file = new File(dir, localFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(data);
        bufferedWriter.close();
    }

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
            Log.d("status", "a file: " + readData);
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

    private void uploadAnyFile(String firebaseFolder, String sourceFileName, String extension){
        //readAndWriteFiles(tempFileName);
        FirebaseStorage storage = FirebaseStorage.getInstance();

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


}

