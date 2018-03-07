/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Thanks to
 * https://codereview.stackexchange.com/questions/165219/implementation-of-a-fraction-class-in-java
 * For the base and gcd function used here
 *
 * @author sean
 */
public final class Fraction implements Comparable {

    private long numerator;

    private long denominator;

    public Fraction(String inValue) {
        inValue = inValue.trim();

        //If they give us garbage, give them a 0 back
        if (inValue == null || inValue == "") {
            numerator = 0;
            denominator = 1;
            return;
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
            }
            return;
        }
    }

    public Fraction(@JsonProperty("Numerator") Long numerator, @JsonProperty("Denominator") Long denominator) {
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
    }

    public Fraction(Long num) {
        this.numerator = num;
        this.denominator = 1;
    }

    public Fraction(int num) {
        this.numerator = num;
        this.denominator = 1;
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

            long leftScale = this.numerator * right.denominator;
            long rightScale = right.numerator * this.denominator;

            return Long.compare(leftScale, leftScale);
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

}
