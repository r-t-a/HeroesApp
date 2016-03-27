package com.ryan.heroestopbuilds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database to hold heroes and skills, Table lookup is done by hero name as they are clicked
 * from the ExpandableList.
 *
 * @author ryan
 */
public class HeroDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "heroDatabase";
    private static final String TABLE_HEROES = "heroes";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SKILLS = "skills";

    public HeroDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEROES + " ( " +
                        KEY_ID + " INTEGER primary key autoincrement, " +
                        KEY_NAME + " TEXT NOT NULL, " +
                        KEY_SKILLS + " TEXT NOT NULL" + ")"
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
        cv.put(KEY_NAME, hero);
        cv.put(KEY_SKILLS, skills);
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

    public void updateHero(String name, String skills) {
        SQLiteDatabase db = this.getWritableDatabase();
        name = name.replace("'", "\'");
        String updateQuery = "UPDATE " + TABLE_HEROES +
                             " SET " + KEY_SKILLS +"="+ "'"+skills+"'" +
                             " WHERE " + KEY_NAME +"="+ "'"+name+"'";
        System.out.println("QUERY: " + updateQuery);
        db.rawQuery(updateQuery,null);
    }
}
