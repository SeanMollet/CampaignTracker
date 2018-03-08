/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Thanks to
 * https://codereview.stackexchange.com/questions/165219/implementation-of-a-fraction-class-in-java
 * For the base and gcd function used here
 *
 * @author sean
 */
public final class Fraction implements Comparable, ObservableValue {

    @JsonProperty("Numerator")
    private long numerator;
    @JsonProperty("Denominator")
    private long denominator;
    @JsonIgnore
    private StringProperty strValue;

    @JsonIgnore
    public final String getStrValue() {
        return strValue.get();
    }

    @JsonIgnore
    public final void setStrValue(String value) {
        this.fromString(value);
        strValue.set(this.toString());
    }

    @JsonIgnore
    public StringProperty strValueProperty() {
        return strValue;
    }

    private void ConfigProp() {
        strValue = new SimpleStringProperty(this.toString());
        strValue.addListener((observable, oldValue, newValue) -> {
            fromString(newValue);
        });
    }

    public Fraction() {
        this(0l, 1l, true);
    }

    public Fraction(int num) {
        this((long) num, 1l);
    }

    public Fraction(Long num) {
        this(num, 1l);
    }

    public Fraction(String inValue) {
        fromString(inValue);
    }

    public Fraction(@JsonProperty("Numerator") Long numerator, @JsonProperty("Denominator") Long denominator) {
        this(numerator, denominator, true);
    }

    public Fraction(Long numerator, Long denominator, boolean wantToReduce) {
        if (denominator == 0) {
            throw new IllegalArgumentException("The denominator is zero.");
        }
        if (numerator == 0) {
            this.numerator = 0;
            this.denominator = 1;
        } else {
            this.numerator = numerator;
            this.denominator = denominator;
        }
        if (denominator < 0) {
            this.numerator = -1 * this.numerator;
            this.denominator = -1 * this.denominator;
        }
        if (wantToReduce == true) {
            this.reduce();
        }
        ConfigProp();
    }

    private boolean fromString(String inValue) {
        inValue = inValue.trim();
        String oldValue = this.toString();

        //If they give us garbage, give them a 0 back
        if (inValue == null || inValue == "") {
            numerator = 0;
            denominator = 1;
            return true;
        }

        // Not special, is it a Fraction?
        int slashPos = inValue.indexOf('/');

        if (slashPos > -1) {
            Long wholeNumberValue = 0l;
            //Check if there is a whole value on the left
            int spaceLoc = inValue.indexOf(' ');
            if (spaceLoc > -1) {
                String wholeValueStr = inValue.substring(0, spaceLoc);
                inValue = inValue.substring(spaceLoc).trim();
                if (Utilities.tryParseLong(inValue)) {
                    wholeNumberValue = Long.parseLong(inValue);
                }
                slashPos = inValue.indexOf('/');
            }
            // string is in the form of numerator/denominator
            String denom = inValue.substring(slashPos + 1);
            String Num = inValue.substring(0, slashPos);
            if (Utilities.tryParseLong(denom) && Utilities.tryParseLong(Num)) {
                denominator = Long.parseLong(denom);
                numerator = Long.parseLong(Num) + (wholeNumberValue * denominator);
                ConfigProp();
                onChange(oldValue, this.toString());
                return true;
            }
        }
        return false;
    }

    public int toInt() {
        if (this.denominator != 0) {
            return (int) (this.numerator / this.denominator);
        }
        return 0;
    }

    @Override
    public String toString() {
        if (this.denominator == 1) {
            return Long.toString(numerator);
        } else if (this.denominator == 0) {
            return "NaN";
        } else {
            if (this.numerator > this.denominator) {
                long whole = numerator / denominator;
                long temp_num = numerator % denominator;

                return Long.toString(whole) + " " + Long.toString(temp_num) + "/" + Long.toString(this.denominator);
            } else {
                return Long.toString(this.numerator) + "/" + Long.toString(this.denominator);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction f = ((Fraction) obj);
        Long gcd = gcd(numerator, denominator);
        f.reduce();
        if (this.numerator / gcd == f.numerator && this.denominator / gcd == f.denominator) {
            return true;
        } else {
            return false;
        }
    }

    public Fraction reduce() {
        Long gcd = gcd(numerator, denominator);
        numerator = numerator / gcd;
        denominator = denominator / gcd;
        return this;
    }

    public static Fraction add(Fraction f1, Fraction f2, boolean w) {
        return new Fraction(f1.numerator * f2.denominator + f2.numerator * f1.denominator, f1.denominator * f2.denominator, w);
    }

    public static Fraction sub(Fraction f1, Fraction f2, boolean w) {
        return new Fraction(f1.numerator * f2.denominator - f2.numerator * f1.denominator, f1.denominator * f2.denominator, w);
    }

    public static Fraction mul(Fraction f1, Fraction f2, boolean w) {
        return new Fraction(f1.numerator * f2.numerator, f1.denominator * f2.denominator, w);
    }

    public static Fraction div(Fraction f1, Fraction f2, boolean w) {
        return new Fraction(f1.numerator * f2.denominator, f1.denominator * f2.numerator, w);
    }

    public static long gcd(long a, long b) {
        if (a < 0) {
            a = -1 * a;
        }
        if (b < 0) {
            b = -1 * b;
        }
        long t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Fraction) {
            Fraction right = (Fraction) o;
            if (this.denominator == 0) {
                return -1;
            }
            if (right.denominator == 0) {
                return 1;
            }

            Long leftScale = this.numerator * right.denominator;
            Long rightScale = right.numerator * this.denominator;

            return leftScale.compareTo(rightScale);
        }

        if (o instanceof Long || o instanceof Integer) {
            Long testval = (Long) o;
            if (this.numerator < this.denominator && testval > 0) {
                return -1;
            }
            Long whole = this.numerator / this.denominator;
            Long mod = this.numerator % this.denominator;

            if (whole > testval) {
                return 1;
            }
            if (whole == testval) {
                if (mod == 0) {
                    return 0;
                }
                if (mod > 0) {
                    return 1;
                }

            }
            return -1;
        }
        return 1;
    }

    private List<ChangeListener> cListener;

    private void onChange(String oldValue, String newValue) {
        if (cListener != null) {
            for (ChangeListener listener : cListener) {
                listener.changed(this, oldValue, newValue);
            }
        }
    }

    @Override
    public void addListener(ChangeListener listener) {

        if (cListener == null) {
            cListener = new ArrayList<>();
        }
        cListener.add(listener);
    }

    @Override
    public void removeListener(ChangeListener listener) {
        if (cListener != null && cListener.contains(listener)) {
            cListener.remove(listener);
        }
    }

    @Override
    @JsonIgnore
    public Object getValue() {
        return this;
    }

    //private List<InvalidationListener> Ilistener;
    @Override
    public void addListener(InvalidationListener listener) {
//We're never invalid
//        if (Ilistener == null) {
//            Ilistener = new ArrayList<>();
//        }
//        Ilistener.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
//We're never invalid
//        if (Ilistener != null && Ilistener.contains(listener)) {
//            Ilistener.remove(listener);
//        }
    }

    public long getNumerator() {
        return numerator;
    }

    public void setNumerator(long numerator) {
        String oldValue = toString();
        this.numerator = numerator;
        onChange(oldValue, this.toString());
    }

    public long getDenominator() {
        return denominator;
    }

    public void setDenominator(long denominator) {
        String oldValue = toString();
        this.denominator = denominator;
        onChange(oldValue, this.toString());
    }

}
