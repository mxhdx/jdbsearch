package com.tutoref.dbsearch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;

/**
 * 
 * @author Mahdi El Masaoudi
 * 
 *
 */
public class ConnectionWindow {

	private JFrame frmConnection;
	private JTextField textHostname;
	private JTextField textPortNumber;
	private JTextField textDatabaseName;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JComboBox<String> comboDatabaseType;
	private JButton btnTest;
	private JButton btnConnect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionWindow window = new ConnectionWindow();
					window.frmConnection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectionWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConnection = new JFrame();
		frmConnection.setResizable(false);
		frmConnection.setTitle("Connection");
		frmConnection.setBounds(100, 100, 467, 317);
		frmConnection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnection.getContentPane().setLayout(null);
		
		textHostname = new JTextField();
		textHostname.setColumns(10);
		textHostname.setBounds(200, 66, 191, 20);
		frmConnection.getContentPane().add(textHostname);
		
		textPortNumber = new JTextField();
		textPortNumber.setColumns(10);
		textPortNumber.setBounds(200, 97, 191, 20);
		frmConnection.getContentPane().add(textPortNumber);
		
		textDatabaseName = new JTextField();
		textDatabaseName.setColumns(10);
		textDatabaseName.setBounds(200, 128, 191, 20);
		frmConnection.getContentPane().add(textDatabaseName);
		
		comboDatabaseType = new JComboBox<String>();
		comboDatabaseType.setBounds(200, 35, 191, 20);
		frmConnection.getContentPane().add(comboDatabaseType);
		
		textUsername = new JTextField();
		textUsername.setBounds(200, 159, 191, 20);
		frmConnection.getContentPane().add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(200, 190, 191, 20);
		frmConnection.getContentPane().add(textPassword);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(302, 221, 89, 23);
		frmConnection.getContentPane().add(btnConnect);
		
		btnTest = new JButton("Test");
		btnTest.setBounds(210, 221, 89, 23);
		frmConnection.getContentPane().add(btnTest);
		
		JLabel lblDatabaseType = new JLabel("Database type : ");
		lblDatabaseType.setBounds(87, 38, 103, 14);
		frmConnection.getContentPane().add(lblDatabaseType);
		
		JLabel lblHostname = new JLabel("Hostname : ");
		lblHostname.setBounds(87, 69, 103, 14);
		frmConnection.getContentPane().add(lblHostname);
		
		JLabel lblPortNumber = new JLabel("Port number : ");
		lblPortNumber.setBounds(87, 100, 103, 14);
		frmConnection.getContentPane().add(lblPortNumber);
		
		JLabel lblDatabaseName = new JLabel("Database name : ");
		lblDatabaseName.setBounds(87, 131, 103, 14);
		frmConnection.getContentPane().add(lblDatabaseName);
		
		JLabel lblUsername = new JLabel("Username : ");
		lblUsername.setBounds(87, 162, 103, 14);
		frmConnection.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setBounds(87, 193, 103, 14);
		frmConnection.getContentPane().add(lblPassword);
	}
	
	public JComboBox<String> getComboDatabaseType() {
		return comboDatabaseType;
	}
	
	public JTextField getTextUsername() {
		return textUsername;
	}
	
	public JTextField getTextDatabaseName() {
		return textDatabaseName;
	}
	
	public JTextField getTextPortNumber() {
		return textPortNumber;
	}
	
	public JPasswordField getTextPassword() {
		return textPassword;
	}
	
	public JTextField getTextHostname() {
		return textHostname;
	}
	
	public JButton getBtnTest() {
		return btnTest;
	}
	
	public JButton getBtnConnect() {
		return btnConnect;
	}
}
