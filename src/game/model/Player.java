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
    
   // public static int getPlayerPositionX
    
    public Player(int x, int y) {
        super();
        this.pos_x = x;
        this.pos_y = y;
        Player.PLAYER_DEFAULT_X = 200; //ModelObject.WINDOW_X - 50;
        Player.PLAYER_DEFAULT_Y = 300; // ModelObject.WINDOW_Y / 2;
    }
    
    @Override
    public void setPlayerType(String type) {
        playerType = type;
    }
    
    @Override
    public String getPlayerType() {
        return playerType;
    }
    
    @Override
    public void revertPlayerPosition() {
        System.out.println("POS_X: " + pos_x);
        pos_x = 200;
        System.out.println("POS_X: " + pos_x);
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

    @Override
    public void move(int offsetX, int offsetY) {

        pos_x += offsetX;
        pos_y += offsetY;
        
        /*if(pos_x > ModelObject.WINDOW_X || pos_x < 0) {     //check the end of border
            pos_x -= offsetX;
        }
        if(pos_y + (Player.IMAGE_HEIGHT / 2) > ModelObject.WINDOW_Y || pos_y < 0) {
            pos_y -= offsetY;
        }*/
        
        super.move(pos_x, pos_y);
    }
    
}
