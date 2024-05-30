package brewhaha;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import networking.Client;
import networking.GameUser;
import networking.Server;
import networking.Server.ServerCallback;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.TreeUI;

class GameTimer extends AnimationTimer {
	private Stage stage;
	private long startSpawn;
	private GraphicsContext gc;
	private Witch witch;
	private Scene scene;
	private ArrayList<Building> buildings;

	private static boolean shoot;
	private double backgroundx;
	private double time;
	private int win;	//1 if the player wins, 0 if lose
	
	private Image background = new Image( "images/bg3.png" );
	private int isMultiplayer;
	private String playerName;
	private int port;
	private Client client;
	private Server server;
	private int isServer;

	public final static int HEIGHT_WITCH = 60;
	public final static int XPOS = 790;
	public final static int BACKGROUND_SPEED = 1;
	public final static double SPAWN_DELAY = 5.00;
    
	// Single
    GameTimer(Scene scene, GraphicsContext gc, Stage stage, int isMultiplayer) {
    	Random r = new Random();
    	this.gc = gc;
    	this.scene = scene;    	
    	this.stage = stage;
    	this.win = 0;
    	this.witch = new Witch("Apollo", r.nextInt((Game.WINDOW_HEIGHT-GameTimer.HEIGHT_WITCH)-(50))+50);
    	this.buildings = new ArrayList<Building>();
    	this.startSpawn = System.nanoTime();
    	this.prepareActionHandlers();
    	this.spawnUfo();
    }

	// Server Multiplayer
    GameTimer(Scene scene, GraphicsContext gc, Stage stage, int isMultiplayer, int isServer, Server server) {
    	Random r = new Random();
    	this.gc = gc;
    	this.scene = scene;    	
    	this.stage = stage;
    	this.win = 0;
    	this.witch = new Witch("Apollo", r.nextInt((Game.WINDOW_HEIGHT-GameTimer.HEIGHT_WITCH)-(50))+50);
    	this.buildings = new ArrayList<Building>();
    	this.startSpawn = System.nanoTime();
    	this.prepareActionHandlers();
    	this.spawnUfo();
		this.server = server;
    }

	// Client Mulitplayer
    GameTimer(Scene scene, GraphicsContext gc, Stage stage, int isMultiplayer, int isServer, Client client) {
    	Random r = new Random();
    	this.gc = gc;
    	this.scene = scene;    	
    	this.stage = stage;
    	this.win = 0;
    	this.witch = new Witch("Apollo", r.nextInt((Game.WINDOW_HEIGHT-GameTimer.HEIGHT_WITCH)-(50))+50);
    	this.buildings = new ArrayList<Building>();
    	this.startSpawn = System.nanoTime();
    	this.prepareActionHandlers();
    	this.spawnUfo();
		this.client = client;
    }
    
    @Override
	public void handle(long currentNanoTime)
    {
		this.redrawBackgroundImage();
        this.autoSpawn(currentNanoTime);
                
        this.renderSprites();
        this.moveSprites();
        
        this.drawScore();

        
        //calls the method to set the game over scene once the spaceship died
        if(!this.witch.isAlive()) {
        	this.stop();
        	this.setGameOver(this.win, this.isMultiplayer);		// draw Game Over text
        }
    }
    
    //Sets the game to a win and the spaceship dies
    void setWin() {
    	this.win = 1;
    	this.witch.die();
    }
    
    private void redrawBackgroundImage() {
		// clear the canvas
        this.gc.clearRect(0, 0, Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);

        // redraw background image (moving effect)
        this.backgroundx += GameTimer.BACKGROUND_SPEED;

        this.gc.drawImage( background, this.backgroundx-this.background.getWidth(), 0 );
        this.gc.drawImage( background, this.backgroundx, 0 );
        
        if(this.backgroundx>=Game.WINDOW_WIDTH) 
        	this.backgroundx = Game.WINDOW_WIDTH-this.background.getWidth();
    }
    
  //Method for spawning a power-up every 10secs and 3 UFOs every 5secs
    private void autoSpawn(long currentNanoTime) {
    	double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 300000000.0;
       
        
        // spawn UFO
        if(spawnElapsedTime > GameTimer.SPAWN_DELAY) {
        	this.spawnUfo();
        	this.startSpawn = System.nanoTime();
        }
    }    

   
    
    //Time setter
    void setTime(double time) {
    	this.time = time;
    }

	private void renderSprites() {
    	// draw Spaceship
        this.witch.render(this.gc);
        
        // draw Sprites in ArrayLists
        for (Building building : this.buildings )
        	building.render( this.gc );
    }
    
	//Calls the move method of all the sprites
    private void moveSprites() {
        //this.moveSpaceship();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    	executorService.schedule(this::moveSpaceship, 0, TimeUnit.SECONDS);
    
		this.moveUFO();
    }
	
    //Catches the left and right key presses for the spaceship's movement
	private void prepareActionHandlers() {
    	this.scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                if(code.equals("SPACE")) {
                	GameTimer.shoot = true;
                }
            }
        });
    	
    	this.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
              
                if(code.equals("SPACE")) {
                	GameTimer.shoot = false;
                }
            }
        });
    }
	
    //Gets called in handle() to move the spaceship
	private void moveSpaceship() {
		if (GameTimer.shoot)
        	this.witch.fly();
        else {
        	this.witch.setDX(0);
			this.witch.setDY(0);
			this.witch.move();
        }
	}
	
	
	//Method for drawing the player's score in the status bar
	private void drawScore(){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.gc.setFill(Color.YELLOW);
		this.gc.fillText("SCORE:", 460, 30);
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText(witch.getScore()+"", 550, 30);
	}
	
	

     //If they are outside the screen, they get removed from the ArrayList
	private void moveUFO() {
		for(int i = 0; i < this.buildings.size(); i++){
			Building m = this.buildings.get(i);
			if(m.isVisible()){
				m.move();
				m.checkCollision(this.witch);
				if((m.getXPos() < this.witch.getXPos()) && m.getPos() == 0 && m.getPassed() == false){
					this.witch.gainScore(1);
					m.setPassed();
				}
			
			}
			else this.buildings.remove(i);
		}
	}
	
	
	private void spawnUfo(){
		System.out.println("SPAWNING");
		int yPos, xPos = GameTimer.XPOS;
		Random r = new Random();
		
		// BOTTOM BUILDING
		yPos = r.nextInt(Game.WINDOW_HEIGHT-100) + 100;
		System.out.println(yPos);
		this.buildings.add(new Building(xPos, yPos, r.nextInt(2), 1));
		
		
		// TOP BUILDING
		yPos -= 650;
		this.buildings.add(new Building(xPos, yPos, r.nextInt(2) + 2, 0));
		
	}

	
	//Method for displaying the Game Over scene
	private void setGameOver(int type, int isMultiplayer) {
		if (isMultiplayer == 0) {
			GameOverScene gScene = new GameOverScene(type, witch.getScore());
			this.stage.setScene(gScene.getScene());
		}
		else {
			if (isServer == 1) {
				System.out.println("DEAD: " + playerName);
				System.out.println("DEAD: " + playerName);
				this.client.getGameUser().setDead(true);
				System.out.println("DEAD: " + playerName);
			}
			else {			
				String deathMessage = "die " + playerName;
				try {
					System.out.println("DEAD: " + playerName);
					System.out.println("DEAD: " + playerName);
					this.client.sendPacket(deathMessage.getBytes(), InetAddress.getLocalHost(), port);
					this.client.getGameUser().setDead(true);
					System.out.println("DEAD: " + playerName);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				MultiplayerGameOverScene gScene = new MultiplayerGameOverScene(type, witch.getScore(), client);
				this.stage.setScene(gScene.getScene());
			}
		}
	}
}

