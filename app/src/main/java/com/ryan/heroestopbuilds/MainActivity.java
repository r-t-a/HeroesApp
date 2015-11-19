package com.ryan.heroestopbuilds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Activity with an ExpandableListView.  The ELV will hold the hero skills and by LongPressing the
 * user can call to hotslogs.com and get the popular skills.
 *
 * @author ryan
 */
public class MainActivity extends AppCompatActivity implements CallBackInterface {

    ExpandableListView expandList;
    CustomExpandableAdapter customAdapt;
    HeroDatabase db = new HeroDatabase(this);
    ArrayList<Heroes> list = new ArrayList<>();
    JSoupTalker talker = null;
    private ProgressDialog pd = null;
    private final String TAG = null;
    String selection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.customAdapt = new CustomExpandableAdapter(this);
        setContentView(R.layout.activity_main);
        expandList = (ExpandableListView) findViewById(R.id.expandableList);
        ArrayList<Heroes> expand = heroList();
        customAdapt = new CustomExpandableAdapter(MainActivity.this, expand);
        expandList.setAdapter(customAdapt);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //make sure to check for active ui stuff that we need to close
        if(pd != null) {
            pd.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,InfoPreferenceActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_refresh) {
            if(!isNetworkAvailable()) {
                Toast toast = Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG);
                toast.show();
            }
            onRefreshButton("all");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Make sure the device is connected when refreshing/initial populating
     *
     * @return activeNetwork
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * Build the ExpandableListView,
     * Check the database to fill skill list on any heroes that have them.
     *
     * @return list for CustomAdapter
     */
    public ArrayList<Heroes> heroList() {
        list = new ArrayList<>();
        for (int i = 0; i < Constants.HERO_NAMES.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(Constants.HERO_NAMES[i]);
            hero.setPortrait(Constants.PORTRAITS[i]);
            ArrayList<Skills> skillList = new ArrayList<>();
            Skills skills = new Skills();
            if(db.getSkills(Constants.HERO_NAMES[i]) == null) {
                skills.setName("Refresh to get skills");
                skillList.add(skills);
            } else {
                skills.setName(db.getSkills(Constants.HERO_NAMES[i]));
                skillList.add(skills);
            }
            hero.setSkills(skillList);
            list.add(hero);
        }
        return list;
    }

    /**
     * Wrapper for calling to JSoup execution from Adapter using the interface
     *
     * @param s selection of hero to refresh
     */
    @Override
    public void onRefreshButton(String s) {
        if(isNetworkAvailable() && talker == null) {
            new JSoupTalker().execute(s);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Pretty prints the skills returning from the list
     *
     * @param popularSkills takes the list to modify
     * @return final string for DB
     */
    public String prettyPrinter(ArrayList<String> popularSkills) {
        //add our final list to a new list to be passed to MainActivity
        return popularSkills.toString()
                .replace(",", "\n")
                .replace("[", " ")
                .replace("]", "")
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2")
                .replace("ofthe", " of the")
                .replace("of", " of")
                .replace("for", " for")
                .replace("Likea", "Like a")
                .replace("AShark", "A Shark")
                .replace("Isa", "Is a")
                .replace("Grav OBomb3000", "Grav O Bomb 3000")
                .replace("1000", " 1000 ")
                .replace("Sprayn", "Spray n'")
                .replace("Withthe", "With the")
                .replace("20", " 20");
    }

    /**
     * Check the database for skill updates
     *
     * @param passed hero being passed from AsyncTask
     * @param format throw formatted skill in DB
     */
    public void checkDB(String passed, String format) {
        List<String> storedSkills = db.getAllHeroes();
        //Update or add new
        if (storedSkills.contains(passed)) {
            db.updateHero(passed, format);
        } else {
            db.addHero(passed, format);
        }
    }

    /**
     * Get table data from web and get the popular skill list from hotslogs
     *
     * @param doc Document from URL
     * @param popularString the popular skill list from web table
     * @param convert string of popular string
     * @return the formatted DB string
     */
    public String getTableFromWeb(Document doc, String popularString, String convert) {
        ArrayList<String> skillNames = new ArrayList<>();
        ArrayList<Integer> gamesInt = new ArrayList<>();
        ArrayList<String> popularSkills;
        //get the table
        Element table = doc.getElementsByTag("table").get(2);
        for (Element row : table.select("tr")) {
            Elements cols = row.select("td");
            if(cols.size() > 10) {
                gamesInt.add(Integer.valueOf(cols.get(0).text()));
                Integer popular = Collections.max(gamesInt);
                popularString = popular.toString();
                if (cols.get(0).text().equals(popularString)) {
                    //add to new array
                    skillNames.add(row.text());
                }
            }
        }
        // If there's a row of skills with the most popular number, snatch it
        for (String eval : skillNames) {
            if (eval.contains(popularString)) {
                convert = eval;
            }
        }
        // Split that long string up and put it into a list
        popularSkills = new ArrayList<>(Arrays.asList(convert.split(" ")));
        popularSkills.remove(0);  //remove games play #
        popularSkills.remove(0);  //removing win percent #
        popularSkills.remove(0);  //removing % sign
        // Pretty print
        return prettyPrinter(popularSkills);
    }

    /**
     * JSoup is used to get website data from hotslogs.com. This info will be thrown to the
     * Database once acquired to keep it up to date
     */
    public class JSoupTalker extends AsyncTask<String, Void, String> {

        String popularString, convert, format = null;

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "PreExecute");
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Gathering Popular Builds");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String...params) {
            Log.i(TAG, "InBackground");
            Document doc;
            String passed = params[0];

            if (passed.equals("Anub'arak")) {
                passed = "Anub%27arak";
            } else if(passed.equals("Kael'thas")) {
                passed = "Kael%27thas";
            }

            if(passed.equals("all")) {
                try {
                    for (String aHero: Constants.HERO_NAMES) {
                        if (aHero.equals("Anub'arak")) {
                            aHero = "Anub%27arak";
                        } else if(aHero.equals("Kael'thas")) {
                            aHero = "Kael%27thas";
                        }
                        doc = Jsoup.connect(Constants.URL + aHero).maxBodySize(0).get();
                        format = getTableFromWeb(doc, popularString, convert);
                        // Double check in logcat we got the right skills
                        Log.i(TAG, aHero + ":   " + format);
                        checkDB(aHero,format);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    doc = Jsoup.connect(Constants.URL + passed).maxBodySize(0).get();
                    format = getTableFromWeb(doc, popularString, convert);
                    // Double check in logcat we got the right skills
                    Log.i(TAG, passed + ":   " + format);
                    checkDB(passed, format);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "PostExecute");
            ArrayList<Heroes> expand = heroList();
            customAdapt = new CustomExpandableAdapter(MainActivity.this, expand);
            expandList.setAdapter(customAdapt);
            talker = null;
            selection = null;
            pd.dismiss();
        }
    }
}
