package com.example.datossensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sensorLight;
    private Sensor sensorProximity;

    private TextView tvLuz;
    private TextView tvProx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLuz = (TextView) findViewById(R.id.luz_sensor);
        tvProx = (TextView) findViewById(R.id.proximidad_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensorLight == null) {
            tvLuz.setText("Error en sensor Light");
        }
        if (sensorProximity == null) {
            tvProx.setText("Error en sensor Proximity");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();
        float valorActual = event.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                tvLuz.setText(getResources().getString(R.string.luz_sensor, valorActual));
                break;
            case Sensor.TYPE_PROXIMITY:
                tvProx.setText(getResources().getString(R.string.proximidad_sensor, valorActual));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorLight != null) {
            sensorManager.registerListener(this, sensorLight, sensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorProximity != null) {
            sensorManager.registerListener(this, sensorProximity, sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
