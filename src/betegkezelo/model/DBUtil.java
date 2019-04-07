package betegkezelo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String DB_URL = "jdbc:mysql://localhost:3306/javabeadando?serverTimezone=Europe/Budapest";
	private static String USER = "testuser";
	private static String PASS = "asdasd1";

	public static Connection GetDBConnection() {
		Connection conn = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Got connection: "+conn);
			return conn;
		} catch (SQLException se) {
			se.getMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public static void DisconnectFromDB(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.getMessage();
		}
	}

}
