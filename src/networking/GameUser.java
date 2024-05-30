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
    private boolean ready;
    private boolean dead;
    private int place;

    private int winner; 
    // set to 0 if winner, checks if other players are alive, if 0, then siya na lang natitira
    // kumbaga ito yung place niya

    // constructor
    public GameUser(String name, InetAddress address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
        this.score = 0;
        this.ready = false;
        this.dead = false;
        this.place = 0;
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

    // setter if the player is ready
    public void setReady(boolean ready){
        this.ready = ready;
    }

    // getter if the player is ready
    public boolean isReady(){
        return this.ready;
    }

    public void setDead(boolean dead){
        this.dead = dead;
    }

    public boolean isDead(){
        return this.dead;
    }

    public void setWinner(int winner){
        this.winner = winner;
    }

    public boolean isWinner(){
        if (this.winner != 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public void setPlace(int place){
        this.place = place;
    }

    public int getPlace(){
        return this.place;
    }
}
