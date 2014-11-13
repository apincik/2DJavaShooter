/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

/**
 *
 * @author beh01
 */
public class Wall extends ModelObject{
    private int pos_x, pos_y;
    public static int IMAGE_WIDTH;
    public static int IMAGE_HEIGHT;
    public static int PLAYER_DEFAULT_X;
    public static int PLAYER_DEFAULT_Y;
    public static int PLAYER_MOVE_OFFSET = 10;
    
    private String playerType;       //local - remote
    
   // public static int getPlayerPositionX
    
    public Wall(int x, int y) {
        super();
        this.pos_x = x;
        this.pos_y = y;
        Wall.PLAYER_DEFAULT_X = 200; //ModelObject.WINDOW_X - 50;
        Wall.PLAYER_DEFAULT_Y = 300; // ModelObject.WINDOW_Y / 2;
    }

    public int getWallPositionX() {
        return pos_x;
    }
    
    public int getWallPositionY() {
        return pos_y;
    }

    @Override
    public void move(int offsetX, int offsetY) {

        pos_x += offsetX;
        pos_y += offsetY;


    }
    
}
