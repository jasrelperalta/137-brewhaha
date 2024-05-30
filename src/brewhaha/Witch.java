package brewhaha;

import java.util.Random;
import javafx.scene.image.Image;

class Witch extends Sprite {
	private String name;
	private boolean alive;
	private int score;
	private double velocityY; //move bird up/down speed.
    private double gravity;

	private final static Image SPACESHIP_IMAGE = new Image("images/witch.png");
	private final static Image FLY_IMAGE = new Image("images/witch2.png");	
	private final static double INITIAL_X = 100;
	public final static int SPACESHIP_SPEED = 2;
	public final static int ADDED_SPEED = 3;
	public final static int jumpHeight = 100;

	Witch(String name, double y){
       	super(Witch.INITIAL_X, 200,Witch.SPACESHIP_IMAGE);
       	Random r = new Random();
		this.name = name;
		this.alive = true;
		this.velocityY = 0;
		this.gravity = 0.1;
		//this.speed = SPACESHIP_SPEED;
	}

	
	//Score getter
	int getScore(){
		return this.score;
	}

	
	//Die setter
    void die(){
    	this.alive = false;
    }
    
    //Deducts a damage to the spaceship's strength
    void getDamage(int damage) {
    	//this.die();
    }
	
    //Method for jumping
    public void fly(){
    	this.loadImage(FLY_IMAGE);
        velocityY = -5; // JUMP HEIGHT
        
    }
    
    //Adds 1 to the score when a UFO was shot
    void gainScore(int increase){
    	this.score+=increase;
    	System.out.println("Score: "+score);
    }
    
    //Alive state getter
    boolean isAlive(){
    	return this.alive;
    } 

    //Method for moving
    void move() {
    	//bird
        velocityY += gravity;
        this.yPos += velocityY;
        this.yPos = Math.max(this.yPos, 0); //apply gravity to current bird.y, limit the bird.y to top of the canvas
        this.loadImage(SPACESHIP_IMAGE);
        
        if(this.yPos > 600) {
        	this.die();
        }
	}
	
}