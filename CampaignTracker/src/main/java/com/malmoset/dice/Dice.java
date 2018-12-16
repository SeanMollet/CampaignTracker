/* 
 * Copyright 2018 Malmoset LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malmoset.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author sean
 */
public class Dice {

    private static long x = 0;

    public static Random rand;

    public static int RollLoot(String coin) {
        if (coin != null && coin.length() > 0) {
            int multiplier = 1;
            int dicecount = 0;
            int dicesize = 0;
            //I really need to redo this with a regex
            if (coin.contains("*")) {
                if (tryParseInt(coin.substring(coin.indexOf("*") + 1))) {
                    multiplier = Integer.parseInt(coin.substring(coin.indexOf("*") + 1));
                }
                if (tryParseInt(coin.substring(coin.indexOf("d") + 1, coin.indexOf("*")))) {
                    dicesize = Integer.parseInt(coin.substring(coin.indexOf("d") + 1, coin.indexOf("*")));
                }
            } else {
                if (tryParseInt(coin.substring(coin.indexOf("d") + 1))) {
                    dicesize = Integer.parseInt(coin.substring(coin.indexOf("d") + 1));
                }
            }
            if (tryParseInt(coin.substring(0, coin.indexOf("d")))) {
                dicecount = Integer.parseInt(coin.substring(0, coin.indexOf("d")));
            }
            int rolls = rollXwithMod(dicecount, dicesize, 0);
            return rolls * multiplier;
        }
        return 0;
    }

    public static int rollXwithMod(int DiceCount, int DiceSize, int Modifier) {
        int roll = 0;
        for (int a = 0; a < DiceCount; a++) {
            roll += roll(DiceSize, RollTypes.Normal);
        }
        roll += Modifier;
        return roll;
    }

    public static int roll(int size, RollTypes rolltype) {
        int roll = singleRoll(size);

        switch (rolltype) {
            case Normal:
                return roll;
            case Advantage:
                int advRoll = singleRoll(size);
                if (advRoll > roll) {
                    return advRoll;
                }
                return roll;
            case Disadvantage:
                int disadvRoll = singleRoll(size);
                if (disadvRoll < roll) {
                    return disadvRoll;
                }
                return roll;
        }
        return roll;
    }

    private static int singleRoll(int size) {
        if (rand == null) {
            rand = new Random();
        }
        return rand.nextInt(size) + 1;
    }

    public static long randomLong() {
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        return x;
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<String> RollTypesList() {
        ArrayList<String> types = new ArrayList<>();
        types.add("Normal");
        types.add("Advantage");
        types.add("Disadvantage");
        return types;
    }

    public enum RollTypes {
        Normal,
        Advantage,
        Disadvantage;

    }
}
