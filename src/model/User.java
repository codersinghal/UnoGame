/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author harshit
 */
public class User {

    private ArrayList<Card> mydeck = new ArrayList<>();
    private String name;

    public ArrayList<Card> getMyDeck() {
        return mydeck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
