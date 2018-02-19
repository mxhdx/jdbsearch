package com.tutoref.dbsearch.database;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tutoref.dbsearch.config.Config;
import com.tutoref.dbsearch.config.DBTypeEnum;

public class ConnectionManager {

	private static ConnectionManager instance;
	private Config config;
	private ComboPooledDataSource comboPooledDataSource;
	private DBTypeEnum dbType;

	private ConnectionManager(){
		config = Config.getConfig();
	}
	
	public static synchronized ConnectionManager getInstance(){
		if(instance == null)
			instance = new ConnectionManager();
		return instance;
	}
	
	public ComboPooledDataSource getComboPooledDataSource() {
		return comboPooledDataSource;
	}
	
	public boolean testConnectionParameters(String databaseType, String databaseHost, String databasePort, String databaseName, String databaseUsername, String databasePassword) throws PropertyVetoException, SQLException{
		ComboPooledDataSource tempComboPooledDataSource = new ComboPooledDataSource();
		databaseType = databaseType.toLowerCase();
		try {
			tempComboPooledDataSource.setDriverClass(getDBDriver(databaseType));
			tempComboPooledDataSource.setJdbcUrl(buildJdbcUrl(databaseType, databaseHost, databasePort, databaseName));
			tempComboPooledDataSource.setUser(databaseUsername);
			tempComboPooledDataSource.setPassword(databasePassword);
			tempComboPooledDataSource.getConnection();
		} catch (PropertyVetoException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} finally{
			if(tempComboPooledDataSource!=null){
				tempComboPooledDataSource.close();
			}
		}
		return true;
	}
	
	
	public ComboPooledDataSource createConnectionPool(String databaseType, String databaseHost, String databasePort, String databaseName, String databaseUsername, String databasePassword) throws PropertyVetoException, SQLException{
		comboPooledDataSource = new ComboPooledDataSource();
		databaseType = databaseType.toLowerCase();
		try {
			comboPooledDataSource.setDriverClass(getDBDriver(databaseType));
			comboPooledDataSource.setJdbcUrl(buildJdbcUrl(databaseType, databaseHost, databasePort, databaseName));
	  	  	comboPooledDataSource.setUser(databaseUsername);
	  	  	comboPooledDataSource.setPassword(databasePassword);
	  	  	comboPooledDataSource.getConnection();
		} catch (PropertyVetoException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}
		return comboPooledDataSource;
	}
	
	private String buildJdbcUrl(String databaseType, String databaseHost, String databasePort, String databaseName) {
		String prefix = "jdbc:"+databaseType+"://";
		String dbSpecificParameters="";
		if(DBTypeEnum.MYSQL.symbol().equals(databaseType)){
			dbSpecificParameters="?useSSL=false&serverTimezone=UTC";
			dbType = DBTypeEnum.MYSQL;
		}
		return prefix+databaseHost+":"+databasePort+"/"+databaseName+dbSpecificParameters;
	}

	private String getDBDriver(String databaseType){
		return config.getProperty("driver." +databaseType);
	}
	
	
	public DBTypeEnum getDbType() {
		return dbType;
	}

	public boolean isConnected(){
		return false; //TODO
	}
}
