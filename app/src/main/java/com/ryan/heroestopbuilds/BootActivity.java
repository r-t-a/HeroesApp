package com.ryan.heroestopbuilds;

import android.app.Activity;
import android.os.Bundle;

/**
 * Main loading screen as the app gets hotslogs
 * information
 * TODO: finish this to talk to JSOUP PreExecute
 *
 * Created by ryan on 7/3/15.
 */
public class BootActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot_layout);
    }

}
