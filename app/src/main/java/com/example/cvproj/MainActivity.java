 package com.example.cvproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

 public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

     JavaCameraView cameraView;
     Mat mRgba, imgGray, imgCanny;

     BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
         @Override
         public void onManagerConnected(int status) {
             switch(status){
                 case BaseLoaderCallback.SUCCESS: {
                     cameraView.enableView();
                     break;
                 }
                 default: {
                     super.onManagerConnected(status);
                 }
             }
         }
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = findViewById(R.id.cameraView);
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCvCameraViewListener(this);

        //cameraBridgeViewBase.enableView();
    }

    @Override
    protected void onPause(){
         super.onPause();
         if (cameraView != null) {
            cameraView.disableView();
         }
    }

    @Override
    protected void onDestroy() {
         super.onDestroy();
         if (cameraView != null) {
             cameraView.disableView();
         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(), "Load success", Toast.LENGTH_SHORT).show();
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            Toast.makeText(getApplicationContext(), "Not load", Toast.LENGTH_SHORT).show();
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
        }
    }

     @Override
     public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        imgGray = new Mat(height, width, CvType.CV_8UC1);
        imgCanny = new Mat(height, width, CvType.CV_8UC1);
     }

     @Override
     public void onCameraViewStopped() {
        mRgba.release();
     }

     @Override
     public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
         mRgba = inputFrame.rgba();

         Imgproc.cvtColor(mRgba, imgGray, Imgproc.COLOR_RGB2GRAY);
         Imgproc.Canny(imgGray, imgCanny, 50, 150);

         //return imgGray;
         return imgCanny;
     }
 }
