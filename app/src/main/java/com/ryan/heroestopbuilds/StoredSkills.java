package com.ryan.heroestopbuilds;


/**
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
