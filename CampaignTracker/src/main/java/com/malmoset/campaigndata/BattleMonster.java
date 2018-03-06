/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author sean
 */
public class BattleMonster extends Monster {

    private ObjectProperty<LocalDateTime> spawned;
    private IntegerProperty index;
    private IntegerProperty hPtoChange;
    private BooleanProperty persuaded;
    private IntegerProperty savingRoll;

    private boolean currentHPSet = false;
    private int currentHP;

}
