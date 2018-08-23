package com.gerrymatthewnick.randomsideproject.FingerFishing;


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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    public static final String PREFRENCES_HIGHSCORE = "highscore";
    public static final String PREFERENCES_COINS = "coins";
    public static boolean active = false;

    public static boolean cherryExist = false;
    public static boolean wormExist = false;
    public static boolean coinExist = false;

    public int level;
    private int fishId;
    private RelativeLayout rl;
    private Context con = this;
    private Activity act = this;
    private int score = 0;

    Handler initialDelayCherry = new Handler();
    Handler initialDelayWorm = new Handler();
    Handler initialDelayCoin = new Handler();
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

        ProgressBar health = findViewById(R.id.healthBar);
        health.setLayoutParams(new RelativeLayout.LayoutParams(getScreenWidth() - 200, 50));
        health.setX(100);
        health.setY(100);
        health.setMinimumWidth(getScreenWidth()/2);
        health.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //load highscore to display on game_activity activity
        SharedPreferences settingsHigh = getSharedPreferences(PREFRENCES_HIGHSCORE, MODE_PRIVATE);
        int highscore = settingsHigh.getInt("highest", 0);
        TextView highDis = findViewById(R.id.highscoreDisplay);
        highDis.setText(highDis.getText().toString() + Integer.toString(highscore));

        //load coins to display on game activity activity
        SharedPreferences settingsCoin = getSharedPreferences(PREFERENCES_COINS, MODE_PRIVATE);
        final int coins = settingsCoin.getInt("coinCount", 0);
        TextView coinDis = findViewById(R.id.coinDisplay);
        coinDis.setText(Integer.toString(coins));

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
                Fish fish = new Fish(fishId, 1000, 9 + level, rl, con, currentFish, changeFishVelocity, moveFish, level);
                fish.spawnFish();
                fish.setX(getScreenWidth()/2);
                fish.setY(getScreenHeight()/2);

                fish.startChangeVelocity();
                fish.startVelocity();

                ImageView line = findViewById(R.id.fishingLine);

                //Create healthbar and start checking for overlaps
                Healthbar healthbar = new Healthbar(rl, con, act, checkOverlap, currentFish, changeDelay, itemSpawnDelayWorm, itemSpawnDelayCherry, itemSpawnDelayCoin, line, coins, level);
                healthbar.spawnHealth();
                healthbar.startCheck();

                //Run item spawn runnables
                initialDelayCherry.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runnableSpawnItemCherry.run();
                    }
                }, 2000);

                initialDelayWorm.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runnableSpawnItemWorm.run();
                    }
                }, 3000);

                initialDelayCoin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runnableSpawnItemCoin.run();
                    }
                }, 4000);
            }
        }, 1500);

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    Runnable runnableSpawnItemCherry = new Runnable() {
        @Override
        public void run() {
            //spawn cherry randomly on screen every SPAWN_DELAY_CHERRY

            Cherry cherry = new Cherry(rl, con, removeItemDelayCherry);
            cherry.spawnCherry();

            if (active) {
                itemSpawnDelayCherry.postDelayed(runnableSpawnItemCherry, cherry.getItemDelay());
            }
            else {
                itemSpawnDelayCherry.removeCallbacksAndMessages(runnableSpawnItemCherry);
            }
        }
    };

    Runnable runnableSpawnItemWorm = new Runnable() {
        @Override
        public void run() {
            //spawn worm randomly on screen every SPAWN_DELAY_WORM
            Worm worm = new Worm(rl, con, removeItemDelayWorm);
            worm.spawnWorm();

            if (active) {
                itemSpawnDelayWorm.postDelayed(runnableSpawnItemWorm, worm.getItemDelay());
            }
            else {
                itemSpawnDelayWorm.removeCallbacksAndMessages(runnableSpawnItemWorm);
            }
        }
    };

    Runnable runnableSpawnItemCoin = new Runnable() {
        @Override
        public void run() {
            //spawn coin randomly on screen every SPAWN_DELAY_COIN
            Coin coin = new Coin(rl, con, removeItemDelayCoin);
            coin.spawnCoin();

            if (active) {
                itemSpawnDelayCoin.postDelayed(runnableSpawnItemCoin, coin.getItemDelay());
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
        ImageView line = findViewById(R.id.fishingLine);

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
        changeFishVelocity.removeCallbacksAndMessages(null);
        moveFish.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        //prevent back button
    }
    @Override
    public void onPause() {
        super.onPause();
        active = false;
        changeFishVelocity.removeCallbacksAndMessages(null);
        moveFish.removeCallbacksAndMessages(null);
    }
}

//TODO make fish face direction of velocity