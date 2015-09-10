package com.ryan.heroestopbuilds;

import java.util.ArrayList;

/**
 * Backup purposes when online gathering fails
 * Keep this until better refactoring for online portion so it's
 * more stable
 *
 * @author ryan
 */
public class OfflineBackup {

    String abathur[]  = {"Pressurized Glands", "Adrenal Overload", "Needlespine", 
            "Evolve Monstrosity", "Soma Transference", "Envenomed Spikes",
            "Hivemind"};
    String anubarak[] = {"Assault Scarab", "Legion of Beetles", "Leeching Scarabs", 
            "Locust Swarm", "Burning Rage", "Blood for Blood",
            "Hive Master"};
    String arthas[]   = {"Regeneration Master", "Destruction", "Rune Tap",
            "Army of the Dead", "Spell Shield", "Stoneskin",
            "Hardened Shield"};
    String azmodan[]  = {"Taste for Blood", "Bound Minion", "Infernal Globe",
            "Black Pool", "March of Sin", "Blood for Blood",
            "Bolt of the Storm"};
    String brightw[]  = {"Unfurling Spray", "Manic Pixie", "Mistified",
             "Blink Heal", "Shield Dust", "Bouncy Dust",
             "Revitalizing Mist"};
    String chen[]     = {"Regeneration Master", "Amplified Healing", "Brewmaster's Balance",
             "Storm, Earth, Fire", "Relentless", "Pressure Point", "Elemental Conduit"};
    String diablo[]   = {"Soul Feast", "Fire Devil", "Soul Steal",
             "Lightning Breath", "Firestorm", "Rampage", "Hellstorm"};
    String etc[]      = {"Rolling Like a Stone", "Double-Necked Guitar", "Loud Speakers",
             "Mosh Pit", "Show Stopper", "Echo Pedal", "Tour Bus"};
    String falstad[]  = {"Power Throw", "Gathering Power", "Secret Weapon",
             "Hinterland Blast", "Giant Killer", "Hammer Time",
             "Nexus Fury"};
    String gazlowe[]  = {"Extra TNT", "Clockwerk Steam Fists", "Rock-it! Turret XL",
             "Grav-o-bomb 3000", "Turret Storage", "Long-Ranged Turrets",
             "Miniature Black Hole"};
    String illidan[]  = {"Shadow Shield", "Immolation", "First Aid",
             "Metamorphosis", "Sixth Sense", "Blood for Blood",
             "Demonic Form"};
    String jaina[]    = {"Deep Chill", "Snowstorm", "Frostbitten",
             "Summon Water Elemental", "Improved Ice Block", "Northern Exposure",
             "Bolt of the Storm"};
    String johanna[]  = {"Knight Takes Pawn", "Laws of Hope", "Battle Momentum",
             "Blessed Shield", "Burning Rage", "Blessed Hammer",
             "Indestructible"};
    String kaelthas[] = {"Mana Addict", "Nether Wind", "Fission Bomb",
             "Phoenix", "Flamethrower", "Ignite",
             "Bolt of the Storm"};
    String kerrigan[] = {"Sweeping Grasp", "Envenom", "Battle Momentum",
             "Maelstrom", "Sprint", "Blood for Blood",
             "Bolt of the Storm"};
    String kharazim[] = {"Transcendence", "Protective Shield", "Echo of Heaven", "Divine Palm",
            "Fists of Fury", "Soothing Breeze", "Storm Shield"};
    String leoric[] = {"Reanimation", "Fealty Unto Death", "Ossein Renewal", "March of the Black King",
              "Crushing Hope", "Consume Vitality", "Death March"};
    String lili[]     = {"Timeless Creature", "Mending Serpent", "Lightning Serpent",
             "Jug of 1,000 Cups", "Shrink Ray", "Serpent Sidekick",
             "KungFu Hustle"};
    String malfurion[]= {"Harmony", "Rampant Growth", "Enduring Growth",
             "Tranquility", "Life Seed", "Tenacious Roots",
             "Serenity"};
    String muradin[]  = {"Reverberation", "Thunderburn", "Piercing Bolt",
             "Avatar", "Thunderstrike", "Stoneform",
             "Rewind"};
    String murky[]    = {"Block", "Living The Dream", "Compressed Air",
             "Octo-Grab", "Wrath of Cod", "Slimy Pufferfish",
             "...And A Shark Too!"};
    String nazeebo[]  = {"Death Ritual", "Spider Cluster", "Gidbinn",
             "Gargantuan", "Sprint", "Leaping Spiders",
             "Humongoid"};
    String nova[]     = {"Ambush Snipe", "Gathering Power", "Anti-Armor Shells",
             "Precision Strike", "Lethal Decoy", "Double Fake",
             "Fast Reload"};
    String raynor[]   = {"Seasoned Marksman", "Focused Attack", "Revolution Overdrive",
             "Raynor's Raiders", "Giant Killer", "Berserk",
             "Nexus Fury"};
    String rehgar[]   = {"Spiritwalker's Grace", "Feral Heart", "Earth Shield",
            "Ancestral Healing", "Healing Surge", "Tidal Waves",
            "Storm Shield"};
    String rexxar[] = {"Hunter-Gatherer", "Hungry Bear", "Aspect of the Beast", "Bestial Wrath",
                        "Wildfire Bear", "Feign Death", "Spirit Bond"};
    String hammer[]   = {"Advanced Artillery", "Maelstrom Shells", "First Aid",
             "Napalm Strike", "Giant Killer", "Hover Siege Mode",
             "Nexus Fury"};
    String sonya[]    = {"War Paint", "Superiority", "Poisoned Spear",
             "Wrath of the Berserker", "Mystical Spear", "Furious Blow",
             "Ignore Pain"};
    String stitches[] = {"Heavy Slam", "Putrid Ground", "Toxic Gas",
             "Putrid Bile", "Mega Smash", "Pulverize",
             "Regenerative Bile"};
    String sylvanas[] = {"With the Wind", "Envenom", "Unstable Poison",
             "Wailing Arrow", "Evasive Fire", "Cold Embrace",
             "Bolt of the Storm"};
    String tassadar[] = {"Overload", "Healing Ward", "Static Charge",
             "Archon", "Shrink Ray", "Second Strike",
             "Twilight Archon"};
    String butcher[] = {"Victuals", "Unrelenting Pursuit", "Abbattoir", "Furnace Blast",
             "Crave Flesh", "Blood Frenzy", "Nexus Blades"};
    String vikings[]  = {"Explosive Attacks", "Pain Don't Hurt", "Baleog the Fierce",
             "Play Again", "Nordic Attack Squad", "Executioner",
             "Fury of the Storm"};
    String thrall[]   = {"Rolling Thunder", "Envenom", "Wind Shear",
             "Sundering", "Grace of Air", "Tempest Fury",
             "Nexus Blades"};
    String tychus[]   = {"Armor Piercing Rounds", "Spray 'n' Pray", "First Aid",
             "Commandeer Odin", "Lead Rain", "Stoneskin",
             "Big Red Button"};
    String tyrael[]   = {"Regeneration Master", "Amplified Healing", "Battle Momentum",
             "Judgement", "Imposing Will", "Blood for Blood",
             "Hardened Shield"};
    String tyrande[]  = {"Empower", "Pierce", "Lunar Blaze",
             "Starfall", "Overflowing Light", "Ranger",
             "Rewind"};
    String uthur[]    = {"Conjurer's Pursuit", "Protective Shield", "Cleanse",
             "Divine Shield", "Shrink Ray", "Benediction",
             "Redemption"};
    String valla[]    = {"Rancor", "Manticore", "Searing Attacks",
             "Rain of Vengeance", "Giant Killer", "Blood for Blood",
             "Nexus Fury"};
    String zagara[]   = {"Reconstitution", "Envenomed Spines", "Rapid Incubation",
             "Devouring Maw", "Mutalisk", "Brood Expansion",
             "Bolt of the Storm"};
    String zeratul[]  = {"Regeneration Master", "Focused Attack", "Follow Through",
             "Void Prison", "Wormhole", "Double Bombs",
             "Rewind"};


    /**
     * Purely for visual, so if offline on initial load the user doesn't just
     * see a blank screen, at least populate the hero list since it's
     * hard coded.
     *
     * @return offline list (error with network or disconnect)
     */
    public  ArrayList<Heroes> offlineTempList() {
        ArrayList<Heroes> list = new ArrayList<>();
        //populate the expandable list with the heroes
        for (int i = 0; i < Constants.HERO_NAMES.length; i++) {
            Heroes hero = new Heroes();
            //set name and portrait
            hero.setName(Constants.HERO_NAMES[i]);
            hero.setPortrait(Constants.PORTRAITS[i]);
            ArrayList<Skills> skillList = new ArrayList<>();

            for(int j = 0; j < 7; j++) {
                Skills skills = new Skills();
                switch (Constants.HERO_NAMES[i]) {
                    case "Abathur":
                        skills.setName(abathur[j]);
                        skillList.add(skills);
                        break;
                    case "Anub'arak":
                        skills.setName(anubarak[j]);
                        skillList.add(skills);
                        break;
                    case "Arthas":
                        skills.setName(arthas[j]);
                        skillList.add(skills);
                        break;
                    case "Azmodan":
                        skills.setName(azmodan[j]);
                        skillList.add(skills);
                        break;
                    case "Brightwing":
                        skills.setName(brightw[j]);
                        skillList.add(skills);
                        break;
                    case "Chen":
                        skills.setName(chen[j]);
                        skillList.add(skills);
                        break;
                    case "Diablo":
                        skills.setName(diablo[j]);
                        skillList.add(skills);
                        break;
                    case "E.T.C.":
                        skills.setName(etc[j]);
                        skillList.add(skills);
                        break;
                    case "Falstad":
                        skills.setName(falstad[j]);
                        skillList.add(skills);
                        break;
                    case "Gazlowe":
                        skills.setName(gazlowe[j]);
                        skillList.add(skills);
                        break;
                    case "Illidan":
                        skills.setName(illidan[j]);
                        skillList.add(skills);
                        break;
                    case "Jaina":
                        skills.setName(jaina[j]);
                        skillList.add(skills);
                        break;
                    case "Johanna":
                        skills.setName(johanna[j]);
                        skillList.add(skills);
                        break;
                    case "Kael'thas":
                        skills.setName(kaelthas[j]);
                        skillList.add(skills);
                        break;
                    case "Kerrigan":
                        skills.setName(kerrigan[j]);
                        skillList.add(skills);
                        break;
                    case "Kharazim":
                        skills.setName(kharazim[j]);
                        skillList.add(skills);
                        break;
                    case "Leoric":
                        skills.setName(leoric[j]);
                        skillList.add(skills);
                        break;
                    case "Li Li":
                        skills.setName(lili[j]);
                        skillList.add(skills);
                        break;
                    case "Malfurion":
                        skills.setName(malfurion[j]);
                        skillList.add(skills);
                        break;
                    case "Muradin":
                        skills.setName(muradin[j]);
                        skillList.add(skills);
                        break;
                    case "Murky":
                        skills.setName(murky[j]);
                        skillList.add(skills);
                        break;
                    case "Nazeebo":
                        skills.setName(nazeebo[j]);
                        skillList.add(skills);
                        break;
                    case "Nova":
                        skills.setName(nova[j]);
                        skillList.add(skills);
                        break;
                    case "Rehgar":
                        skills.setName(rehgar[j]);
                        skillList.add(skills);
                        break;
                    case "Rexxar":
                        skills.setName(rexxar[j]);
                        skillList.add(skills);
                        break;
                    case "Raynor":
                        skills.setName(raynor[j]);
                        skillList.add(skills);
                        break;
                    case "Sgt. Hammer":
                        skills.setName(hammer[j]);
                        skillList.add(skills);
                        break;
                    case "Sonya":
                        skills.setName(sonya[j]);
                        skillList.add(skills);
                        break;
                    case "Stitches":
                        skills.setName(stitches[j]);
                        skillList.add(skills);
                        break;
                    case "Sylvanas":
                        skills.setName(sylvanas[j]);
                        skillList.add(skills);
                        break;
                    case "Tassadar":
                        skills.setName(tassadar[j]);
                        skillList.add(skills);
                        break;
                    case "The Butcher":
                        skills.setName(butcher[j]);
                        skillList.add(skills);
                        break;
                    case "The Lost Vikings":
                        skills.setName(vikings[j]);
                        skillList.add(skills);
                        break;
                    case "Thrall":
                        skills.setName(thrall[j]);
                        skillList.add(skills);
                        break;
                    case "Tychus":
                        skills.setName(tychus[j]);
                        skillList.add(skills);
                        break;
                    case "Tyrael":
                        skills.setName(tyrael[j]);
                        skillList.add(skills);
                        break;
                    case "Tyrande":
                        skills.setName(tyrande[j]);
                        skillList.add(skills);
                        break;
                    case "Uther":
                        skills.setName(uthur[j]);
                        skillList.add(skills);
                        break;
                    case "Valla":
                        skills.setName(valla[j]);
                        skillList.add(skills);
                        break;
                    case "Zagara":
                        skills.setName(zagara[j]);
                        skillList.add(skills);
                        break;
                    case "Zeratul":
                        skills.setName(zeratul[j]);
                        skillList.add(skills);
                        break;
                }
            }
            hero.setSkills(skillList);
            list.add(hero);
        }
        return list;
    }
}
