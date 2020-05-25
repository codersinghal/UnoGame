/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpack;

import java.util.ArrayList;
import model.Card;

/**
 *
 * @author harshit
 */
public interface GameMethods {

    /**
     *
     * @param playerno
     * @param name
     */
    public void Startgame(int playerno, String name);

    /**
     *
     * @return player numbers
     */
    public int getPlayers();

    /**
     *
     * @param protoDeck
     * @param playerno
     */
    public void distribute(ArrayList<Card> protoDeck, int playerno);

    /**
     * draw card from deck
     */
    public void draw();

    /**
     *
     * @param cardno perform action on throwing card
     */
    public void throwCard(int cardno);

    /**
     * regulate turn
     */
    public void turn();

    /**
     *
     */
    public void skip();

    /**
     *
     */
    public void reverse();

    /**
     *
     */
    public void plustwo();

    /**
     *
     */
    public void plusfour();

    /**
     *
     */
    public void pass();

    /**
     * add two cards on time up
     */
    public void penalty();

}
