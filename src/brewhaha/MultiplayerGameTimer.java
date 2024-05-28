package brewhaha;

import brewhaha.GameTimer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import networking.Client;
import networking.Server;


public class MultiplayerGameTimer extends GameTimer {
    private DatagramSocket socket;
    private byte[] buf = new byte[1024];
    private boolean playerIsServer;
    private Server server;
    private Client client;

    MultiplayerGameTimer(Scene scene, GraphicsContext gc, Stage stage, Server server, Client client, boolean playerIsServer) {
        super(scene, gc, stage);
        this.server = server;
        this.client = client;
        this.playerIsServer = playerIsServer;
    }

    @Override
    public void handle(long currentNanoTime) {
        super.redrawBackgroundImage();
        if (playerIsServer){
            super.autoSpawn(currentNanoTime);
            if(super.getNewBuilding() == true) {
                // Get the last two buildings added
                int buildingCount = super.getBuildings().size();
                if (buildingCount >= 2) {
                    Building lastBuilding = super.getBuildings().get(buildingCount - 1);
                    Building secondLastBuilding = super.getBuildings().get(buildingCount - 2);

                    // Send the buildings
                    try {
                        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
                        
                        objStream.writeObject(lastBuilding);
                        byte[] lastBuildingBytes = byteStream.toByteArray();
                        this.server.sendToClients(lastBuildingBytes);

                        byteStream.reset();  // Reset the stream to clear the previous object

                        objStream.writeObject(secondLastBuilding);
                        byte[] secondLastBuildingBytes = byteStream.toByteArray();
                        this.server.sendToClients(secondLastBuildingBytes);
                    } catch (IOException e) {
                        System.out.println("Error serializing buildings");
                        e.printStackTrace();
                    }
                }
            }
        }
        super.renderSprites();
        super.moveSprites();
        
        super.drawScore();

        
        //calls the method to set the game over scene once the spaceship died
        if(!super.getWitch().isAlive()) {
        	this.stop();
        	super.setGameOver(1);		// draw Game Over text
        }
    }

    public void addBuilding(Building building){
        super.addBuilding(building);
    }

}