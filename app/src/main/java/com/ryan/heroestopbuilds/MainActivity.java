package com.ryan.heroestopbuilds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

import static android.view.Gravity.CENTER;

/**
 * Simple activity that will handle the ExpandableListView
 * after getting the recent skills from AsyncTask, this
 * will be done once and auto update every week or so (still
 * deciding on that)
 *
 * @author ryan
 */
public class MainActivity extends AppCompatActivity {

    String hero_names[] = {"Abathur", "Anub'arak", "Arthas", "Azmodan", "Brightwing", "Chen",
            "Diablo", "E.T.C.", "Falstad", "Gazlowe", "Illidan", "Jaina",
            "Johanna", "Kaelthas", "Kerrigan", "Leoric", "Li Li", "Malfurion", "Muradin",
            "Murky", "Nazeebo", "Nova", "Raynor", "Rehgar", "Sgt. Hammer",
            "Sonya", "Stitches", "Sylvanas", "Tassadar", "The Butcher", "The Lost Vikings",
            "Thrall", "Tychus", "Tyrael", "Tyrande", "Uther", "Valla",
            "Zagara", "Zeratul"};

    int portraits[] = {R.drawable.abathur, R.drawable.anubarak, R.drawable.arthas,
            R.drawable.azmodan, R.drawable.brightwing, R.drawable.chen,
            R.drawable.diablo, R.drawable.elite_tauren_chieftain, R.drawable.falstad,
            R.drawable.gazlowe, R.drawable.illidan, R.drawable.jaina,
            R.drawable.johanna, R.drawable.kaelthas, R.drawable.kerrigan, R.drawable.leoric,
            R.drawable.li_li, R.drawable.malfurion, R.drawable.muradin,
            R.drawable.murky, R.drawable.nazeebo, R.drawable.nova,
            R.drawable.raynor, R.drawable.rehgar, R.drawable.sergeant_hammer,
            R.drawable.sonya, R.drawable.stitches, R.drawable.sylvanas,
            R.drawable.tassadar, R.drawable.the_butcher, R.drawable.the_lost_vikings,
            R.drawable.thrall, R.drawable.tychus, R.drawable.tyrael, R.drawable.tyrande,
            R.drawable.uther, R.drawable.valla, R.drawable.zagara,
            R.drawable.zeratul};

    PopupWindow popupWindow;
    ExpandableListView expandList;
    ArrayList<String> outList = new ArrayList<>();
    HeroDatabase db = new HeroDatabase(this);
    private JSoupTalker talker = null;
    private ProgressDialog pd = null;
    private final String TAG = null;
    String internetWarning = "No Internet Connection Detected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = this.getSharedPreferences("appInfo", 0);
        boolean firstTime = settings.getBoolean("first_time", true);
        if (firstTime) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("first_time", false);
            editor.apply();
            if(talker == null && isNetworkAvailable()) {
                // Get response from web
                JSoupTalker talker = new JSoupTalker(new AsyncResponse() {
                    @Override
                    public void processFinish(ArrayList<String> output) {
                        outList.addAll(output);
                    }
                });
                talker.execute();
            } else {
                Toast toast = Toast.makeText(this,internetWarning, Toast.LENGTH_LONG);
                toast.show();
                expandList = (ExpandableListView) findViewById(R.id.expandableList);
                ArrayList<Heroes> offlineList = offlineTempList();
                CustomExpandableAdapter customAdapt = new CustomExpandableAdapter(MainActivity.this, offlineList);
                expandList.setAdapter(customAdapt);
            }
        } else {
            expandList = (ExpandableListView) findViewById(R.id.expandableList);
            ArrayList<Heroes> initList = setHeroes();
            CustomExpandableAdapter customAdapt = new CustomExpandableAdapter(MainActivity.this, initList);
            expandList.setAdapter(customAdapt);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            popupWindow = showInfoPopup();
            popupWindow.showAtLocation(findViewById(R.id.expandableList), CENTER, 0, 15);
            return true;
        } if(id == R.id.action_refresh) {
            if(!isNetworkAvailable()) {
                Toast toast = Toast.makeText(this,internetWarning, Toast.LENGTH_LONG);
                toast.show();
            } else {
                this.deleteDatabase("heroes");
                if (talker == null) {
                    // Get response from web
                    JSoupTalker talker = new JSoupTalker(new AsyncResponse() {
                        @Override
                        public void processFinish(ArrayList<String> output) {
                            outList.addAll(output);
                        }
                    });
                    talker.execute();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Make sure the device is connected when refreshing/initial populating
     *
     * @return activeNetwork
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * Purely for visual, so if offline on initial load the user doesn't just
     * see a blank screen, at least populate the hero list since it's
     * hard coded.
     *
     * @return offline list (error with network or disconnect)
     */
    public ArrayList<Heroes> offlineTempList() {
        ArrayList<Heroes> list = new ArrayList<>();
        //populate the expandable list with the heroes
        for (int i = 0; i < hero_names.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(hero_names[i]);
            hero.setPortrait(portraits[i]);
            ArrayList<Skills> skillList = new ArrayList<>();
            Skills skills = new Skills();
            skills.setName("Looks like we didn't get the skills,\n" +
                           "check your internet connection\n" +
                           "and try refreshing");
            skillList.add(skills);
            hero.setSkills(skillList);
            list.add(hero);
        }
        return list;
    }

    /**
     * Make the Expandable List and gather the current
     * popular build skills from hotslogs to populate the
     * children views.
     *
     * @return ArrayList for expandableList
     */
    public ArrayList<Heroes> setHeroes() {

        ArrayList<Heroes> list = new ArrayList<>();
        //populate the expandable list with the heroes
        for (int i = 0; i < hero_names.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(hero_names[i]);
            hero.setPortrait(portraits[i]);

            //set child group skills
            //possible refactoring could be done here, but as it stands now,
            //it's easy to add any new Hero that comes along
            ArrayList<Skills> skillList = new ArrayList<>();
            Skills skills = new Skills();

            List<String> storedSkills = db.getAllHeroes();
            if(storedSkills.size() == 0) {
                Toast toast = Toast.makeText(this, internetWarning, Toast.LENGTH_LONG);
                toast.show();
                Log.e(TAG, "Disconnection error, refreshing skills");
            } else {
                switch (hero_names[i]) {
                    case "Abathur":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Anub'arak":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Arthas":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Azmodan":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Brightwing":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Chen":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Diablo":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "E.T.C.":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Falstad":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Gazlowe":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Illidan":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Jaina":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Johanna":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Kaelthas":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Kerrigan":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Leoric":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Li Li":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Malfurion":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Muradin":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Murky":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Nazeebo":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Nova":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Rehgar":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Raynor":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Sgt. Hammer":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Sonya":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Stitches":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Sylvanas":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Tassadar":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "The Butcher":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "The Lost Vikings":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Thrall":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Tychus":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Tyrael":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Tyrande":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Uther":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Valla":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Zagara":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                    case "Zeratul":
                        skills.setName(storedSkills.get(i));
                        skillList.add(skills);
                        break;
                }
                hero.setSkills(skillList);
                list.add(hero);
            }
        }
        return list;
    }

    /**
     * Popup window for general information, accessed through
     * 3 dot menu
     *
     * @return popupWindow
     */
    public PopupWindow showInfoPopup() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.info_popup, new LinearLayout(getBaseContext()), false);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button btnDismiss = (Button)popupView.findViewById(R.id.buttonOk);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // dismiss dialog window
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    /**
     * JSoup is used to get website data from hotslogs.com
     * This info will be thrown to the Database once acquired
     * to keep it up to date
     */
    private class JSoupTalker extends AsyncTask<Void, Void, ArrayList<String>> {

        private AsyncResponse listener;
        ArrayList<String> passList = new ArrayList<>();
        String popularString = null;
        String convert = null;
        String url = "https://www.hotslogs.com/Sitewide/HeroDetails?Hero=";

        public JSoupTalker(AsyncResponse listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "PreExecute");
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Gathering Popular Builds");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Void...params) {
            Log.i(TAG, "InBackground");
            Document doc;
            try {
                for (String aHero: hero_names) {
                    ArrayList<Integer> gamesPlayed = new ArrayList<>();
                    ArrayList<String> skillNames = new ArrayList<>();
                    ArrayList<String> popularSkills;

                    doc = Jsoup.connect(url + aHero).maxBodySize(0).get();

                    //get the table
                    Element table = doc.getElementsByTag("table").get(2);
                    for (Element row : table.select("tr")) {
                        Elements cols = row.select("td");
                        if(cols.size() > 10) {
                            gamesPlayed.add(Integer.valueOf(cols.get(0).text()));
                            Integer popular = Collections.max(gamesPlayed);

                            //************* in case of emergency, break glass ****************************
                            //************* meaning, swap out this with integer code *********************
                            //************* meaning, hotslogs changed their table code :( ****************
                            //************* meaning, rip me **********************************************
                            //************* refactor to account for these changes ************************
                            //
                            //String values = cols.get(0).text().replaceAll("%", "").trim();
                            //gamesPlayed.add(Float.valueOf(values));
                            //get the largest games played value, this is the most popular
                            //Float popular = Collections.max(gamesPlayed);
                            //****************************************************************************

                            popularString = popular.toString();
                            if (cols.get(0).text().equals(popularString)) {
                                //add to new array
                                skillNames.add(row.text());
                            }
                        }
                    }
                    //if there's a row of skills with the most popular number, snatch it
                    for (String eval : skillNames) {
                        if (eval.contains(popularString)) {
                            convert = eval;
                        }
                    }
                    //split that long string up and put it into a list
                    popularSkills = new ArrayList<>(Arrays.asList(convert.split(" ")));
                    popularSkills.remove(0);  //remove games play #
                    popularSkills.remove(0);  //removing win percent #
                    popularSkills.remove(0);  //removing % sign
                    //add our final list to a new list to be passed to MainActivity
                    passList.add(String.valueOf(popularSkills));
                }
                    //double check in logcat we got the right skills
                    listener.processFinish(passList);
                    Log.i(TAG, "Popular Skills" + passList);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            Log.i(TAG, "PostExecute");

            for(int i = 0; i < hero_names.length; i++) {
                String format = outList.get(i)
                        .replace(",", "\n")
                        .replace("[", " ")
                        .replace("]", "");
                db.addHero(new StoredSkills(format));
            }

            //build the list and set the adapter with our custom one
            expandList = (ExpandableListView) findViewById(R.id.expandableList);
            ArrayList<Heroes> heroList = setHeroes();
            CustomExpandableAdapter customAdapt = new CustomExpandableAdapter(MainActivity.this, heroList);
            expandList.setAdapter(customAdapt);
            talker = null;
            pd.dismiss();
        }
    }
}
