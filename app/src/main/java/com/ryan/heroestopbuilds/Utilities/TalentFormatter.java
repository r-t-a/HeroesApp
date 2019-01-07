package com.ryan.heroestopbuilds.Utilities;

import java.util.ArrayList;

public class TalentFormatter {

    private static final int levelMod = 5;

    public static String prettyPrinter(ArrayList<String> popularSkills) {
        ArrayList<String> finalList = new ArrayList<>();
        String lgSpacing = String.format("%" + 3 + "s", "");
        String smSpacing = String.format("%" + 1 + "s", "");

        for (int i = 0; i <= levelMod; i++) {
            if (i <= 2) {
                finalList.add("Level " + (1 + 3 * i) + ": " + lgSpacing + popularSkills.get(i));
            } else {
                finalList.add("Level " + (1 + 3 * i) + ": " + smSpacing + popularSkills.get(i));
            }
        }
        finalList.add("Level 20:  Player's Choice");
        String formattedList = finalList.toString()
                .replace(",", "\n")
                .replace("[", " ")
                .replace("]", "")
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
        return formattedList
                .replace("Jug of 1\n000 Cups", "Jug of 1,000 Cups")
                .replace("Kneel\n Peasants!", "Kneel Peasants!");
    }
}
