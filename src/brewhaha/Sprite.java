package brewhaha;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
	protected Image img;
	protected double xPos, yPos, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;

    public Sprite(double xPos, double yPos, Image image){
		this.xPos = xPos;
		this.yPos = yPos;
		this.loadImage(image);
		this.visible = true;
	}

	private Rectangle2D getBounds(){
		return new Rectangle2D(this.xPos, this.yPos, this.width-10, this.height-10);
	}

	private void setSize(){
		this.width = this.img.getWidth();
        this.height = this.img.getHeight();
	}
	
	//checks if the two image intersects with each other
	protected boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
	
	//loads the image
	protected void loadImage(Image image){
		try{
			this.img = image;
	        this.setSize();
		} catch(Exception e)	{
			e.printStackTrace();
		}
	}
	
	public void render(GraphicsContext gc){
        gc.drawImage( this.img, this.xPos, this.yPos );
    }
	
	//image getter
	public Image getImage(){
		return this.img;
	}
	
	//x position getter
	public double getXPos(){
		return this.xPos;
	}

	//y position getter
	public double getYPos(){
		return this.yPos;
	}

	public void setDX(int val){
		this.dx = val;
	}

	public void setDY(int val){
		this.dy = val;
	}

	//visibility getter
	public boolean isVisible(){
		return visible;
	}
	
	//visibility setter
	public void vanish(){
		this.visible = false;
	}
	
}
