package com.ryan.heroestopbuilds.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HeroDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "heroDatabase";
    private static final String TABLE_HEROES = "heroes";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SKILLS = "skills";
    //private static final String KEY_FAVORITE = "favorite";

    public HeroDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEROES + " ( " +
                        KEY_ID + " INTEGER primary key autoincrement, " +
                        KEY_NAME + " TEXT NOT NULL, " +
                        KEY_SKILLS + " TEXT NOT NULL " + ")"
                        //KEY_FAVORITE + " INTEGER " + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROES);
        onCreate(db);
    }

    public void addHero(String hero, String skills) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(skills.equals("Refresh to get skills")) {
            return;
        }
        cv.put(KEY_NAME, hero);
        if(skills.equals("Refresh to get skills")) {
            return;
        }
        cv.put(KEY_SKILLS, skills);
        //cv.put(KEY_FAVORITE, 0);
        db.insert(TABLE_HEROES, null, cv);
        db.close();
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

    public List<String> getAllHeroes() {
        List<String> heroList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HEROES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String skills = cursor.getString(1);

                heroList.add(skills);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return heroList;
    }
//
//    public List<String> getFavoriteHeroes() {
//        List<String> favoriteList = new ArrayList<>();
//        String selectQuery = "SELECT * " +
//                "             FROM " + TABLE_HEROES +
//                              "WHERE " + KEY_FAVORITE + " == 1";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if(cursor.moveToFirst()) {
//            do {
//                String skills = cursor.getString(1);
//                favoriteList.add(skills);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return favoriteList;
//    }

    public void updateHero(String name, String skills) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateData = new ContentValues();
        if(skills.equals("Refresh to get skills")) {
            return;
        }
        updateData.put(KEY_SKILLS, skills);
        String where=KEY_NAME + "= ?";
        db.update(TABLE_HEROES,updateData,where,new String[]{name});
    }

//    public void updateFavoriteHero(String name, int fav) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues updateData = new ContentValues();
//        updateData.put(KEY_FAVORITE, fav);
//        String where = KEY_NAME + "= ?";
//        db.update(TABLE_HEROES, updateData, where, new String[]{name});
//    }
}
