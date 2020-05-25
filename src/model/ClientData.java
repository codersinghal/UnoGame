/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author harshit
 */
public class ClientData implements Serializable {

    private ArrayList<Card> userlist = new ArrayList<>();
    int current, noOfPlayers;
    Card midcard;
    String timer;

    public ClientData(ArrayList<Card> userlist, int current, int noOfPlayers, String timer, Card midcard) {
        this.userlist = userlist;
        this.current = current;
        this.noOfPlayers = noOfPlayers;
        this.timer = timer;
        this.midcard = midcard;
    }

    public ArrayList<Card> getList() {
        return userlist;
    }

    public int getCurr() {
        return current;
    }

    public Card getMid() {
        return midcard;
    }

    public int getPlayers() {
        return noOfPlayers;
    }

    public String getTimer() {
        return timer;
    }

}
