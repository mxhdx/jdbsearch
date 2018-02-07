package com.tutoref.dbsearch.config;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Config {
	
	final private static String PROPERTIES_FILE_PATH="config.properties";
	
	private static Config instance;
	
	private static Properties properties;
	
	private Config(){
		try{
			properties = new Properties();
			properties.load(Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH));
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	
	public String getProperty(String name){
		return properties.getProperty(name);
	}
	
	public static synchronized Config getConfig(){
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
}
