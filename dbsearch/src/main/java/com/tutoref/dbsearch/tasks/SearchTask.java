package com.tutoref.dbsearch.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Callable;

import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

import com.tutoref.dbsearch.database.ConnectionManager;
import com.tutoref.dbsearch.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchTask.
 */
public class SearchTask implements Callable{
	
	/** The connection manager. */
	private ConnectionManager connectionManager;
	
	/** The expression. */
	private String expression;
	
	/** The case sensitive. */
	private boolean caseSensitive;
	
	/** The whole expression. */
	private boolean wholeExpression;
	
	/** The trim. */
	private boolean trim;
	
	/** The table name. */
	private String tableName;
	
	/** The table model. */
	private DefaultTableModel tableModel;
	
	/** The progress bar. */
	private JProgressBar progressBar;
	
	/**
	 * Instantiates a new search task.
	 *
	 * @param connectionManager the connection manager
	 * @param expression the expression
	 * @param caseSensitive the case sensitive
	 * @param wholeExpression the whole expression
	 * @param trim the trim
	 * @param tableName the table name
	 * @param tableModel the table model
	 * @param progressBar the progress bar
	 */
	public SearchTask(ConnectionManager connectionManager, String expression, boolean caseSensitive, boolean wholeExpression, boolean trim, String tableName, DefaultTableModel tableModel, JProgressBar progressBar){
		this.connectionManager=connectionManager;
		this.expression=expression;
		this.caseSensitive=caseSensitive;
		this.wholeExpression=wholeExpression;
		this.trim=trim;
		this.tableName=tableName;
		this.tableModel=tableModel;
		this.progressBar = progressBar;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	public Object call() throws Exception {
		Thread.sleep(1000L);
		Connection connection = connectionManager.getComboPooledDataSource().getConnection();
		Statement st = connection.createStatement();
		String reqTable ="SELECT * FROM "+tableName;
		ResultSet rs = st.executeQuery(reqTable);
		while(rs.next()){
			int nbColumns=rs.getMetaData().getColumnCount();
			for(int i=1;i<=nbColumns;i++){
				if(Util.isBlobType(rs.getMetaData().getColumnType(i))) continue;
				String value=rs.getString(i);
				if(value == null) continue;
				if(trim) value.trim();
				String displayValue=value.toString();
				if(!caseSensitive){
					expression = expression.toUpperCase();
					value = value.toUpperCase();
				}
				if(wholeExpression){
					if(value.equals(expression)){
						tableModel.addRow(new Object[] { tableName, rs.getMetaData().getColumnName(i),displayValue});
						tableModel.fireTableDataChanged();
					}
				}else{
					if(value.contains(expression)){
						tableModel.addRow(new Object[] { tableName, rs.getMetaData().getColumnName(i),displayValue});
						tableModel.fireTableDataChanged();
					}
				}
			}
			progressBar.setValue(progressBar.getValue()+1);
		}
		
		
		if(rs!=null){
			rs.close();
		}
		
		if(st!=null){
			st.close();
		}
		
		if(connection!=null){
			connection.close();
		}
		
		return null;
	}

}
