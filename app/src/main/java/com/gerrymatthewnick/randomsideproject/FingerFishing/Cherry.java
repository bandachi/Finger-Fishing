package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

public class Cherry extends Item {

    private final int ITEM_DELAY_MAX = 6000;
    private final int ITEM_DELAY_MIN = 2000;
    private final String ITEM_TYPE = "cherry";
    private final int ITEM_SIZE = 64;

    private int itemDelayRand;

    public Cherry(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCherry() {
        itemDelayRand = (int)(Math.random() * (ITEM_DELAY_MAX - ITEM_DELAY_MIN)) + ITEM_DELAY_MIN;
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE);
    }
    /*
    public void cherryEffect() {
        Item.removeItem(this.getImage(), rl);

        TextView score = act.findViewById(R.id.scoreDisplay);
        int temp = Integer.parseInt(score.getText().toString());
        temp += 100 * level;
        score.setText(Integer.toString(temp));
        mSoundPool.play(soundIdCherryPickup, 1, 1, 0, 0, 1);

        SharedPreferences cherryFile = act.getSharedPreferences(PREFERENCES_HIGHSCORE, MODE_PRIVATE);

        int highscore = cherryFile.getInt("highest", 0);

        if (temp > highscore) {
            SharedPreferences.Editor editor = cherryFile.edit();
            editor.putInt("highest", temp);
            editor.apply();
            TextView highScoreText = act.findViewById(R.id.highscoreDisplay);
            highScoreText.setText("Highscore: " + Integer.toString(temp));
        }

        SharedPreferences cherryCountFile = act.getSharedPreferences(PREFERENCES_CHERRY_COUNT, MODE_PRIVATE);
        int currentCherryCount = cherryCountFile.getInt("cherries", 0);
        SharedPreferences.Editor editorStats = cherryCountFile.edit();

        editorStats.putInt("cherries", currentCherryCount + 1);
        editorStats.apply();
    }*/

    public int getItemDelay() {
        return itemDelayRand;
    }
}
