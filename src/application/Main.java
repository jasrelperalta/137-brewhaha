/*************************************************************************************************************************
 * BREWHAHA - CMSC 137 PROJECT
 * 
 * Jasrel Roby T. Peralta
 * Roxanne Ysabel P. Resuello
 * May 2024
 *************************************************************************************************************************/

package application;

import brewhaha.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application 
{
		
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
       Game game = new Game();
       game.setStage(stage);
    }
    
    
}