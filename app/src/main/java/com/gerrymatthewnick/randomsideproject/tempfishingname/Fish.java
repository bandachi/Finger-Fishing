package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Fish {

    private int type;
    private int changeFreq;
    private RelativeLayout rl;
    private static Context context;
    private int maxVel;
    private float velX;
    private float velY;
    private android.os.Handler changeVel;
    private android.os.Handler frames;



    private ImageView fish;

    public Fish(int fishType, int change, int vel, RelativeLayout relativeL, Context current, android.os.Handler CHANGE_FISH_VELOCITY, android.os.Handler MOVE_FISH) {

        type = fishType;
        changeFreq = change;
        maxVel = vel;
        rl = relativeL;
        context = current;
        changeVel = CHANGE_FISH_VELOCITY;
        frames = MOVE_FISH;

    }

    public void setX(float x) {
        fish.setX(x);
    }
    public void setY(float y) {
        fish.setY(y);
    }

    Runnable runnableChange = new Runnable() {
        @Override
        public void run() {
            velX = 0;
            velY = 0;
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
            }
            try {
                velX = (float)(Math.random() * maxVel);
                velY = (float)(Math.random() * maxVel);

                int rand1 = (int)(Math.floor(Math.random() * 10)+ 1);
                int rand2 = (int)(Math.floor(Math.random() * 10)+ 1);

                if (rand1 > 5) {
                    velX = -velX;
                }
                if (rand2 > 5) {
                    velY = -velY;
                }
            }
            finally {
                changeVel.postDelayed(runnableChange, changeFreq);
            }
        }
    };

    public void startChangeVelocity() {
        runnableChange.run();
    }

    Runnable runnableVelocity = new Runnable() {
        @Override
        public void run() {
            try {

                fish.setX(fish.getX() + velX);
                fish.setY(fish.getY() + velY);
            }
            finally {
                frames.postDelayed(runnableVelocity, 50);
            }
        }
    };
    public void startVelocity() {
        runnableVelocity.run();
    }


    public void spawnFish() {
        fish = new ImageView(context);
        fish.setImageResource(type);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        fish.setLayoutParams(lp);
        rl.addView(fish);
    }

}
