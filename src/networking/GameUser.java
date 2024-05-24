package networking;

import java.net.InetAddress;

// class of a player in the game
public class GameUser {

    // string to store the player name
    private String name;

    // string to store the player's IP address
    private InetAddress address;

    // integer to store the player's port number
    private int port;

    private int score;
    private int serverId;

    // constructor
    public GameUser(String name, InetAddress address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
    }

    // getter for the player name
    public String getName(){
        return name;
    }

    // getter for the player's IP address
    public InetAddress getAddress(){
        return address;
    }

    // getter for the player's port number
    public int getPort(){
        return port;
    }

    // setter for the player server id
    public void setServerId(int id){
        this.serverId = id;
    }

    // getter for the player server id
    public int getServerId(){
        return serverId;
    }


    // setter for the player name
    public void setName(String name){
        this.name = name;
    }
}
