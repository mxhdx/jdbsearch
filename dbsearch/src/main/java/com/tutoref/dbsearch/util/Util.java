package com.tutoref.dbsearch.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tutoref.dbsearch.config.DBTypeEnum;

/**
 * The Class Util. Reprensents an utility class offering many useful functions.
 */
public class Util {
	
	/**
	 * Clean.
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


	/**
	 * Gets the number of results from resut set.
	 *
	 * @param rs the rs
	 * @return the number of results from resut set
	 */
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
	
	/**
	 * Checks if is blob type.
	 *
	 * @param columnType the column type
	 * @return true, if is blob type
	 */
	public static boolean isBlobType(int columnType) {
		if(java.sql.Types.CLOB == columnType || java.sql.Types.BLOB == columnType)
			return true;
		return false;
	}


	/**
	 * Extract table name.
	 *
	 * @param rsTables the rs tables
	 * @param dbTypeEnum the db type enum
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public static String extractTableName(ResultSet rsTables, DBTypeEnum dbTypeEnum) throws SQLException {
		String tableName=null;
		if(DBTypeEnum.MYSQL.equals(dbTypeEnum)){
			tableName = rsTables.getString(1);
		}else if(DBTypeEnum.ORACLE.equals(dbTypeEnum)){
			tableName = rsTables.getString(1) + "." + rsTables.getString(2);
		}else if(DBTypeEnum.POSTGRESQL.equals(dbTypeEnum)){
			tableName = rsTables.getString(1) + "." + rsTables.getString(2);
		}
		return tableName;
	}
}
