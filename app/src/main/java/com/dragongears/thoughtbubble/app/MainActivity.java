package com.dragongears.thoughtbubble.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements OnClickListener {
    final Context context = this;

    SharedPreferences preferences;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);

        EditText et = (EditText) findViewById(R.id.editMessage);
        et.setText(preferences.getString("pref_thought_text", "Hello!"));

        et.setSelection(et.getText().length());

        if (isLandscape()) {
            goToBubbleActivity();
        }

        ImageButton btadd = (ImageButton)findViewById(R.id.btnAdd);
        btadd.setOnClickListener(this);

        lvItems = (ListView) findViewById(R.id.listView);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        try {
            JSONArray jsonArray2 = new JSONArray(preferences.getString("pref_thought_array", "[\"Hello!\",\"#@$%!\",\"WTF\",\"TGIF\"]"));
            for (int i = 0; i < jsonArray2.length(); i++) {
                items.add(jsonArray2.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText et = (EditText) findViewById(R.id.editMessage);
                et.setText(((TextView) view).getText().toString());
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long rowId) {
                removeItemFromList(position);
                return true;
            }
        });

        lvItems.setEmptyView(findViewById(R.id.emptyElement));
    }

    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setIcon(R.drawable.ic_launcher);
        alert.setTitle("Remove message");
        alert.setMessage("Do you want to remove this message from the list?");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                items.remove(deletePosition);
                itemsAdapter.notifyDataSetChanged();
                itemsAdapter.notifyDataSetInvalidated();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // just close
            }
        });


        alert.show();

    }

    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.editMessage);

        switch (v.getId()) {
            case R.id.btnAdd:
                if (et.getText().length() > 0) {
                    itemsAdapter.insert(et.getText().toString(), 0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();


        SharedPreferences.Editor preferencesEditor = preferences.edit();

        EditText et = (EditText) findViewById(R.id.editMessage);
        preferencesEditor.putString("pref_thought_text", et.getText().toString());

        JSONArray jsArray = new JSONArray(items);
        preferencesEditor.putString("pref_thought_array", jsArray.toString());

        preferencesEditor.apply();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        EditText et = (EditText) findViewById(R.id.editMessage);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

        goToBubbleActivity();
    }

    protected void goToBubbleActivity() {
        //Starting a new Intent
        Intent intent = new Intent(this, ThoughtActivity.class);

        EditText et = (EditText) findViewById(R.id.editMessage);
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

// TODO: Animate thought bubbles
// TODO: Thought symbols
// TODO: About
// TODO: Intents
