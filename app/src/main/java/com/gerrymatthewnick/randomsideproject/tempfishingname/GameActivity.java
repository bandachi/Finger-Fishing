package com.gerrymatthewnick.randomsideproject.tempfishingname;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    static boolean active = false;
    static int level;

    private int fishId;
    private RelativeLayout rl;
    private Context con = this;
    private Activity act = this;
    public static ImageView line;

    Handler startSpawnFish = new Handler();
    Handler changeFishVelocity;
    Handler moveFish;
    Handler checkOverlap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rl = findViewById(R.id.rlGame);
        line = findViewById(R.id.fishingLine);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            level = extras.getInt("levelNumber");
        }
        else {
            level = 1;
        }

        //Start the fish spawning method after 3 seconds
        startSpawnFish.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeFishVelocity = new Handler();
                moveFish = new Handler();
                checkOverlap = new Handler();

                TextView levelText = findViewById(R.id.levelDisplay);
                levelText.setText("Level: " + level);

                int currentFish = View.generateViewId();
                fishId = getResources().getIdentifier("fish1" , "drawable", getPackageName());

                Fish fish = new Fish(fishId, 1000, 10, rl, con, currentFish, changeFishVelocity, moveFish);
                fish.spawnFish();
                fish.setX(getScreenWidth()/2);
                fish.setY(getScreenHeight()/2);

                fish.startChangeVelocity();
                fish.startVelocity();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Healthbar healthbar = new Healthbar(rl, con, act, checkOverlap, currentFish);
                healthbar.spawnHealth();

               healthbar.startCheck();
            }
        }, 3000);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    //keep fishing line image on screen touch
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                line.setX(x);
                line.setY(y);
                break;

            case MotionEvent.ACTION_MOVE:
                line.setX(x);
                line.setY(y);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        active = false;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    public void onBackPressed() {
        //prevent back button
    }
    @Override
    public void onPause() {
        super.onPause();
        active = false;
    }
}