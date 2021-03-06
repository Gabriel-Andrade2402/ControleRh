package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.Connection;

public class dbCon {
	private static Connection conn=null;
	public static Connection getConnection() {
		if(conn==null) {
			try {
				Properties props= loadProperties();
				String url= props.getProperty("dburl");
				conn=DriverManager.getConnection(url, props);
			}catch(SQLException e) {
				System.out.println("Erro : "+e.getMessage());
			}
		}
		return conn;
	}
	private static Properties loadProperties() {
		try(FileInputStream file=new FileInputStream("db.properties")) {
			Properties props= new Properties();
			props.load(file);
			return props;
		}catch(IOException e) {
			System.out.println("Erro : "+ e.getMessage());
		}
		return null;
	}
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Erro : "+ e.getMessage());
			}
		}
	}
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				System.out.println("Erro : "+ e.getMessage());
			}
		}
	}
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro : "+ e.getMessage());
			}
		}
	}
	
}
