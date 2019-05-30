 package com.example.cvproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(), "Load success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Not load", Toast.LENGTH_SHORT).show();
        }

        //cameraBridgeViewBase.enableView();
    }
}
