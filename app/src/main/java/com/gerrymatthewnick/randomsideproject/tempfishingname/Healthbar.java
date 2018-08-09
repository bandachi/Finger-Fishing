package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.getScreenWidth;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.line;

public class Healthbar {
    private ProgressBar health;

    private RelativeLayout rl;
    private Context con;
    private Activity act;
    private android.os.Handler checkOverlap;
    private int currentFish;
    private ImageView fish;

    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future end;



    public Healthbar(RelativeLayout rl, Context con, Activity act, android.os.Handler checkOverlap, int currentFish) {
        this.rl = rl;
        this.con = con;
        this.act = act;
        this.checkOverlap = checkOverlap;
        this.currentFish = currentFish;
    }

    public void spawnHealth() {
        health = new ProgressBar(con, null, android.R.attr.progressBarStyleHorizontal);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        health.setLayoutParams(lp);
        health.setLayoutParams(new RelativeLayout.LayoutParams(getScreenWidth()-200, 50));
        rl.addView(health);
        health.setMax(1000);
        health.setProgress(250);
        health.setX(100);
        health.setMinimumWidth(getScreenWidth()/2);
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
        }
        else {
            health.incrementProgressBy(-3);
        }


        if (health.getProgress() < 10) {
            end.cancel(true);
            act.finish();
            Intent intent = new Intent(con, loseActivity.class);
            con.startActivity(intent);
            return true;
        }
        else if (health.getProgress() > 990) {
            end.cancel(true);
            act.finish();
            Intent intent = new Intent(con, winActivity.class);
            con.startActivity(intent);
            return true;
        }
        else {
            return false;
        }

    }

    Runnable check = new Runnable() {
        boolean done = false;

        @Override
        public void run() {
            end.cancel(true);
               done = overlap(fish, line);
               if (!done) {
                   checkOverlap.postDelayed(check, 50);
               }

        }
    };
    public void startCheck() {

        end = threadPoolExecutor.submit(check);
        check.run();
    }

}
