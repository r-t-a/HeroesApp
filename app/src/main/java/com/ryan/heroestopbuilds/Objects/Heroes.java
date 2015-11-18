package com.ryan.heroestopbuilds.Objects;

import com.ryan.heroestopbuilds.Skills;

import java.util.ArrayList;

/**
 * Hero group for the Expandable List and Database
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
