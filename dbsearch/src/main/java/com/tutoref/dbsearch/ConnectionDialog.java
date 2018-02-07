package com.tutoref.dbsearch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class ConnectionDialog extends JDialog {
	private JTextField textHostname;
	private JTextField textPortNumber;
	private JTextField textDatabaseName;
	private JTextField textUsername;
	private JPasswordField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConnectionDialog dialog = new ConnectionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConnectionDialog() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel mainPanel = new JPanel();
			getContentPane().add(mainPanel, BorderLayout.CENTER);
			mainPanel.setLayout(null);
			
			JLabel label = new JLabel("Database type : ");
			label.setBounds(69, 33, 103, 14);
			mainPanel.add(label);
			
			JComboBox<String> comboDatabaseType = new JComboBox<String>();
			comboDatabaseType.setBounds(182, 30, 191, 20);
			mainPanel.add(comboDatabaseType);
			
			textHostname = new JTextField();
			textHostname.setColumns(10);
			textHostname.setBounds(182, 61, 191, 20);
			mainPanel.add(textHostname);
			
			JLabel label_1 = new JLabel("Hostname : ");
			label_1.setBounds(69, 64, 103, 14);
			mainPanel.add(label_1);
			
			textPortNumber = new JTextField();
			textPortNumber.setColumns(10);
			textPortNumber.setBounds(182, 92, 191, 20);
			mainPanel.add(textPortNumber);
			
			JLabel label_2 = new JLabel("Port number : ");
			label_2.setBounds(69, 95, 103, 14);
			mainPanel.add(label_2);
			
			textDatabaseName = new JTextField();
			textDatabaseName.setColumns(10);
			textDatabaseName.setBounds(182, 123, 191, 20);
			mainPanel.add(textDatabaseName);
			
			JLabel label_3 = new JLabel("Database name : ");
			label_3.setBounds(69, 126, 103, 14);
			mainPanel.add(label_3);
			
			JLabel label_4 = new JLabel("Username : ");
			label_4.setBounds(69, 157, 103, 14);
			mainPanel.add(label_4);
			
			textUsername = new JTextField();
			textUsername.setColumns(10);
			textUsername.setBounds(182, 154, 191, 20);
			mainPanel.add(textUsername);
			
			textPassword = new JPasswordField();
			textPassword.setBounds(182, 185, 191, 20);
			mainPanel.add(textPassword);
			
			JLabel label_5 = new JLabel("Password : ");
			label_5.setBounds(69, 188, 103, 14);
			mainPanel.add(label_5);
			
			JButton testButton = new JButton("Test");
			testButton.setBounds(192, 216, 89, 23);
			mainPanel.add(testButton);
			
			JButton connectButton = new JButton("Connect");
			connectButton.setBounds(284, 216, 89, 23);
			mainPanel.add(connectButton);
		}
	}
}
