package libraryModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqlAccess {  
		static Connection connect = null;
		static Statement statement = null;
		public static PreparedStatement preparedStatement = null;
		public static ResultSet resultSet = null;
		public static Connection getConnection() {
			String username, password;
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your username");
			username = sc.nextLine();
			System.out.println("Enter your password");
			password = sc.nextLine();
			Connection connect = SqlAccess.getConnection(username, password);
			//sc.close();
			return connect;
		}
	    public static Connection getConnection(String username, String password)
	    {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		try {
				connect = DriverManager
				        .getConnection("jdbc:mysql://localhost:5814/LibraryDataModel?"
				        	+ " verifyServerCertificate=false&useSSL=true&"
				            + "user="+username+"&password="+password);
				            		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return connect; 
	    }

	} 


