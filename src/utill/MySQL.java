/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mneura
 */
public class MySQL {

    public static final String HOST = "sql12.freesqldatabase.com";
    public static final String DATABASE = "sql12791060";
    public static final String PORT = "3306";
    public static final String USERNAME = "sql12791060";
    public static final String PASSWORD = "S2YGTc8eN9";

    public static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE,
                        USERNAME,
                        PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void executeIUD(String query) throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);
    }

    public static ResultSet executeSearch(String query) throws SQLException {
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

}
