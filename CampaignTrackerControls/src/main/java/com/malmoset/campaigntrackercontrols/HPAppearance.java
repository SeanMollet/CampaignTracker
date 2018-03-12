/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

/**
 *
 * @author sean
 */
public class HPAppearance {

    public static String Appearance(int CurrentHP, int maxHP) {
        if (maxHP == 0) {
            return "Healthy";
        }
        float health = (float) CurrentHP / (float) maxHP;
        if (health >= 1.0f) {
            return "Healthy";
        }
        if (health >= 0.9f) {
            return "Bruised";
        }
        if (health >= 0.7f) {
            return "Banged Up";
        }
        if (health >= 0.5f) {
            return "Bloody";
        }
        if (health >= 0.3f) {
            return "Ragged";
        }
        if (health >= 0.1f) {
            return "Bloody as hell";
        }
        if (health < 0.1f) {
            return "Death's door";
        }
        return "Excellent";
    }
}
