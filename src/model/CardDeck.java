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
public class CardDeck {

    private static ArrayList<Card> protoDeck = new ArrayList<>();

    public CardDeck() {

        for (Card.Number number : Card.Number.values()) {
            for (Card.Color color : Card.Color.values()) {
                if ((number != Card.Number.PLUSFOUR && color != Card.Color.NULL)) {
                    protoDeck.add(new Card(number, color));
                    protoDeck.add(new Card(number, color));
                }
            }
        }
        protoDeck.add(new Card(Card.Number.PLUSFOUR, Card.Color.NULL));
        protoDeck.add(new Card(Card.Number.PLUSFOUR, Card.Color.NULL));
        protoDeck.add(new Card(Card.Number.PLUSFOUR, Card.Color.NULL));
        protoDeck.add(new Card(Card.Number.PLUSFOUR, Card.Color.NULL));

    }

    public static ArrayList<Card> newDeck() {
        return new ArrayList<Card>(protoDeck); // Return copy of prototype deck
    }

}
