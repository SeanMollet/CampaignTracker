/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Loot;

//Thanks to http://news.kynosarges.org/2016/10/28/javafx-spinner-for-numbers/
import com.malmoset.campaigndata.Loot.LootItem;
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
public class LootTypeStringConverter extends StringConverter<LootItem.LootType> {

    @Override
    public LootItem.LootType fromString(String s) {
        if (s == null || s.isEmpty()) {
            return LootItem.LootType.MONEY;
        }

        try {
            String result = s.toUpperCase().replace(" ", "");
            LootItem.LootType type = LootItem.LootType.valueOf(result);
            return type;
        } catch (Exception e) {
            return LootItem.LootType.MONEY;
        }
    }

    @Override
    public String toString(LootItem.LootType value) {
        if (value == null) {
            value = LootItem.LootType.MONEY;
        }
        switch (value) {
            case MONEY:
                return "Money";
            case GEMSTONE:
                return "Gemstone";
            case ARTOBJECT:
                return "Art Object";
            case MAGICITEM:
                return "Magic Item";
        }
        return "Money";
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
