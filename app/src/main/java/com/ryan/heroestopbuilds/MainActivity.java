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
    List<heroSelection> list = new ArrayList<>();
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
        List<heroSelection> heroes = heroList();
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

    public enum heroSelection {
        ABATHUR("Abathur", R.drawable.abathur),
        ANUBARAK("Anub'arak", R.drawable.anubarak),
        ARTANIS("Artanis", R.drawable.artanis),
        ARTHAS("Arthas", R.drawable.arthas),
        AZMODAN("Azmodan", R.drawable.azmodan),
        BRIGHTWING("Brightwing", R.drawable.brightwing),
        CHEN("Chen", R.drawable.chen),
        CHO("Cho", R.drawable.cho),
        DEHAKA("Dehaka", R.drawable.dehaka),
        DIABLO("Diablo", R.drawable.diablo),
        ETC("E.T.C", R.drawable.elite_tauren_chieftain),
        FALSTAD("Falstad", R.drawable.falstad),
        GALL("Gall", R.drawable.gall),
        GAZLOWE("Gazlowe", R.drawable.gazlowe),
        GREYMANE("Greymane", R.drawable.greymane),
        ILLIDAN("Illidan", R.drawable.illidan),
        JAINA("Jaina", R.drawable.jaina),
        JOHANNA("Johanna", R.drawable.johanna),
        KAELTHAS("Kael'thas", R.drawable.kaelthas),
        KERRIGAN("Kerrigan", R.drawable.kerrigan),
        KHARAZIM("Kharazim", R.drawable.kharazim),
        LEORIC("Leoric", R.drawable.leoric),
        LILI("Li Li", R.drawable.li_li),
        LIMING("Li-Ming", R.drawable.li_ming),
        LTMORALES("Lt. Morales", R.drawable.morales),
        LUNARA("Lunara", R.drawable.lunara),
        MALFURION("Malfurion", R.drawable.malfurion),
        MURADIN("Muradin", R.drawable.muradin),
        MURKY("Murky", R.drawable.murky),
        NAZEEBO("Nazeebo", R.drawable.nazeebo),
        NOVA("Nova", R.drawable.nova),
        RAYNOR("Raynor", R.drawable.raynor),
        REHGAR("Rehgar", R.drawable.rehgar),
        REXXAR("Rexxar", R.drawable.rexxar),
        SGTHAMMER("Sgt. Hammer", R.drawable.sergeant_hammer),
        SONYA("Sonya", R.drawable.sonya),
        STITCHES("Stitches", R.drawable.stitches),
        SYLVANAS("Sylvanas", R.drawable.sylvanas),
        TASSADAR("Tassadar", R.drawable.tassadar),
        THEBUTCHER("The Butcher", R.drawable.the_butcher),
        THELOSTVIKINGS("The Lost Vikings", R.drawable.the_lost_vikings),
        THRALL("Thrall", R.drawable.thrall),
        TRACER("Tracer", R.drawable.tracer),
        TYCHUS("Tychus", R.drawable.tychus),
        TYRAEL("Tyrael", R.drawable.tyrael),
        TYRANDE("Tyrande", R.drawable.tyrande),
        UTHER("Uther", R.drawable.uther),
        VALLA("Valla", R.drawable.valla),
        XUL("Xul", R.drawable.xul),
        ZAGARA("Zagara", R.drawable.zagara),
        ZERATUL("Zeratul", R.drawable.zeratul);

        private final String name;
        private final int portrait;
        heroSelection(String name, int portrait) {
            this.name = name;
            this.portrait = portrait;
        }
        public String getName() {return name;}
        public int getPortrait() {return portrait;}
    }

    /**
     * Build the ExpandableListView,
     * Check the database to fill skill list on any heroes that have them.
     *
     * @return list for CustomAdapter
     */
    public List<heroSelection> heroList() {
        list = Arrays.asList(heroSelection.values());
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
     * Pretty prints the skills returning from the list
     *
     * @param popularSkills takes the list to modify
     * @return final string for DB
     */
    public String prettyPrinter(ArrayList<String> popularSkills) {
        //add our final list to a new list to be passed to MainActivity
        String level1 =  "Level 1:     " + popularSkills.get(0);
        String level4 =  "Level 4:     " + popularSkills.get(1);
        String level7 =  "Level 7:     " + popularSkills.get(2);
        String level10 = "Level 10:   " + popularSkills.get(3);
        String level13 = "Level 13:   " + popularSkills.get(4);
        String level16 = "Level 16:   " + popularSkills.get(5);
        String level20 = "Level 20:  " + popularSkills.get(6);
        ArrayList<String> finalList = new ArrayList<>();
        finalList.add(level1);
        finalList.add(level4);
        finalList.add(level7);
        finalList.add(level10);
        finalList.add(level13);
        finalList.add(level16);
        finalList.add(level20);
        return finalList.toString()
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
                .replace("Sprayn", "Spray n")
                .replace("Withthe", "With the")
                .replace("20", " 20")
                .replace("G o forthe" , "Go for the")
                .replace("Thatsthe", "Thats the")
                .replace("Lambtothe", "Lamb to the")
                .replace("Onthe", "On the");
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
        for (String eval : skillNames) {
            if (eval.contains(popularString)) {
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
        if(popularSkills.get(1).matches("\\s")) {
            popularSkills.remove(0);  //remove games play #
            popularSkills.remove(0);  //remove whitespace
        } else {
            popularSkills.remove(0);  //remove games play #
            popularSkills.remove(0);  //removing win percent #
            popularSkills.remove(0);  //removing % sign
        }
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
            pd.setCancelable(false);
            pd.setMessage("Gathering Popular Builds");
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
                    for(heroSelection h: heroSelection.values()) {
                        String names = h.getName();
                        doc = Jsoup.connect(URL + names).maxBodySize(0).get();
                        format = getTableFromWeb(doc, popularString, convert);
                        // Double check in logcat we got the right skills
                        Log.i(TAG, names + ":   " + format);
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
            customAdapt.notifyDataSetChanged();
            talker = null;
            selection = null;
            pd.dismiss();
        }
    }
}
