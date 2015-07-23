package com.ryan.heroestopbuilds;


/**
 * This gets thrown into the SQLite Database
 * which all we care about are the specific skills for the hero
 *
 * @author ryan
 */
public class StoredSkills {

    private int id;
    private String skills;

    public StoredSkills(String skills) {
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkills() {
        return skills;
    }

}
