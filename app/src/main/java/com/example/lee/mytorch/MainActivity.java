package com.example.lee.mytorch;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    private Camera mCamera=null;
    public TimerTask mTimerTask;
    public Timer mTimer;
    int mInt;
    boolean flag=false;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOn=(Button)findViewById(R.id.lightOn);
        Button btnOff=(Button)findViewById(R.id.lightOff);
        Button btnSOS=(Button)findViewById(R.id.lightSOS);
        mCameraManager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);


        try {
            String[] cameraList=mCameraManager.getCameraIdList();
            for(String str:cameraList){
            }
        }catch (CameraAccessException e){
            Log.e("error",e.getMessage());
        }

        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SOS();
             /* if(!flag){
                  flag=true;
                  SOS();
              }else{
                  flag=false;
                  lightSwitch(true);
              }*/
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightSwitch(false);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightSwitch(true);
            }
        });
    }

    private void SOS(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(mInt=0;;mInt=mInt==0?1:0){
                    switch (mInt) {
                        case 1:
                            lightSwitch(false);
                            break;
                        case 0:
                            lightSwitch(true);
                            break;
                    }
                }
            }
        },0);
    }

  /*  private void SOS(){
        for(mInt=0;;mInt=mInt==0?1:0){
            switch (mInt){
                case 1:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lightSwitch(false);
                        }
                    },1000);
                    break;
                case 0:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lightSwitch(true);
                        }
                    },3000);
                    break;
            }
        }
    }*/

    //灯光开关控制
    private void lightSwitch(final boolean lightStatus) {
        if(lightStatus){
                try{
                    mCameraManager.setTorchMode("0",false);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
        else{
            try{
                mCameraManager.setTorchMode("0",true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
