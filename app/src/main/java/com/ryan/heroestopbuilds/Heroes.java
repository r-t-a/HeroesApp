package com.ryan.heroestopbuilds;

import java.util.ArrayList;

/**
 * Hero group for the Expandable List
 *
 * @author ryan
 */
public class Heroes {

    private String name;
    private int portrait;
    private ArrayList<Skills> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public ArrayList<Skills> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skills> skills) {
        this.skills = skills;
    }
}
