package com.ryan.heroestopbuilds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Database to hold information after it's loaded
 * from JSoup.  This is so information can be stored
 * and not have to be fully reloaded every time the
 * user opens up the app.  Perhaps reload once
 * every week or so.  Popular builds from hotslogs won't
 * change on a daily basis, after all.
 *
 * @author ryan
 */
public class HeroDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "heroDatabase";
    private static final String TABLE_HEROES = "heroes";

    private static final String KEY_ID = "id";
    private static final String KEY_SKILLS = "skills";

    public HeroDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEROES + " ( " +
                        KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_SKILLS + " TEXT NOT NULL" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROES);
        onCreate(db);
    }

    public void addHero(StoredSkills hero) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SKILLS, hero.getSkills());

        db.insert(TABLE_HEROES, null, values);
        db.close();
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

        return heroList;
    }

}
