/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.control;


import database.Database;
import game.model.Attacker;
import game.model.ModelObject;
import game.model.Player;
import game.model.Shoot;
import game.view.GameView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author andrej
 */
public class Controller {

    public GameView view;
    private List<ModelObject> objects;
    private Thread thread;
    private boolean running = false;
    private Database conn;
    private boolean playerLoggedIn = false;
    
    private int attackersCount = 0;
    private int killedAttackers = 0;
    
    private boolean lostGame = false;
    
    private String user = "";

    public Controller() {
        objects = new ArrayList<>();
        conn = new Database();
        conn.setTable("users");
    }

    
    
    private void initializeGame() {
        
        if(isPlayerLoggedIn() == false) {
            //this.view.showLoginDialog();
            this.view.notLoggedInMessage();
            stop();
        }
        
        readHighScore();
        Player player = new Player(200, 220);
        objects.add(player);
      
    }

    
    
    private synchronized void update() {
        
        if(attackersCount < 2) {
            while(attackersCount < 2) {
                addAttacker();
            }
        }
        
        Player player = (Player) objects.get(0);
        int p_posX = player.getPlayerPositionX();
        int p_posY = player.getPlayerPositionY();
        
        if(view.isClicked())
        {
            player.move(view.getClickedXPosition(), view.getClickedYPosition());
        }
        else
        {
            player.move(200, 220);  //initial
        }
        
        for(ModelObject o :objects) {
                if(o instanceof Attacker) {
                    o.move( player.getPlayerPositionX(), player.getPlayerPositionY());
                }
                if(o instanceof Shoot) {
                        o.move(0, 0);
                }
        }
        
        //CHECKING SHOOTING
        if(view.mainPanel.shooting == true) {
            Shoot sh = new Shoot(p_posX, p_posY, view.mainPanel.newShootX, view.mainPanel.newShootY);
            objects.add(sh);
            
            view.mainPanel.shooting = false;
            view.mainPanel.newShootY = 0;
            view.mainPanel.newShootX = 0;
        }
        
        checkPlayerCollision();
        checkCollisions();
        
        view.updateModel(objects);

    }
    
    
    private synchronized void addAttacker() {
        
        Random rand = new Random();
        int randX = rand.nextInt((400 - 50) + 50) + 50;
        int randY = rand.nextInt((400 - 50) + 50) + 50;
        
        Attacker attacker = new Attacker(randX, randY);
        objects.add(attacker);
        attackersCount++;
        
    }
    
    
    
    private synchronized void checkPlayerCollision() {
        
        int at_posX = 0, at_posY = 0;
        int p_posX = 0, p_posY = 0;
        
        Player player = (Player) objects.get(0);
        p_posX = player.getPlayerPositionX();
        p_posY = player.getPlayerPositionY();
        
        for (ModelObject o : objects) {
            if (o instanceof Attacker) {
                Attacker at = (Attacker) o;
                if (at.visible == true) {
                    at_posX = at.getAttackerPositionX();
                    at_posY = at.getAttackerPositionY();
                    if (at_posX >= p_posX && at_posX <= (p_posX + 30)) {
                        if (at_posY >= p_posY && at_posY <= (p_posY + 30)) {
                            lostGame = true;
                        }
                    }
                }

            }
        }
    }
    
    
    private synchronized void checkCollisions() {
        
        int at_posX = 0, at_posY = 0;
        int sh_posX = 0, sh_posY = 0;
        
        for (ModelObject o : objects) {
            if (o instanceof Attacker) {
                Attacker at = (Attacker) o;
                if (at.visible == true) {
                    at_posX = at.getAttackerPositionX();
                    at_posY = at.getAttackerPositionY();
                    for (ModelObject p : objects) {
                        if (p instanceof Shoot) {
                            Shoot sh = (Shoot) p;
                            if (sh.visible == true) {
                                sh_posX = sh.getShootPositionX();
                                sh_posY = sh.getShootPositionY();

                                if (sh_posX >= at_posX && sh_posX <= (at_posX + 36)) {
                                    if (sh_posY >= at_posY && sh_posY <= (at_posY + 70)) {
                                        at.visible = false;
                                        sh.visible = false;
                                        attackersCount--;// = attackersCount - 1;
                                        killedAttackers++;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    
    
 
    private void render() {
        
        view.repaint();
    }
    
    
    
    public synchronized void start() {
        initializeGame();
        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                while (running) {
                    if(lostGame == true) {
                        handleLostGame();
                        break;
                    }
                    update();
                    render();
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        thread.start();
    }

    
    
    public void stop() {
        
        writePlayersScore();
        running = false;
        try {
            thread.join();
            objects.clear();
        } catch (InterruptedException e) {
            System.out.println("Stop " + e);
        }

        view.repaint();
    }

    
    
    public void setView(GameView view) {
        this.view = view;
    }
    
    
    private void showFailedLogin() {
        
        this.view.notLoggedInMessage();
        stop();
        
    }
    
    
    
    public boolean isRunning() {
        
        return running;
    }
    
    
    
    public boolean loginPlayer(HashMap<String, String> mp) 
    {
        HashMap<String, String> data;
        data = new HashMap<String, String>();
        
        String user = mp.get("user");
        String password = mp.get("password");
        
        data.put("user", user);
        data.put("password", password);
        
        ResultSet rs = conn.search(data);
        
        try {
            if(rs.next() == false) {
                showFailedLogin();
                return false;
            }
        }
        catch(SQLException e) {
            System.out.println("Nepodarilo sa precitat resultset pri logine." + e);
        }
        
        playerLoggedIn = true;
        this.user = user;
        view.handleButtonsAfterLogin();
        view.showMessage("Boli ste prihlaseny, mozete spustit hru.");
        view.setUsernameLabel("Prihlaseny user: " + user);
        
        return true;
    }
    
    
    private void readHighScore() {
        
        ResultSet rs = conn.searchHighestScore();
        String scoreList = "HIGH SCORE \n";
        
        try {
        while(rs.next()) {
                String name = rs.getString("user");
                int highscore = rs.getInt("highscore");
                scoreList = "" + scoreList + name + " -> " + highscore + "\n";
            }
        }
        catch(SQLException e) {
            
            System.out.println("Error pri citani highscore resultu.");
        }
        System.out.println("SCORE LIST: " + scoreList);
        view.debugText(scoreList);
        
    }
    
    
    
    private void handleLostGame() {
        
        //running = false;
        //writePlayersScore();
        view.showMessage("Prehrali ste, vase skore: " + killedAttackers);
        stop();
        
    }
    
    
    
    
    private boolean writePlayersScore() 
    {
        HashMap<String, String> data;
        data = new HashMap<String, String>();

        data.put("user", this.user);
        
        ResultSet rs = conn.search(data);
        
        try {
            if(rs.next() == false) {
                System.out.println("prihlaseny uzivatel sa nenasiel...");
                return false;
            }
            int score = rs.getInt("highscore");
            
            if(score < killedAttackers) {
                //write new highScore for user
                data = new HashMap<String, String>();
                data.put("highscore", killedAttackers + "");
                
                if(conn.update(data, this.user) == true) {
                    System.out.println("Score v databaze bolo upravene.");
                }
                else {
                    System.out.println("Error - Score v databaze nebolo upravene.");
                }
            }
            
        }
        catch(SQLException e) {
            System.out.println("Nepodarilo sa precitat resultset pri zapise skore - " + e);
        }
 
        return true;
    }
    
    
    
    public boolean registerPlayer(HashMap<String, String> mp)
    {
        
        HashMap<String, String> data;
        data = new HashMap<String, String>();
        
        String user = mp.get("user");
        String password = mp.get("password");
        
        data.put("user", user);
        data.put("password", password);
        
        boolean result = conn.insert(data);
        System.out.println("insert QUERY DONE");
        
        if(result != false)
        {
           view.showMessage("Boli ste registrovany, prihlaste sa.");
           return true;
        }
        
        view.showMessage("Registrácia sa nezdarila.");
        return false;
        
    }
    
    
    
    public boolean isPlayerLoggedIn()
    {
        
        return playerLoggedIn;
    }
    
    
    
    public void logoutPlayer()
    {
        playerLoggedIn = false;
        view.setUsernameLabel("");
        stop();
    }
    
    
    
    
    
    
    
    

}
