/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import database.Database;
import game.control.Controller;
import game.view.GameView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author andrej
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Database conn = new Database();
        //conn.setTable("users");
        
        
        //pridavanie do tabulky
        /*HashMap<String, String> data;
        data = new HashMap<String, String>();
        
        data.put("user", "andrej");
        data.put("password", "1234");
        
        ResultSet result = conn.insert(data);
        
        System.out.println("QUERY DONE");*/
        
        /*HashMap<String, String> data;
        data = new HashMap<String, String>();
        
        data.put("user", "andrej");
        data.put("password", "1234");
        
        ResultSet rs = conn.search(data);
        
        try {
        while(rs.next()) {
                
                String name = rs.getString("user");
                String password = rs.getString("password");
                System.out.println("MENO: " + name);
                System.out.println("PASSWORD:" + password);
            }
        }
        catch(SQLException e) {
            System.out.println("Query SEARCH where exception thrown: " + e);
            
        }*/

        Controller controller = new Controller();
        GameView view = new GameView(controller);
        view.setVisible(true);
              
    }
    
}
