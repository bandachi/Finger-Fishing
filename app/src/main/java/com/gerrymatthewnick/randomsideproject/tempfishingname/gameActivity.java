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
    Handler CHANGE_FISH_VELOCITY;

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
                CHANGE_FISH_VELOCITY = new Handler();

                int id = getResources().getIdentifier("fish1" , "drawable", getPackageName());


                Fish fish = new Fish(id, 1000, 10, rl, getApplicationContext(), CHANGE_FISH_VELOCITY);
                fish.spawnFish();
                fish.setX(300);
                fish.setY(300);

                fish.startChangeVelocity();
            }
        }, 3000);

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