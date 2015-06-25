package com.ryan.heroestopbuilds;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;

/**
 * This app will just have one activity that will list the Heroes
 * and have an expandable list display their top skills on click.
 * The action bar menu will just have a general info button that
 * can display a popup that shows the info the user may want, ie
 * version number or the latest heroes update.
 *
 * Created by ryan on 6/18/15
 */
public class MainActivity extends ActionBarActivity {

    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView expandList = (ExpandableListView) findViewById(R.id.expandableList);
        //build the list and set the adapter with our custom one
        ArrayList<Heroes> heroList = setHeroes();
        CustomExpandableAdapter customAdapt = new CustomExpandableAdapter(MainActivity.this, heroList);
        expandList.setAdapter(customAdapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            popupWindow = showInfoPopup();
            popupWindow.showAtLocation(findViewById(R.id.expandableList), CENTER, 0, 15);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Disgusting piece of code that even the IDE is complaining about
     * There's a lot of strings to keep track of that are thrown into
     * an ArrayList as heroes and their skills.  Which is what our
     * expandable list is comprised of.  In an ideal world I'll have this
     * talk to hotslogs.com and update automatically.  But for now, hardcode
     * is the way and ugly way.
     * TODO: update this to talk to hotslogs instead of hardcoding
     *
     * @return ArrayList for expandableList
     */
    public ArrayList<Heroes> setHeroes() {

        //TODO: add pepe
        String hero_names[] = {"Abathur", "Anub'arak", "Arthas", "Azmodan", "Brightwing", "Chen",
                                "Diablo", "E.T.C.", "Falstad", "Gazlowe", "Illidan", "Jaina",
                                "Johanna", "Kaelthas", "Kerrigan", "Li Li", "Malfurion", "Muradin",
                                "Murky", "Nazeebo", "Nova", "Raynor", "Rehgar", "Sgt. Hammer",
                                "Sonya", "Stitches", "Sylvanas", "Tassadar", "The Lost Vikings",
                                "Thrall", "Tychus", "Tyrael", "Tyrande", "Uther", "Valla",
                                "Zagara", "Zeratul"};

        int portraits[] = {R.drawable.abathur, R.drawable.anubarak, R.drawable.arthas,
                           R.drawable.azmodan, R.drawable.brightwing, R.drawable.chen,
                           R.drawable.diablo,  R.drawable.elite_tauren_chieftain, R.drawable.falstad,
                           R.drawable.gazlowe, R.drawable.illidan, R.drawable.jaina,
                           R.drawable.johanna, R.drawable.kaelthas, R.drawable.kerrigan,
                           R.drawable.li_li,   R.drawable.malfurion, R.drawable.muradin,
                           R.drawable.murky,   R.drawable.nazeebo, R.drawable.nova,
                           R.drawable.raynor,  R.drawable.rehgar, R.drawable.sergeant_hammer,
                           R.drawable.sonya,   R.drawable.stitches, R.drawable.sylvanas,
                           R.drawable.tassadar,R.drawable.the_lost_vikings, R.drawable.thrall,
                           R.drawable.tychus,  R.drawable.tyrael, R.drawable.tyrande,
                           R.drawable.uther,   R.drawable.valla, R.drawable.zagara,
                           R.drawable.zeratul};

        String abathur[]  = {"Level 1: Pressurized Glands", "Level 4: Adrenal Overload", "Level 7: Needlespine",
                            "Level 10: Evolve Monstrosity", "Level 13: Soma Transference", "Level 16: Envenomed Spikes",
                            "Level 20: Hivemind"};
        String anubarak[] = {"Level 1: Assault Scarab", "Level 4: Legion of Beetles", "Level 7: Leeching Scarabs",
                            "Level 10: Locust Swarm", "Level 13: Burning Rage", "Level 16: Blood for Blood",
                            "Level 20: Hive Master"};
        String arthas[]   = {"Level 1: Block", "Level 4: Destruction", "Level 7: Rune Tap",
                            "Level 10: Army of the Dead", "Level 13: Biting Cold", "Level 16: Frostmourne Feeds",
                            "Level 20: Hardened Shield"};
        String azmodan[]  = {"Level 1: Taste for Blood", "Level 4: Bound Minion", "Level 7: Infernal Globe",
                            "Level 10: Demonic Invasion", "Level 13: March of Sin", "Level 16: Battleborn",
                            "Level 20: Perishing Flame"};
        String brightw[]  = {"Level 1: Bribe", "Level 4: Protective Shield", "Level 7: Gust of Healing",
                             "Level 10: Blink Heal", "Level 13: Sprint", "Level 16: Critterize",
                             "Level 20: Storm Shield"};
        String chen[]     = {"Level 1: Regeneration Master", "Level 4: Amplified Healing", "Level 7: Brewmaster's Balance",
                             "Level 10: Storm, Earth, Fire", "Level 13: Enough to Share", "Level 16: Pressure Point",
                             "Level 20: Elemental Conduit"};
        String diablo[]   = {"Level 1: Soul Feast", "Level 4: Fire Devil", "Level 7: Soul Steal",
                             "Level 10: Lightning Breath", "Level 13: Firestorm", "Level 16: Rampage",
                             "Level 20: Hellstorm"};
        String etc[]      = {"Level 1: Rolling Like a Stone", "Level 4: Double-Necked Guitar", "Level 7: Loud Speakers",
                             "Level 10: Mosh Pit", "Level 13: Show Stopper", "Level 16: Echo Pedal",
                             "Level 20: Tour Bus"};
        String falstad[]  = {"Level 1: Power Throw", "Level 4: Gathering Power", "Level 7: Secret Weapon",
                             "Level 10: Hinterland Blast", "Level 13: Giant Killer", "Level 16: Hammer Time",
                             "Level 20: Nexus Fury"};
        String gazlowe[]  = {"Level 1: Extra TNT", "Level 4: Reduce, Reuse, Recycle", "Level 7: Rock-it! Turret XL",
                             "Level 10: Grav-o-bomb 3000", "Level 13: Turret Storage", "Level 16: Long-Ranged Turrets",
                             "Level 20: Miniature Black Hole"};
        String illidan[]  = {"Level 1: Shadow Shield", "Level 4: Immolation", "Level 7: First Aid",
                             "Level 10: Metamorphosis", "Level 13: Sixth Sense", "Level 16: Blood for Blood",
                             "Level 20: Demonic Form"};
        String jaina[]    = {"Level 1: Deep Chill", "Level 4: Snowstorm", "Level 7: Frostbitten",
                             "Level 10: Summon Water Elemental", "Level 13: Sprint", "Level 16: Northern Exposure",
                             "Level 20: Bolt of the Storm"};
        String johanna[]  = {"Level 1: Reinforce", "Level 4: Laws of Hope", "Level 7: Battle Momentum",
                             "Level 10: Blessed Shield", "Level 13: Burning Rage", "Level 16: Imposing Presence",
                             "Level 20: Indestructible"};
        String kaelthas[] = {"Level 1: Mana Addict", "Level 4: Gathering Power", "Level 7: Fission Bomb",
                             "Level 10: Phoenix", "Level 13: Flamethrower", "Level 16: Ignite",
                             "Level 20: Bolt of the Storm"};
        String kerrigan[] = {"Level 1: Sweeping Grasp", "Level 4: Envenom", "Level 7: Battle Momentum",
                             "Level 10: Maelstrom", "Level 13: Sprint", "Level 16: Blood for Blood",
                             "Level 20: Bolt of the Storm"};
        String lili[]     = {"Level 1: Conjurer's Pursuit", "Level 4: Mending Serpent", "Level 7: The Good Stuff",
                             "Level 10: Jug of 1,000 Cups", "Level 13: Shrink Ray", "Level 16: Two for One",
                             "Level 20: Jug of 1,000,000 Cups"};
        String malfurion[]= {"Level 1: Conjurer's Pursuit", "Level 4: Protective Shield", "Level 7: Enduring Growth",
                             "Level 10: Tranquility", "Level 13: Life Seed", "Level 16: Tenacious Roots",
                             "Level 20: Serenity"};
        String muradin[]  = {"Level 1: Reverberation", "Level 4: Thunderburn", "Level 7: Piercing Bolt",
                             "Level 10: Avatar", "Level 13: Thunderstrike", "Level 16: Stoneform",
                             "Level 20: Unstoppable Force"};
        String murky[]    = {"Level 1: Block", "Level 4: Living The Dream", "Level 7: Compressed Air",
                             "Level 10: Octo-Grab", "Level 13: Wrath of Cod", "Level 16: Slimy Pufferfish",
                             "Level 20: ...And A Shark Too!"};
        String nazeebo[]  = {"Level 1: Death Ritual", "Level 4: Spider Cluster", "Level 7: Gidbinn",
                             "Level 10: Gargantuan", "Level 13: Sprint", "Level 16: Leaping Spiders",
                             "Level 20: Humongoid"};
        String nova[]     = {"Level 1: Ambush Snipe", "Level 4: Gathering Power", "Level 7: Anti-Armor Shells",
                             "Level 10: Precision Strike", "Level 13: Lethal Decoy", "Level 16: Double Fake",
                             "Level 20: Fast Reload"};
        String rehgar[]   = {"Level 1: Spiritwalker's Grace", "Level 4: Feral Heart", "Level 7: Earth Shield",
                             "Level 10: Ancestral Healing", "Level 13: Healing Surge", "Level 16: Blood for Blood",
                             "Level 20: Storm Shield"};
        String raynor[]   = {"Level 1: Seasoned Marksman", "Level 4: Focused Attack", "Level 7: Revolution Overdrive",
                             "Level 10: Hyperion", "Level 13: Giant Killer", "Level 16: Bullseye",
                             "Level 20: Nexus Fury"};
        String hammer[]   = {"Level 1: Advanced Artillery", "Level 4: Maelstrom Shells", "Level 7: First Aid",
                             "Level 10: Napalm Strike", "Level 13: Giant Killer", "Level 16: Hover Siege Mode",
                             "Level 20: Nexus Fury"};
        String sonya[]    = {"Level 1: War Paint", "Level 4: Shattered Ground", "Level 7: Poisoned Spear",
                             "Level 10: Wrath of the Berserker", "Level 13: Mystical Spear", "Level 16: Furious Blow",
                             "Level 20: Ignore Pain"};
        String stitches[] = {"Level 1: Heavy Slam", "Level 4: Amplified Healing", "Level 7: Tenderizer",
                             "Level 10: Gorge", "Level 13: Relentless", "Level 16: Pulverize",
                             "Level 20: Regenerative Bile"};
        String sylvanas[] = {"Level 1: With the Wind", "Level 4: Envenom", "Level 7: Unstable Poison",
                             "Level 10: Wailing Arrow", "Level 13: Evasive Fire", "Level 16: Cold Embrace",
                             "Level 20: Deafening Blast"};
        String tassadar[] = {"Level 1: Conjurer's Pursuit", "Level 4: Healing Ward", "Level 7: Static Charge",
                             "Level 10: Archon", "Level 13: Shrink Ray", "Level 16: Second Strike",
                             "Level 20: Twilight Archon"};
        String vikings[]  = {"Level 1: Viking Hoard", "Level 4: Spin to Win!", "Level 7: Jump!",
                             "Level 10: Longboat Raid!", "Level 13: Impatience is a Virtue", "Level 16: Norse Force!",
                             "Level 20: Ragnarok 'n' Roll!"};
        String thrall[]   = {"Level 1: Rolling Thunder", "Level 4: Envenom", "Level 7: Wind Shear",
                             "Level 10: Sundering", "Level 13: Grace of Air", "Level 16: Tempest Fury",
                             "Level 20: Nexus Blades"};
        String tychus[]   = {"Level 1: Armor Piercing Rounds", "Level 4: Spray 'n' Pray", "Level 7: First Aid",
                             "Level 10: Commandeer Odin", "Level 13: Lead Rain", "Level 16: Stoneskin",
                             "Level 20: Focusing Diodes"};
        String tyrael[]   = {"Level 1: Purge Evil", "Level 4: Amplified Healing", "Level 7: Battle Momentum",
                             "Level 10: Judgement", "Level 13: Imposing Will", "Level 16: Blood for Blood",
                             "Level 20: Hardened Shield"};
        String tyrande[]  = {"Level 1: Ranger's Mark", "Level 4: Pierce", "Level 7: Lunar Blaze",
                             "Level 10: Starfall", "Level 13: Overflowing Light", "Level 16: Trueshot Aura",
                             "Level 20: Celestial Wrath"};
        String uthur[]    = {"Level 1: Conjurer's Pursuit", "Level 4: Protective Shield", "Level 7: Cleanse",
                             "Level 10: Divine Shield", "Level 13: Shrink Ray", "Level 16: Benediction",
                             "Level 20: Redemption"};
        String valla[]    = {"Level 1: Composite Arrows", "Level 4: Arsenal", "Level 7: Searing Attacks",
                             "Level 10: Strafe", "Level 13: Frost Shot", "Level 16: Blood for Blood",
                             "Level 20: Nexus Fury"};
        String zagara[]   = {"Level 1: Reconstitution", "Level 4: Envenomed Spines", "Level 7: Rapid Incubation",
                             "Level 10: Devouring Maw", "Level 13: Grooved Spines", "Level 16: Brood Expansion",
                             "Level 20: Bolt of the Storm"};
        String zeratul[]  = {"Level 1: Greater Cleave", "Level 4: Focused Attack", "Level 7: Follow Through",
                             "Level 10: Void Prison", "Level 13: Wormhole", "Level 16: Double Bombs",
                             "Level 20: Nexus Blades"};

        ArrayList<Heroes> list = new ArrayList<>();
        //populate the expandable list with the heroes
        for(int i = 0; i<hero_names.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(hero_names[i]);
            hero.setPortrait(portraits[i]);

            //set child group skills
            ArrayList<Skills> sk_list = new ArrayList<>();
            //7 skills per hero
            for(int j = 0; j< 7; j++) {
                Skills skills = new Skills();
                switch (hero_names[i]) {
                    case "Abathur":
                        skills.setName(abathur[j]);
                        sk_list.add(skills);
                        break;
                    case "Anub'arak":
                        skills.setName(anubarak[j]);
                        sk_list.add(skills);
                        break;
                    case "Arthas":
                        skills.setName(arthas[j]);
                        sk_list.add(skills);
                        break;
                    case "Azmodan":
                        skills.setName(azmodan[j]);
                        sk_list.add(skills);
                        break;
                    case "Brightwing":
                        skills.setName(brightw[j]);
                        sk_list.add(skills);
                        break;
                    case "Chen":
                        skills.setName(chen[j]);
                        sk_list.add(skills);
                        break;
                    case "Diablo":
                        skills.setName(diablo[j]);
                        sk_list.add(skills);
                        break;
                    case "E.T.C.":
                        skills.setName(etc[j]);
                        sk_list.add(skills);
                        break;
                    case "Falstad":
                        skills.setName(falstad[j]);
                        sk_list.add(skills);
                        break;
                    case "Gazlowe":
                        skills.setName(gazlowe[j]);
                        sk_list.add(skills);
                        break;
                    case "Illidan":
                        skills.setName(illidan[j]);
                        sk_list.add(skills);
                        break;
                    case "Jaina":
                        skills.setName(jaina[j]);
                        sk_list.add(skills);
                        break;
                    case "Johanna":
                        skills.setName(johanna[j]);
                        sk_list.add(skills);
                        break;
                    case "Kaelthas":
                        skills.setName(kaelthas[j]);
                        sk_list.add(skills);
                        break;
                    case "Kerrigan":
                        skills.setName(kerrigan[j]);
                        sk_list.add(skills);
                        break;
                    case "Li Li":
                        skills.setName(lili[j]);
                        sk_list.add(skills);
                        break;
                    case "Malfurion":
                        skills.setName(malfurion[j]);
                        sk_list.add(skills);
                        break;
                    case "Muradin":
                        skills.setName(muradin[j]);
                        sk_list.add(skills);
                        break;
                    case "Murky":
                        skills.setName(murky[j]);
                        sk_list.add(skills);
                        break;
                    case "Nazeebo":
                        skills.setName(nazeebo[j]);
                        sk_list.add(skills);
                        break;
                    case "Nova":
                        skills.setName(nova[j]);
                        sk_list.add(skills);
                        break;
                    case "Rehgar":
                        skills.setName(rehgar[j]);
                        sk_list.add(skills);
                        break;
                    case "Raynor":
                        skills.setName(raynor[j]);
                        sk_list.add(skills);
                        break;
                    case "Sgt. Hammer":
                        skills.setName(hammer[j]);
                        sk_list.add(skills);
                        break;
                    case "Sonya":
                        skills.setName(sonya[j]);
                        sk_list.add(skills);
                        break;
                    case "Stitches":
                        skills.setName(stitches[j]);
                        sk_list.add(skills);
                        break;
                    case "Sylvanas":
                        skills.setName(sylvanas[j]);
                        sk_list.add(skills);
                        break;
                    case "Tassadar":
                        skills.setName(tassadar[j]);
                        sk_list.add(skills);
                        break;
                    case "The Lost Vikings":
                        skills.setName(vikings[j]);
                        sk_list.add(skills);
                        break;
                    case "Thrall":
                        skills.setName(thrall[j]);
                        sk_list.add(skills);
                        break;
                    case "Tychus":
                        skills.setName(tychus[j]);
                        sk_list.add(skills);
                        break;
                    case "Tyrael":
                        skills.setName(tyrael[j]);
                        sk_list.add(skills);
                        break;
                    case "Tyrande":
                        skills.setName(tyrande[j]);
                        sk_list.add(skills);
                        break;
                    case "Uther":
                        skills.setName(uthur[j]);
                        sk_list.add(skills);
                        break;
                    case "Valla":
                        skills.setName(valla[j]);
                        sk_list.add(skills);
                        break;
                    case "Zagara":
                        skills.setName(zagara[j]);
                        sk_list.add(skills);
                        break;
                    case "Zeratul":
                        skills.setName(zeratul[j]);
                        sk_list.add(skills);
                        break;
                }
            }
            hero.setSkills(sk_list);
            list.add(hero);
        }
        return list;
    }

    /**
     * Popup window for general information, accessed through
     * 3 dot menu
     *
     * @return popupWindow
     *
     * TODO: Add HOTS update version number or name ie. "Johanna Update"
     */
    public PopupWindow showInfoPopup() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.info_popup, new LinearLayout(getBaseContext()), false);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button btnDismiss = (Button)popupView.findViewById(R.id.buttonOk);
        btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // dismiss dialog window
                popupWindow.dismiss();
            }});
        return popupWindow;
    }
}
