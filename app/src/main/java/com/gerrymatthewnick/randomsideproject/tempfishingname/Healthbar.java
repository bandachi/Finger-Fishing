package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.cherryExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.cherryImage;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenWidth;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.level;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.line;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormImage;

//TODO make healthbar not an object
public class Healthbar {

    private ProgressBar health;
    private int currentFish;
    private ImageView fish;

    private RelativeLayout rl;
    private Context con;
    private Activity act;
    private android.os.Handler checkOverlap;
    private Handler changeDelay;
    private Handler itemSpawnDelayWorm;
    private Handler itemSpawnDelayCherry;

    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future end;

    public Healthbar(RelativeLayout rl, Context con, Activity act, Handler checkOverlap, int currentFish, Handler changeDelay, Handler itemSpawnDelayWorm, Handler itemSpawnDelayCherry) {
        this.rl = rl;
        this.con = con;
        this.act = act;
        this.checkOverlap = checkOverlap;
        this.currentFish = currentFish;
        this.changeDelay = changeDelay;
        this.itemSpawnDelayCherry = itemSpawnDelayCherry;
        this.itemSpawnDelayWorm = itemSpawnDelayWorm;
    }

    public void spawnHealth() {
        health = new ProgressBar(con, null, android.R.attr.progressBarStyleHorizontal);
        health.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        health.setLayoutParams(lp);
        health.setLayoutParams(new RelativeLayout.LayoutParams(getScreenWidth() - 200, 50));
        rl.addView(health);
        health.setMax(1000);
        health.setProgress(250);
        health.setX(100);
        health.setMinimumWidth(getScreenWidth() / 2);
        health.setY(100);

        fish = act.findViewById(currentFish);
    }

    public boolean overlap(ImageView first, ImageView second) {
        Rect fishRect = new Rect();
        Rect lineRect = new Rect();

        first.getHitRect(fishRect);
        second.getHitRect(lineRect);

        lineRect.top = lineRect.bottom - 10;

        if (fishRect.contains(lineRect)) {
            health.incrementProgressBy(3);
        } else {
            health.incrementProgressBy(-1);
        }

        if (health.getProgress() < 10 && active) {
            end.cancel(true);
            itemSpawnDelayWorm.removeCallbacksAndMessages(null);
            itemSpawnDelayCherry.removeCallbacksAndMessages(null);

            act.finish();
            Intent intent = new Intent(con, LoseActivity.class);
            TextView score = act.findViewById(R.id.scoreDisplay);

            int temp = Integer.parseInt(score.getText().toString());
            intent.putExtra("scoreNumber", temp);
            con.startActivity(intent);

            return true;
        } else if (health.getProgress() > 990 && active) {
            end.cancel(true);
            itemSpawnDelayWorm.removeCallbacksAndMessages(null);
            itemSpawnDelayCherry.removeCallbacksAndMessages(null);

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

    public void overlapItemCherry(ImageView line) {

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

    public void overlapItemWorm(ImageView line) {

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

    Runnable check = new Runnable() {
        boolean done = false;

        @Override
        public void run() {

            TextView testForItem = act.findViewById(R.id.textView4);
            testForItem.setText("C" + Boolean.toString(cherryExist) + " W" + Boolean.toString(wormExist));
            end.cancel(true);
            if (cherryExist == true) {
                overlapItemCherry(line);
            }
            if (wormExist == true) {
                overlapItemWorm(line);
            }

            done = overlap(fish, line);
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