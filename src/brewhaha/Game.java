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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class Game {
	private Stage stage;
	private Scene splashScene;	// the splash scene
	private Scene gameScene;		// the game scene
	private MultiplayerScene mutiplayerScene;	// the scene
	private AboutScene aboutScene;		//about scene
	private Group root;
	private Canvas canvas;
	
	public final static int WINDOW_WIDTH = 800;
	public final static int WINDOW_HEIGHT = 500;
	
	public Game(){
		this.root = new Group();
		this.gameScene = new Scene( root );
		this.canvas = new Canvas( Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT );
        this.root.getChildren().add( this.canvas );
	}
	
	//Sets the stage
	public void setStage(Stage stage) {
		this.stage = stage;
		stage.setTitle( "Brewhaha" );
        
		this.initSplash(stage);
		
		stage.setScene( this.splashScene );
        stage.setResizable(false);
		stage.show();
	}
	
	//Method for initializing the splash scene
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
        ImageView imageview = new ImageView(new Image("images/mainmenu.jpg", true));
        imageview.setFitHeight(Game.WINDOW_HEIGHT);
        imageview.setFitWidth(Game.WINDOW_WIDTH);
        root.getChildren().addAll(imageview,this.createVBox());
        this.splashScene = new Scene(root);
	}
    
    private VBox createVBox() {
    	VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(165,220,00,00));
        vbox.setSpacing(8);

        Button b1 = new Button();			// start
        Button b2 = new Button();			// mp
        Button b3 = new Button();			// about/exit
        

        b1.setStyle("-fx-background-color: transparent;");
        b2.setStyle("-fx-background-color: transparent;");
        b3.setStyle("-fx-background-color: transparent;");
        
        vbox.getChildren().add(b1);
        vbox.getChildren().add(b2);
        vbox.getChildren().add(b3);
        b1.setPrefSize(120, 35);
        b2.setPrefSize(150, 35);
        b3.setPrefSize(120, 35);
        
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setGame(stage);		// changes the scene into the game scene
            }
        });
        
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setMultiplayer(stage);		// changes the scene into the multiplayer scene
            }
        });
        
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setAbout(stage);		// changes the scene into the about scene
            }
        });
        
        return vbox;
    }
	
    //Method for setting game scene
	private void setGame(Stage stage) {
        stage.setScene( this.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        
        GameTimer gameTimer = new GameTimer(gameScene, gc, this.stage);
        gameTimer.start();
        
	}	
	
	//Method for setting multiplayer scene
	private void setMultiplayer(Stage stage) {
        // Create a dialog asking for IP Address
        TextInputDialog ipDialog = new TextInputDialog();
        ipDialog.setTitle("Enter IP Address");
        ipDialog.setHeaderText("Please enter IP Address:");
        Optional<String> ipAddressResult = ipDialog.showAndWait();
        if (ipAddressResult.isPresent()) {
            try {
                InetAddress serverAddress = InetAddress.getByName(ipAddressResult.get());
                this.mutiplayerScene = new MultiplayerScene(stage, splashScene, serverAddress);
            } catch (UnknownHostException e) {
                System.out.println("Error getting the ip address");
            }
        }


		stage.setScene(this.mutiplayerScene.getScene());
	}
	
	//Method for setting about scene
	private void setAbout(Stage stage) {
		this.aboutScene = new AboutScene(stage, splashScene);
		stage.setScene(this.aboutScene.getScene());
	}

}
