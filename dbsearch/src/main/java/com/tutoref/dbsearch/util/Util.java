package com.tutoref.dbsearch.util;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

public class Util {
	
	/**
	 * 
	 * @param value String The string to clean
	 * @return String The cleaned value
	 */
	public static String clean(String value){
		if(value == null)
			return "";
		value = value.trim();
		return value;
	}
	
	
	/**
	 * Gets the root exception.
	 *
	 * @param exception the exception
	 * @return the root exception
	 */
	public static Throwable getRootException(Throwable exception){
		Throwable rootException=exception;
		while(rootException.getCause()!=null){
			rootException = rootException.getCause();
		}
		return rootException;
	}


	public static int getNumberOfResultsFromResutSet(ResultSet rs) {
		int count = 0;
		try {
			while(rs.next()){
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static boolean isBlobType(int columnType) {
		if(java.sql.Types.CLOB == columnType || java.sql.Types.BLOB == columnType)
			return true;
		return false;
	}
}
