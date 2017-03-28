package com.mobileappscompany.training.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREF_THIS = "SharedPreferencesTest";
    private final static String FILE_NAME = "MyPersistentFileName.txt";
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences shared = getSharedPreferences(SHARED_PREF_THIS, MODE_PRIVATE);
        text = (EditText) findViewById(R.id.editText);
        File file = new File(FILE_NAME);
        if(!file.exists()) {
        }
    }

    private void write(String entry){

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            Log.d("TAG", fos.getFD().toString());
            fos.write(entry.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        int c;
        FileInputStream fIn = null;
        try {
            fIn = openFileInput(FILE_NAME);
            StringBuilder temp = new StringBuilder();
            while ((c = fIn.read()) != -1) {
                temp.append(Character.toString((char)c));
            }
            text.setText(temp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fIn != null) {
                try {
                    fIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        write(text.getText().toString());
    }
}
