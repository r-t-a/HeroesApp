package com.ryan.heroestopbuilds.Preferences;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ryan.heroestopbuilds.BuildConfig;
import com.ryan.heroestopbuilds.R;

/**
 * Info page
 *
 * @author ryan
 */

public class InfoPreferenceActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        InfoPreference infoP = new InfoPreference();
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                infoP).commit();

        setContentView(R.layout.activity_info);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class InfoPreference extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onResume() {
            super.onResume();
            initializePreferences();
        }

        public void initializePreferences() {
            addPreferencesFromResource(R.xml.pref_info);

            Preference version = findPreference("VRS");
            version.setSummary(BuildConfig.VERSION_NAME);

            Preference dev = findPreference("DEV");
            dev.setSummary(R.string.dev);
        }
    }
}
