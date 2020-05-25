/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harshit
 */
public class Server implements Runnable {

    private static int port = 3128;

    DataInputStream inputstring;
    DataOutputStream out;
    ObjectOutputStream output;
    GamePlay obj;
    static ArrayList<ClientHandler> list = new ArrayList<>();

    public static void main(String args[]) {
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        try {
            int i = 0;
            //To change body of generated methods, choose Tools | Templates.
            ServerSocket serverobj = new ServerSocket(port);

            obj = new GamePlay();
            while (true) {

                Socket client = serverobj.accept();
                handle(client, i);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handle(Socket client, int i) {
        String name = null;
//        try {
//            client.setSoTimeout(10000);
//        } catch (SocketException ex) {
//            
//        }
        try {

            output = new ObjectOutputStream(client.getOutputStream());
            out = new DataOutputStream(client.getOutputStream());
            inputstring = new DataInputStream(client.getInputStream());
        } catch (Exception e) {
        }
        try {
            name = inputstring.readUTF();
        } catch (IOException ex) {
            try {
                out.writeUTF("you have been disconnected");
                client.close();
            } catch (IOException ex1) {

            }

        }
        ClientHandler chandler = new ClientHandler(client, out, inputstring, output, i, this, name, obj);
        list.add(chandler);
        obj.Startgame(i, name);
        Thread t1 = new Thread(chandler);
        t1.start();
        broadcast(name + " has joined the game");

    }

    public static void broadcast(String string) {
        //To change body of generated methods, choose Tools | Templates.

        list.forEach(it -> it.sendString(string));

    }

    public void sendAll() {
        removeCheck();
        list.forEach(it
                -> it.sendData()
        );
    }

    public void removeCheck() {
        Iterator<ClientHandler> it = list.iterator();
        while (it.hasNext()) {
            ClientHandler ch = it.next();
            if (GamePlay.userlist.get(list.indexOf(ch)).getMyDeck().size() <=0) {
                ch.sendNotif();
                Server.broadcast(ch.username + " won and left");
                ch.obj.totalPlayers -= 1;
                ch.obj.current = (ch.obj.current) % (ch.obj.totalPlayers + 1);
                GamePlay.userlist.remove(list.indexOf(ch));
                it.remove();
                ch.disconnect();
                return;
            }

        }

    }
}
