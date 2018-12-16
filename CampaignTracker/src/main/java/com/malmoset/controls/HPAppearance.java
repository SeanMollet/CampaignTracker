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
package com.malmoset.controls;

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
