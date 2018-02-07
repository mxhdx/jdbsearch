package com.tutoref.dbsearch.database;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public interface C3P0ConnectionPool {

	public Connection getConnection();
	
	public ComboPooledDataSource getComboPooledDataSource();
	
	public boolean isConnected();
}
