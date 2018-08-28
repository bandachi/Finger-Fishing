package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_CHERRY_COUNT;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COINS;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COIN_COUNT;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_HIGHSCORE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_SOUND;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_WORM_COUNT;
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
    private int soundIdWin;
    private boolean soundOption;
    private Chronometer timer;
    private Handler checkOverlap;
    private Handler changeDelay;
    private Handler itemSpawnDelayWorm;
    private Handler itemSpawnDelayCherry;
    private Handler itemSpawnDelayCoin;

    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future end;

    public Healthbar(RelativeLayout rl, Context con, Handler checkOverlap, int currentFish, Handler changeDelay, Handler itemSpawnDelayWorm, Handler itemSpawnDelayCherry, Handler itemSpawnDelayCoin, ImageView line, int coins, int level, SoundPool mSoundPool, Chronometer timer) {
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
        this.timer = timer;
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
            soundIdWin = mSoundPool.load(act, R.raw.win_sound, 1);
        }
    }

    //check if line is overlapping the fish
    private boolean overlap(ImageView first) {
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

            int elapsedMillis = (int) (SystemClock.elapsedRealtime() - timer.getBase());
            intent.putExtra("currentTime", elapsedMillis);
            timer.stop();

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

            mSoundPool.play(soundIdWin, 1, 1, 0, 0, 1);

            con.startActivity(intent);
            act.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            return true;
        } else {
            return false;
        }
    }

    //check if line is overlapping a cherry
    private void overlapItemCherry() {
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

            SharedPreferences cherryFile = act.getSharedPreferences(PREFERENCES_HIGHSCORE, MODE_PRIVATE);

            int highscore = cherryFile.getInt("highest", 0);

            if (temp > highscore) {
                SharedPreferences.Editor editor = cherryFile.edit();
                editor.putInt("highest", temp);
                editor.apply();
                TextView highScoreText = act.findViewById(R.id.highscoreDisplay);
                highScoreText.setText("Highscore: " + Integer.toString(temp));
            }

            SharedPreferences cherryCountFile = act.getSharedPreferences(PREFERENCES_CHERRY_COUNT, MODE_PRIVATE);
            int currentCherryCount = cherryCountFile.getInt("cherries", 0);
            SharedPreferences.Editor editorStats = cherryCountFile.edit();

            editorStats.putInt("cherries", currentCherryCount + 1);
            editorStats.apply();
        }
    }
    //check if line is overlapping a worm
    private void overlapItemWorm() {
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

            SharedPreferences wormCountFile = act.getSharedPreferences(PREFERENCES_WORM_COUNT, MODE_PRIVATE);
            int currentWormCount = wormCountFile.getInt("worms", 0);
            SharedPreferences.Editor editorStats = wormCountFile.edit();

            editorStats.putInt("worms", currentWormCount + 1);
            editorStats.apply();
        }


    }

    //check if line is overlapping a coin
    private void overlapItemCoin() {
        ImageView coinImage = act.findViewById(Coin.coinId);

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        coinImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            Item.removeItem(coinImage, rl);
            coinExist = false;

            //for current coin count
            coins++;
            SharedPreferences coinsFile = act.getSharedPreferences(PREFERENCES_COINS, MODE_PRIVATE);
            SharedPreferences.Editor editor = coinsFile.edit();
            editor.putInt("coinCount", coins);
            editor.apply();

            TextView coin = act.findViewById(R.id.coinDisplay);
            coin.setText(Integer.toString(coins));

            //for total coin count over the total time played
            SharedPreferences coinCountFile = act.getSharedPreferences(PREFERENCES_COIN_COUNT, MODE_PRIVATE);
            int currentCoinCount = coinCountFile.getInt("coins", 0);
            SharedPreferences.Editor editorStats = coinCountFile.edit();

            editorStats.putInt("coins", currentCoinCount + 1);
            editorStats.apply();
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

//TODO Future