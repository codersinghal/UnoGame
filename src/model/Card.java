/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author harshit
 */
public class Card implements Serializable {

    public enum Number {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX,
        SEVEN, EIGHT, NINE, PLUSTWO, SKIP, REV, PLUSFOUR
    }

    public enum Color {
        RED, BLUE, GREEN, YELLOW, NULL
    }

    private final Number number;
    private final Color color;

    public Card(Number number, Color color) {
        this.number = number;
        this.color = color;
    }

    public Number number() {
        return number;
    }

    public Color color() {
        return color;
    }

    public String toString() {
        return number + " of " + color;
    }

}
