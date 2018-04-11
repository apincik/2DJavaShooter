/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

/**
 *
 * @author beh01
 */
public class Player extends ModelObject{
    
    private int pos_x, pos_y;
    public static int IMAGE_WIDTH;
    public static int IMAGE_HEIGHT;
    public static int PLAYER_DEFAULT_X;
    public static int PLAYER_DEFAULT_Y;
    public static int PLAYER_MOVE_OFFSET = 10;
    
    private String playerType;       //local - remote
    
    private boolean is_moving;
    
    public int moveToX = -100;
    public int moveToY = -100;
    
    private boolean reachedX;
    private boolean reachedY;
    
    private boolean reachedMessage = false;
    
    public boolean moving_animation = false;
    
    public boolean moving_right = false;
    public boolean moving_left = false;
  
    
   // public static int getPlayerPositionX
    
    public Player(int x, int y) {
        
        super();
        
        this.pos_x = x;
        this.pos_y = y;
        
        Player.PLAYER_DEFAULT_X = 200; //ModelObject.WINDOW_X - 50;
        Player.PLAYER_DEFAULT_Y = 220; // ModelObject.WINDOW_Y / 2;
        is_moving = false;
        
    }

    @Override
    public void setPlayerPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    
    public int getPlayerPositionX() {
        return pos_x;
    }
    
    
    public int getPlayerPositionY() {
        return pos_y;
    }
    
    
    public boolean isMoving() {
        
        return is_moving;
    }
    
    

    @Override
    public void move(int posX, int posY) {

        //pos_x += offsetX;
        //pos_y += offsetY;
        
        //set positions, that player should move to
        
  
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
                    pos_x += Player.PLAYER_MOVE_OFFSET;
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
                    pos_x -= Player.PLAYER_MOVE_OFFSET;
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
                    pos_y += Player.PLAYER_MOVE_OFFSET;
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
                    pos_y -= Player.PLAYER_MOVE_OFFSET;
                    reachedY = false;
                }
                else
                {
                    reachedY = true;
                }
            }
   
        }
        
        if(reachedY == true && reachedX == true) 
        {   
            disableMovingAnimation();
        }
        
        /*if(reachedX == false && reachedY == false) {
        
        //System.out.println("Player moving ? => " + is_moving + " TO: x:" + moveToX + " y: " + moveToY);
        //System.out.println("Player actual postion after move: x:" + pos_x + " y: " + pos_y);
        reachedMessage = false;
        
        }
        else 
        {
            if(reachedMessage == false) {
                System.out.println("Players reached his position !!!");
                reachedMessage = true;
            }
            //is_moving = false;
        }*/
        
        //super.move(200, 300);
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
