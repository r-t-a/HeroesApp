package com.ryan.heroestopbuilds;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


/**
 * Database for holding the hero names, skills
 * TODO: Finish implementing to make life easier
 *
 * Created by ryan on 6/24/2015.
 */

public class HeroesDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "heroList";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public HeroesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void addHero(Heroes hero, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("portrait", hero.getPortrait());
        cv.put("name", hero.getName());

        db.insert("heroes", null, cv);
    }

    public void addSkills(Skills skills, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("skills", skills.getName());
    }
}