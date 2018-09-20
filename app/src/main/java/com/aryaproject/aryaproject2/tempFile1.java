package com.aryaproject.aryaproject2;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class tempFile1 {

    public void updateFile1() {

        String fileName = "C:\\Users\\King Of HearTs\\AndroidStudioProjects\\AryaProject2\\app\\src\\main\\res\\raw\\healthstate.txt";
        String str = "hello";
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("q12", "error1");
        }
        try {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("q12", "error2");
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("q12", "error3");
        }
    }

}
