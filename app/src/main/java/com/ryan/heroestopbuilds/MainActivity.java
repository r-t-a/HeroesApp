package com.ryan.heroestopbuilds;

import android.app.ProgressDialog;
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
 * after getting the recent build info from BootActivity
 * ActionBar Menu will have an info button and a refresh
 * option to be implemented later.
 *
 * @author ryan
 */
public class MainActivity extends AppCompatActivity {

    String hero_names[] = {"Abathur", "Anub'arak", "Arthas", "Azmodan", "Brightwing", "Chen",
            "Diablo", "E.T.C.", "Falstad", "Gazlowe", "Illidan", "Jaina",
            "Johanna", "Kaelthas", "Kerrigan", "Li Li", "Malfurion", "Muradin",
            "Murky", "Nazeebo", "Nova", "Raynor", "Rehgar", "Sgt. Hammer",
            "Sonya", "Stitches", "Sylvanas", "Tassadar", "The Butcher", "The Lost Vikings",
            "Thrall", "Tychus", "Tyrael", "Tyrande", "Uther", "Valla",
            "Zagara", "Zeratul"};

    PopupWindow popupWindow;
    ExpandableListView expandList;
    ArrayList<String> outList = new ArrayList<>();

    // Get response from web
    JSoupTalker talker = new JSoupTalker(new AsyncResponse() {
        @Override
        public void processFinish(ArrayList<String> output) {
            outList.addAll(output);
            System.out.println("OutList " + outList);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        talker.execute();
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
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Make the Expandable List and gather the current
     * popular build skills from hotslogs to populate the
     * children views.
     *
     * @return ArrayList for expandableList
     */
    public ArrayList<Heroes> setHeroes() {

        int portraits[] = {R.drawable.abathur, R.drawable.anubarak, R.drawable.arthas,
                           R.drawable.azmodan, R.drawable.brightwing, R.drawable.chen,
                           R.drawable.diablo,  R.drawable.elite_tauren_chieftain, R.drawable.falstad,
                           R.drawable.gazlowe, R.drawable.illidan, R.drawable.jaina,
                           R.drawable.johanna, R.drawable.kaelthas, R.drawable.kerrigan,
                           R.drawable.li_li,   R.drawable.malfurion, R.drawable.muradin,
                           R.drawable.murky,   R.drawable.nazeebo, R.drawable.nova,
                           R.drawable.raynor,  R.drawable.rehgar, R.drawable.sergeant_hammer,
                           R.drawable.sonya,   R.drawable.stitches, R.drawable.sylvanas,
                           R.drawable.tassadar,R.drawable.the_butcher,R.drawable.the_lost_vikings,
                           R.drawable.thrall,  R.drawable.tychus,  R.drawable.tyrael, R.drawable.tyrande,
                           R.drawable.uther,   R.drawable.valla, R.drawable.zagara,
                           R.drawable.zeratul};

        ArrayList<Heroes> list = new ArrayList<>();
        //populate the expandable list with the heroes
        for(int i = 0; i<hero_names.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(hero_names[i]);
            hero.setPortrait(portraits[i]);

            //set child group skills
            //possible refactoring could be done here but as it stands now,
            //it's easy to add any new Hero
            ArrayList<Skills> sk_list = new ArrayList<>();
            Skills skills = new Skills();
            switch (hero_names[i]) {
                case "Abathur":
                    skills.setName(outList.get(0));
                    sk_list.add(skills);
                    break;
                case "Anub'arak":
                    skills.setName(outList.get(1));
                    sk_list.add(skills);
                    break;
                case "Arthas":
                    skills.setName(outList.get(2));
                    sk_list.add(skills);
                    break;
                case "Azmodan":
                    skills.setName(outList.get(3));
                    sk_list.add(skills);
                    break;
                case "Brightwing":
                    skills.setName(outList.get(4));
                    sk_list.add(skills);
                    break;
                case "Chen":
                    skills.setName(outList.get(5));
                    sk_list.add(skills);
                    break;
                case "Diablo":
                    skills.setName(outList.get(6));
                    sk_list.add(skills);
                    break;
                case "E.T.C.":
                    skills.setName(outList.get(7));
                    sk_list.add(skills);
                    break;
                case "Falstad":
                    skills.setName(outList.get(8));
                    sk_list.add(skills);
                    break;
                case "Gazlowe":
                    skills.setName(outList.get(9));
                    sk_list.add(skills);
                    break;
                case "Illidan":
                    skills.setName(outList.get(10));
                    sk_list.add(skills);
                    break;
                case "Jaina":
                    skills.setName(outList.get(11));
                    sk_list.add(skills);
                    break;
                case "Johanna":
                    skills.setName(outList.get(12));
                    sk_list.add(skills);
                    break;
                case "Kaelthas":
                    skills.setName(outList.get(13));
                    sk_list.add(skills);
                    break;
                case "Kerrigan":
                    skills.setName(outList.get(14));
                    sk_list.add(skills);
                    break;
                case "Li Li":
                    skills.setName(outList.get(15));
                    sk_list.add(skills);
                    break;
                case "Malfurion":
                    skills.setName(outList.get(16));
                    sk_list.add(skills);
                    break;
                case "Muradin":
                    skills.setName(outList.get(17));
                    sk_list.add(skills);
                    break;
                case "Murky":
                    skills.setName(outList.get(18));
                    sk_list.add(skills);
                    break;
                case "Nazeebo":
                    skills.setName(outList.get(19));
                    sk_list.add(skills);
                    break;
                case "Nova":
                    skills.setName(outList.get(20));
                    sk_list.add(skills);
                    break;
                case "Rehgar":
                    skills.setName(outList.get(21));
                    sk_list.add(skills);
                    break;
                case "Raynor":
                    skills.setName(outList.get(22));
                    sk_list.add(skills);
                    break;
                case "Sgt. Hammer":
                    skills.setName(outList.get(23));
                    sk_list.add(skills);
                    break;
                case "Sonya":
                    skills.setName(outList.get(24));
                    sk_list.add(skills);
                    break;
                case "Stitches":
                    skills.setName(outList.get(25));
                    sk_list.add(skills);
                    break;
                case "Sylvanas":
                    skills.setName(outList.get(26));
                    sk_list.add(skills);
                    break;
                case "Tassadar":
                    skills.setName(outList.get(27));
                    sk_list.add(skills);
                    break;
                case "The Butcher":
                    skills.setName(outList.get(28));
                    sk_list.add(skills);
                    break;
                case "The Lost Vikings":
                    skills.setName(outList.get(29));
                    sk_list.add(skills);
                    break;
                case "Thrall":
                    skills.setName(outList.get(30));
                    sk_list.add(skills);
                    break;
                case "Tychus":
                    skills.setName(outList.get(31));
                    sk_list.add(skills);
                    break;
                case "Tyrael":
                    skills.setName(outList.get(32));
                    sk_list.add(skills);
                    break;
                case "Tyrande":
                    skills.setName(outList.get(33));
                    sk_list.add(skills);
                    break;
                case "Uther":
                    skills.setName(outList.get(34));
                    sk_list.add(skills);
                    break;
                case "Valla":
                    skills.setName(outList.get(35));
                    sk_list.add(skills);
                    break;
                case "Zagara":
                    skills.setName(outList.get(36));
                    sk_list.add(skills);
                    break;
                case "Zeratul":
                    skills.setName(outList.get(37));
                    sk_list.add(skills);
                    break;
                }
            hero.setSkills(sk_list);
            list.add(hero);
        }
        return list;
    }

    /**
     * Popup window for general information, accessed through
     * 3 dot menu
     *
     * @return popupWindow
     *
     * TODO: Add HOTS update version number or name ie. "Johanna Update"
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
     * JSoup will be used to get website data from hotslogs.com
     * This info will be thrown to the Database once acquired
     * to keep it up to date
     */
    private class JSoupTalker extends AsyncTask<Void, Void, ArrayList<String>> {

        private final String TAG = null;
        private AsyncResponse listener;
        ArrayList<String> passList = new ArrayList<>();
        String popularString = null;
        String convert = null;
        private ProgressDialog pd = null;

        public JSoupTalker(AsyncResponse listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("loading");
            pd.show();
            Log.i(TAG,"PreExecute");
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

                    doc = Jsoup.connect("https://www.hotslogs.com/Sitewide/HeroDetails?Hero=" + aHero)
                            .maxBodySize(0).get();
                    //get the table
                    Element table = doc.getElementsByTag("table").get(2);

                    //get the "Games Played" Rows
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        if (tds.size() > 10) {
                            //Grab all games played values
                            gamesPlayed.add(Integer.valueOf(tds.get(0).text()));
                            //get the largest games played value, this is the most popular
                            Integer popular = Collections.max(gamesPlayed);
                            popularString = popular.toString();
                            if (tds.get(0).text().equals(popularString)) {
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
                    popularSkills.remove(0);  //removing games played #
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
            //build the list and set the adapter with our custom one
            expandList = (ExpandableListView) findViewById(R.id.expandableList);
            ArrayList<Heroes> heroList = setHeroes();
            CustomExpandableAdapter customAdapt = new CustomExpandableAdapter(MainActivity.this, heroList);
            expandList.setAdapter(customAdapt);

            if (pd != null) {
                pd.dismiss();
            }
        }
    }
}
