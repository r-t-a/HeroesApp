package com.ryan.heroestopbuilds.Utilities;

import java.util.ArrayList;

public class TalentFormatter {

    private static final int levelMod = 5;


    public static String prettyPrinter(ArrayList<String> popularSkills) {
        ArrayList<String> finalList = new ArrayList<>();
        String lgSpacing = String.format("%" + 3 + "s", "");
        String smSpacing = String.format("%" + 1 + "s", "");
        int weGotAChromie = 1;
        //Check first skills, this means we got a Chromie
        for(String s : popularSkills) {
            if (s.matches("(?i)(CompoundingAether|DeepBreathing|TimewalkersPursuit|PeerIntoTheFuture)*")) {
                weGotAChromie = 0;
                finalList.add("Level 1:    " + popularSkills.get(0));
                break;
            }
        }
        //add our final list to a new list to be passed to MainActivity
        for (int i = 0; i <= levelMod; i++) {
            if (i <= 2) {
                finalList.add("Level " + (weGotAChromie + 3 * i) + ": " + lgSpacing + popularSkills.get(i));
            } else {
                finalList.add("Level " + (weGotAChromie + 3 * i) + ": " + smSpacing + popularSkills.get(i));
            }
        }
        // formatting for Chromie & others
        if(weGotAChromie == 1) {
            finalList.add("Level20:  Players Choice");
        } else {
            finalList.remove(1);
            finalList.add("Level 19:  Players Choice");
        }
        return finalList.toString()
                .replace(",", "\n")
                .replace("[", " ")
                .replace("]", "")
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2")
                .replace("ofthe", " of the")
                .replace("of", " of")
                .replace("for", " for")
                .replace("asthe", " as the")
                .replace("Likea", "Like a")
                .replace("AShark", "A Shark")
                .replace("Isa", "Is a")
                .replace("Grav OBomb3000", "Grav O Bomb 3000")
                .replace("1000", " 1000 ")
                .replace("Sprayn", "Spray n")
                .replace("Withthe", "With the")
                .replace("20", " 20")
                .replace("G o forthe" , "Go for the")
                .replace("Thatsthe", "Thats the")
                .replace("Lambtothe", "Lamb to the")
                .replace("Pastand", "Past and")
                .replace("Reachingthrough", "Reaching Through")
                .replace("Slowing Sands", "  Slowing Sands")
                .replace("Temporal Loop", "  Temporal Loop")
                .replace("Boundby" , "Bound by")
                .replace("Way  of", "Way of")
                .replace("Largeand", "Large and")
                .replace("Inthe", "In the")
                .replace("Temperedby", "Tempered by")
                .replace("ATouch", "A Touch")
                .replace("Oneinthe", "One in the")
                .replace("Deadly Charge Lvl 20", "Deadly Charge")
                .replace("Eyesinthe", "Eyes in the")
                .replace("Pressthe", "Press the")
                .replace("Onthe", "On the")
                .replace("Letthe", "Let the")
                .replace("Player Choice", "Players Choice")
                .replace("Seekerinthe", "Seeker in the")
                .replace("Sliceand", "Slice and")
                .replace("Chasethe", "Chase the")
                .replace("inthe"," in the")
                .replace("AFishy", "A Fishy")
                .replace("Fearthe", "Fear the")
                .replace("Hitthe", "Hit the")
                .replace("Kel Thuzad", "Kel'Thuzad")
                .replace("Fromthe", "From the");
    }
}
