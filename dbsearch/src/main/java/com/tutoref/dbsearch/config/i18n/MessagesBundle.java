package com.tutoref.dbsearch.config.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;



/**
 * The Class MessagesBundle. Loads the localized strings from the messages text file.
 */
public class MessagesBundle {
	
	/** The locale. */
	private Locale locale = Locale.getDefault();
    
    /** The bundle. */
    private ResourceBundle bundle;
    
	/** The instance. */
	private static MessagesBundle instance;
	

	
	/**
	 * Instantiates a new messages bundle.
	 */
	private MessagesBundle(){
		try{
			// if french, use France's french
			if("fr"==locale.getLanguage()){
				locale=new Locale("fr", "FR");
			}
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
	
	
	/**
	 * Gets the message.
	 *
	 * @param message the message
	 * @return the message
	 */
	public String getMessage(String message){
		return bundle.getString(message);
	}
	
	/**
	 * Gets the single instance of MessagesBundle.
	 *
	 * @return single instance of MessagesBundle
	 */
	public static synchronized MessagesBundle getInstance(){
		if(instance == null)
			instance = new MessagesBundle();
		return instance;
	}
	
}
