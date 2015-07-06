package com.ryan.heroestopbuilds;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static android.view.Gravity.CENTER;

/**
 * Simple activity that will handle the ExpandableListView
 * after getting the recent build info from BootActivity
 * ActionBar Menu will have an info button and a refresh
 * option to be implemented later
 *
 * Created by ryan on 6/18/15
 */
public class MainActivity extends ActionBarActivity implements AsyncResponse {

    PopupWindow popupWindow;
    JSoupTalker talker = new JSoupTalker(this);
    ExpandableListView expandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            talker.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        expandList = (ExpandableListView) findViewById(R.id.expandableList);
        //build the list and set the adapter with our custom one
        ArrayList<Heroes> heroList = setHeroes();
        CustomExpandableAdapter customAdapt = new CustomExpandableAdapter(MainActivity.this, heroList);
        expandList.setAdapter(customAdapt);
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
     * Needed for JSoup class
     *
     * @param output from interface
     */
    @Override
    public void processFinish(ArrayList<String[]> output) {}

    /**
     * Make the Expandable List and gather the current
     * popular build skills from hotslogs to populate the
     * children views.
     *
     * @return ArrayList for expandableList
     */
    public ArrayList<Heroes> setHeroes() {

        //TODO: add pepe
        String hero_names[] = {"Abathur", "Anub'arak", "Arthas", "Azmodan", "Brightwing", "Chen",
                                "Diablo", "E.T.C.", "Falstad", "Gazlowe", "Illidan", "Jaina",
                                "Johanna", "Kaelthas", "Kerrigan", "Li Li", "Malfurion", "Muradin",
                                "Murky", "Nazeebo", "Nova", "Raynor", "Rehgar", "Sgt. Hammer",
                                "Sonya", "Stitches", "Sylvanas", "Tassadar", "The Butcher", "The Lost Vikings",
                                "Thrall", "Tychus", "Tyrael", "Tyrande", "Uther", "Valla",
                                "Zagara", "Zeratul"};

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
                    skills.setName(talker.passList.get(0));
                    sk_list.add(skills);
                    break;
                case "Anub'arak":
                    skills.setName(talker.passList.get(1));
                    sk_list.add(skills);
                    break;
                case "Arthas":
                    skills.setName(talker.passList.get(2));
                    sk_list.add(skills);
                    break;
                case "Azmodan":
                    skills.setName(talker.passList.get(3));
                    sk_list.add(skills);
                    break;
                case "Brightwing":
                    skills.setName(talker.passList.get(4));
                    sk_list.add(skills);
                    break;
                case "Chen":
                    skills.setName(talker.passList.get(5));
                    sk_list.add(skills);
                    break;
                case "Diablo":
                    skills.setName(talker.passList.get(6));
                    sk_list.add(skills);
                    break;
                case "E.T.C.":
                    skills.setName(talker.passList.get(7));
                    sk_list.add(skills);
                    break;
                case "Falstad":
                    skills.setName(talker.passList.get(8));
                    sk_list.add(skills);
                    break;
                case "Gazlowe":
                    skills.setName(talker.passList.get(9));
                    sk_list.add(skills);
                    break;
                case "Illidan":
                    skills.setName(talker.passList.get(10));
                    sk_list.add(skills);
                    break;
                case "Jaina":
                    skills.setName(talker.passList.get(11));
                    sk_list.add(skills);
                    break;
                case "Johanna":
                    skills.setName(talker.passList.get(12));
                    sk_list.add(skills);
                    break;
                case "Kaelthas":
                    skills.setName(talker.passList.get(13));
                    sk_list.add(skills);
                    break;
                case "Kerrigan":
                    skills.setName(talker.passList.get(14));
                    sk_list.add(skills);
                    break;
                case "Li Li":
                    skills.setName(talker.passList.get(15));
                    sk_list.add(skills);
                    break;
                case "Malfurion":
                    skills.setName(talker.passList.get(16));
                    sk_list.add(skills);
                    break;
                case "Muradin":
                    skills.setName(talker.passList.get(17));
                    sk_list.add(skills);
                    break;
                case "Murky":
                    skills.setName(talker.passList.get(18));
                    sk_list.add(skills);
                    break;
                case "Nazeebo":
                    skills.setName(talker.passList.get(19));
                    sk_list.add(skills);
                    break;
                case "Nova":
                    skills.setName(talker.passList.get(20));
                    sk_list.add(skills);
                    break;
                case "Rehgar":
                    skills.setName(talker.passList.get(21));
                    sk_list.add(skills);
                    break;
                case "Raynor":
                    skills.setName(talker.passList.get(22));
                    sk_list.add(skills);
                    break;
                case "Sgt. Hammer":
                    skills.setName(talker.passList.get(23));
                    sk_list.add(skills);
                    break;
                case "Sonya":
                    skills.setName(talker.passList.get(24));
                    sk_list.add(skills);
                    break;
                case "Stitches":
                    skills.setName(talker.passList.get(25));
                    sk_list.add(skills);
                    break;
                case "Sylvanas":
                    skills.setName(talker.passList.get(26));
                    sk_list.add(skills);
                    break;
                case "Tassadar":
                    skills.setName(talker.passList.get(27));
                    sk_list.add(skills);
                    break;
                case "The Butcher":
                    skills.setName(talker.passList.get(28));
                    sk_list.add(skills);
                    break;
                case "The Lost Vikings":
                    skills.setName(talker.passList.get(29));
                    sk_list.add(skills);
                    break;
                case "Thrall":
                    skills.setName(talker.passList.get(30));
                    sk_list.add(skills);
                    break;
                case "Tychus":
                    skills.setName(talker.passList.get(31));
                    sk_list.add(skills);
                    break;
                case "Tyrael":
                    skills.setName(talker.passList.get(32));
                    sk_list.add(skills);
                    break;
                case "Tyrande":
                    skills.setName(talker.passList.get(33));
                    sk_list.add(skills);
                    break;
                case "Uther":
                    skills.setName(talker.passList.get(34));
                    sk_list.add(skills);
                    break;
                case "Valla":
                    skills.setName(talker.passList.get(35));
                    sk_list.add(skills);
                    break;
                case "Zagara":
                    skills.setName(talker.passList.get(36));
                    sk_list.add(skills);
                    break;
                case "Zeratul":
                    skills.setName(talker.passList.get(37));
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
        btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // dismiss dialog window
                popupWindow.dismiss();
            }});
        return popupWindow;
    }
}
