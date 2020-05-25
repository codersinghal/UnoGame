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
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Card;
import model.ClientData;

/**
 *
 * @author harshit
 */
public class ClientHandler implements Runnable {
    
    GamePlay obj;
    ObjectOutputStream oos;
    DataInputStream dis;
    DataOutputStream out;
    
    Socket client;
    int state;  //index of user added
    Server serverobj;
    String username;
    
    ClientHandler(Socket client, DataOutputStream out, DataInputStream dis, ObjectOutputStream oos, int state, Server serverobj, String username, GamePlay obj) {
        this.client = client;
        this.out = out;
        this.dis = dis;
        this.oos = oos;
        this.state = state;
        this.serverobj = serverobj;
        this.username = username;
        this.obj = obj;
        
    }
    
    @Override
    public void run() {
        
        while (true) {
            try {
                int recieve = dis.readInt();
                recieved(recieve);
            } catch (IOException ex) {
                disconnect();
            }
            
        }
        
    }
    
    public void disconnect() {
        try {
            client.close(); //To change body of generated methods, choose Tools | Templates.
        } catch (IOException ex) {
            
        }
    }
    
    private void recieved(int recieve) {
        switch (recieve) {
            case 100: {
                String chat;
                try {
                    chat = dis.readUTF();
                    
                    Server.broadcast("message" + username+" - "+chat);
                    return;
                    
                } catch (Exception e) {
                }
                
            }
            case 200: {
                obj.draw();
                break;
            }
            case 300: {
                obj.pass();
                break;
            }
            case 400: {
                obj.penalty();
                break;
            }
            case 500: {
                break;
            }
            default: {
                obj.throwCard(recieve);
            }

            //To change body of generated methods, choose Tools | Templates.
        }
        serverobj.sendAll();
        
    }
    
    void sendString(String string) {
        System.out.println(username);
        try {
            out.writeUTF("broadcasted");
            out.flush();
            out.writeUTF(string);
            out.flush();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        //To change body of generated methods, choose Tools | Templates.
    }
    
    void sendData() {
        try {
            out.writeUTF("notbroadcasted");
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ArrayList<Card> mydeck = GamePlay.userlist.get(Server.list.indexOf(this)).getMyDeck();
        System.out.println(mydeck);
        Card midcard = GamePlay.midcard.get(GamePlay.midcard.size() - 1);
        String time = "notimer";
        if (Server.list.indexOf(this) == obj.getCurrent()) {
            time = "timer";
        }
        ClientData out1 = new ClientData(mydeck, obj.getCurrent(), obj.getPlayers(), time, midcard);
        
        try {
            oos.reset();
            oos.writeObject(out1);
            
            System.out.println(out1.getList());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void sendNotif() {
        try {
            out.writeUTF("you won");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
