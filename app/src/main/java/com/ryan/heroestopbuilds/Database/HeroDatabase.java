package com.ryan.heroestopbuilds.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ryan.heroestopbuilds.Models.Heroes;

import java.util.ArrayList;
import java.util.List;

public class HeroDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "heroDatabase";
    private static final String TABLE_HEROES = "heroes";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SKILLS = "skills";
    private static final String KEY_URL = "url";

    public HeroDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEROES + " ( " +
                        KEY_ID + " INTEGER primary key autoincrement, " +
                        KEY_NAME + " TEXT NOT NULL, " +
                        KEY_SKILLS + " TEXT NOT NULL, " +
                        KEY_URL + " TEXT " + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROES);
        onCreate(db);
    }

    public void addHeroSkills(String hero, String skills) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, hero);
        if(skills.equals("Refresh to get skills")) {
            return;
        }
        cv.put(KEY_SKILLS, skills);
        db.insert(TABLE_HEROES, null, cv);
        db.close();
    }

    public void addHero(Heroes hero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, hero.getName());
        cv.put(KEY_URL, hero.getIcon().getPortrait());
        cv.put(KEY_SKILLS, "Refresh to get skills");

        db.insert(TABLE_HEROES, null, cv);
        db.close();
    }

    public boolean recordExists(String name) {
        Cursor cursor = this.getReadableDatabase().query(TABLE_HEROES,new String[]{KEY_NAME},
                KEY_NAME + "=?",new String[]{String.valueOf(name)},null,null,null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public String getSkills(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HEROES, new String[]{KEY_ID, KEY_NAME, KEY_SKILLS},
                KEY_NAME + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            return cursor.getString(2);
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public List<Heroes> getAllHeroes() {
        List<Heroes> heroList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] field = {KEY_NAME, KEY_URL};
        Cursor cursor = db.query(TABLE_HEROES, field, null, null, null, null, KEY_NAME +" ASC");

        int iname = cursor.getColumnIndex(KEY_NAME);
        int iurl = cursor.getColumnIndex(KEY_URL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(iname);
            String url = cursor.getString(iurl);
            heroList.add(new Heroes(name, url));
        }
        return heroList;
    }

    public void updateHeroSkills(String name, String skills) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateData = new ContentValues();
        if(skills.equals("Refresh to get skills")) {
            return;
        }
        updateData.put(KEY_SKILLS, skills);
        String where=KEY_NAME + "= ?";
        db.update(TABLE_HEROES,updateData,where,new String[]{name});
    }
}
