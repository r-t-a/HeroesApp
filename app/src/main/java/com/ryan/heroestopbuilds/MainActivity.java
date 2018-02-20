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
import com.ryan.heroestopbuilds.Models.Heroes;
import com.ryan.heroestopbuilds.Preferences.InfoPreferenceActivity;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CallBackInterface, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    ExpandableListView expandList;
    CustomExpandableAdapter customAdapt;
    HeroDatabase db = new HeroDatabase(this);
    List<Heroes> list = new ArrayList<>();
    JSoupTalker talker = null;
    private final String TAG = null;
    String selection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.customAdapt = new CustomExpandableAdapter(this);
        setContentView(R.layout.activity_main);

        loadJson();
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

    @Override
    public void onRefreshButton(String s) {
        if(isNetworkAvailable() && talker == null) {
            new JSoupTalker().execute(s);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void expandAll() {
        int count = customAdapt.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandList.expandGroup(i);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public List<Heroes> buildList() {
        list = db.getAllHeroes();
        expandList = findViewById(R.id.expandableList);
        customAdapt = new CustomExpandableAdapter(MainActivity.this, list);
        expandList.setAdapter(customAdapt);
        return list;
    }

    private void loadJson() {
        Rest.INSTANCE.api().getAllHeroes().enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
                if(response.body() != null && response.isSuccessful()) {
                    for (Heroes heroes : response.body()){
                        Log.i("onResponse", heroes.toString());
                        if(!db.recordExists(heroes.getName())) {
                            db.addHero(heroes);
                        }
                    }
                }
                buildList();
            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public String onChildPress(String name) {
        String skills;
        skills = db.getSkills(name);
        if(skills == null) {
            return "Refresh to get skills";
        } else {
            return skills;
        }
    }

    public void checkDB(String passed, String format) {
        db.updateHeroSkills(passed, format);
    }

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

    private class JSoupTalker extends AsyncTask<String, String, String> {

        String popularString, convert, format = null;
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "PreExecute");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String...params) {
            Log.i(TAG, "InBackground");
            Document doc;
            String URL = "https://www.hotslogs.com/Sitewide/HeroDetails?Hero=";
            String passed = params[0];
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
