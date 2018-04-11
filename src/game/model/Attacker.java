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
public class Attacker extends ModelObject{
    
    private int pos_x, pos_y;
    public static int IMAGE_WIDTH;
    public static int IMAGE_HEIGHT;
    public static int ATTACKER_DEFAULT_X;
    public static int ATTACKER_DEFAULT_Y;
    public int ATTACKER_MOVE_OFFSET = 1;
    
    private String playerType;       //local - remote
    
    private boolean is_moving;
    public boolean visible = true;
    
    public int moveToX = -100;
    public int moveToY = -100;
    
    private boolean reachedX;
    private boolean reachedY;
    
    private boolean reachedMessage = false;
    
    public boolean moving_animation = false;
    
    public boolean moving_right = false;
    public boolean moving_left = false;
  
    
   // public static int getPlayerPositionX
    
    public Attacker(int x, int y) {
        
        super();
        
        this.pos_x = x;
        this.pos_y = y;
        
        Attacker.ATTACKER_DEFAULT_X = 300; //ModelObject.WINDOW_X - 50;
        Attacker.ATTACKER_DEFAULT_Y = 330; // ModelObject.WINDOW_Y / 2;
        is_moving = false;
        
        Random rand = new Random();
        int randomNum = rand.nextInt((2 - 1) + 1) + 1;
        
        ATTACKER_MOVE_OFFSET = 1;//randomNum;
        
    }

    @Override
    public void setAttackerPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    
    public int getAttackerPositionX() {
        return pos_x;
    }
    
    
    public int getAttackerPositionY() {
        return pos_y;
    }
    
    
    public boolean isMoving() {
        
        return is_moving;
    }
    
    

    @Override
    public void move(int posX, int posY) {

        if(moveToX != posX) {
            
            if(posX > moveToX)
            {
                moving_right = true;
                moving_left = false;
            }
            else
            {
                moving_left =true;
                moving_right = false;
            }
            moveToX = posX;
            moveToY = posY;
            is_moving = true;
            
        }

        if(is_moving == true) {
            
            if(pos_x < moveToX) {
                if((pos_x + 10) < moveToX)
                {
                    pos_x += ATTACKER_MOVE_OFFSET;
                    reachedX = false;
                }
                else
                {
                    reachedX = true;
                }
            }
            else {
                if((pos_x - 10) > moveToX)
                {
                    pos_x -= ATTACKER_MOVE_OFFSET;
                    reachedX = false;
                }
                else
                {
                    reachedX = true;
                }
            }
            
            if(pos_y < moveToY) {
                if((pos_y + 10) < moveToY)
                {
                    pos_y += ATTACKER_MOVE_OFFSET;
                    reachedY = false;
                }
                else
                {
                    reachedY = true;
                }
            }
            else {
                if((pos_y - 10) > moveToY)
                {
                    pos_y -= ATTACKER_MOVE_OFFSET;
                    reachedY = false;
                }
                else
                {
                    reachedY = true;
                }
            }
   
        }

        super.move(pos_x, pos_y);
    }
    
    
    
    public void enableMovingAnimation() {

        moving_animation = true;

        
    }
    
    
    public void disableMovingAnimation() {
        
        moving_animation = false;
        
    }
    
    
    
    public boolean isMovingAnimation() {
        
        return moving_animation;
        
    }
    
    
}
