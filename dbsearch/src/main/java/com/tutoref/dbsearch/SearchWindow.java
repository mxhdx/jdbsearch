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
import java.awt.Component;


import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.tutoref.dbsearch.config.i18n.MessagesBundle;
import com.tutoref.dbsearch.database.C3P0ConnectionPool;
import com.tutoref.dbsearch.database.ConnectionManager;
import com.tutoref.dbsearch.util.Util;

import javax.swing.JDialog;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class SearchWindow {

	private JFrame frame;
	private JMenuBar menuBar;
	private JTable tableResults;
	private JTextField textExpression;
	private ConnectionManager connectionManager;
	private JCheckBox chckCaseSensitive;
	private JCheckBox chckWholeExpression;
	private JCheckBox chckTrim;
	private JSpinner spinnerMaxConnections;
	private int MAX_THREADS_DEFAULT=10;
	
	
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
		connectionManager = ConnectionManager.getInstance();
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
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearch();
			}
		});
		btnSearch.setBounds(405, 19, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		chckWholeExpression = new JCheckBox("Whole expression");
		chckWholeExpression.setBounds(405, 76, 135, 23);
		frame.getContentPane().add(chckWholeExpression);
		
		chckCaseSensitive = new JCheckBox("Case sensitive");
		chckCaseSensitive.setBounds(405, 50, 196, 23);
		frame.getContentPane().add(chckCaseSensitive);
		
		chckTrim = new JCheckBox("Trim");
		chckTrim.setBounds(405, 102, 97, 23);
		frame.getContentPane().add(chckTrim);
		
		JLabel lblExpression = new JLabel("Expression : ");
		lblExpression.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExpression.setBounds(10, 21, 109, 19);
		frame.getContentPane().add(lblExpression);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(665, 507, 89, 23);
		frame.getContentPane().add(btnClear);
		
		spinnerMaxConnections = new JSpinner();
		spinnerMaxConnections.setBounds(123, 51, 55, 20);
		spinnerMaxConnections.setValue(MAX_THREADS_DEFAULT);
		frame.getContentPane().add(spinnerMaxConnections);
		
		JLabel lblMaxConnections = new JLabel("Max connections : ");
		lblMaxConnections.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxConnections.setBounds(10, 54, 109, 14);
		frame.getContentPane().add(lblMaxConnections);
		setEnabled(false);
		openConnectionDialog();
		setEnabled(true);
	}

	private void doSearch(){
		String expression=Util.clean(textExpression.getText());
		boolean caseSensitive = chckCaseSensitive.isSelected();
		boolean wholeExpression = chckWholeExpression.isSelected();
		boolean trim = chckTrim.isSelected();
		int maxThreads = (Integer) spinnerMaxConnections.getValue();
		connectionManager.getComboPooledDataSource().setMaxPoolSize(maxThreads);
		try {
			Connection connection = connectionManager.getComboPooledDataSource().getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("show tables");
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void openConnectionDialog() {
		if(!connectionManager.isConnected()){
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
	
	
	protected JCheckBox getChckCaseSensitive() {
		return chckCaseSensitive;
	}
	protected JCheckBox getChckWholeExpression() {
		return chckWholeExpression;
	}
	protected JCheckBox getChckTrim() {
		return chckTrim;
	}
	protected JSpinner getSpinnerMaxConnections() {
		return spinnerMaxConnections;
	}
}
