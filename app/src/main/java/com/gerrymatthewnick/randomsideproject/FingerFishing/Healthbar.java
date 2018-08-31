package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.active;

public class Healthbar {

    private ProgressBar health;
    private int currentFish;
    private int level;
    private int decayRate = -1;
    private int increaseRate = 3;
    private ImageView fish;
    private ImageView line;
    private Context con;
    private Activity act;
    private Sound sound;
    private Chronometer timer;
    private Handler checkOverlap;
    private Handler itemSpawnDelayWorm;
    private Handler itemSpawnDelayCherry;
    private Handler itemSpawnDelayCoin;
    private Handler changeHealthDecay;

    private Cherry cherryObj;
    private Worm wormObj;
    private Coin coinObj;

    private ImageView cherryImage;
    private ImageView wormImage;
    private ImageView coinImage;

    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future end;

    public Healthbar(Context con, Handler checkOverlap, int currentFish, Handler itemSpawnDelayWorm, Handler itemSpawnDelayCherry, Handler itemSpawnDelayCoin, ImageView line, int level, Chronometer timer, Sound sound, Handler changeHealthDecay) {
        this.con = con;
        this.act = (Activity) con;
        this.checkOverlap = checkOverlap;
        this.currentFish = currentFish;
        this.itemSpawnDelayCherry = itemSpawnDelayCherry;
        this.itemSpawnDelayWorm = itemSpawnDelayWorm;
        this.itemSpawnDelayCoin = itemSpawnDelayCoin;
        this.line = line;
        this.level = level;
        this.timer = timer;
        this.sound = sound;
        this.changeHealthDecay = changeHealthDecay;
    }

    //spawn the healthbar
    public void spawnHealth() {
        health = act.findViewById(R.id.healthBar);
        fish = act.findViewById(currentFish);
    }

    //check if line is overlapping the fish
    private boolean overlap() {
        Rect fishRect = new Rect();
        Rect lineRect = new Rect();

        fish.getHitRect(fishRect);
        line.getHitRect(lineRect);

        lineRect.top = lineRect.bottom - 10;

        if (fishRect.contains(lineRect)) {
            health.incrementProgressBy(increaseRate);
            this.fish.setColorFilter(Color.CYAN * health.getProgress());
        } else {
            health.incrementProgressBy(decayRate);
            this.fish.clearColorFilter();
        }

        //check if healthbar is below 0, if so, go to lose activity
        if (health.getProgress() <= 0 && active) {

            int elapsedMillis = (int) (SystemClock.elapsedRealtime() - timer.getBase());
            finish();

            Intent intent = new Intent(con, LoseActivity.class);
            TextView score = act.findViewById(R.id.scoreDisplay);

            int temp = Integer.parseInt(score.getText().toString());
            intent.putExtra("scoreNumber", temp);
            intent.putExtra("time", elapsedMillis);
            con.startActivity(intent);
            act.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            return true;
        }
        //check if healthbar is above 1000, if so, go to win activity
        else if (health.getProgress() >= 1000 && active) {

            int elapsedMillis = (int) (SystemClock.elapsedRealtime() - timer.getBase());
            finish();

            Intent intent = new Intent(con, WinActivity.class);
            intent.putExtra("levelNumber", level + 1);

            TextView score = act.findViewById(R.id.scoreDisplay);
            int temp = Integer.parseInt(score.getText().toString());
            intent.putExtra("scoreNumber", temp);
            intent.putExtra("currentTime", elapsedMillis);

            sound.playWin();

            con.startActivity(intent);
            act.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            return true;
        } else {
            return false;
        }
    }

    public void finish() {
        timer.stop();

        end.cancel(true);
        itemSpawnDelayWorm.removeCallbacksAndMessages(null);
        itemSpawnDelayCherry.removeCallbacksAndMessages(null);
        itemSpawnDelayCoin.removeCallbacksAndMessages(null);
        changeHealthDecay.removeCallbacksAndMessages(null);

        act.finish();
    }

    //check if line is overlapping a cherry
    private void overlapItemCherry() {

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        cherryImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            cherryObj.cherryEffect(act, 100 * level, sound);
        }
    }

    //check if line is overlapping a worm
    private void overlapItemWorm() {

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        wormImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            wormObj.wormEffect(act, health, sound);
        }
    }

    //check if line is overlapping a coin
    private void overlapItemCoin() {

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        coinImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            coinObj.coinEffect(act);
        }
    }

    //check if line is overlapping any important image views
    Runnable check = new Runnable() {
        boolean done = false;

        @Override
        public void run() {

            //Check if line is overlapping items
            end.cancel(true);
            if (cherryImage != null) {
                overlapItemCherry();
            }
            if (wormImage != null) {
                overlapItemWorm();
            }
            if (coinImage != null) {
                overlapItemCoin();
            }
            //If the game is won or lost, done = true and check runnable is terminated
            done = overlap();
            if (!done && active) {
                checkOverlap.postDelayed(check, 10);
            } else {
                checkOverlap.removeCallbacks(check);
            }
        }
    };

    Runnable decay = new Runnable() {
        @Override
        public void run() {
            decayRate--;
            if (active) {
                changeHealthDecay.postDelayed(decay, 15000);
            } else {
                changeHealthDecay.removeCallbacksAndMessages(null);
            }
        }
    };

    public void setItem(Item item) {
        String type = item.getType();

        //set item and image according to what item type they are
        if (type.equals("cherry")) {
            cherryObj = (Cherry) item;
            cherryImage = item.getImage();
        } else if (type.equals("worm")) {
            wormObj = (Worm) item;
            wormImage = item.getImage();
        } else if (type.equals("coin")) {
            coinObj = (Coin) item;
            coinImage = item.getImage();
        }
    }

    public void startCheck() {
        end = threadPoolExecutor.submit(check);
        check.run();
    }

    public void startTime() {
        decay.run();
    }
}