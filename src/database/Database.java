/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author andrej
 */
public class Database {
    
    private Connection conn;
    private Statement statement;
    private ResultSet rs;
    private String table;
    
    
    public Database() {
    
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "");
            statement = conn.createStatement();
            
        }catch(Exception e) {
            System.out.println("Error - database connection - " + e);
            e.printStackTrace();
        }
        
    }
    
    
    //testing getting data
    public void getTestData() {
        
        try {
            
            String query = "select * from employee";
            rs = statement.executeQuery(query);
            System.out.println("Record from database:");
            
            while(rs.next()) {
                
                String name = rs.getString("name");
                System.out.println("MENO: " + name);
            }
            
        }catch(Exception e) {
            System.out.println("Error getting data" + e);
        }
    }
    
    
    public void close() 
    {
        try {
            conn.close();
        }
        catch(SQLException e) 
        {
            System.out.println("Error during closing database connection: " + e);
        }
        
    }
    
    
    
    public void setTable(String tableName) 
    {
        table = tableName;
    }
    
    
    
    public String getTable()
    {
        return table;
    }
    
    
    
    public ResultSet select() 
    {
        try {
            
            String query = "select * from " + getTable();
            System.out.println("DB Query: " + query);
            rs = statement.executeQuery(query);

        }catch(Exception e) {
            System.out.println("Error getting data" + e);
        }
        
        return rs;
        
    }
    
    
    /**
     * 
     * @param mp
     * @return 0 => FALSE, 1 .. affected rows => TRUE
     */
    public boolean insert(HashMap<String, String> mp)
    {
        String keys = "";
        String values = "";
        int result = 0;

        Iterator it = mp.entrySet().iterator();
        while (it.hasNext())
        {
           Map.Entry pairs = (Map.Entry) it.next();
           System.out.println(pairs.getKey() + " = " + pairs.getValue());
           
           keys = "" + keys + pairs.getKey() + ",";
           values = "" + values + "'" + pairs.getValue() + "'" + ",";
           
           it.remove(); // avoids a ConcurrentModificationException
        }
        
        keys = keys.substring(0, keys.length() -1);
        values = values.substring(0, values.length() -1);
        
        try {
            
            String query = "INSERT INTO " + getTable() + " (" + keys + ")" + " VALUES (" + values + ")";
            System.out.println("DB Query: " + query);
            //rs = statement.executeUpdate(query);
            result = statement.executeUpdate(query);
            
        }catch(Exception e) {
            System.out.println("Error getting data" + e);

        }
        
        if(result == 0) {
            return false;
        }
        
        return true;
        //return rs;
        
    }
    
    
    
    public boolean update(HashMap<String, String> mp, String identity)
    {
        String updateQuery = "";
        int result = 0;

        Iterator it = mp.entrySet().iterator();
        while (it.hasNext())
        {
           Map.Entry pairs = (Map.Entry) it.next();
           System.out.println(pairs.getKey() + " = " + pairs.getValue());

           updateQuery = "" + updateQuery + pairs.getKey() + "='" + pairs.getValue() + "', ";
           
           it.remove(); // avoids a ConcurrentModificationException
        }
        
        updateQuery = updateQuery.substring(0, updateQuery.length() - 2);

        try {
            
            String query = "UPDATE " + getTable() + " SET " + updateQuery + " WHERE user='" + identity + "'"; 
            System.out.println("DB Update - Query: " + query);
            //rs = statement.executeUpdate(query);
            result = statement.executeUpdate(query);
            
        }catch(Exception e) {
            System.out.println("Error getting data" + e);

        }
        
        if(result == 0) {
            return false;
        }
        
        return true;
        //return rs;
        
    }
    
    
    
    public ResultSet search(HashMap<String, String> mp)
    {
        
        String searchQuery = "";
        
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext())
        {
           Map.Entry pairs = (Map.Entry) it.next();
           System.out.println(pairs.getKey() + " = " + pairs.getValue());
           
           searchQuery = "" + searchQuery + pairs.getKey() + "='" + pairs.getValue() + "' AND ";
           
           it.remove(); // avoids a ConcurrentModificationException
        }
        
        searchQuery = searchQuery.substring(0, searchQuery.length()-5);
        
        try {
            String query;
            if(searchQuery != "")
            {
                query = "SELECT * FROM " + getTable() + " WHERE " + searchQuery;
            }
            else
            {
                query = "SELECT * FROM " + getTable();
            }
                
            System.out.println("DB Query: " + query);
            rs = statement.executeQuery(query);
            
        }catch(Exception e) {
            System.out.println("Error getting data" + e);
        }
        
        return rs;
    }
    
    public ResultSet searchHighestScore()
    {
         try {
            String query = "SELECT * FROM " + getTable() + " ORDER BY highscore DESC LIMIT 3";
            System.out.println("Executing query for score search: " + query);
            rs = statement.executeQuery(query);
           
        } catch (Exception e) {
            System.out.println("Error getting data" + e);
        }

        return rs;
    }
    
    
    
    

    
}
