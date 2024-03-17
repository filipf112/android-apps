package com.example.ballmazegamefinal;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private View backBtn;
    private GameView gameView;
    private Button btnBallMaze;
    private Button btnDrawingView;

    private float[] acceleration = new float[3];
    private final int MOVING_AVG_POINTS = 4;
    private List<float[]> accelerationRawPoints;

    private float[] filteredAcceleration;


    SensorEventListener accelListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(final SensorEvent event) {

                System.arraycopy(event.values, 0, acceleration, 0, 3);

                accelerationRawPoints.add(acceleration);


            if (updateAvailable()) {
                updateMarbleValues();
                if (drawingView.isMarbleInit()) {
                    drawingView.moveMarble(filteredAcceleration);
                    drawingView.resetView();
                }
                clearSensorFilterBuffers();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private boolean updateAvailable() {
        boolean accelerometer_ready = (accelerationRawPoints.size() >= MOVING_AVG_POINTS);
        if (accelerometer_ready)
            return true;
        return false;
    }


    private void updateMarbleValues() {
        filteredAcceleration = movingAverageFilter(accelerationRawPoints);

    }


    private void clearSensorFilterBuffers() {
        accelerationRawPoints.clear();
    }

    public float[] movingAverageFilter(List<float[]> points) {
        final int AXIS_NUM = 3;
        float[] sum = new float[AXIS_NUM];
        float[] avg = new float[AXIS_NUM];

        for (int i = 0; i < AXIS_NUM; i++) {
            sum[i] = 0;
            avg[i] = 0;
        }

        if (points.size() > MOVING_AVG_POINTS) {
            int elementsToRemove = points.size() - MOVING_AVG_POINTS;
            while (elementsToRemove != 0) {
                int index = elementsToRemove - 1;
                points.remove(index);
                elementsToRemove--;
            }
        }


        for (float[] arrayValue : points) {

            int index = 0;
            for (float axis : arrayValue) {
                sum[index] += axis;
                index++;
            }
        }

        int axisIndex = 0;
        for (float axisSumValue : sum) {
            avg[axisIndex] = axisSumValue / MOVING_AVG_POINTS;
            axisIndex++;

        }
        return avg;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawingView = findViewById(R.id.drawingView);
        gameView = findViewById(R.id.gameView);
        btnBallMaze = findViewById(R.id.btnBallMaze);
        btnDrawingView = findViewById(R.id.btnDrawingView);
        backBtn = findViewById(R.id.backBtn);


        drawingView.setVisibility(View.GONE);
        gameView.setVisibility(View.GONE);
        backBtn.setVisibility(View.GONE);


        btnBallMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setVisibility(View.VISIBLE);
                gameView.setVisibility(View.GONE);
                backBtn.setVisibility(View.VISIBLE);
                setVariable();
            }
        });

        btnDrawingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setVisibility(View.GONE);
                gameView.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.VISIBLE);
                setVariable();
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            // setup sensor
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            // exit
            finish();
        }

        accelerationRawPoints = new ArrayList<float[]>();
        filteredAcceleration = new float[6];


    }

    private void setVariable() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelListener, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(accelListener);
        super.onPause();
    }

    public void wonGameOption() {
        if (!isFinishing()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Gratulacje!");
            alert.setMessage("Wygrales! Grasz jeszcze raz?");

            alert.setPositiveButton("Sprobuj ponownie", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent = getIntent();
                    startActivity(intent);
                }
            });

            alert.setNegativeButton("Wyjdz", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }

    public void gameOverOption() {
        if (!isFinishing()) { // Check if the activity is still active
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Koniec gry");
            alert.setMessage("Przegrales. Jeszcze raz?");

            alert.setPositiveButton("Sprobuj ponownie", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            alert.setNegativeButton("Wyjdz", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }
}