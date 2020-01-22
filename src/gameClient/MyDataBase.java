package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
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

	//counts number of games played with server
	public static void countGamesPlayed(int id){ //number of times played, and highest level on
		try {
			connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs where userID= "+id;
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next())
			{   
				NumOfGames++;	 //holds number of games played		
			}

			statement.close();			
			resultSet.close();

			connection.close();	


		}catch(SQLException e) {
			e.printStackTrace();
		}

	}
	//best score of current level on

	public static double[] bestScore(Arena a, int id) throws SQLException{ 


		double[] tmp = new double[2];
		connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
		Statement statement = connection.createStatement();
		String allCustomersQuery1 = "SELECT * FROM Logs where userID= ' "+ id + " ' And levelID = ' " + a.getcurrentlevel() +" ' + ' ORDER BY score DESC ' " ;
		ResultSet resultSet = statement.executeQuery(allCustomersQuery1);

		if(resultSet.next()) {

			tmp[0]= resultSet.getDouble("score");
			tmp[1]= resultSet.getDouble("moves");

		}

		statement.close();			
		resultSet.close();		
		connection.close();	

		return tmp;

	}

	public int getNumOfGames() {
		return this.NumOfGames;
	}


	public static HashMap<Integer, String> AllScores(int id) {
		try {
			connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery1 = "SELECT * FROM Logs as logs inner join (" + "SELECT max(score) as score, levelID FROM Logs"
					+ " where userID = " + id + " group by levelID" + ") as groupedLogs"
					+ " on logs.levelID = groupedLogs.levelID and logs.score = groupedLogs.score" + " where userID = " + id
					+ " order by logs.levelID asc";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery1);

			
			HashMap<Integer, String> tmp = new HashMap<Integer, String>();

			while (resultSet.next()) {
				String value = "" + resultSet.getInt("userID") + "," + resultSet.getInt("levelID") + ","
						+ resultSet.getInt("moves") + "," + resultSet.getInt("score") + "," + resultSet.getDate("time");
				tmp.put(resultSet.getInt("levelID"), value);
			}
			return tmp;	

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;

	}


}
