package com.gerrymatthewnick.randomsideproject.FingerFishing;


import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.getScreenHeight;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.getScreenWidth;

public class Fish {

    private int currentFish;
    private int level;
    private int vel;
    private int fishType;
    private int change;
    private float velX;
    private float velY;

    private RelativeLayout relativeL;
    private Context current;
    private ImageView fish;

    private android.os.Handler changeVel;
    private android.os.Handler frames;


    public Fish(int fishType, int change, int vel, RelativeLayout relativeL, Context current, int currentFish, Handler changeVel, Handler frames, int level) {

        this.fishType = fishType;
        this.change = change;
        this.vel = vel;
        this.relativeL = relativeL;
        this.current = current;
        this.currentFish = currentFish;
        this.changeVel = changeVel;
        this.frames = frames;
        this.level = level;

    }

    public void setX(float x) {
        fish.setX(x);
    }
    public void setY(float y) {
        fish.setY(y);
    }

    //Every change, change the fish's direction and speed
    Runnable runnableChange = new Runnable() {
        @Override
        public void run() {

            velX = 0;
            velY = 0;
            velX = (float)(Math.random() * vel);
            velY = (float)(Math.random() * vel);

            if (level >= 2) {
                velX = (float)(Math.random() * (8 + (0.3 * level * level) - level)) + level;
                velY = (float)(Math.random() * (8 + (0.3 * level * level) - level)) + level;
            }

            int rand1 = (int)(Math.floor(Math.random() * 10) + 1);
            int rand2 = (int)(Math.floor(Math.random() * 10) + 1);

            //randomly make value velocity negative
            if (rand1 > 5) {
                velX = -velX;
                fish.setScaleX(1f);
            }
            else {
                fish.setScaleX(-1f);
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
            if (fish.getY() < getScreenHeight() / 3) {
                velY = Math.abs(velY);
            }
            if (fish.getY() > (getScreenHeight() - fish.getHeight())) {
                velY = -Math.abs(velY);
            }

            changeVel.postDelayed(runnableChange, change + (level * 200));
        }
    };

    public void startChangeVelocity() {
        runnableChange.run();
    }

    //handler to move the fish smoothly
    Runnable runnableVelocity = new Runnable() {
        @Override
        public void run() {
            fish.setX(fish.getX() + velX);
            fish.setY(fish.getY() + velY);

            if (fish.getX() < 0 || fish.getX() > (getScreenWidth() - fish.getWidth()) || fish.getY() < getScreenHeight() / 3 || fish.getY() > (getScreenHeight() - fish.getHeight())) {
                changeVel.removeCallbacks(runnableChange);
                runnableChange.run();
            }

            frames.postDelayed(runnableVelocity, 20);
        }
    };
    public void startVelocity() {
        runnableVelocity.run();
    }

    //add the fish to the layout
    public void spawnFish() {
        fish = new ImageView(current);
        fish.setImageResource(fishType);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        fish.setLayoutParams(lp);
        fish.setId(currentFish);
        relativeL.addView(fish);
    }
}