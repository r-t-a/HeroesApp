package com.ryan.heroestopbuilds;

import java.util.ArrayList;

/**
 * For JSoup talker to pass back information to MainActivity
 *
 * @author ryan
 */
public interface AsyncResponse {
    void processFinish(ArrayList<String> output);
}
