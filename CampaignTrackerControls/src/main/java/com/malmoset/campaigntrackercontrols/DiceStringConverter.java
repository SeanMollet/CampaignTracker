/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

//Thanks to http://news.kynosarges.org/2016/10/28/javafx-spinner-for-numbers/
import com.malmoset.dice.Dice;
import javafx.scene.control.*;
import javafx.util.StringConverter;

/**
 * Converts between user-edited strings and {@link Integer} values. Accepts an
 * optional {@link Runnable} that resets the editor on
 * {@link NumberFormatException}, or a {@link TextField} or {@link Spinner} that
 * is preemptively monitored for invalid input during typing, and restricts
 * valid input to a specified range when committed.
 *
 * @author Christoph Nahr
 * @version 1.0.2
 */
public class DiceStringConverter extends StringConverter<Dice.RollTypes> {

    @Override
    public Dice.RollTypes fromString(String s) {
        if (s == null || s.isEmpty()) {
            return Dice.RollTypes.Normal;
        }

        try {
            //String result = s.toUpperCase().trim();
            Dice.RollTypes type = Dice.RollTypes.valueOf(s);
            return type;
        } catch (Exception e) {
            return Dice.RollTypes.Normal;
        }
    }

    @Override
    public String toString(Dice.RollTypes value) {
        if (value == null) {
            value = Dice.RollTypes.Normal;
        }
//        String result = toTitleCase(value.toString());
//        return result;
        return value.toString();
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
