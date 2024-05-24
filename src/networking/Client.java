package networking;

import java.net.*;

public class Client implements Runnable {
    
    // datagram socket for the client
    private DatagramSocket socket;

    // server address
    private InetAddress serverAddress;

    // server port
    private int serverPort;

    // GameUser object for the player
    private GameUser player;

    // thread for the client
    Thread t = new Thread(this);

    // Callback interface
    public interface ClientCallback {
        void onMessageReceived(String message);
    }

    private ClientCallback callback;
    
    // constructor
    public Client(int serverPort, String name, ClientCallback callback){
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getLocalHost();
            this.serverPort = serverPort;
            this.callback = callback;
            this.player = new GameUser(name, serverAddress, serverPort);
            //System.out.println("Client created server address: " + serverAddress + " server port: " + serverPort);
        } catch (Exception e) {
            System.out.println("Error creating client socket");
        }
        t.start();
        System.out.println("Client thread started");
    }

    // method to send a packet
    public void sendPacket(byte[] data, InetAddress address, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Error sending packet");
            e.printStackTrace();
        }
    }
    
    // method to receive a packet
    public byte[] receivePacket(){
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (Exception e) {
            System.out.println("Error receiving packet");
        }
        return data;
    }

    // method to send a chat message
    public void sendChatMessage(String message, InetAddress address, int port, String name){
        byte[] data = ("chat " + name + ": " + message).getBytes();
        sendPacket(data, address, port);
    }
    

    // connect to the server and send a connect message containing the player's name
    public void connect(InetAddress address, int port, String name){
        byte[] data = ("connect " + name).getBytes();
        sendPacket(data, address, port);
    }

    // send ready message
    public void sendReadyMessage(InetAddress address, int port, String name){
        byte[] data = "ready".getBytes();
        sendPacket(data, address, port);
    }
    
    // method to close the socket
    public void close(){
        socket.close();
    }

    // run method for the client
    @Override
    public void run() {
        System.out.println("Client running");
        while (true){
            byte[] data = receivePacket();
            System.out.println(new String(data).trim());
            if (new String(data).trim().startsWith("chat")){
                System.out.println(new String(data).trim().substring(5));
                // Notify the callback about the new message
            if (callback != null) {
                callback.onMessageReceived(new String(data).trim().substring(5));
            }

            }
            else if (new String(data).trim().startsWith("player")){
                System.out.println(new String(data).trim().substring(7));
            }
            else if (new String(data).trim().startsWith("ready")){
                System.out.println(new String(data).trim().substring(6));
            }

            
        }
    }
    
}
