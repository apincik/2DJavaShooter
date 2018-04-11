/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;

import game.model.Attacker;
import game.model.GameMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import game.model.ModelObject;
import game.model.Player;
import game.model.Shoot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 *
 * @author andrej
 */
public class MainPanel extends javax.swing.JPanel {

    
    List<ModelObject> model;    //game objects
    Image backBuffer;
    BufferedImage background;
    BufferedImage texture_grass;
    BufferedImage texture_path;
    BufferedImage player;
    BufferedImage player_moving;
    BufferedImage player_right;
    BufferedImage player_moving_right;
    BufferedImage attacker;
    BufferedImage attacker_left;
    BufferedImage attacker_right;
    BufferedImage shoot;
    
    GameMap gameMap;
    
    int rows;
    int cols;
    
    private int clickedXPosition;
    private int clickedYPosition;
    
    private boolean firstClick;
    
    public int mapPos;
    public int mapXPosition;
    public int newXPosition;
    
    public int newShootX;
    public int newShootY;
    
    public boolean shooting = false;
    
    
    public MainPanel() {
        
        initComponents();       //pre-defined java method
        backBuffer= new BufferedImage(ModelObject.WIDTH, ModelObject.HEIGTH, BufferedImage.SCALE_DEFAULT);
        
        rows = ModelObject.AREA_WIDTH / 50;
        gameMap = new GameMap();
        mapPos = 60;
        mapXPosition = 0;
        
        try {
            background = ImageIO.read(getClass().getResource("/game/view/bground.png"));
            texture_grass = ImageIO.read(getClass().getResource("/game/view/texture_grass.png"));
            texture_path = ImageIO.read(getClass().getResource("/game/view/texture_path.png"));
            player = ImageIO.read(getClass().getResource("/game/view/player.png"));
            player_right = ImageIO.read(getClass().getResource("/game/view/player_right.png"));
            player_moving = ImageIO.read(getClass().getResource("/game/view/player_moving.png"));
            player_moving_right = ImageIO.read(getClass().getResource("/game/view/player_moving_right.png"));
            attacker = ImageIO.read(getClass().getResource("/game/view/attacker.png"));
            attacker_left = ImageIO.read(getClass().getResource("/game/view/attacker_left.png"));
            attacker_right = ImageIO.read(getClass().getResource("/game/view/attacker_right.png"));
            shoot = ImageIO.read(getClass().getResource("/game/view/ammo.png"));
            
            rows = ModelObject.AREA_WIDTH / 50; //texture_grass.getWidth();
            cols = ModelObject.AREA_HEIGHT / 50; //texture_grass.getHeight();
            
            System.out.println("ROWS: " + rows);
            System.out.println("COLS: " + cols);
            
            rows = 10;
            cols = 8;
            
        } catch (IOException ex) {
           
        }
    }
    
    
    private void paintBackground(Graphics g) {
        
        //g.drawImage(background, 0, 0, backBuffer.getWidth(null),backBuffer.getHeight(null),null);
        
        int width = 50; //texture_grass.getWidth();
        int height = 50; texture_grass.getHeight();
        
        String map = gameMap.getMap();
        char mapSymbol;
        
        mapPos = mapXPosition;
        
        for(int i=0; i <= rows + 1 ; i++) {         
            for(int j=0; j <= cols + 1; j++) {
                
                
                //System.out.print("mapPosition: " + mapPosition);
                
                if(mapPos < map.length())
                {
                    mapSymbol = map.charAt(mapPos);
                    mapPos = mapPos + 1;
                    //System.out.print(mapPosition + "-" + mapSymbol);
                }
                else
                {
                    mapPos = mapPos - 100;      //ak je na konci mapy, posun dozadu vykreslenie na 100 prvkov
                    if(mapPos < 0)
                    {
                        mapPos = 0;
                    }
                    mapSymbol = map.charAt(mapPos);
                }
                
                if(gameMap.isGrass(mapSymbol) == true)
                {
                    g.drawImage(texture_grass, i * width, j * height, width, height, null);    
                }
                
                if(gameMap.isPath(mapSymbol) == true)
                {
                    g.drawImage(texture_path, i * width, j * height, width, height, null);    
                } 
                
            }
            
            //System.out.println("NEXT_ROW");
            
        }
        //mapPosition = 0;
       // System.out.println("END OF MAP");
        
    }
    
    
    public synchronized void paintAnimatedPlayer(Graphics g, Player p) {
        
        if(isClicked())
        {
            if(p.isMoving() == true) {
                
                mapXPosition = (p.getX() / 45) * 10;
                if(p.isMovingAnimation() == true)
                {
                    if(p.moving_left == true)
                    {
                        g.drawImage(player_moving, p.getX(), p.getY(), player.getWidth(), player.getHeight(), null);
                    }
                    else
                    {
                        g.drawImage(player_moving_right, p.getX(), p.getY(), player.getWidth(), player.getHeight(), null);
                    }
                    p.disableMovingAnimation();
                }
                else
                {
                    if(p.moving_left == true)
                    {
                        g.drawImage(player, p.getX(), p.getY(), player.getWidth(), player.getHeight(), null);
                    }
                    else
                    {
                        g.drawImage(player_right, p.getX(), p.getY(), player.getWidth(), player.getHeight(), null);
                    }
                    
                    p.enableMovingAnimation();
                }
            }
            else 
            {
                g.drawImage(player, p.getX(), p.getY(), player.getWidth(), player.getHeight(), null);
            }
        }
        else
        {
            g.drawImage(player_moving, p.PLAYER_DEFAULT_X, p.PLAYER_DEFAULT_Y, player.getWidth(), player.getHeight(), null);
        }
    }
    
    
    
    private void paintAttacker(Graphics g, Attacker p) {
        
        if(p.visible == true) {
            if (p.isMoving() == true) {

                mapXPosition = (p.getX() / 45) * 10;

                if (p.moving_left == true) 
                {
                    g.drawImage(attacker, p.getX(), p.getY(), attacker.getWidth(), attacker.getHeight(), null);
                } 
                else
                {
                    g.drawImage(attacker_right, p.getX(), p.getY(), attacker.getWidth(), attacker.getHeight(), null);
                }
            }
            else
            {
                g.drawImage(attacker_right, p.getX(), p.getY(), attacker.getWidth(), attacker.getHeight(), null);
            }
        }
          
                   
    }
    
    
    
    private void paintShoot(Graphics g, Shoot sh)
    {
        if(sh.visible == true) {
            g.drawImage(shoot, sh.getShootPositionX(), sh.getShootPositionY(), 20, 20, null);   
        }
    }
    
    
    
    
    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        Graphics g = backBuffer.getGraphics();
        paintBackground(g);
        if (model!=null ) {
            for(ModelObject o :model) {
                if (o instanceof Player) {
                    paintAnimatedPlayer(g, (Player) o);
                    //g.drawImage(player, o.getX(), o.getY(), player.getWidth(), player.getHeight(), null);
                }
                if(o instanceof Attacker) {
                    paintAttacker(g, (Attacker) o);
                }
                if(o instanceof Shoot) {
                    paintShoot(g, (Shoot) o);
                }
            }
        }

        Graphics2D graphic2d = (Graphics2D) graphic;
        graphic2d.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), null);
        
    }
    
    
    
    public void updateModel(List<ModelObject> data) {
        this.model = data;
    }
    
    
    public int getClickedXPosition() {
        
        return clickedXPosition;
        
    }
    
    public int getClickedYPosition() {
        
        return clickedYPosition;
    }
    
    
    public boolean isClicked() {
        
        return firstClick;
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        
        if(SwingUtilities.isRightMouseButton(evt)) {
            shooting = true;
            newShootX = evt.getX();
            newShootY = evt.getY();
        }
        
        if(SwingUtilities.isLeftMouseButton(evt)) {
            if(firstClick == false)
            {
                firstClick = true;
            }

            clickedXPosition = evt.getX();
            clickedYPosition = evt.getY();
        
        }
    }//GEN-LAST:event_formMouseReleased
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
