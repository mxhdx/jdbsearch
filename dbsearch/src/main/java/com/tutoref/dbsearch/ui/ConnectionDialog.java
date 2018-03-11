package com.tutoref.dbsearch.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

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
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionDialog.
 */
public class ConnectionDialog extends JDialog {
	
	/** The combo database type. */
	JComboBox<String> comboDatabaseType;
	
	/** The text hostname. */
	private JTextField textHostname;
	
	/** The text port number. */
	private JTextField textPortNumber;
	
	/** The text database name. */
	private JTextField textDatabaseName;
	
	/** The text username. */
	private JTextField textUsername;
	
	/** The text password. */
	private JPasswordField textPassword;
	
	/** The connection manager. */
	private ConnectionManager connectionManager;
	
	/** The progress bar. */
	private JProgressBar progressBar;
	
	/** The messages bundle. */
	private MessagesBundle messagesBundle;
	
	/** The main panel. */
	private JPanel mainPanel;
	
	/** The connection successful. */
	private boolean connectionSuccessful;
	

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
			
			JLabel label = new JLabel(messagesBundle.getMessage("ui.database.type")+" : ");
			label.setBounds(32, 33, 140, 14);
			mainPanel.add(label);
			
			comboDatabaseType = new JComboBox<String>();
			comboDatabaseType.setModel(new DefaultComboBoxModel(DBTypeEnum.MYSQL.values()));
			comboDatabaseType.setBounds(182, 30, 203, 20);
			mainPanel.add(comboDatabaseType);
			
			textHostname = new JTextField();
			textHostname.setColumns(10);
			textHostname.setBounds(182, 61, 203, 20);
			mainPanel.add(textHostname);
			
			JLabel label_1 = new JLabel(messagesBundle.getMessage("ui.hostname")+" : ");
			label_1.setBounds(32, 64, 140, 14);
			mainPanel.add(label_1);
			
			textPortNumber = new JTextField();
			textPortNumber.setColumns(10);
			textPortNumber.setBounds(182, 92, 203, 20);
			mainPanel.add(textPortNumber);
			
			JLabel label_2 = new JLabel(messagesBundle.getMessage("ui.port.number")+" : ");
			label_2.setBounds(32, 95, 140, 14);
			mainPanel.add(label_2);
			
			textDatabaseName = new JTextField();
			textDatabaseName.setColumns(10);
			textDatabaseName.setBounds(182, 123, 203, 20);
			mainPanel.add(textDatabaseName);
			
			JLabel label_3 = new JLabel(messagesBundle.getMessage("ui.database.name")+" : ");
			label_3.setBounds(32, 126, 140, 14);
			mainPanel.add(label_3);
			
			JLabel label_4 = new JLabel(messagesBundle.getMessage("ui.username")+" : ");
			label_4.setBounds(32, 157, 140, 14);
			mainPanel.add(label_4);
			
			textUsername = new JTextField();
			textUsername.setColumns(10);
			textUsername.setBounds(182, 154, 203, 20);
			mainPanel.add(textUsername);
			
			textPassword = new JPasswordField();
			textPassword.setBounds(182, 185, 203, 20);
			mainPanel.add(textPassword);
			
			JLabel label_5 = new JLabel(messagesBundle.getMessage("ui.password")+" : ");
			label_5.setBounds(32, 188, 140, 14);
			mainPanel.add(label_5);
			
			JButton testButton = new JButton(messagesBundle.getMessage("ui.test"));
			testButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new Thread(new Runnable() {
						public void run() {
							getProgressBar().setVisible(true);
							getProgressBar().setValue(50);
							setFieldsEnabled(false);
							try {
								if(connectionManager.testConnectionParameters(Util.clean(String.valueOf(comboDatabaseType.getSelectedItem())), Util.clean(textHostname.getText()), Util.clean(textPortNumber.getText()), Util.clean(textDatabaseName.getText()), Util.clean(textUsername.getText()), Util.clean(textPassword.getText()))){
									JOptionPane.showMessageDialog(getContentPane(), messagesBundle.getMessage("db.informations.valid"), messagesBundle.getMessage("success"), JOptionPane.INFORMATION_MESSAGE);
									getProgressBar().setValue(0);
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(getContentPane(), messagesBundle.getMessage("db.informations.not.valid")+":\n"+Util.getRootException(e).getLocalizedMessage(), messagesBundle.getMessage("error"), JOptionPane.ERROR_MESSAGE);
								getProgressBar().setValue(0);
							} finally{
								getProgressBar().setValue(100);
								getProgressBar().setVisible(false);
								setFieldsEnabled(true);
							}
							
						}
					}).start();
					
				}
			});
			testButton.setBounds(182, 216, 95, 23);
			mainPanel.add(testButton);
			
			JButton connectButton = new JButton(messagesBundle.getMessage("ui.connect"));
			connectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						public void run() {
							getProgressBar().setVisible(true);
							getProgressBar().setValue(50);
							setFieldsEnabled(false);
							try {
								connectionManager.createConnectionPool(Util.clean(String.valueOf(comboDatabaseType.getSelectedItem())), Util.clean(textHostname.getText()), Util.clean(textPortNumber.getText()), Util.clean(textDatabaseName.getText()), Util.clean(textUsername.getText()), Util.clean(textPassword.getText()));
								getProgressBar().setValue(0);
								setVisible(false);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(getContentPane(), messagesBundle.getMessage("db.informations.not.valid")+":\n"+Util.getRootException(e).getLocalizedMessage(), messagesBundle.getMessage("error"), JOptionPane.ERROR_MESSAGE);
								getProgressBar().setValue(0);
							} finally{
								connectionSuccessful=true;
								getProgressBar().setValue(100);
								getProgressBar().setVisible(false);
								setFieldsEnabled(true);
							}
						}
					}).start();
				}
			});
			connectButton.setBounds(281, 216, 104, 23);
			mainPanel.add(connectButton);
			
			progressBar = new JProgressBar();
			progressBar.setBounds(testButton.getBounds().x, 250, 146, 14);
			progressBar.setValue(0);
			progressBar.setMaximum(100);
			progressBar.setVisible(false);
			mainPanel.add(progressBar);

			// TODO, remove me
			textHostname.setText("localhost");
			textDatabaseName.setText("postgres");
			textPortNumber.setText("5432");
			textUsername.setText("postgres");
			textPassword.setText("");
			comboDatabaseType.setSelectedIndex(2);
		}
	}
	
	/**
	 * Gets the progress bar.
	 *
	 * @return the progress bar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	/**
	 * Enables or disables all the components in the main JPanel.
	 * 
	 * @param enabled boolean if true, enable all the components in the main JPanel, otherwise disable them.
	 */
	private void setFieldsEnabled(boolean enabled){
		for(Component component: mainPanel.getComponents()){
			component.setEnabled(enabled);
		}
	}

	/**
	 * Checks if is connection successful.
	 *
	 * @return true, if is connection successful
	 */
	public boolean isConnectionSuccessful() {
		return connectionSuccessful;
	}	
	
}
