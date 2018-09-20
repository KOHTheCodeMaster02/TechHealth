package com.aryaproject.aryaproject2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import javax.net.ssl.HttpsURLConnection;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseStorage storage;
    StorageReference storageReference;
    InputStream stream;
    UploadTask uploadTask;
    private String currentEmail = "asi@afa.no";
    private String folderName = "abc";
    private String currentFileName = "null";

    static String cEmail = "no email";

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

    private void sync1() throws IOException {

        storage = FirebaseStorage.getInstance();

        currentEmail = HomePageActivity.cEmail;
        Log.d("CurrentEmail", currentEmail);
        folderName = ashwin(currentEmail);
        Log.d("Ashwin", folderName);

        currentFileName = ashwinDate();

        as2();
        //as1();
        //File newFile = koh7();

        //koh6();
        //koh5();
        //koh4();
        //koh3();
        //koh2();
        //koh1();
        //hcma1();

        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/users/" + folderName + "/").child(currentFileName + ".txt");
        stream = getResources().openRawResource(R.raw.healthstate);


        uploadTask = storageReference.putStream(stream);

        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/users/" + folderName + "/").child(currentFileName + ".csv");
        stream = getResources().openRawResource(R.raw.demo);
        uploadTask = storageReference.putStream(stream);

        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/temp/").child("recent_csv.txt");
        stream = getResources().openRawResource(R.raw.recent_csv);
        uploadTask = storageReference.putStream(stream);

        storageReference = storage.getReferenceFromUrl("gs://aryaproject2-7252e.appspot.com/temp/").child("userid.txt");
        stream = getResources().openRawResource(R.raw.userid);
        uploadTask = storageReference.putStream(stream);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                msg1();
            }
        });

    }


private void koh9(){
    try {
        FileOutputStream fos = new FileOutputStream(koh7());

//        how to upload a file to a url using java

//        String str = read
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

}
    //  Asish Fav. nhi h

    private void koh8(){

        URLConnection urlConnection = null;
        try{
            // file leni hai
            String str = "hello";
            FileOutputStream fos = new FileOutputStream(koh7());
            byte[] b = str.getBytes();
//https://storage.googleapis.com/aryaproject2-7252e.appspot.com/users/1/2018-09-263_23-15-75.txt
            URL url = new URL("https://storage.googleapis.com/aryaproject2-7252e.appspot.com/users/1/2018-09-263_23-15-75.txt");
            urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            if(urlConnection instanceof HttpsURLConnection){
                ((HttpsURLConnection) urlConnection ).setRequestMethod("PUT");
                ((HttpsURLConnection) urlConnection ).setRequestProperty("Content-type", "text/plain");
                ((HttpsURLConnection) urlConnection ).connect();
            }
            BufferedOutputStream bos = new BufferedOutputStream(urlConnection.getOutputStream());
            bos.write(b);
            fos.close();



            bos.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private File koh7(){
        //koh6();
        File dir = getFilesDir();
        File file = new File(dir, "a1.txt");
        //File f = new File()
        try {

            String str = "hello";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(str);
            bufferedWriter.close();
/*
            //BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\New Text Document.txt"));
            String line = null;
            while((line = bufferedReader.readLine() ) != null ){
                Log.d("aq1", line);
            }*/

        } catch (IOException e){
            e.printStackTrace();
            Log.d("aq1", "error!");
            Log.d("aq1", file.getPath());
        }

        return file;
    }

    private void koh6() {
        Formatter x = new Formatter();
        File d = getFilesDir();
        Log.d("q1", d.getPath());
        try {
            x = new Formatter("a1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();


            Log.d("q12", e.getMessage());
        }
        String data = "hello";
        x.format(data);
        x.close();

    }

    private void koh5() {

        tempFile1 t1 = new tempFile1();
        //t1.updateFile1();

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

    private String ashwinDate(){

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD_HH-mm-SS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void koh4()  {

        try {
            String fileName = "a1.txt";
            String str = "hello";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(str);
            bufferedWriter.close();

        } catch (IOException e){
            e.printStackTrace();
            Log.d("aq1", "error!");
        }
    }

    private void koh3(){
        String fileName = "C:/Users/King Of HearTs/AndroidStudioProjects/AryaProject2/app/src/main/res/raw/a1.txt";
        try {
            File file = new File("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\a1.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter pw = new PrintWriter(file);
            pw.print("healthy!");
            pw.close();
        } catch (IOException e){
            e.printStackTrace();
            Log.d("aq1", "error!");
        }
    }

    private void koh2() {
        String fileName = "C:/Users/King Of HearTs/AndroidStudioProjects/AryaProject2/app/src/main/res/raw/healthstate.txt";
        String str = "Hello";
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("q11", "invalid Path");
        }
        try {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("q11", "written1");
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("q11", "written2");
        }
        Log.d("q11", "written");
    }

    private void koh1()  {

        String fileName = "C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\healthstate.txt";
        PrintWriter printWriter = null;
        try (FileWriter fileWriter = new FileWriter(fileName)) {

            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.print("healthy string");
        //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        printWriter.close();

    }

    private void hcma1(){

        String fileNameUserid;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\healthstate.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("healthy");
        printWriter.close();
//            PrintWriter out = new PrintWriter("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\" + currentFileName + ".txt");
//            out.print("he");

/*
            PrintWriter out = new PrintWriter("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\userid.txt");

            out.print(folderName);
*/
/*

            out = new PrintWriter("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\recent_csv.txt");
            out.print(currentFileName + ".csv");

            PrintWriter out1 = new PrintWriter("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\" + currentFileName + ".txt");
            out1.print("healthy");

            out = new PrintWriter("C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\" + currentFileName + ".csv");
*/

        //  Simulator.


        //out.close();
//            out1.close();

    }
    //  Upload Code Working


    private void as2(){

        File dir = getFilesDir();
        File file = new File(dir, "a1.txt");

        Uri file2 = Uri.fromFile(file);
        StorageReference storageRef = storage.getReference();


        StorageReference river = storageRef.child("users/1/a2.txt");
        UploadTask uploadTask = river.putFile(file2);

    }


    private void as1() throws IOException {
        URL u1 = new URL("https://storage.googleapis.com/aryaproject2-7252e.appspot.com/users/1/2018-09-263_23-15-75.txt");
        URLConnection u2 = u1.openConnection();
        InputStream is = u2.getInputStream();
        int i;
        while( (i = is.read()) != -1){
            Log.d("q1", String.valueOf((char) i));
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


/*
    Aims:
    1. Upload CSV File.
    2. Health State File Upload.
    3. Upload Name of CSV File & Userid in text files.

    4. Download Health State file.












 */













