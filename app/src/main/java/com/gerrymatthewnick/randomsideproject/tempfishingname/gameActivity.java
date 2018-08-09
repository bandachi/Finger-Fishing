package com.gerrymatthewnick.randomsideproject.tempfishingname;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class gameActivity extends AppCompatActivity {


    private int id;
    private RelativeLayout rl;
    public static ImageView line;
    public static TextView change;
    final Handler START_SPAWN_FISH = new Handler();
    Handler CHANGE_FISH_VELOCITY;
    Handler MOVE_FISH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rl = findViewById(R.id.rlGame);
        line = findViewById(R.id.fishingLine);
        change = findViewById(R.id.textView2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //Start fish spawning method after 3 seconds
        START_SPAWN_FISH.postDelayed(new Runnable() {
            @Override
            public void run() {
                CHANGE_FISH_VELOCITY = new Handler();
                MOVE_FISH = new Handler();

                id = getResources().getIdentifier("fish1" , "drawable", getPackageName());


                Fish fish = new Fish(id, 1000, 15, rl, getApplicationContext(), CHANGE_FISH_VELOCITY, MOVE_FISH);
                fish.spawnFish();
                fish.setX(300);
                fish.setY(300);

                fish.startChangeVelocity();
                fish.startVelocity();
            }
        }, 3000);

    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
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