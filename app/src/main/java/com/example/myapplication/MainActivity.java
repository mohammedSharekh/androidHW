package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Sensor proximitySensor;
    SensorManager sensorManager;
    Intent intent;
    View rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl = findViewById(R.id.screenView);
        Intent intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);

        headset_on headset = new headset_on();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headset,filter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor==null){
            Toast.makeText(this,"No Proximity Sensor found in the Device",Toast.LENGTH_LONG).show();
        }else {
            sensorManager.registerListener(psl,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    public class headset_on extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra("state",-1);
            Intent inte = new Intent(MainActivity.this,MyService.class);
            switch (state) {
                case 0:
                    Toast.makeText(context, "Headset Off", Toast.LENGTH_SHORT).show();
                    context.stopService(inte);
                    break;
                case 1:
                    Toast.makeText(context, "Headset On", Toast.LENGTH_SHORT).show();
                    context.startService(inte);
                    break;
                default:
                    Toast.makeText(context, "undefind", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    SensorEventListener psl = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent senE) {
            Intent intent = new Intent(MainActivity.this,MyService.class);
            int state = intent.getIntExtra("state",-1);

            if (senE.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (senE.values[0] == 0) {
                    rl.setBackgroundResource(R.color.blue);
                    stopService(intent);
                } else {
                    rl.setBackgroundResource(R.color.white);
                    if (state == -1) {
                        startService(intent);
                    }else {
                        stopService(intent);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}