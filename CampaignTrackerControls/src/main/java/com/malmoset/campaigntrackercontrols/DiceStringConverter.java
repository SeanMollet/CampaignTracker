/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

//Thanks to http://news.kynosarges.org/2016/10/28/javafx-spinner-for-numbers/
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

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
public class DiceStringConverter extends NumberStringConverter {

    @Override
    public Integer fromString(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        try {
            //Trim off the D
            if (s.toLowerCase().contains("d")) {
                s = s.substring(1);
            }
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String toString(Integer value) {
        if (value == null) {
            return "D0";
        }
        return "D" + Integer.toString(value);
    }
}
