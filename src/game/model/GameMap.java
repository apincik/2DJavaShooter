/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

/**
 *
 * @author andrej
 */
public class GameMap {
    
    
    private String map;
    
    private String grass = "x";
    private String path = "y";
    
    public GameMap() {
        
        map = loadMap();
        
    }
    
    
    private String loadMap() {
        
       /* String map = new String     ("yyyyyyyyyy"
                                   + "yyyyyyyyyy"
                                   + "xxxxxxxxxx"
                                   + "xxxxyyyxxx"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy");*/
        
        String map = new String     ("yyyyyyyyyy"
                                   + "xxxxxxxxxx"
                                   + "xxxxxxxxxx"
                                   + "yyyyyyyyyy"
                                   + "xxxxxyyyyy"
                                   + "yyyyyyxxxx"
                                   + "xxxxxxxxxx"
                                   + "xxxxxxyyyy"
                                   + "xxxxxxyyyy"
                                   + "yyyyyyyyyy"
                                   + "xxxxxxxxxx"
                                   + "xxxxyyyxxx"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "xxxxyyyyyy"
                                   + "yyyyyyyyyy"
                                   + "yyyyyxyyyy"
                                   + "xxxxyyyyyy");
        
        return map;
    }
    
    
    
    public String getMap() {
        
        return map;
        
    }
    
    
    
    public boolean isGrass(char symbol)
    {
        String actualSymbol;
        actualSymbol = "" + symbol;
        
        if(actualSymbol.equals(grass))
        {
            return true;
        }
        
        return false;
    }
    
    
    
    public boolean isPath(char symbol) 
    {
        String actualSymbol;
        actualSymbol = "" + symbol;
        
        if(actualSymbol.equals(path))
        {
            return true;
        }
        
        return false;
    }
    
    
    
    
    
    
    
}
