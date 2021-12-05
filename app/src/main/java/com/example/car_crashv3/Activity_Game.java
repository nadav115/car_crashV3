package com.example.car_crashv3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Activity_Game extends AppCompatActivity {
    private Game g;
    private Bundle b;
    private SensorManager sensorManager;
    private Sensor accSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        g=new Game((Vibrator) getSystemService(Context.VIBRATOR_SERVICE), this);

        findViews();

        initBundle();
        g.modifyGameByBundle(b);
        if(g.getTiltMode()){
            initSensor();
        }

        g.newGame();

        g.getButtonLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.movePlayer(true);
            }
        });

        g.getButtonRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.movePlayer(false);
            }
        });

        g.getButtonMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            movementWithTilt(x,y);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };

    private void movementWithTilt(float x, float y) {
        if (g.isTiltModeInitialized()) {
            float intervalX = g.getInitializedX()-x;
            float lastViewedIntervalX = g.getLastViewedX()-x;
            float intervalY = g.getInitializedY()-y;
            if(intervalX>g.SENSITIVITY &&  lastViewedIntervalX>g.SENSITIVITY){ //move right
                g.movePlayer(false);
                g.setLastViewedX(x);
            }
            else if(intervalX<-g.SENSITIVITY && lastViewedIntervalX<-g.SENSITIVITY){ //move left
                g.movePlayer(true);
                g.setLastViewedX(x);
            }
            else if(lastViewedIntervalX>g.SENSITIVITY || lastViewedIntervalX<-g.SENSITIVITY){ // movement was reset
                g.setLastViewedX(x);
            }
            if(intervalY>g.SENSITIVITY*2){ //make game faster
                g.setDelay(g.getDelay()-((int)intervalY)*3);
            }
            else if(intervalY<-g.SENSITIVITY*1){ // make game slower
                g.setDelay(g.getDelay()-((int)intervalY*12));
            }

        }
        else{
            g.setInitializedX(x);
            g.setInitializedY(y);
            g.setLastViewedX(x);
            g.setTiltModeInitialized(true);
        }
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void initBundle() {
        if(getIntent().hasExtra(Activity_Options.BUNDLE)){
            b=getIntent().getExtras().getBundle(Activity_Options.BUNDLE);
        }
        else b=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        g.onResume();
        g.startTicker();
        if(g.getTiltMode())
            sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        g.onPause();
        g.stopTicker();
        if(g.getTiltMode())
            sensorManager.unregisterListener(accSensorEventListener);
    }

    private void findViews(){
        g.setTimer(findViewById(R.id.odometer));
        g.setPoints(findViewById(R.id.points));
        g.setButtonLeft(findViewById(R.id.buttonLeft));
        g.setButtonRight(findViewById(R.id.buttonRight));
        g.setButtonMenu(findViewById(R.id.game_BTN_menu));
        g.setLives(new ImageView[]{
                findViewById(R.id.life1),
                findViewById(R.id.life2),
                findViewById(R.id.life3)
        });

        g.setTiles(new Tile[][]{{
                new Tile( findViewById(R.id.panel_IMG_tile11), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile12), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile13), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile14), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile15), Tile.EMPTY)
        }, {
                new Tile( findViewById(R.id.panel_IMG_tile21), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile22), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile23), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile24), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile25), Tile.EMPTY)
        }, {
                new Tile( findViewById(R.id.panel_IMG_tile31), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile32), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile33), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile34), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile35), Tile.EMPTY)
        }, {
                new Tile( findViewById(R.id.panel_IMG_tile41), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile42), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile43), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile44), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile45), Tile.EMPTY)
        }, {
                new Tile( findViewById(R.id.panel_IMG_tile51), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile52), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile53), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile54), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile55), Tile.EMPTY)
        }, {
                new Tile( findViewById(R.id.panel_IMG_tile61), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile62), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile63), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile64), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile65), Tile.EMPTY)
        },  {
                new Tile( findViewById(R.id.panel_IMG_tile81), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile82), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile83), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile84), Tile.EMPTY),
                new Tile( findViewById(R.id.panel_IMG_tile85), Tile.EMPTY)

        }});


    }




}