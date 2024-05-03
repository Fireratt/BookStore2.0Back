package com.example.myapp.data ; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DataPersistence {
    public static void connect() throws ClassNotFoundException , SQLException
    {
        // statically load the driver ; it will register to the driver manager automatically 
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306";
		String user = "root";
		String passwd = "12345678";

		Connection con = DriverManager.getConnection(url, user, passwd);
		System.out.println(con.getTransactionIsolation());

    }
}