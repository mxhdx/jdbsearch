package com.tutoref.dbsearch.database;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tutoref.dbsearch.config.Config;
import com.tutoref.dbsearch.config.DBTypeEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionManager.
 */
public class ConnectionManager {

	/** The instance. */
	private static ConnectionManager instance;
	
	/** The config. */
	private Config config;
	
	/** The combo pooled data source. */
	private ComboPooledDataSource comboPooledDataSource;
	
	/** The db type. */
	private DBTypeEnum dbType;

	/**
	 * Instantiates a new connection manager.
	 */
	private ConnectionManager(){
		config = Config.getConfig();
	}
	
	/**
	 * Gets the single instance of ConnectionManager.
	 *
	 * @return single instance of ConnectionManager
	 */
	public static synchronized ConnectionManager getInstance(){
		if(instance == null)
			instance = new ConnectionManager();
		return instance;
	}
	
	/**
	 * Gets the combo pooled data source.
	 *
	 * @return the combo pooled data source
	 */
	public ComboPooledDataSource getComboPooledDataSource() {
		return comboPooledDataSource;
	}
	
	/**
	 * Test connection parameters.
	 *
	 * @param databaseType the database type
	 * @param databaseHost the database host
	 * @param databasePort the database port
	 * @param databaseName the database name
	 * @param databaseUsername the database username
	 * @param databasePassword the database password
	 * @return true, if successful
	 * @throws PropertyVetoException the property veto exception
	 * @throws SQLException the SQL exception
	 */
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
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			if(tempComboPooledDataSource!=null){
				tempComboPooledDataSource.close();
			}
		}
		return true;
	}
	
	
	/**
	 * Creates the connection pool.
	 *
	 * @param databaseType the database type
	 * @param databaseHost the database host
	 * @param databasePort the database port
	 * @param databaseName the database name
	 * @param databaseUsername the database username
	 * @param databasePassword the database password
	 * @return the combo pooled data source
	 * @throws PropertyVetoException the property veto exception
	 * @throws SQLException the SQL exception
	 */
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
	
	/**
	 * Builds the jdbc url.
	 *
	 * @param databaseType the database type
	 * @param databaseHost the database host
	 * @param databasePort the database port
	 * @param databaseName the database name
	 * @return the string
	 */
	private String buildJdbcUrl(String databaseType, String databaseHost, String databasePort, String databaseName) {
		String prefix = null;
		String dbSpecificParameters="";
		if(DBTypeEnum.MYSQL.symbol().equals(databaseType)){
			dbSpecificParameters="?useSSL=false&serverTimezone=UTC";
			dbType = DBTypeEnum.MYSQL;
			prefix=config.getProperty("jdbc.mysql.prefix");
		}else if(DBTypeEnum.ORACLE.symbol().equals(databaseType)){
			dbType = DBTypeEnum.ORACLE;
			prefix=config.getProperty("jdbc.oracle.prefix");
		}else if(DBTypeEnum.POSTGRESQL.symbol().equals(databaseType)){
			dbType = DBTypeEnum.POSTGRESQL;
			prefix=config.getProperty("jdbc.postgresql.prefix");
		}
		return prefix+databaseHost+":"+databasePort+"/"+databaseName+dbSpecificParameters;
	}

	/**
	 * Gets the DB driver.
	 *
	 * @param databaseType the database type
	 * @return the DB driver
	 */
	private String getDBDriver(String databaseType){
		return config.getProperty("driver." +databaseType);
	}
	
	
	/**
	 * Gets the db type.
	 *
	 * @return the db type
	 */
	public DBTypeEnum getDbType() {
		return dbType;
	}

}
