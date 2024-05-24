package networking;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


// class for the server to be used in MultiplayerScene.java
public class Server implements Runnable {
    
    // datagram socket for the server
    private DatagramSocket socket;

    // state of the server 
    private int state;

    // player list array
    private ArrayList<GameUser> players = new ArrayList<GameUser>();

    // thread for the server
	Thread t = new Thread(this);

    // constructor
    public Server(int port){
        this.state = GameState.INITIALIZING_SERVER;
        try {
            this.socket = new DatagramSocket(port, InetAddress.getLocalHost());
        } catch (SocketException e) {
            System.out.println("Error creating server socket");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // start the server thread
        t.start();
        System.out.println("Server thread started");
    }

    // run method for the server
    public void run(){
        this.state = GameState.WAITING_FOR_PLAYERS;
        System.out.println("Server waiting for players");
        
        // add the server to the player list
        addPlayer(new GameUser("Server", socket.getInetAddress(), socket.getPort()));
        
        // magical while loop
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);

                // if the packet is a connect packet, add the player to the player list
                if (new String(packet.getData()).trim().startsWith("connect")){
                    System.out.println(new String(packet.getData()).trim().substring(7) + " connected");
                    // add the player to the player list
                    addPlayer(new GameUser(new String(packet.getData()).trim().substring(7), packet.getAddress(), packet.getPort()));
                    // print the number of players
                    System.out.println("Number of players: " + players.size());
                }

                // if the packet is a chat message, send it to all clients
                else if (new String(packet.getData()).trim().startsWith("chat")){
                    sendToClients(packet.getData());
                }

                // if the packet is a ready packet, send it to all clients
                else if (new String(packet.getData()).trim().startsWith("ready")){
                    sendToClients(packet.getData());
                }
            } catch (IOException e) {
                System.out.println("Error receiving packet");
            }
        }
    }

    // getter for the server state
    public int getState(){
        return state;
    }

    // setter for the server state
    public void setState(int state){
        this.state = state;
    }

    // send a packet to connected clients
    public void sendToClients(byte[] data){
        for (int i = 1; i < players.size(); i++){
            GameUser player = players.get(i);
            System.out.println("Sending to " + player.getName() + " at " + player.getAddress() + " on port " + player.getPort());
            DatagramPacket packet = new DatagramPacket(data, data.length, player.getAddress(), player.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.out.println("Error sending packet");
            }
        }
    }
    // add a player to the player list
    public void addPlayer(GameUser player){
        players.add(player);
    }

    // get the player list
    public ArrayList<GameUser> getPlayers(){
        return players;
    }

    // get address of the server
    public InetAddress getAddress(){
        return socket.getInetAddress();
    }

    // get port of the server
    public int getPort(){
        return socket.getPort();
    }

    // close the server
    public void close(){
        socket.close();
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
