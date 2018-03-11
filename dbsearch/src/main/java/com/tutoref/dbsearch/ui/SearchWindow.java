package com.tutoref.dbsearch.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

import com.tutoref.dbsearch.config.DBTypeEnum;
import com.tutoref.dbsearch.config.i18n.MessagesBundle;
import com.tutoref.dbsearch.database.ConnectionManager;
import com.tutoref.dbsearch.tasks.SearchTask;
import com.tutoref.dbsearch.util.Util;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.JProgressBar;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchWindow.
 */
public class SearchWindow {
	
	/** The Constant EXPORT_DELIMITER. */
	public static final String EXPORT_DELIMITER="|";

	/** The frame. */
	private JFrame frame;
	
	/** The menu bar. */
	private JMenuBar menuBar;
	
	/** The table results. */
	private JTable tableResults;
	
	/** The table model. */
	private DefaultTableModel tableModel;
	
	/** The text expression. */
	private JTextField textExpression;
	
	/** The connection manager. */
	private ConnectionManager connectionManager;
	
	/** The chck case sensitive. */
	private JCheckBox chckCaseSensitive;
	
	/** The chck whole expression. */
	private JCheckBox chckWholeExpression;
	
	/** The chck trim. */
	private JCheckBox chckTrim;
	
	/** The spinner max connections. */
	private JSpinner spinnerMaxConnections;
	
	/** The max threads default. */
	private int MAX_THREADS_DEFAULT=10;
	
	/** The panel. */
	private JPanel panel;
	
	/** The progress bar. */
	private JProgressBar progressBar;
	
	/** The executor service. */
	private ExecutorService executorService;
	
	/** The connection dialog. */
	private ConnectionDialog connectionDialog;
	
	/** The messages bundle. */
	private MessagesBundle messagesBundle;
	
	/** The is searching. */
	private boolean isSearching;
	
	/** The btn search. */
	private JButton btnSearch;
	
	/** The tabbed pane. */
	private JTabbedPane tabbedPane;
	
	
	/**
	 * Launch the application.
	 *
	 * @param args the arguments
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
		messagesBundle = MessagesBundle.getInstance();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 780, 625);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuMenu = new JMenu("Menu");
		menuBar.add(menuMenu);
		
		JSeparator separator = new JSeparator();
		menuMenu.add(separator);
		
		JMenuItem menuItemExportResults = new JMenuItem(messagesBundle.getMessage("ui.export"));
		menuItemExportResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportResults();
			}
		});
		menuMenu.add(menuItemExportResults);
		
		JSeparator separator_1 = new JSeparator();
		menuMenu.add(separator_1);
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmWindowClosing();
			}
		});
		menuMenu.add(menuItemExit);
		// exit behavior
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmWindowClosing();
			}
		});
		
		JMenu menuHelp = new JMenu("?");
		menuBar.add(menuHelp);
		
		JMenuItem menuItemHelp = new JMenuItem(messagesBundle.getMessage("ui.help"));
		menuHelp.add(menuItemHelp);
		
		JMenuItem menuItemAbout = new JMenuItem(messagesBundle.getMessage("ui.about"));
		menuHelp.add(menuItemAbout);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 134, 744, 362);
		frame.getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab(messagesBundle.getMessage("ui.results"), null, scrollPane, null);
		
		tableResults = new JTable();
		scrollPane.setViewportView(tableResults);
		tableModel = new DefaultTableModel(0, 0);
		String header[] = new String[] { messagesBundle.getMessage("ui.table"), messagesBundle.getMessage("ui.column"), messagesBundle.getMessage("ui.value")}; 
		tableModel.setColumnIdentifiers(header);
		tableResults.setModel(tableModel);
		
		textExpression = new JTextField();
		textExpression.setBounds(123, 20, 270, 20);
		frame.getContentPane().add(textExpression);
		textExpression.setColumns(10);
		
		btnSearch = new JButton(messagesBundle.getMessage("ui.search"));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						if(isSearching){
							shutdownAllTasks();
						}else{
							isSearching=true;
							setEnabled(false);
							getBtnSearch().setText(messagesBundle.getMessage("ui.stop"));
							doSearch();
						}
					}
				}).start();
				
			}
		});
		btnSearch.setBounds(405, 19, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		chckWholeExpression = new JCheckBox(messagesBundle.getMessage("ui.whole.expression"));
		chckWholeExpression.setBounds(405, 76, 196, 23);
		frame.getContentPane().add(chckWholeExpression);
		
		chckCaseSensitive = new JCheckBox(messagesBundle.getMessage("ui.case.sensitive"));
		chckCaseSensitive.setBounds(405, 50, 196, 23);
		frame.getContentPane().add(chckCaseSensitive);
		
		chckTrim = new JCheckBox(messagesBundle.getMessage("ui.trim"));
		chckTrim.setBounds(405, 102, 196, 23);
		frame.getContentPane().add(chckTrim);
		
		JLabel lblExpression = new JLabel(messagesBundle.getMessage("ui.expression")+" : ");
		lblExpression.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExpression.setBounds(10, 21, 109, 19);
		frame.getContentPane().add(lblExpression);
		
		JButton btnClear = new JButton(messagesBundle.getMessage("ui.clear"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearResults();
			}
		});
		btnClear.setBounds(665, 507, 89, 23);
		frame.getContentPane().add(btnClear);
		
		spinnerMaxConnections = new JSpinner();
		spinnerMaxConnections.setBounds(123, 51, 55, 20);
		spinnerMaxConnections.setValue(MAX_THREADS_DEFAULT);
		frame.getContentPane().add(spinnerMaxConnections);
		
		JLabel lblMaxConnections = new JLabel(messagesBundle.getMessage("ui.max.connections")+" : ");
		lblMaxConnections.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxConnections.setBounds(10, 54, 109, 14);
		frame.getContentPane().add(lblMaxConnections);
		
		panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 545, 764, 20);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(618, 0, 146, 20);
		progressBar.setVisible(false);
		panel.add(progressBar);
		setEnabled(false);
		openConnectionDialog();
		setEnabled(true);
		if(!connectionDialog.isConnectionSuccessful()){
			System.exit(0);
		}
		
	}

	/**
	 * Do search.
	 */
	private void doSearch(){
		String expression=Util.clean(textExpression.getText());
		boolean caseSensitive = chckCaseSensitive.isSelected();
		boolean wholeExpression = chckWholeExpression.isSelected();
		boolean trim = chckTrim.isSelected();
		int maxThreads = (Integer) spinnerMaxConnections.getValue();
		connectionManager.getComboPooledDataSource().setMaxPoolSize(maxThreads);		
		executorService= Executors.newFixedThreadPool(maxThreads);
		progressBar.setVisible(true);
		progressBar.setValue(0);
		Connection connection;
		try {
			connection = connectionManager.getComboPooledDataSource().getConnection();
			Statement stTables = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rsTables = stTables.executeQuery(getListTablesQuery(connectionManager.getDbType()));
			int nbTables = Util.getNumberOfResultsFromResutSet(rsTables);
			rsTables.beforeFirst();
			progressBar.setMaximum(nbTables);
			while(rsTables.next()){
				String tableName = Util.extractTableName(rsTables, connectionManager.getDbType());
				final Future<Object> searchTask=executorService.submit(new SearchTask(connectionManager, expression, caseSensitive, wholeExpression, trim, tableName, tableModel, progressBar));
			} 
			executorService.shutdown();
			try {
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				setEnabled(true);
				isSearching=false;
				JOptionPane.showMessageDialog(frame, messagesBundle.getMessage("ui.search.completed"),messagesBundle.getMessage("ui.search.completed"),JOptionPane.INFORMATION_MESSAGE);
				getBtnSearch().setText(messagesBundle.getMessage("ui.search"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Shutdown all tasks.
	 */
	private void shutdownAllTasks(){
		if(executorService != null ){
			executorService.shutdownNow();
		}
		setEnabled(true);
		isSearching=false;
		getBtnSearch().setText(messagesBundle.getMessage("ui.search"));
	}
	
	/**
	 * Gets the list tables query.
	 *
	 * @param dbType the db type
	 * @return the list tables query
	 */
	private String getListTablesQuery(DBTypeEnum dbType) {
		String req=null;
		switch(dbType){
		case MYSQL:
			req="show tables";
			break;
		case ORACLE:
			req="select * from all_tables";
			break;
		case POSTGRESQL:
			req="SELECT table_schema,table_name FROM information_schema.tables";
			break;
		default:
			// N.A
		}
		return req;
	}


	
	/**
	 * Clear results.
	 */
	private void clearResults(){
		int dialogResult = JOptionPane.showConfirmDialog(frame, messagesBundle.getMessage("ui.confirm.clear.results"),messagesBundle.getMessage("ui.confirm"),JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
		}
	}
	
	
	/**
	 * Export results.
	 */
	private void exportResults(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle( messagesBundle.getMessage("ui.specify.filename"));   
		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File exportFile = fileChooser.getSelectedFile();
		    if(!exportFile.exists()) {
		    	try {
					exportFile.createNewFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frame, messagesBundle.getMessage("cannot.create.file")+":\n"+Util.getRootException(e).getLocalizedMessage(), messagesBundle.getMessage("error"), JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
		    }
		    StringBuilder sb = new StringBuilder();
		    for(int i=0;i<tableModel.getRowCount();i++){
		    	for(int j=0;j<tableModel.getColumnCount();j++){
		    		String value = (String)tableModel.getValueAt(i, j);
		    		if(value==null)
		    			value="";
		    		sb.append(value);
		    		if(j<tableModel.getColumnCount())
		    			sb.append(EXPORT_DELIMITER);
		    		
		    	}
		    	sb.append("\n");
		    }
		    try {
		    	  FileWriter fw = new FileWriter(exportFile);
		    	  fw.write(sb.toString());
		    	  fw.close();
		    	  JOptionPane.showMessageDialog(frame, messagesBundle.getMessage("file.created"),messagesBundle.getMessage("file.created"),JOptionPane.INFORMATION_MESSAGE);
		    	} catch (IOException e) {
		    		JOptionPane.showMessageDialog(frame, messagesBundle.getMessage("cannot.save.file")+":\n"+Util.getRootException(e).getLocalizedMessage(), messagesBundle.getMessage("error"), JOptionPane.ERROR_MESSAGE);
		    	  e.printStackTrace();
		    	}
		    
		}
	}
	
	/**
	 * Open connection dialog.
	 */
	private void openConnectionDialog() {
		connectionDialog = new ConnectionDialog();
		connectionDialog.setVisible(true);
	}

	/**
	 * Gets the menu bar.
	 *
	 * @return the menu bar
	 */
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
		getTableResults().setEnabled(enabled);
		//must be always enabled because we want the user to be able to stop the search in any moment
		getBtnSearch().setEnabled(true);
		
	}
	
	
	/**
	 * Confirm window closing.
	 */
	private void confirmWindowClosing(){
		int dialogResult = JOptionPane.showConfirmDialog(frame, messagesBundle.getMessage("ui.confirm.exit"),messagesBundle.getMessage("ui.confirm"),JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			frame.dispose();
		}
	}
	
	/**
	 * Gets the chck case sensitive.
	 *
	 * @return the chck case sensitive
	 */
	protected JCheckBox getChckCaseSensitive() {
		return chckCaseSensitive;
	}
	
	/**
	 * Gets the chck whole expression.
	 *
	 * @return the chck whole expression
	 */
	protected JCheckBox getChckWholeExpression() {
		return chckWholeExpression;
	}
	
	/**
	 * Gets the chck trim.
	 *
	 * @return the chck trim
	 */
	protected JCheckBox getChckTrim() {
		return chckTrim;
	}
	
	/**
	 * Gets the spinner max connections.
	 *
	 * @return the spinner max connections
	 */
	protected JSpinner getSpinnerMaxConnections() {
		return spinnerMaxConnections;
	}
	
	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	protected JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Gets the progress bar.
	 *
	 * @return the progress bar
	 */
	protected JProgressBar getProgressBar() {
		return progressBar;
	}
	
	
	/**
	 * Gets the btn search.
	 *
	 * @return the btn search
	 */
	public JButton getBtnSearch() {
		return btnSearch;
	}
	
	/**
	 * Gets the table results.
	 *
	 * @return the table results
	 */
	public JTable getTableResults() {
		return tableResults;
	}
	
	/**
	 * Gets the tabbed pane.
	 *
	 * @return the tabbed pane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}


