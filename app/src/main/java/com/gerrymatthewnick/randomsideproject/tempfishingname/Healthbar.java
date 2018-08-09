package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.getScreenWidth;

public class Healthbar {

    private RelativeLayout rl;
    private Context con;
    private ProgressBar health;

    public Healthbar(RelativeLayout rl, Context con) {
        this.rl = rl;
        this.con = con;
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
    }
}
