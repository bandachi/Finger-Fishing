package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COINS;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_SOUND;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.cherryExist;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.coinExist;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.wormExist;

public class Healthbar {

    private ProgressBar health;
    private int currentFish;
    private int coins;
    private int level;
    private ImageView fish;
    private ImageView line;
    private RelativeLayout rl;
    private Context con;
    private Activity act;
    private SoundPool mSoundPool;
    private int soundIdWormPickup;
    private int soundIdCherryPickup;
    private boolean soundOption;

    private Handler checkOverlap;
    private Handler changeDelay;
    private Handler itemSpawnDelayWorm;
    private Handler itemSpawnDelayCherry;
    private Handler itemSpawnDelayCoin;

    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future end;

    public Healthbar(RelativeLayout rl, Context con, Handler checkOverlap, int currentFish, Handler changeDelay, Handler itemSpawnDelayWorm, Handler itemSpawnDelayCherry, Handler itemSpawnDelayCoin, ImageView line, int coins, int level, SoundPool mSoundPool) {
        this.rl = rl;
        this.con = con;
        this.act = (Activity)con;
        this.checkOverlap = checkOverlap;
        this.currentFish = currentFish;
        this.changeDelay = changeDelay;
        this.itemSpawnDelayCherry = itemSpawnDelayCherry;
        this.itemSpawnDelayWorm = itemSpawnDelayWorm;
        this.itemSpawnDelayCoin = itemSpawnDelayCoin;
        this.line = line;
        this.coins = coins;
        this.level = level;
        this.mSoundPool = mSoundPool;
    }

    //spawn the healthbar
    public void spawnHealth() {
        health = act.findViewById(R.id.healthBar);
        fish = act.findViewById(currentFish);
    }

    //init soundPool
    public void initSound() {
        SharedPreferences settingsSound = act.getSharedPreferences(PREFERENCES_SOUND, MODE_PRIVATE);
        soundOption = settingsSound.getBoolean("soundOption", true);
        if (soundOption) {
            soundIdCherryPickup = mSoundPool.load(act, R.raw.cherry_pickup, 1);
            soundIdWormPickup = mSoundPool.load(act, R.raw.worm_pickup, 1);
        }
    }

    //check if line is overlapping the fish
    public boolean overlap(ImageView first) {
        Rect fishRect = new Rect();
        Rect lineRect = new Rect();

        first.getHitRect(fishRect);
        line.getHitRect(lineRect);

        lineRect.top = lineRect.bottom - 10;

        if (fishRect.contains(lineRect)) {
            health.incrementProgressBy(3);
            this.fish.setColorFilter(Color.CYAN * health.getProgress());
        } else {
            health.incrementProgressBy(-1);
            this.fish.clearColorFilter();
        }

        //check if healthbar is below 0, if so, go to lose activity
        if (health.getProgress() <= 0 && active) {
            end.cancel(true);
            itemSpawnDelayWorm.removeCallbacksAndMessages(null);
            itemSpawnDelayCherry.removeCallbacksAndMessages(null);
            itemSpawnDelayCoin.removeCallbacksAndMessages(null);

            act.finish();
            Intent intent = new Intent(con, LoseActivity.class);
            TextView score = act.findViewById(R.id.scoreDisplay);

            int temp = Integer.parseInt(score.getText().toString());
            intent.putExtra("scoreNumber", temp);
            con.startActivity(intent);
            act.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            return true;
        }
        //check if healthbar is above 1000, if so, go to win activity
        else if (health.getProgress() >= 1000 && active) {
            end.cancel(true);
            itemSpawnDelayWorm.removeCallbacksAndMessages(null);
            itemSpawnDelayCherry.removeCallbacksAndMessages(null);
            itemSpawnDelayCoin.removeCallbacksAndMessages(null);

            act.finish();
            Intent intent = new Intent(con, WinActivity.class);
            intent.putExtra("levelNumber", level + 1);

            TextView score = act.findViewById(R.id.scoreDisplay);
            int temp = Integer.parseInt(score.getText().toString());
            intent.putExtra("scoreNumber", temp);

            con.startActivity(intent);
            act.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            return true;
        } else {
            return false;
        }
    }

    //check if line is overlapping a cherry
    public void overlapItemCherry() {
        ImageView cherryImage = act.findViewById(Cherry.cherryId);

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        cherryImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            Item.removeItem(cherryImage, rl);
            cherryExist = false;
            TextView score = act.findViewById(R.id.scoreDisplay);
            int temp = Integer.parseInt(score.getText().toString());
            temp += 100 * level;
            score.setText(Integer.toString(temp));
            mSoundPool.play(soundIdCherryPickup, 1, 1, 0, 0, 1);
        }
    }
    //check if line is overlapping a worm
    public void overlapItemWorm() {
        ImageView wormImage = act.findViewById(Worm.wormId);

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        wormImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            Item.removeItem(wormImage, rl);
            wormExist = false;
            health.incrementProgressBy(100);
            mSoundPool.play(soundIdWormPickup, 1, 1, 0, 0, 1);
        }
    }

    //check if line is overlapping a coin
    public void overlapItemCoin() {
        ImageView coinImage = act.findViewById(Coin.coinId);

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        coinImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            Item.removeItem(coinImage, rl);
            coinExist = false;

            coins++;
            SharedPreferences coinsFile = act.getSharedPreferences(PREFERENCES_COINS, MODE_PRIVATE);
            SharedPreferences.Editor editor = coinsFile.edit();
            editor.putInt("coinCount", coins);
            editor.apply();

            TextView coin = act.findViewById(R.id.coinDisplay);
            coin.setText(Integer.toString(coins));

        }
    }

    //check if line is overlapping any important image views
    Runnable check = new Runnable() {
        boolean done = false;

        @Override
        public void run() {

            end.cancel(true);
            if (cherryExist && act.findViewById(Cherry.cherryId) != null) {
                overlapItemCherry();
            }
            if (wormExist && act.findViewById(Worm.wormId) != null) {
                overlapItemWorm();
            }
            if (coinExist && act.findViewById(Coin.coinId) != null) {
                overlapItemCoin();
            }

            done = overlap(fish);
            if (!done && active) {
                checkOverlap.postDelayed(check, 10);
            } else {
                checkOverlap.removeCallbacks(check);
            }
        }
    };
    public void startCheck() {
        end = threadPoolExecutor.submit(check);
        check.run();
    }
}

//TODO Future and make line not static