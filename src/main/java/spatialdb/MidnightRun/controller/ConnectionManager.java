package spatialdb.MidnightRun.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager 
{
	private String url;
	private String username;
	private String password;
	private boolean isConnected = false;
	private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
	private Connection connection = null;
	
	public ConnectionManager()
	{
		Properties props = new Properties();
		try 
		{
			props.load(ConnectionManager.class.getResourceAsStream("/database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.url = props.getProperty("db.url");
		this.username = props.getProperty("db.username");
		this.password = props.getProperty("db.password");
	}
	
	public ConnectionManager(String url, String username, String password)
	{
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public void connect()
	{
		if (!isConnected)
		{
			logger.debug("Loading driver.");
			try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) 
			{
				logger.error("Could not load driver.", e);
				return;
			}
	 
			logger.debug("Driver loaded!");
			
			try 
			{
				logger.info("Connecting to: " + url);
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) 
			{
				logger.error("Could not connect to database.", e);
				return;
			}
			if (connection != null)
			{
				isConnected = true;
			}
			else
			{
				isConnected = false;
			}	
		}
	}
	
	public boolean isConnected()
	{
		logger.info("Connected: " +isConnected);
		return isConnected;
	}
	
	public void closeConnection()
	{
		try 
		{
			connection.close();
			logger.info("Connection closed");
		} catch (SQLException e) 
		{
			logger.error("Could not close connection.", e);
		}
	}
	
	public ResultSet executeQuery(String query) throws SQLException
	{
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);
		return result;
	}
}
