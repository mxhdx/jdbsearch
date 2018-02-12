package com.tutoref.dbsearch;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tutoref.dbsearch.config.DBTypeEnum;
import com.tutoref.dbsearch.config.i18n.MessagesBundle;
import com.tutoref.dbsearch.database.ConnectionManager;
import com.tutoref.dbsearch.util.Util;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class ConnectionDialog extends JDialog {
	
	JComboBox<String> comboDatabaseType;
	private JTextField textHostname;
	private JTextField textPortNumber;
	private JTextField textDatabaseName;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private ConnectionManager connectionManager;
	private JProgressBar progressBar;
	private MessagesBundle messagesBundle;
	private JPanel mainPanel;
	
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
		connectionManager = ConnectionManager.getInstance();
		messagesBundle = MessagesBundle.getInstance();
		setModal(true);
		setBounds(100, 100, 450, 307);
		getContentPane().setLayout(new BorderLayout());
		{
			mainPanel = new JPanel();
			getContentPane().add(mainPanel, BorderLayout.CENTER);
			mainPanel.setLayout(null);
			
			JLabel label = new JLabel("Database type : ");
			label.setBounds(69, 33, 103, 14);
			mainPanel.add(label);
			
			comboDatabaseType = new JComboBox<String>();
			comboDatabaseType.setModel(new DefaultComboBoxModel(DBTypeEnum.MYSQL.values()));
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
			testButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new Thread(new Runnable() {
						public void run() {
							getProgressBar().setVisible(true);
							getProgressBar().setValue(50);
							setFieldEnabled(false);
							try {
								if(connectionManager.testConnectionParameters(Util.clean(String.valueOf(comboDatabaseType.getSelectedItem())), Util.clean(textHostname.getText()), Util.clean(textPortNumber.getText()), Util.clean(textDatabaseName.getText()), Util.clean(textUsername.getText()), Util.clean(textPassword.getText()))){
									JOptionPane.showMessageDialog(getContentPane(), messagesBundle.getMessage("db.informations.valid"), messagesBundle.getMessage("Success"), JOptionPane.INFORMATION_MESSAGE);
									getProgressBar().setValue(0);
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(getContentPane(), messagesBundle.getMessage("db.informations.not.valid")+":\n"+Util.getRootException(e).getLocalizedMessage(), messagesBundle.getMessage("Failed"), JOptionPane.ERROR_MESSAGE);
								getProgressBar().setValue(0);
							} finally{
								getProgressBar().setValue(100);
								getProgressBar().setVisible(false);
								setFieldEnabled(true);
							}
							
						}
					}).start();
					
				}
			});
			testButton.setBounds(192, 216, 89, 23);
			mainPanel.add(testButton);
			
			JButton connectButton = new JButton("Connect");
			connectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						public void run() {
							getProgressBar().setVisible(true);
							getProgressBar().setValue(50);
							setFieldEnabled(false);
							try {
								connectionManager.createConnectionPool(Util.clean(String.valueOf(comboDatabaseType.getSelectedItem())), Util.clean(textHostname.getText()), Util.clean(textPortNumber.getText()), Util.clean(textDatabaseName.getText()), Util.clean(textUsername.getText()), Util.clean(textPassword.getText()));
								getProgressBar().setValue(0);
								setVisible(false);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(getContentPane(), messagesBundle.getMessage("db.informations.not.valid")+":\n"+Util.getRootException(e).getLocalizedMessage(), messagesBundle.getMessage("Failed"), JOptionPane.ERROR_MESSAGE);
								getProgressBar().setValue(0);
							} finally{
								getProgressBar().setValue(100);
								getProgressBar().setVisible(false);
								setFieldEnabled(true);
							}
						}
					}).start();
				}
			});
			connectButton.setBounds(284, 216, 89, 23);
			mainPanel.add(connectButton);
			
			progressBar = new JProgressBar();
			progressBar.setBounds(testButton.getBounds().x, 250, 146, 14);
			progressBar.setValue(0);
			progressBar.setMaximum(100);
			progressBar.setVisible(false);
			mainPanel.add(progressBar);
		}
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	/**
	 * Enables or disables all the components in the main JPanel.
	 * 
	 * @param enabled boolean if true, enable all the components in the main JPanel, otherwise disable them.
	 */
	private void setFieldEnabled(boolean enabled){
		for(Component component: mainPanel.getComponents()){
			component.setEnabled(enabled);
		}
	}
}
