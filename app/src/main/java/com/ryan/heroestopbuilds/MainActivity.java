package com.ryan.heroestopbuilds;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ryan.heroestopbuilds.Adapters.CustomExpandableAdapter;
import com.ryan.heroestopbuilds.Database.HeroDatabase;
import com.ryan.heroestopbuilds.Interface.CallBackInterface;
import com.ryan.heroestopbuilds.Preferences.InfoPreferenceActivity;
import com.ryan.heroestopbuilds.Utilities.HeroSelection;
import com.ryan.heroestopbuilds.Utilities.TalentFormatter;

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
 * Activity with an ExpandableListView.  The ELV will hold the hero skills and by pressing the
 * refresh button the user can call to hotslogs.com and get the popular skills.
 *
 * @author ryan
 */
public class MainActivity extends AppCompatActivity implements CallBackInterface,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    ExpandableListView expandList;
    CustomExpandableAdapter customAdapt;
    HeroDatabase db = new HeroDatabase(this);
    List<HeroSelection> list = new ArrayList<>();
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
        List<HeroSelection> heroes = heroList();
        customAdapt = new CustomExpandableAdapter(MainActivity.this, heroes);
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
        //Enable searching
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(getApplicationContext(),InfoPreferenceActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onClose() {
        customAdapt.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        customAdapt.filterData(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        customAdapt.filterData(query);
        expandAll();
        return false;
    }

    /**
     * Expand the ExpandableListView when called
     */
    private void expandAll() {
        int count = customAdapt.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandList.expandGroup(i);
        }
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
    public List<HeroSelection> heroList() {
        list = Arrays.asList(HeroSelection.values());
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
     * In case there are no popular skills on hotslogs
     *
     * @param name hero being passed in
     * @return nothing if skills not found, else do like normal
     */
    public String onChildPress(String name) {
        String skills;
        skills = db.getSkills(name);
        if(skills == null) {
            return "Refresh to get skills";
        } else {
            return skills;
        }
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
                String formatInt = cols.get(0).text().replace(",","");
                gamesInt.add(Integer.valueOf(formatInt));
                Integer popular = Collections.max(gamesInt);
                popularString = popular.toString();
                if (formatInt.equals(popularString)) {
                    //add to new array
                    skillNames.add(row.text());
                }
            }
        }
        for (String eval : skillNames) {
            String save = eval.replace(",","");
            if (save.contains(popularString)) {
                convert = eval;
            }
        }
        if(convert == null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), R.string.no_builds, Toast.LENGTH_SHORT).show();
                }
            });
            convert = "Refresh to get skills";
            return convert;
        }
        // Split that long string up and put it into a list
        popularSkills = new ArrayList<>(Arrays.asList(convert.split(" ")));
        popularSkills.remove(0);  //remove games play #
        popularSkills.remove(0);  //remove whitespace
        if(popularSkills.get(0).equals("%")) {
            popularSkills.remove(0);  // remove % sign if still present
        }
        // Pretty print
        return TalentFormatter.prettyPrinter(popularSkills);
    }

    /**
     * JSoup is used to get website data from hotslogs.com. This info will be thrown to the
     * Database once acquired to keep it up to date
     */
    public class JSoupTalker extends AsyncTask<String, String, String> {

        String popularString, convert, format = null;

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "PreExecute");
            pd = new ProgressDialog(MainActivity.this);
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            //pd.setMessage("Gathering Popular Builds");
            pd.show();
        }

        @Override
        protected String doInBackground(String...params) {
            Log.i(TAG, "InBackground");
            Document doc;
            String URL = "https://www.hotslogs.com/Sitewide/HeroDetails?Hero=";
            String passed = params[0];
            if(passed.equals("all")) {
                try {
                    for(HeroSelection h: HeroSelection.values()) {
                        String names = h.getName();
                        publishProgress("Gathering Popular Builds " + names);
                        doc = Jsoup.connect(URL + names).maxBodySize(0).get();
                        format = getTableFromWeb(doc, popularString, convert);
                        // Double check in logcat we got the right skills
                        //Log.i(TAG, names + ":   " + format);
                        checkDB(names,format);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    pd.setMessage("Gathering Popular Builds " + passed);
                    doc = Jsoup.connect(URL + passed).maxBodySize(0).get();
                    format = getTableFromWeb(doc, popularString, convert);
                    // Double check in logcat we got the right skills
                    //Log.i(TAG, passed + ":   " + format);
                    checkDB(passed, format);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public void onProgressUpdate(String... args){
            pd.setMessage(args[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "PostExecute");
            customAdapt.notifyDataSetChanged();
            talker = null;
            selection = null;
            pd.dismiss();
        }
    }
}
