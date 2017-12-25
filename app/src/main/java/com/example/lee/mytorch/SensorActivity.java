package com.example.lee.mytorch;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by lee on 2017/12/17.
 */

public class SensorActivity extends Activity implements SensorEventListener, View.OnClickListener {

    private CameraManager mCameraManager;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private Button mButton;
    private boolean processState = true;
    int turn = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


       // mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            String[] cameraList = mCameraManager.getCameraIdList();
            for (String str : cameraList) {
            }
        } catch (CameraAccessException e) {
            Log.e("error", e.getMessage());
        }

        mButton = (Button) findViewById(R.id.SensorOn);
        mButton.setOnClickListener(this);
    }


    protected void lightSwitch(final boolean lightStatus) {
        if (lightStatus) {
            try {
                mCameraManager.setTorchMode("0", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                mCameraManager.setTorchMode("0", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
  /*  @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }*/

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean light = false;

        float xValue = sensorEvent.values[0];// Acceleration minus Gx on the x-axis
        float yValue = sensorEvent.values[1];//Acceleration minus Gy on the y-axis
        float zValue = sensorEvent.values[2];//Acceleration minus Gz on the z-axis
//        float xValueNew = sensorEvent.values[0];// Acceleration minus Gx on the x-axis
//        float yValueNew = sensorEvent.values[1];//Acceleration minus Gy on the y-axis
//        float zValueNew = sensorEvent.values[2];//Acceleration minus Gz on the z-axis

//      double delta= Math.sqrt(Math.pow((xValue-xValueNew),2)+Math.pow((yValue-yValueNew),2));
        xValue=Math.abs(xValue);
        yValue=Math.abs(yValue);
        System.out.println("xValue"+xValue+";yValue"+yValue+";zValue"+zValue);
//        System.out.println("delta"+delta);
        if (xValue > 20 || yValue > 20) {
            turn++;
        }


        //android.hardware.Camera cam = android.hardware.Camera.open();
        //android.hardware.Camera.Parameters p = cam.getParameters();

        if (!processState) {
            if(turn%2==1&&turn>0) {
                lightSwitch(false);
            }
            if(turn%2==0&&turn>0) {
                lightSwitch(true);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void onClick(View view) {
        if (processState) {
            mButton.setText("SensorOn");
            //onResume();
            processState =false;
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            mButton.setText("SensorOff");
            //onPause();
            processState = true;
            mSensorManager.unregisterListener(this);
        }
    }
}

