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
    final Handler startSpawnFish = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rl = findViewById(R.id.rlGame);
        line = findViewById(R.id.fishingLine);

        startSpawnFish.postDelayed(new Runnable() {
            @Override
            public void run() {
                spawnFish();
            }
        }, 3000);

    }

    public void spawnFish() {

        //Add fish to layout
        ImageView fish = new ImageView(this);
        fish.setImageResource(R.drawable.fish1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        fish.setLayoutParams(lp);
        rl.addView(fish);

        fish.setX(300);
        fish.setY(300);

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
