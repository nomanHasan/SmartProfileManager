package com.example.no.profilemanagerservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by no on 5/5/2016.
 */
public class SmartService extends Service implements SensorEventListener{

    SensorManager sensorManager;

    Sensor ProximitySensor;
    Sensor Accelerometer;
    Sensor GyroScope;

    AudioManager audioManager;

    public static final int update=1000;
    private long lastupdate;


    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        sensorManager.registerListener(this,ProximitySensor,sensorManager.SENSOR_DELAY_NORMAL);

        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MSG", "Service Started");
        sensorManager.registerListener(this, ProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, Accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Stop Service", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastupdate > update) {
                lastupdate = currentTime;
                float l = event.values[0];

                Log.d(l+" ","TEST");

                if (l <= 5.0) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Toast.makeText(SmartService.this, "slient", Toast.LENGTH_SHORT);
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Toast.makeText(SmartService.this, "General", Toast.LENGTH_SHORT);
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
