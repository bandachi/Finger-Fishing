package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.PREFERENCES_COINS;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.cherryExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.cherryImage;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.coinExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.coinImage;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.coins;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenWidth;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.level;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.line;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormImage;

public class Healthbar {

    private ProgressBar health;
    private int currentFish;
    private ImageView fish;
    private RelativeLayout rl;
    private Context con;
    private Activity act;

    private Handler checkOverlap;
    private Handler changeDelay;
    private Handler itemSpawnDelayWorm;
    private Handler itemSpawnDelayCherry;
    private Handler itemSpawnDelayCoin;

    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future end;

    public Healthbar(RelativeLayout rl, Context con, Activity act, Handler checkOverlap, int currentFish, Handler changeDelay, Handler itemSpawnDelayWorm, Handler itemSpawnDelayCherry, Handler itemSpawnDelayCoin) {
        this.rl = rl;
        this.con = con;
        this.act = act;
        this.checkOverlap = checkOverlap;
        this.currentFish = currentFish;
        this.changeDelay = changeDelay;
        this.itemSpawnDelayCherry = itemSpawnDelayCherry;
        this.itemSpawnDelayWorm = itemSpawnDelayWorm;
        this.itemSpawnDelayCoin = itemSpawnDelayCoin;
    }

    //spawn the healthbar
    public void spawnHealth() {
        health = act.findViewById(R.id.healthBar);
        fish = act.findViewById(currentFish);
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
        } else {
            health.incrementProgressBy(-1);
        }

        //check if healthbar is below 10, if so, go to lose activity
        if (health.getProgress() < 10 && active) {
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

            return true;
        }
        //check if healthbar is above 990, if so, go to win activity
        else if (health.getProgress() > 990 && active) {
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

            return true;
        } else {
            return false;
        }
    }

    //check if line is overlapping a cherry
    public void overlapItemCherry() {

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        cherryImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            TextView score = act.findViewById(R.id.scoreDisplay);
            int temp = Integer.parseInt(score.getText().toString());
            temp += 100 * level;
            score.setText(Integer.toString(temp));
            Item.removeItem(cherryImage, rl);
            cherryExist = false;
        }

    }

    //check if line is overlapping a worm
    public void overlapItemWorm() {

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        wormImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            health.incrementProgressBy(100);
            Item.removeItem(wormImage, rl);
            wormExist = false;
        }
    }

    //check if line is overlapping a coin
    public void overlapItemCoin() {

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        coinImage.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            coins++;
            SharedPreferences coinsFile = act.getSharedPreferences(PREFERENCES_COINS, MODE_PRIVATE);
            SharedPreferences.Editor editor = coinsFile.edit();
            editor.putInt("coinCount", coins);
            editor.apply();

            TextView coin = act.findViewById(R.id.coinDisplay);
            coin.setText(Integer.toString(coins));

            Item.removeItem(coinImage, rl);
            coinExist = false;
        }
    }

    //check if line is overlapping any important image views
    Runnable check = new Runnable() {
        boolean done = false;

        @Override
        public void run() {

            end.cancel(true);
            if (cherryExist) {
                overlapItemCherry();
            }
            if (wormExist) {
                overlapItemWorm();
            }
            if (coinExist) {
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