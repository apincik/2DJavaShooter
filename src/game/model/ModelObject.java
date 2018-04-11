/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

import java.awt.Dimension;

/**
 *
 * @author andrej
 */
public class ModelObject {
    
    public static int WIDTH = 500;
    public static int HEIGTH = 400;
    
    public static int AREA_WIDTH = 500;
    public static int AREA_HEIGHT = 400;
    
    public static int WINDOW_X;
    public static int WINDOW_Y;
    public int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public  void setPlayerType(String type) {};
    public  String getPlayerType() {return "";};
    public void revertPlayerPosition() {};
    public void setPlayerPosition(int x, int y) {};
        
    
    
    public void setPanelSize(Dimension dimension) {
        this.WINDOW_X = 500;
        this.WINDOW_Y = 400;
    }

    public void move(int offsetX, int offsetY) {
        
        int old_x = x, old_y = y;
        
        x = offsetX;
        x %= ModelObject.WIDTH;
        y = offsetY;
        y %= ModelObject.HEIGTH;
        
        if(x > ModelObject.AREA_WIDTH - 50|| x < 0) {
            x = old_x;
        }
        if(y > ModelObject.AREA_HEIGHT - 100 || y < 0) {
            y = old_y;
        }
    }
    
    public ModelObject() {
        x = Player.PLAYER_DEFAULT_X;
        y = Player.PLAYER_DEFAULT_Y;
    }
    
    public void setAttackerPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    public void setShootPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    
}
