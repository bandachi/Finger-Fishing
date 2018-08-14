package com.gerrymatthewnick.randomsideproject.tempfishingname;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    static boolean active = false;
    static int level;
    public static int currentItemIdCherry = -1;
    public static int currentItemIdWorm = -1;
    public static final int SPAWN_DELAY_CHERRY = 4000;
    public static final int SPAWN_DELAY_WORM = 6000;
    public static ImageView line;

    private int fishId;
    private RelativeLayout rl;
    private Context con = this;
    private Activity act = this;
    private int score = 0;


    Handler startSpawnFish = new Handler();
    Handler itemSpawnDelayCherry = new Handler();
    Handler itemSpawnDelayWorm = new Handler();
    Handler removeItemDelayCherry = new Handler();
    Handler removeItemDelayWorm = new Handler();
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
            score = extras.getInt("scoreNumber");
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
                levelText.setTextColor(Color.BLACK);
                levelText.setTextSize(22);

                TextView scoreText = findViewById(R.id.scoreDisplay);
                scoreText.setText(Integer.toString(score));

                int currentFish = View.generateViewId();
                fishId = getResources().getIdentifier("fish1" , "drawable", getPackageName());

                Fish fish = new Fish(fishId, 1000, 9 + level, rl, con, currentFish, changeFishVelocity, moveFish);
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
                currentItemIdCherry = -1;
                currentItemIdWorm = -1;
                Healthbar healthbar = new Healthbar(rl, con, act, checkOverlap, currentFish);
                healthbar.spawnHealth();
                healthbar.startCheck();

                runnableSpawnItemCherry.run();
                runnableSpawnItemWorm.run();
            }
        }, 3000);

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    Runnable runnableSpawnItemWorm = new Runnable() {
        @Override
        public void run() {

            currentItemIdWorm = View.generateViewId();
            Item item = new Item(rl, con, removeItemDelayWorm, "worm");
            item.spawn();

            if (active) {
                itemSpawnDelayWorm.postDelayed(runnableSpawnItemWorm, SPAWN_DELAY_WORM);
            }
            else {
                itemSpawnDelayCherry.removeCallbacks(runnableSpawnItemWorm);
            }
        }
    };

    Runnable runnableSpawnItemCherry = new Runnable() {
        @Override
        public void run() {

            currentItemIdCherry = View.generateViewId();
            Item item = new Item(rl, con, removeItemDelayCherry, "cherry");
            item.spawn();

            if (active) {
                itemSpawnDelayCherry.postDelayed(runnableSpawnItemCherry, SPAWN_DELAY_CHERRY);
            }
            else {
                itemSpawnDelayCherry.removeCallbacks(runnableSpawnItemCherry);
            }
        }
    };

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

//TODO make fish face direction of velocity