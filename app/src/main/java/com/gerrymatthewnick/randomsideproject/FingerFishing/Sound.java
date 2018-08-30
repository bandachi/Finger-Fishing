package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_SOUND;

public class Sound {

    public SoundPool mSoundPool;
    private int soundIdWormPickup;
    private int soundIdCherryPickup;
    private int soundIdWin;
    private boolean soundOption;

    private Activity act;

    public Sound(Activity act) {
        this.act = act;
    }

    //initialize sound and assign sound ids for later use
    public void initSound() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        SharedPreferences settingsSound = act.getSharedPreferences(PREFERENCES_SOUND, MODE_PRIVATE);
        soundOption = settingsSound.getBoolean("soundOption", true);
        if (soundOption) {
            soundIdCherryPickup = mSoundPool.load(act, R.raw.cherry_pickup, 1);
            soundIdWormPickup = mSoundPool.load(act, R.raw.worm_pickup, 1);
            soundIdWin = mSoundPool.load(act, R.raw.win_sound, 1);
        }
    }

    public void playWin() {
        mSoundPool.play(soundIdWin, 1, 1, 0, 0, 1);
    }

    public void playWormPickup() {
        mSoundPool.play(soundIdWormPickup, 1, 1, 0, 0, 1);
    }

    public void playCherryPickup() {
        mSoundPool.play(soundIdCherryPickup, 1, 1, 0, 0, 1);
    }
}
