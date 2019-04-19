package com.dragongears.thoughtbubble.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ThoughtActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= 16 ) {
            uiOptions = uiOptions
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
        decorView.setSystemUiVisibility(uiOptions);

        // Figure out if the device is rotated left or right
        if ((getWindowManager().getDefaultDisplay().getRotation() & 2) == 0) {
            setContentView(R.layout.activity_thought_left);
        } else {
            setContentView(R.layout.activity_thought_right);
        }

        // Display the message from the main activity
        Intent i = getIntent();
        String message = i.getStringExtra("message");
        AutoResizeTextView artv = (AutoResizeTextView) findViewById(R.id.thoughtText);
        artv.setText(message);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

            ImageView smallBubble = (ImageView) findViewById(R.id.smallBubble);
            Animation fadeInAnimationSmall = AnimationUtils.loadAnimation(this, R.anim.bubble_fade_in);
            fadeInAnimationSmall.setFillAfter(true);
            smallBubble.startAnimation(fadeInAnimationSmall);

            ImageView largeBubble = (ImageView) findViewById(R.id.largeBubble);
            Animation fadeInAnimationLarge = AnimationUtils.loadAnimation(this, R.anim.bubble_fade_in);
            fadeInAnimationLarge.setStartOffset(150);
            fadeInAnimationLarge.setFillAfter(true);
            largeBubble.setAnimation(fadeInAnimationLarge);

            AutoResizeTextView textBubble = (AutoResizeTextView) findViewById(R.id.thoughtText);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.bubble_fade_in);
            fadeInAnimation.setStartOffset(300);
            fadeInAnimation.setFillAfter(true);
            textBubble.setAnimation(fadeInAnimation);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Go back to the main activity when the device is rotated
        //Starting a new Intent
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
