package networking;

import brewhaha.Building;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    // constants for the server state
    public final static int GAME_START=0;
    public final static int IN_PROGRESS=1;
    public final static int GAME_END=2;
    public final static int WAITING_FOR_PLAYERS=3;
    public final static int INITIALIZING_SERVER=4;

    public final static String SERVER_ADDRESS = "192.168.5.180";
    
    private List<GameUser> players;
    private List<Building> buildings;

    public GameState() {
        this.players = new ArrayList<>();
        this.buildings = new ArrayList<>();
    }

    // method to add a player to the player list
    public void addPlayer(GameUser player){
        players.add(player);
    }

    // method to remove a player from the player list
    public void removePlayer(GameUser player){
        players.remove(player);
    }

    // method to get the player list
    public List<GameUser> getPlayers(){
        return players;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setPlayers(List<GameUser> players) {
        this.players = players;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}