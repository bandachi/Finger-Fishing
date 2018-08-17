package com.gerrymatthewnick.randomsideproject.tempfishingname;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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

    public static final String PREFRENCES_HIGHSCORE = "highscore";
    public static int highscore = 0;
    static boolean active = false;
    static int level;
    public static final int SPAWN_DELAY_CHERRY = 4000;
    public static final int SPAWN_DELAY_WORM = 6000;
    public static final int SPAWN_DELAY_COIN = 7000;
    public static ImageView line;

    public static ImageView cherryImage;
    public static boolean cherryExist = false;
    public static ImageView wormImage;
    public static boolean wormExist = false;
    public static ImageView coinImage;
    public static boolean coinExist = false;

    private int fishId;
    private RelativeLayout rl;
    private Context con = this;
    private Activity act = this;
    private int score = 0;

    Handler initialDelay = new Handler();
    Handler startSpawnFish = new Handler();
    Handler itemSpawnDelayCherry = new Handler();
    Handler itemSpawnDelayWorm = new Handler();
    Handler itemSpawnDelayCoin = new Handler();
    Handler removeItemDelayCherry = new Handler();
    Handler removeItemDelayWorm = new Handler();
    Handler removeItemDelayCoin = new Handler();
    Handler changeFishVelocity;
    Handler moveFish;
    Handler checkOverlap;
    Handler changeDelay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rl = findViewById(R.id.rlGame);
        line = findViewById(R.id.fishingLine);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //load highscore to display on game_activity activity
        SharedPreferences settings = getSharedPreferences(PREFRENCES_HIGHSCORE, MODE_PRIVATE);
        highscore = settings.getInt("highest", 0);
        TextView highDis = findViewById(R.id.highscoreDisplay);
        highDis.setText(highDis.getText().toString() + Integer.toString(highscore));

        //get level and score from previous game levels
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

                //Display level text
                TextView levelText = findViewById(R.id.levelDisplay);
                levelText.setText("Level: " + level);
                levelText.setTextColor(Color.BLACK);
                levelText.setTextSize(22);

                //Display score text
                TextView scoreText = findViewById(R.id.scoreDisplay);
                scoreText.setText(Integer.toString(score));

                //Get fish image from resources
                int currentFish = View.generateViewId();
                fishId = getResources().getIdentifier("fish1" , "drawable", getPackageName());

                //Create fish object and start moving it
                Fish fish = new Fish(fishId, 1000, 9 + level, rl, con, currentFish, changeFishVelocity, moveFish);
                fish.spawnFish();
                fish.setX(getScreenWidth()/2);
                fish.setY(getScreenHeight()/2);

                fish.startChangeVelocity();
                fish.startVelocity();

                //Create healthbar and start checking for overlaps
                Healthbar healthbar = new Healthbar(rl, con, act, checkOverlap, currentFish, changeDelay, itemSpawnDelayWorm, itemSpawnDelayCherry);
                healthbar.spawnHealth();
                healthbar.startCheck();

                //Run item spawn runnables
                initialDelay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runnableSpawnItemCherry.run();
                        runnableSpawnItemWorm.run();
                        runnableSpawnItemCoin.run();
                    }
                }, 3000);

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
            //spawn worm randomly on screen every SPAWN_DELAY_WORM
            Item item = new Item(rl, con, removeItemDelayWorm, "worm");
            item.spawn();

            if (active) {
                itemSpawnDelayWorm.postDelayed(runnableSpawnItemWorm, SPAWN_DELAY_WORM);
            }
            else {
                itemSpawnDelayWorm.removeCallbacksAndMessages(runnableSpawnItemWorm);
            }
        }
    };

    Runnable runnableSpawnItemCherry = new Runnable() {
        @Override
        public void run() {
            //spawn cherry randomly on screen every SPAWN_DELAY_CHERRY
            Item item = new Item(rl, con, removeItemDelayCherry, "cherry");
            item.spawn();

            if (active) {
                itemSpawnDelayCherry.postDelayed(runnableSpawnItemCherry, SPAWN_DELAY_CHERRY);
            }
            else {
                itemSpawnDelayCherry.removeCallbacksAndMessages(runnableSpawnItemCherry);
            }
        }
    };

    Runnable runnableSpawnItemCoin = new Runnable() {
        @Override
        public void run() {
            //spawn coin randomly on screen every SPAWN_DELAY_COIN
            Item item = new Item(rl, con, removeItemDelayCoin, "coin");
            item.spawn();

            if (active) {
                itemSpawnDelayCoin.postDelayed(runnableSpawnItemCoin, SPAWN_DELAY_COIN);
            }
            else {
                itemSpawnDelayCoin.removeCallbacksAndMessages(runnableSpawnItemCoin);
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