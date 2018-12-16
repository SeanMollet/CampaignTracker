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
public class Styles {

    public static String getGreen() {
        return "    -fx-background-color:\n"
                + "        linear-gradient(#f0ff35, #a9ff00),\n"
                + "        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\n"
                + "    -fx-background-radius: 6, 5;\n"
                + "    -fx-background-insets: 0, 1;\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n"
                + "    -fx-text-fill: #395306;";
    }

    public static String getSmall() {
        return "-fx-font-size: 9px;";
    }
}
