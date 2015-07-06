package com.ryan.heroestopbuilds;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

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
 * JSoup will be used to get website data from hotslogs.com
 * This info will be thrown to the Database once acquired
 * to keep it up to date
 *
 * Created by ryan on 6/23/15.
 */

public class JSoupTalker extends AsyncTask<Void, Void, ArrayList<String[]>> {

    private static final String TAG = null;
    private AsyncResponse listener;
    ProgressDialog progressDialog;
    ArrayList<String> passList = new ArrayList<>();
    String popularString = null;
    String convert = null;

    public JSoupTalker(AsyncResponse listener) {
        this.listener = listener;
    }

    //TODO: finish this for BootActivity
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = ProgressDialog.show(JSoupTalker.this, "Wait", "Downloading...");
    }

    @Override
    protected ArrayList<String[]> doInBackground(Void...params) {

        String heroes[] = {"Abathur", "Anub'arak", "Arthas", "Azmodan", "Brightwing", "Chen",
                "Diablo", "E.T.C.", "Falstad", "Gazlowe", "Illidan", "Jaina",
                "Johanna", "Kaelthas", "Kerrigan", "Li Li", "Malfurion", "Muradin",
                "Murky", "Nazeebo", "Nova", "Raynor", "Rehgar", "Sgt. Hammer",
                "Sonya", "Stitches", "Sylvanas", "Tassadar", "The Butcher", "The Lost Vikings",
                "Thrall", "Tychus", "Tyrael", "Tyrande", "Uther", "Valla",
                "Zagara", "Zeratul"};

        Document doc;
        try {
            for (String aHero: heroes) {
                ArrayList<Integer> gamesPlayed = new ArrayList<>();
                ArrayList<String> skillNames = new ArrayList<>();
                List<String> popularSkills;

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
                //if theres a row of skills with the most popular number, snatch it
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
                Log.i(TAG,"Popular Skills" + passList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> result) {
        listener.processFinish(result);
    }
}