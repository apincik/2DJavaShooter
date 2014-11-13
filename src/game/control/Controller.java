/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.control;


import game.model.ModelObject;
import game.model.Player;
import game.view.GameView;
import java.util.ArrayList;
import java.util.List;
import tcpclient.Client;

/**
 *
 * @author andrej
 */
public class Controller {

    public GameView view;
    private List<ModelObject> objects;
    private Thread thread;
    private boolean running = false;
    public Client client;
    private String rec;     //received message
    private String playerName;
    
    public boolean moving = false;
    private boolean localPlayer = false;
    private boolean started = false;
    
    private boolean oponnent = false;
    
    private final String REGISTER_PLAYER = "reg_player";
    private final String REGISTER_OK = "reg_ok";
    private final String PLAYER_MOVE = "player_move";
    final String REVERT_PLAYER_POS = "init_car_pos";
    final String OPONNENT_POSITION = "op_pos";
    final String OPONNENT_MOVE = "op_move";

    public Controller() {
        objects = new ArrayList<>();
    }

    private synchronized void initializeGame() {
        playerName = view.playersNameSelectDialog();
    }

    private synchronized void update() {
        if(moving == true) {
        objects.get(0).move(view.getDirectionX(), view.getDirectionY()); 
        movePlayerToServer(objects.get(0).getX(),objects.get(0).getY());
        }
        //view.debugText("X: " + objects.get(0).getX() + " Y: " + objects.get(10).getY());
        moving = false;
        view.updateModel(objects);

    }
    
    public synchronized void createLocalPlayer() {
        
        if(localPlayer == false) {
            System.out.println("Vytvorenie noveho hraca.");
            Player player = new Player(500, 300);
            player.setPlayerType("local");
            objects.add(player);
            localPlayer = true;
        }
        
    }
    
    private synchronized void revertLocalPlayerPosition() {
        if(localPlayer == true) {
            objects.get(0).revertPlayerPosition();
            view.debugText("revert function controller called");
        }
    }
    
    public boolean isGameStarted() {
        return started;
    }
    
    private synchronized void initializeClient() {
        client = new Client();
        if(client.initClient() == false) {
            //initializeClient();
            view.cannotConnectMessage();
        }
        else {
            registerPlayerToServer();
        }
    }
    
    private synchronized void movePlayerToServer(int x, int y) {
        
        String command = PLAYER_MOVE + ":" + x + ":" + y;
        client.sendMessage(command);
    }
    
    private synchronized void registerPlayerToServer() {
      
        String command = REGISTER_PLAYER + ":" + playerName;
        client.sendMessage(command);
        
    }
    
    private synchronized void createOponnent(String x, String y) {
        int posx = Integer.parseInt(x);
        int posy = Integer.parseInt(y);
        
        
        if(oponnent == false) {
            Player player = new Player(posx, posy);
            player.setPlayerType("remote");
            objects.add(player);
            oponnent = true;
        }
        
    }
    
    private synchronized void moveOponnent(String x, String y) {
        
        System.out.println("MOVING OPONNENT TO: " + x + " : "  + y);
        
        int posx = Integer.parseInt(x);
        int posy = Integer.parseInt(y);
        
        objects.get(1).setPlayerPosition(posx, posy);
        
    }
    
    /**
     * Received messages handler
     * @param string 
     */
    private  synchronized void handleReceivedMessage(String string) {
        //view.debugText(string);
        
        if("127.0.0.1:Unknown command".equals(string)) {
            return;
        }
        else {
            System.out.println("COMMAND RECEIVED: " + string);
        }
        
        String s[] = string.split(":");
        
        if(REGISTER_OK.equals(s[1])) {
            createLocalPlayer();
        }
        else if(REVERT_PLAYER_POS.equals(s[1])) {
            view.debugText(playerName + " - revert");
            revertLocalPlayerPosition();
        }
        else if(OPONNENT_POSITION.equals(s[1])) {
            createOponnent(s[2], s[3]);
        }
        else if(OPONNENT_MOVE.equals(s[1])) {
            moveOponnent(s[2], s[3]);
        }
        else {
            view.debugText("Received unknown command :"  + string);
            //System.out.println("Received unknown command :"  + s[1] + " ? " + REGISTER_OK);
        }
        /*try {
            thread.join();
        }
        catch(InterruptedException ex) {
            
        }*/
        
    }
    
    public synchronized void start() {
        initializeGame();
        initializeClient();
        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                while (running) {
                    handleReceivedMessage(client.readMessage());
                    update();
                    view.repaint();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        thread.start();
    }

    public void stop() {
        running = false;
        client.sendMessage("down");
        client.closeClientConnection();
        
        try {
            thread.join(); 
        } catch (InterruptedException ex) {
        }
        objects.clear();
        view.repaint();
    }

    public void setView(GameView view) {
        this.view = view;
    }

}
