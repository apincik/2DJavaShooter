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
    
    public static int WIDTH = 600;
    public static int HEIGTH = 400;
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
        this.WINDOW_X = 540;
        this.WINDOW_Y = 350;
    }

    public void move(int offsetX, int offsetY) {
        
        int old_x = x, old_y = y;
        
        x = offsetX;
        x %= ModelObject.WIDTH;
        y = offsetY;
        y %= ModelObject.HEIGTH;
        
        if(x > 560 || x < 0) {
            x = old_x;
        }
        if(y > 310 || y < 0) {
            y = old_y;
        }
    }
    
    public ModelObject() {
        x = Player.PLAYER_DEFAULT_X;
        y = Player.PLAYER_DEFAULT_Y;
    }
    
    
}
