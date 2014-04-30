package com.dragongears.thoughtbubble.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class ThoughtActivity extends ActionBarActivity {

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
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if ((getWindowManager().getDefaultDisplay().getRotation() & 2) == 0) {
            setContentView(R.layout.activity_thought_left);
        } else {
            setContentView(R.layout.activity_thought_right);
        }

        Intent i = getIntent();
        // Receiving the Data
        String message = i.getStringExtra("message");

        // Displaying Received data
        AutoResizeTextView artv = (AutoResizeTextView) findViewById(R.id.thoughtText);
        artv.setText(message);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Starting a new Intent
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
