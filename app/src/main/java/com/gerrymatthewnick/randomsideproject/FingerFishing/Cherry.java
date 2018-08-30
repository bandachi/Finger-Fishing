package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_CHERRY_COUNT;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_HIGHSCORE;

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

    public void cherryEffect(Activity act, int scoreIncrease, Sound sound) {
        Item.removeItem(this.getImage(), rl);

        TextView score = act.findViewById(R.id.scoreDisplay);
        int temp = Integer.parseInt(score.getText().toString());
        temp += scoreIncrease;
        score.setText(Integer.toString(temp));
        sound.playCherryPickup();

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
    }

    public int getItemDelay() {
        return itemDelayRand;
    }
}
