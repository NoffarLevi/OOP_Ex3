package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;


public class MyDataBase {

	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";
	private static int id;
	public static int playSize = 0;
	public static int [][]check; 
	static Connection connection;
	static int NumOfGames =0;
	
	public static void countGamesPlayed(){ //number of times played, and highest level on
		try {
	
		connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
		Statement statement = connection.createStatement();
		String allCustomersQuery = "SELECT * FROM Logs where userID="+id;
		ResultSet resultSet = statement.executeQuery(allCustomersQuery);
	
		while(resultSet.next())
		{   
			NumOfGames++;			
		}
		System.out.println("count "+NumOfGames);
		
		statement.close();			
		resultSet.close();
		
		connection.close();	

		
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}
	
//	public static int currentLevel(){ //current level on
//		try {
//	
//		connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
//		Statement statement = connection.createStatement();
//		String allCustomersQuery1 = "SELECT levelNum FROM Users where userID="+id;
//		ResultSet resultSet = statement.executeQuery(allCustomersQuery1);
//				
//		statement.close();			
//		resultSet.close();		
//		connection.close();	
//		
//		return resultSet.getInt("levelNum");
//		
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	public void setPlayerID(int i) {
		id =i;
	}
	
	public int getNumOfGames() {
		return this.NumOfGames;
	}
	
	//System.out.println("Id: " + resultSet.getInt("UserID")+", "+resultSet.getInt("levelID")+", "+resultSet.getInt("moves")+","+resultSet.getDate("time"));

	
}
