package com.ryan.heroestopbuilds;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static com.ryan.heroestopbuilds.MainActivity.*;


/**
 * @author ryan
 */
public class AddBuildActivity extends AppCompatActivity {

    Spinner heroSpinner;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        heroSpinner = (Spinner) findViewById(R.id.hero_spinner);
        heroSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                heroSelection.values()));
        heroSpinner.setSelection(0);
        populateListView("");
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

            JSONObject json = new JSONObject();
            Iterator<String> iter = json.keys();
            Object value = null;
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    value = json.get(key);
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }

            //convert string to JSONArray
            //parse an Object from a random index in the JSONArray
            //JSONObject anObject = jsonArray.getJSONObject(0);
            System.out.println("JSON : " + value);  //should be Abathur
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return skills;
    }

    public void saveBuild(View view) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ListViewFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ListViewFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ListViewFragment newInstance(int sectionNumber) {
            ListViewFragment fragment = new ListViewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ListViewFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {return 7;}

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    System.out.println("Level 1");
                    return "Level 1";
                case 1:
                    return "Level 4";
                case 2:
                    return "Level 7";
                case 3:
                    return "Level 10";
                case 4:
                    return "Level 13";
                case 5:
                    return "Level 16";
                case 6:
                    return "Level 20";
            }
            return null;
        }
    }
}
