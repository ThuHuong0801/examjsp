/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Admin
 */
public class DatabaseConnection {
    private final static BasicDataSource dataSource = new BasicDataSource();
    
    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        
        
    }
    
    public static Connection getConnection()throws SQLException{
        
        return dataSource.getConnection();
    }
    
}
