package com.gerrymatthewnick.randomsideproject.tempfishingname;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class gameActivity extends AppCompatActivity {


    private RelativeLayout rl;
    private ImageView line;
    final Handler START_SPAWN_FISH = new Handler();
    final Handler CHANGE_FISH_VELOCITY = new Handler();
    final Handler FISH_VELOCITY = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rl = findViewById(R.id.rlGame);
        line = findViewById(R.id.fishingLine);

        //Start fish spawning method after 3 seconds
        START_SPAWN_FISH.postDelayed(new Runnable() {
            @Override
            public void run() {
                //spawnFish();
                int id = getResources().getIdentifier("fish1" , "drawable", getPackageName());
                Fish fish = new Fish(id, 1000, rl, getApplicationContext());
                fish.spawnFish();
                fish.setX(300);
                fish.setY(300);
            }
        }, 3000);

    }

    //repeats in CHANGE intervals, updating moveFish();
    public class changeVelocity implements Runnable {

        private ImageView fish;
        final int CHANGE = 1000;
        public changeVelocity(ImageView img) {
            this.fish = img;
        }

        @Override
        public void run() {
            try {
                moveFish(fish);
            } finally {
                CHANGE_FISH_VELOCITY.postDelayed(this, CHANGE);
            }
        }
    }

    public void spawnFish() {

        //Add fish to layout
        final ImageView fish = new ImageView(this);
        fish.setImageResource(R.drawable.fish1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        fish.setLayoutParams(lp);
        rl.addView(fish);

        fish.setX(300);
        fish.setY(300);

        //change fish's velocity
        changeVelocity obj = new changeVelocity(fish);
        obj.run();

    }


    //set direction and speed of fish
    public void moveFish(ImageView img) {
        int velocityX = (int)(Math.random() * 4);
        int velocityY = (int)(Math.random() * 5);

        img.setX(velocityX);
        img.setY(velocityY);
    }
    //keep fishing line image on screen touch
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                line.setX(x);
                line.setY(y);
                break;

            case MotionEvent.ACTION_MOVE:
                line.setX(x);
                line.setY(y);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return false;
    }




}