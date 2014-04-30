package com.dragongears.thoughtbubble.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);

        EditText et = (EditText) findViewById(R.id.editText);
        et.setText(preferences.getString("pref_thought_text", "Hello!"));

        et.setSelection(et.getText().length());

        if (isLandscape()) {
            goToBubbleActivity();
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();

        EditText et = (EditText) findViewById(R.id.editText);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("pref_thought_text", et.getText().toString());
        preferencesEditor.apply();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        EditText et = (EditText) findViewById(R.id.editText);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

        goToBubbleActivity();
    }

    protected void goToBubbleActivity() {
        //Starting a new Intent
        Intent intent = new Intent(this, ThoughtActivity.class);

        EditText et = (EditText) findViewById(R.id.editText);
        //Sending data to another Activity
        intent.putExtra("message", et.getText().toString());

        startActivity(intent);
        finish();
    }

    protected boolean isLandscape() {
        return (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
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
