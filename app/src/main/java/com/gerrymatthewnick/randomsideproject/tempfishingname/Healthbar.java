package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.currentItemId;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenWidth;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.level;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.line;

public class Healthbar {

    private ProgressBar health;
    private int currentFish;
    private ImageView fish;

    private RelativeLayout rl;
    private Context con;
    private Activity act;
    private android.os.Handler checkOverlap;

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
            health.incrementProgressBy(6);
        } else {
            health.incrementProgressBy(-3);
        }


        if (health.getProgress() < 10) {
            end.cancel(true);
            act.finish();
            if (active) {
                Intent intent = new Intent(con, LoseActivity.class);
                con.startActivity(intent);
            }

            return true;
        } else if (health.getProgress() > 990) {
            end.cancel(true);
            act.finish();
            if (active) {
                Intent intent = new Intent(con, WinActivity.class);
                intent.putExtra("levelNumber", level + 1);

                TextView score = act.findViewById(R.id.scoreDisplay);
                int temp =  Integer.parseInt(score.getText().toString());
                intent.putExtra("scoreNumber", temp);

                con.startActivity(intent);
            }
            return true;
        } else {
            return false;
        }
    }

    public void overlapItem(ImageView line) {
        ImageView item = act.findViewById(currentItemId);

        Rect lineRect = new Rect();
        Rect itemRect = new Rect();

        line.getHitRect(lineRect);
        item.getHitRect(itemRect);

        lineRect.top = lineRect.bottom - 10;

        if (itemRect.contains(lineRect)) {
            TextView score = act.findViewById(R.id.scoreDisplay);
            int temp = Integer.parseInt(score.getText().toString());
            temp+= 100 * level;
            score.setText(Integer.toString(temp));
            Item.removeItem(item, rl);
            currentItemId = -1;
        }

    }

    Runnable check = new Runnable() {
        boolean done = false;

        @Override
        public void run() {
            end.cancel(true);
            if (currentItemId != -1 && act.findViewById(currentItemId) != null) {
                overlapItem(line);
            }

            done = overlap(fish, line);
            if (!done && active) {
                checkOverlap.postDelayed(check, 50);
            }
            else {
                rl.removeAllViews();
            }
        }
    };
    public void startCheck() {
        end = threadPoolExecutor.submit(check);
        check.run();
    }
}