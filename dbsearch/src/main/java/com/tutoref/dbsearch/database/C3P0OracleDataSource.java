package com.tutoref.dbsearch.database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0OracleDataSource {
	
   private static C3P0OracleDataSource instance;
   private ComboPooledDataSource comboPooledDataSource;

   private C3P0OracleDataSource() {
      try {
    	  comboPooledDataSource = new ComboPooledDataSource();
    	  comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
    	  comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/wpdemo?useSSL=false");
    	  comboPooledDataSource.setUser("root");
    	  comboPooledDataSource.setPassword("dbEYKog58+");
      }catch (PropertyVetoException e) {
         e.printStackTrace();
      }
   }

   public static C3P0OracleDataSource getInstance() {
      if (instance == null)
         instance = new C3P0OracleDataSource();
      return instance;
   }

   public Connection getConnection() {
      Connection connection = null;
      try {
    	  connection = comboPooledDataSource.getConnection();
      }catch (SQLException e) {
         e.printStackTrace();
      }
      return connection;
   }
   
   public static void main(String[] args) {
	   C3P0OracleDataSource i = C3P0OracleDataSource.getInstance();
	   i.getConnection();
   }
}