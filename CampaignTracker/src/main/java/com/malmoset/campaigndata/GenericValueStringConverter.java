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
package com.malmoset.campaigndata;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.util.StringConverter;

/**
 *
 * @author sean
 */
public class GenericValueStringConverter extends StringConverter<GenericValueString> {

    // ------------------------------------------------------ Private properties
    final Locale locale;
    final String pattern;
    final NumberFormat numberFormat;

    // ------------------------------------------------------------ Constructors
    public GenericValueStringConverter() {
        this(Locale.getDefault());
    }

    public GenericValueStringConverter(Locale locale) {
        this(locale, null);
    }

    public GenericValueStringConverter(String pattern) {
        this(Locale.getDefault(), pattern);
    }

    public GenericValueStringConverter(Locale locale, String pattern) {
        this(locale, pattern, null);
    }

    public GenericValueStringConverter(NumberFormat numberFormat) {
        this(null, null, numberFormat);
    }

    GenericValueStringConverter(Locale locale, String pattern, NumberFormat numberFormat) {
        this.locale = locale;
        this.pattern = pattern;
        this.numberFormat = numberFormat;
    }

    @Override
    public GenericValueString fromString(String value) {
        // If the specified value is null or zero-length, return null
        if (value == null) {
            return null;
        }

        value = value.trim();

        if (value.length() < 1) {
            return null;
        }

        return new GenericValueString(value);

    }

    @Override
    public String toString(GenericValueString value) {
        return value.getValue();
    }

}
