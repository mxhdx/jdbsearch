package com.tutoref.dbsearch.util;

import java.awt.Component;

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
	
}
