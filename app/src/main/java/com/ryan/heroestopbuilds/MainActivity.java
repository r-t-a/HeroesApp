package com.ryan.heroestopbuilds;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
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
import java.util.concurrent.ExecutionException;

import static android.view.Gravity.CENTER;

/**
 * Activity with an ExpandableListView.  The ELV will hold the hero skills and by LongPressing the
 * user can call to hotslogs.com and get the popular skills.
 *
 * @author ryan
 */
public class MainActivity extends AppCompatActivity {

    PopupWindow popupWindow;
    ExpandableListView expandList;
    CustomExpandableAdapter customAdapt;
    HeroDatabase db = new HeroDatabase(this);
    private JSoupTalker talker = null;
    private ProgressDialog pd = null;
    private final String TAG = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandList = (ExpandableListView) findViewById(R.id.expandableList);
        ArrayList<Heroes> expand = heroList();
        customAdapt = new CustomExpandableAdapter(MainActivity.this, expand);
        expandList.setAdapter(customAdapt);
        setListener();
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
            popupWindow = showInfoPopup();
            popupWindow.showAtLocation(findViewById(R.id.expandableList), CENTER, 0, 15);
            return true;
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
     * Build the ExpandableListView,
     * Check the database to fill skill list on any heroes that have them.
     *
     * @return list for CustomAdapter
     */
    public ArrayList<Heroes> heroList() {
        ArrayList<Heroes> list = new ArrayList<>();
        for (int i = 0; i < Constants.HERO_NAMES.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(Constants.HERO_NAMES[i]);
            hero.setPortrait(Constants.PORTRAITS[i]);
            ArrayList<Skills> skillList = new ArrayList<>();
            Skills skills = new Skills();
            if(db.getSkills(Constants.HERO_NAMES[i]) == null) {
                skills.setName("Press & Hold Hero to Refresh");
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
     * Set LongClickListener that will tell the app to refresh a given hero's skills
     */
    public void setListener() {
        expandList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (talker == null && isNetworkAvailable()) {
                    String selection = "";
                    switch (position) {
                        case 0:
                            selection = Constants.HERO_NAMES[0];
                            break;
                        case 1:
                            selection = Constants.HERO_NAMES[1];
                            break;
                        case 2:
                            selection = Constants.HERO_NAMES[2];
                            break;
                        case 3:
                            selection = Constants.HERO_NAMES[3];
                            break;
                        case 4:
                            selection = Constants.HERO_NAMES[4];
                            break;
                        case 5:
                            selection = Constants.HERO_NAMES[5];
                            break;
                        case 6:
                            selection = Constants.HERO_NAMES[6];
                            break;
                        case 7:
                            selection = Constants.HERO_NAMES[7];
                            break;
                        case 8:
                            selection = Constants.HERO_NAMES[8];
                            break;
                        case 9:
                            selection = Constants.HERO_NAMES[9];
                            break;
                        case 10:
                            selection = Constants.HERO_NAMES[10];
                            break;
                        case 11:
                            selection = Constants.HERO_NAMES[11];
                            break;
                        case 12:
                            selection = Constants.HERO_NAMES[12];
                            break;
                        case 13:
                            selection = Constants.HERO_NAMES[13];
                            break;
                        case 14:
                            selection = Constants.HERO_NAMES[14];
                            break;
                        case 15:
                            selection = Constants.HERO_NAMES[15];
                            break;
                        case 16:
                            selection = Constants.HERO_NAMES[16];
                            break;
                        case 17:
                            selection = Constants.HERO_NAMES[17];
                            break;
                        case 18:
                            selection = Constants.HERO_NAMES[18];
                            break;
                        case 19:
                            selection = Constants.HERO_NAMES[19];
                            break;
                        case 20:
                            selection = Constants.HERO_NAMES[20];
                            break;
                        case 21:
                            selection = Constants.HERO_NAMES[21];
                            break;
                        case 22:
                            selection = Constants.HERO_NAMES[22];
                            break;
                        case 23:
                            selection = Constants.HERO_NAMES[23];
                            break;
                        case 24:
                            selection = Constants.HERO_NAMES[24];
                            break;
                        case 25:
                            selection = Constants.HERO_NAMES[25];
                            break;
                        case 26:
                            selection = Constants.HERO_NAMES[26];
                            break;
                        case 27:
                            selection = Constants.HERO_NAMES[27];
                            break;
                        case 28:
                            selection = Constants.HERO_NAMES[28];
                            break;
                        case 29:
                            selection = Constants.HERO_NAMES[29];
                            break;
                        case 30:
                            selection = Constants.HERO_NAMES[30];
                            break;
                        case 31:
                            selection = Constants.HERO_NAMES[31];
                            break;
                        case 32:
                            selection = Constants.HERO_NAMES[32];
                            break;
                        case 33:
                            selection = Constants.HERO_NAMES[33];
                            break;
                        case 34:
                            selection = Constants.HERO_NAMES[34];
                            break;
                        case 35:
                            selection = Constants.HERO_NAMES[35];
                            break;
                        case 36:
                            selection = Constants.HERO_NAMES[36];
                            break;
                        case 37:
                            selection = Constants.HERO_NAMES[37];
                            break;
                        case 38:
                            selection = Constants.HERO_NAMES[38];
                            break;
                        case 39:
                            selection = Constants.HERO_NAMES[39];
                            break;
                    }
                    try {
                        new JSoupTalker().execute(selection).get();
                        ArrayList<Heroes> expand = heroList();
                        customAdapt = new CustomExpandableAdapter(MainActivity.this, expand);
                        expandList.setAdapter(customAdapt);
                        customAdapt.notifyDataSetChanged();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
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
     * JSoup is used to get website data from hotslogs.com. This info will be thrown to the
     * Database once acquired to keep it up to date
     */
    private class JSoupTalker extends AsyncTask<String, Void, String> {

        String popularString = null;
        String convert = null;

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
            try {
                ArrayList<String> skillNames = new ArrayList<>();
                ArrayList<Integer> gamesInt = new ArrayList<>();
                ArrayList<String> popularSkills;
                String passed = params[0];
                doc = Jsoup.connect(Constants.URL + passed).maxBodySize(0).get();
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
                //Pretty print from html with a few annoying edge cases
                String format = popularSkills.toString()
                        .replace(",", "\n")
                        .replace("[", " ")
                        .replace("]", "")
                        .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2")
                        .replace("ofthe", " of the")
                        .replace("of", " of")
                        .replace("for", " for")
                        .replace("Likea", "Like a")
                        .replace("AShark", "A Shark")
                        .replace("Grav OBomb3000", "Grav O Bomb 3000");
                //double check in logcat we got the right skills
                Log.i(TAG, "Popular Skills" + format);
                List<String> storedSkills = db.getAllHeroes();
                //Update or add new
                if(storedSkills.contains(params[0])) {
                    db.updateHero(params[0], format);
                } else {
                    db.addHero(params[0], format);
                }
                return format;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "PostExecute");
            talker = null;
            pd.dismiss();
        }
    }
}
