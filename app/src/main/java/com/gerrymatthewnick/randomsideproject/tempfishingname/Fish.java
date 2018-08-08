package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.logging.Handler;

public class Fish {

    private int type;
    private int changeFreq;
    private RelativeLayout rl;
    private static Context context;
    private int maxVel;
    private float velX;
    private float velY;
    private android.os.Handler changeVel;



    private ImageView fish;

    public Fish(int fishType, int change, int vel, RelativeLayout relativeL, Context current, android.os.Handler CHANGE_FISH_VELOCITY) {

        type = fishType;
        changeFreq = change;
        maxVel = vel;
        rl = relativeL;
        context = current;
        changeVel = CHANGE_FISH_VELOCITY;


    }



    public void setX(float x) {
        fish.setX(x);

    }
    public void setY(float y) {
        fish.setY(y);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                velX = (float)(Math.random() * maxVel);
                velY = (float)(Math.random() * maxVel);
                fish.setX(velX);
                fish.setY(velY);
            }
            finally {
                changeVel.postDelayed(runnable, changeFreq);
            }
        }
    };

    public void startChangeVelocity() {
        runnable.run();
    }


    public void spawnFish() {
        fish = new ImageView(context);
        fish.setImageResource(type);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        fish.setLayoutParams(lp);
        rl.addView(fish);
    }

}
