package com.ryan.heroestopbuilds;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.ArrayList;

import static com.ryan.heroestopbuilds.MainActivity.*;


/**
 * @author ryan
 */
public class AddBuildActivity extends AppCompatActivity {

    Spinner heroSpinner;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        heroSpinner = (Spinner) findViewById(R.id.hero_spinner);
        heroSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                heroSelection.values()));
        heroSpinner.setSelection(0);
        populateListView(heroSpinner.getSelectedItem().toString());
    }

    public ArrayList<String> populateListView(String selection) {
        ArrayList<String> skills = new ArrayList<>();
        String in = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("heroes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String bufferString = new String(buffer);

            //convert string to JSONArray
            JSONArray jsonArray = new JSONArray(bufferString);
            //parse an Object from a random index in the JSONArray
            JSONObject anObject = jsonArray.getJSONObject(0);
            System.out.println("JSON : " + anObject);  //should be Abathur
        } catch (IOException | JSONException e1) {
            e1.printStackTrace();
        }
        return skills;
    }

    public void saveBuild(View view) {

    }
}
