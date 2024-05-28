package brewhaha;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import networking.Client;
import networking.Server;

import java.io.File;
import java.net.InetAddress;


// notes
    // send packets from server to clients regarding the random generated buildings
    // send packets from clients to server regarding the player's actions
    // send packets from server to clients regarding the other player's actions
    // show other player's sprite on the screen as "ghosts"
    // send packets from server to clients regarding all players' score
    // send packets that the game is over for a player
    // send packets that the game is over for all players except the winner
    // continue showing the game screen even for those already dead
    // show the game over screen if all players are dead

    // use GameState for keeping track of all the game states

public class MultiplayerGame{
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Stage stage;
	private Scene splashScene;
    private Group root;
    private String playerName;
    private Server server;
    private Client client;
    private InetAddress serverAddress;
    private boolean playerIsServer;
    private int port;

    private MultiplayerGameTimer timer;

    public MultiplayerGame(Stage stage, Scene splashScene, boolean playerIsServer, Server server, Client client){
        this.root = new Group();
        this.scene = new Scene(root, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.canvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.root.getChildren().add(this.canvas);
        this.gc = canvas.getGraphicsContext2D();
        this.stage = stage;
        this.splashScene = splashScene;
        this.playerIsServer = playerIsServer;
        this.server = server;
        this.client = client;


        //this.startGame();
    }
    
    public void startGame(){
        // create the game timer
        this.timer = new MultiplayerGameTimer(scene, gc, stage, this.server, this.client, this.playerIsServer);
        // start the game
        System.out.println("Starting multiplayer game");
        // start the game timer
        this.timer.start();
    }

    public void addBuilding(Building building){
        // add the building to the list of buildings
        this.timer.addBuilding(building);
    }

    // get the scene
    public Scene getScene(){
        return this.scene;
    }
}