/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpack;

import java.util.ArrayList;
import java.util.Collections;
import model.Card;
import model.CardDeck;
import model.User;

/**
 *
 * @author harshit
 */
public class GamePlay implements GameMethods {

    int totalPlayers;
    int current = 0;
    private boolean flag = false;
    static CardDeck deckobj;
    static ArrayList<Card> protoDeck;
    static ArrayList<Card> midcard = new ArrayList<>();
    static ArrayList<User> userlist = new ArrayList<>();

    GamePlay() {
        for (int i = 0; i < 20; i++) {
            userlist.add(new User());
        }
        deckobj = new CardDeck();
        protoDeck = CardDeck.newDeck();
        Collections.shuffle(protoDeck);
        midcard.add(protoDeck.get(0));
        protoDeck.remove(0);

    }

    @Override
    public void Startgame(int playerno, String name) {
        totalPlayers = playerno;
        distribute(protoDeck, playerno);
        userlist.get(playerno).setName(name);
    }

    @Override
    public int getPlayers() {

        return totalPlayers + 1;

    }

    @Override
    public void distribute(ArrayList<Card> protoDeck, int playerno) {
        for (int i = 0; i < 7; i++) {
            userlist.get(playerno).getMyDeck().add(protoDeck.get(0));
            protoDeck.remove(0);

        }
    }

    @Override
    public void draw() {
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
        Server.broadcast(userlist.get(current).getName() + " drew a card");
    }

    @Override
    public void throwCard(int cardno) {
        Card card = userlist.get(current).getMyDeck().get(cardno);
        if (card.number() == Card.Number.PLUSFOUR) {
            midcard.add(card);
            userlist.get(current).getMyDeck().remove(card);
            plusfour();
            return;
        }
        if (midcard.get(midcard.size() - 1).number() == Card.Number.PLUSFOUR && midcard.get(midcard.size() - 1).color() == Card.Color.NULL) {
            Server.broadcast(userlist.get(current).getName() + " played his turn");
            midcard.add(card);
            userlist.get(current).getMyDeck().remove(card);
            turn();
            return;
        }
        if (card.number() != midcard.get(midcard.size() - 1).number() && card.color() != midcard.get(midcard.size() - 1).color()) {
            System.out.println("returned");
            return;
        }

        midcard.add(card);
        userlist.get(current).getMyDeck().remove(card);
        if (card.number() == Card.Number.PLUSTWO) {
            userlist.get(current).getMyDeck().remove(card);
            plustwo();
        } else if (card.number() == Card.Number.SKIP) {
            skip();
        } else if (card.number() == Card.Number.REV) {
            reverse();
        } else {
            Server.broadcast(userlist.get(current).getName() + " played his turn");
            turn();

        }
    }

    @Override
    public void turn() {
        if (!flag) {
            current = (current + 1) % getPlayers();
        } else {
            current = ((current - 1) % getPlayers() + getPlayers()) % getPlayers();
        }
        System.out.print("User is playing " + current);
    }

    @Override
    public void skip() {

        if (!flag) {
            Server.broadcast(userlist.get((current + 1) % getPlayers()).getName() + " skipped");
            current = (current + 2) % getPlayers();
        } else {
            Server.broadcast(userlist.get((current - 1) % getPlayers()).getName() + " skipped");
            current = ((current - 2) % getPlayers() + getPlayers()) % getPlayers();
        }
    }

    @Override
    public void reverse() {
        flag = !flag;
        Server.broadcast("Turn reversed");
        turn();
    }

    @Override
    public void plustwo() {
        turn();
        Server.broadcast("Plus Two For " + userlist.get(current).getName());
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
    }

    @Override
    public void plusfour() {
        turn();
        Server.broadcast("Plus Four For " + userlist.get(current).getName());
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);

        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
    }

    @Override
    public void pass() {
        Server.broadcast(userlist.get(current).getName() + " drew a card and passed");
        turn();
    }

    @Override
    public void penalty() {
        Server.broadcast("Penalty for " + userlist.get(current).getName());
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
        userlist.get(current).getMyDeck().add(protoDeck.get(0));
        protoDeck.remove(0);
        turn();
    }

    public int getCurrent() {
        return current;
    }

}
