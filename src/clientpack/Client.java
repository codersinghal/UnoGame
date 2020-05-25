/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientpack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Card;
import model.ClientData;

/**
 *
 * @author harshit
 */
public class Client implements Runnable {

    String name;
    ClientGui gui;
    Socket socket;
    ObjectInputStream ois;
    DataInputStream dis;
    DataOutputStream dos;
    static boolean flag=false;
    Thread t1;
    static int count=0;
    public Client(String name, ClientGui gui) throws IOException {
        this.name = name;
        this.gui = gui;
        socket = new Socket("localhost", 3128);
        dos = new DataOutputStream(socket.getOutputStream());
        //oostring = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        dos.writeUTF(name);
        dos.flush();
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        
        while (true) {
            try {
                String check = dis.readUTF();
                System.out.println(check);
                // for recieving object
                if(check.equals("you won"))
                {
                    String s=dis.readUTF();
                    gui.winner();
                    return ;//throw new IOException();
                }
                if (check.equals("notbroadcasted")) {
                    ClientData obj = (ClientData) ois.readObject();
                    Card midcard = obj.getMid();
                    if(obj.getTimer().equals("timer")&&count-obj.getPlayers()>0)
                    {
                         t1=new Thread(new Watch(this));
                        flag=true;
                        t1.start();
                    }
                    count++;
                    System.out.println(obj.getList());
                    System.out.println("MidCard is" + midcard);
                    gui.changeView(obj);
                    
                    Thread.sleep(1000);
                    
                }
               //not recieving object
                if (check.equals("broadcasted")) {                    
                    String message = dis.readUTF();
                    // condition for recieving chats
                    if (message.substring(0, 7).equals("message")) {
                        gui.updateChat(message.substring(7));
                        continue;
                    }
                    System.out.println(message);
                    gui.getNotify(message);
                    Thread.sleep(1000);
                    
                }
                
            } catch (Exception e) {
                try {
                    ois.close();
                    dos.close();
                    dis.close();
                    socket.close();
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
            
        }
    }

    public void perform(int i) {
        if(flag)
        {
            gui.noTimer();
            t1.suspend();
            
        }
        
            
        try {
            dos.writeInt(i);
            if(i==400)
            {
                t1.suspend();
            }
            if (i == 100) {
                String message = gui.getChat();
                if (!message.equals(null)) {
                    dos.writeUTF(message);
                    dos.flush();
                } else {
                    dos.writeUTF("");
                    dos.flush();
                }
            }            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setTime(int time)
    {
        gui.updateTimer(time);
    }
    
}
