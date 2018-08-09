package com.gerrymatthewnick.randomsideproject.tempfishingname;


import android.content.Context;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.change;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.line;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.getScreenHeight;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.getScreenWidth;

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

    //Every changeFreq, change the fish's direction and speed
    Runnable runnableChange = new Runnable() {
        @Override
        public void run() {
            velX = 0;
            velY = 0;
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
            }
            try {
                velX = (float)(Math.random() * maxVel);
                velY = (float)(Math.random() * maxVel);

                int rand1 = (int)(Math.floor(Math.random() * 10)+ 1);
                int rand2 = (int)(Math.floor(Math.random() * 10)+ 1);

                //randomly make value velocity negative
                if (rand1 > 5) {
                    velX = -velX;
                }
                if (rand2 > 5) {
                    velY = -velY;
                }

                //check if fish is off the screen, if make the fish go back
                if (fish.getX() < 0) {
                    velX = Math.abs(velX);
                }
                if (fish.getX() > (getScreenWidth() - fish.getWidth())) {
                    velX = -Math.abs(velX);
                }
                if (fish.getY() < getScreenHeight()/3) {
                    velY = Math.abs(velY);
                }
                if (fish.getY() > (getScreenHeight() - fish.getHeight())) {
                    velY = -Math.abs(velY);
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

    //handler to move the fish smoothly
    Runnable runnableVelocity = new Runnable() {
        @Override
        public void run() {
            try {
                overlap(fish, line);


                fish.setX(fish.getX() + velX);
                fish.setY(fish.getY() + velY);

                if (fish.getX() < 0 || fish.getX() > (getScreenWidth() - fish.getWidth()) || fish.getY() < getScreenHeight()/3 || fish.getY() > (getScreenHeight() - fish.getHeight())) {
                    changeVel.removeCallbacks(runnableChange);
                    runnableChange.run();
                }
            }
            finally {
                frames.postDelayed(runnableVelocity, 50);
            }
        }
    };
    public void startVelocity() {
        runnableVelocity.run();
    }


    //add the fish to the layout
    public void spawnFish() {
        fish = new ImageView(context);
        fish.setImageResource(type);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        fish.setLayoutParams(lp);
        rl.addView(fish);
    }


    public void overlap(ImageView first, ImageView second) {
        Rect rc1 = new Rect();
        Rect rc2 = new Rect();

        first.getHitRect(rc1);
        second.getHitRect(rc2);

        rc2.top = rc2.bottom - 10;
        if (rc1.contains(rc2)) {
            change.setText("true");
        }
        else {
            change.setText("false");
        }
    }


}
