package com.tutoref.dbsearch.config;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * The Class Config.
 */
public class Config {
	
	/** The Constant PROPERTIES_FILE_PATH. */
	final private static String PROPERTIES_FILE_PATH="config.properties";
	
	/** The instance. */
	private static Config instance;
	
	/** The properties. */
	private static Properties properties;
	
	/**
	 * Instantiates a new config.
	 */
	private Config(){
		try{
			properties = new Properties();
			properties.load(Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH));
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gets a property from the config file.
	 *
	 * @param name the name
	 * @return the property
	 */
	public String getProperty(String name){
		return properties.getProperty(name);
	}
	
	/**
	 * Gets the Config class instance.
	 *
	 * @return the config
	 */
	public static synchronized Config getConfig(){
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
}
