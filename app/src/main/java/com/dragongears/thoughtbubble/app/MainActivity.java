package com.dragongears.thoughtbubble.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    SharedPreferences preferences;
    Boolean showingThought = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                showInputView();
                break;
            case Surface.ROTATION_180:
                showInputView();
                break;
            case Surface.ROTATION_270:
                showThoughtView();
                break;
            case Surface.ROTATION_90:
                showThoughtView();
                break;
        }

    }

    @Override
    protected void onDestroy () {
        super.onDestroy();

        if (!showingThought) {
            EditText et = (EditText) findViewById(R.id.editText);
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putString("pref_thought_text", et.getText().toString());
            preferencesEditor.commit();
        }
    }

    private void showInputView() {
        showingThought = false;
        setContentView(R.layout.activity_main);
    }

    private void showThoughtView() {
        showingThought = true;
        setContentView(R.layout.activity_main_rotate);
        AutoResizeTextView artv = (AutoResizeTextView) findViewById(R.id.thoughtText);
        artv.setText(preferences.getString("pref_thought_text", "Default text"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
