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
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;

public class SearchWindow {

	private JFrame frame;
	private JMenuBar menuBar;
	private JTable tableResults;
	private JTextField textField;

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
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewConnection = new JMenuItem("New connection");
		mnNewMenu.add(mntmNewConnection);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenuItem mntmExportResults = new JMenuItem("Export Results");
		mnNewMenu.add(mntmExportResults);
		
		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		
		JMenu menu = new JMenu("?");
		menuBar.add(menu);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		menu.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		menu.add(mntmAbout);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 134, 744, 362);
		frame.getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Results", null, scrollPane, null);
		
		tableResults = new JTable();
		scrollPane.setViewportView(tableResults);
		
		textField = new JTextField();
		textField.setBounds(10, 41, 291, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(311, 40, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Whole expression");
		chckbxNewCheckBox.setBounds(10, 68, 135, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Case sensitive");
		chckbxNewCheckBox_1.setBounds(204, 68, 196, 23);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Trim");
		chckbxNewCheckBox_2.setBounds(10, 94, 97, 23);
		frame.getContentPane().add(chckbxNewCheckBox_2);
		
		JLabel lblNewLabel = new JLabel("Expression : ");
		lblNewLabel.setBounds(10, 21, 82, 19);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Clear");
		btnNewButton_1.setBounds(665, 507, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
