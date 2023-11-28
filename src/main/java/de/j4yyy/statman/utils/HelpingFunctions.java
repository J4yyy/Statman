package de.j4yyy.statman.utils;

import java.util.Random;

public class HelpingFunctions {

    private static final String[] adjectives = {"awesome", "creative", "brilliant", "fantastic", "epic", "cool", "stellar", "fierce", "great", "amazing", "radical", "spectacular", "stellar", "tremendous", "excellent", "wonderful", "incredible", "mind-blowing", "impressive", "outstanding", "superb", "phenomenal", "fabulous", "marvelous", "extraordinary"};

    public static String generateName() {
        Random random = new Random();
        return adjectives[random.nextInt(adjectives.length)] + "-" + adjectives[random.nextInt(adjectives.length)];
    }

}