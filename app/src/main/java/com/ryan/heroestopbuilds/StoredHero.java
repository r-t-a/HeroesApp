package com.ryan.heroestopbuilds;


/**
 * @author ryan
 */
public class StoredHero {

    private int id;
    private String name;
    private String skills;

    public StoredHero(int id, String name, String skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    public StoredHero(String name, String skills) {
        this.name = name;
        this.skills = skills;
    }

    public StoredHero() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
