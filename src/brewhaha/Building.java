package brewhaha;
import java.util.Random;

import javafx.scene.image.Image;

public class Building extends Sprite {
	private double speed;
	private int position;
	private boolean passed;
	private boolean gainedScore;	//boolean value if the UFO already added a score to the spaceship
	
	// Array of building images
    private final static Image[] BUILDING_IMAGES = {
        //new Image("images/1.png"),
        new Image("images/2.png"),
        //new Image("images/3.png"),
        //new Image("images/4.png"),
        new Image("images/5.png"),
        //new Image("images/6.png"),
        //new Image("images/flipped_1.png"),
        new Image("images/flipped_2.png"),
        //new Image("images/flipped_3.png"),
        //new Image("images/flipped_4.png"),
        new Image("images/flipped_5.png"),
        //new Image("images/flipped_6.png")
    };

	Building(int x, int y, int num, int position){
		super(x, y, BUILDING_IMAGES[num]);
		Random r = new Random();
		this.position = position; // 0 if top, 1 if bottom
		this.speed = 6;
		this.passed = false;
	}
	
	//Moves the UFOs
	void move() {
		this.xPos -= this.speed;
	}
	
	//Moves the UFOs
	void setPassed() {
		this.passed = true;
	}
	
	//Moves the UFOs
	Boolean getPassed() {
		return this.passed;
	}
	
	//Moves the UFOs
	int getPos() {
			return this.position;
		}
	
	void checkCollision(Spaceship spaceship){
	
		if(this.collidesWith(spaceship)){
			spaceship.die();
		}
	}
	
}