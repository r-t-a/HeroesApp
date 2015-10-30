package com.ryan.heroestopbuilds;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * @author ryan
 */
public class DatabaseTest extends AndroidTestCase {
    private HeroDatabase db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new HeroDatabase(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testAddEntry(){
        db.addHero("CoolBro", "skill1 skill2 skill3 skill4");
        db.addHero("OtherCoolHero", "a b c d e f g");
        db.addHero("PlainJane", "one two three skills");

        db.updateHero("CoolBro", "skill4 skill5 skill6 skill7 skill8 skill9");
        db.updateHero("OtherCoolHero", "a b c z x y");
    }
}
