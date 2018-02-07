package com.tutoref.dbsearch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import com.tutoref.dbsearch.config.Config;
import com.tutoref.dbsearch.config.i18n.MessagesBundle;
import com.tutoref.dbsearch.database.C3P0ConnectionPool;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;

public class SearchWindow {

	private JFrame frame;
	private JMenuBar menuBar;
	private JTable tableResults;
	private JTextField textExpression;
	
	private C3P0ConnectionPool connectionPool;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow window = new SearchWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuMenu = new JMenu("Menu");
		menuBar.add(menuMenu);
		
		JMenuItem menuItemNewConnection = new JMenuItem("New connection");
		menuMenu.add(menuItemNewConnection);
		
		JSeparator separator = new JSeparator();
		menuMenu.add(separator);
		
		JMenuItem menuItemExportResults = new JMenuItem("Export Results");
		menuMenu.add(menuItemExportResults);
		
		JSeparator separator_1 = new JSeparator();
		menuMenu.add(separator_1);
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuMenu.add(menuItemExit);
		
		JMenu menuHelp = new JMenu("?");
		menuBar.add(menuHelp);
		
		JMenuItem menuItemHelp = new JMenuItem("Help");
		menuHelp.add(menuItemHelp);
		
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuHelp.add(menuItemAbout);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 134, 744, 362);
		frame.getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Results", null, scrollPane, null);
		
		tableResults = new JTable();
		scrollPane.setViewportView(tableResults);
		
		textExpression = new JTextField();
		textExpression.setBounds(123, 20, 270, 20);
		frame.getContentPane().add(textExpression);
		textExpression.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(405, 19, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JCheckBox chckWholeExpression = new JCheckBox("Whole expression");
		chckWholeExpression.setBounds(405, 76, 135, 23);
		frame.getContentPane().add(chckWholeExpression);
		
		JCheckBox chckCaseSensitive = new JCheckBox("Case sensitive");
		chckCaseSensitive.setBounds(405, 50, 196, 23);
		frame.getContentPane().add(chckCaseSensitive);
		
		JCheckBox chckTrim = new JCheckBox("Trim");
		chckTrim.setBounds(405, 102, 97, 23);
		frame.getContentPane().add(chckTrim);
		
		JLabel lblExpression = new JLabel("Expression : ");
		lblExpression.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExpression.setBounds(10, 21, 109, 19);
		frame.getContentPane().add(lblExpression);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(665, 507, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JSpinner spinnerMaxConnections = new JSpinner();
		spinnerMaxConnections.setBounds(123, 51, 55, 20);
		spinnerMaxConnections.setValue(10);
		frame.getContentPane().add(spinnerMaxConnections);
		
		JLabel lblMaxConnections = new JLabel("Max connections : ");
		lblMaxConnections.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxConnections.setBounds(10, 54, 109, 14);
		frame.getContentPane().add(lblMaxConnections);
		setEnabled(false);
		openConnectionDialog();
		MessagesBundle messagesBundle = MessagesBundle.getMessages();
		System.out.println(messagesBundle.getMessage("database.mysql"));
	}

	private void openConnectionDialog() {
		if(connectionPool==null || !connectionPool.isConnected()){
			JDialog connectionDialog = new ConnectionDialog();
			connectionDialog.setVisible(true);
		}
		
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	/**
	 * Enables or disables all the components in the main JPanel.
	 * 
	 * @param enabled boolean if true, enable all the components in the main JPanel, otherwise disable them.
	 */
	private void setEnabled(boolean enabled){
		for(Component component: this.frame.getContentPane().getComponents()){
			component.setEnabled(enabled);
		}
	}
	
	
}
