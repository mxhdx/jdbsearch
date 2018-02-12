package com.tutoref.dbsearch.config.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;



public class MessagesBundle {
	
	private Locale locale = Locale.getDefault();
    private ResourceBundle bundle;
    
	private static MessagesBundle instance;
	

	
	private MessagesBundle(){
		try{
			bundle=ResourceBundle.getBundle("Messages", locale);
		}catch (java.util.MissingResourceException e){
			// if user's locale is not found, load the default messages file
			try{
				bundle=ResourceBundle.getBundle("Messages",new Locale("en","US"));
			}catch (java.util.MissingResourceException e2){
				JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
		}
		
	}
	
	
	public String getMessage(String message){
		return bundle.getString(message);
	}
	
	public static synchronized MessagesBundle getInstance(){
		if(instance == null)
			instance = new MessagesBundle();
		return instance;
	}
	
}
