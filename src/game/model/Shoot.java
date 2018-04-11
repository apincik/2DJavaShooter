/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

import java.util.Random;

/**
 *
 * @author beh01
 */
public class Shoot extends ModelObject{
    
    private int pos_x, pos_y;
    public static int IMAGE_WIDTH;
    public static int IMAGE_HEIGHT;
    public static int ATTACKER_DEFAULT_X;
    public static int ATTACKER_DEFAULT_Y;
    public int SHOOT_MOVE_OFFSET = 1;
    public boolean visible = true;
    
    public int moveToX = -100;
    public int moveToY = -100;

    private boolean reachedX;
    private boolean reachedY;

    private boolean reachedMessage = false;

    public boolean moving_animation = false;

    public boolean moving_right = false;
    public boolean moving_left = false;

    

    
    public Shoot(int x, int y, int toX, int toY) {
        
        super();
        
        this.pos_x = x;
        this.pos_y = y;
        
        this.moveToX = toX;
        this.moveToY = toY;
        
        Shoot.ATTACKER_DEFAULT_X = 300; //ModelObject.WINDOW_X - 50;
        Shoot.ATTACKER_DEFAULT_Y = 330; // ModelObject.WINDOW_Y / 2;
  
    }

    @Override
    public void setShootPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    
    public int getShootPositionX() {
        return pos_x;
    }
    
    
    public int getShootPositionY() {
        return pos_y;
    }


    @Override
    public void move(int posX, int posY) {

       
       // if(is_moving == true) {
            
            if(pos_x < moveToX) {
                if((pos_x) < moveToX)
                {
                    pos_x += SHOOT_MOVE_OFFSET;
                    reachedX = false;
                }
                else
                {
                    reachedX = true;
                }
            }
            else {
                if((pos_x) > moveToX)
                {
                    pos_x -= SHOOT_MOVE_OFFSET;
                    reachedX = false;
                }
                else
                {
                    reachedX = true;
                }
            }
            
            if(pos_y < moveToY) {
                if((pos_y) < moveToY)
                {
                    pos_y += SHOOT_MOVE_OFFSET;
                    reachedY = false;
                }
                else
                {
                    reachedY = true;
                }
            }
            else {
                if((pos_y) > moveToY)
                {
                    pos_y -= SHOOT_MOVE_OFFSET;
                    reachedY = false;
                }
                else
                {
                    reachedY = true;
                }
            }
            
            if(reachedX == true && reachedY == true) {
                
                visible = false;
                
            }
   
       // }

        super.move(pos_x, pos_y);
    }
    
}
