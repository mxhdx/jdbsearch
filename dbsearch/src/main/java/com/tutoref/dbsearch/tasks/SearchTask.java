package com.tutoref.dbsearch.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Callable;

import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

import com.tutoref.dbsearch.database.ConnectionManager;
import com.tutoref.dbsearch.util.Util;

public class SearchTask implements Callable{
	
	private ConnectionManager connectionManager;
	private String expression;
	private boolean caseSensitive;
	private boolean wholeExpression;
	private boolean trim;
	private String tableName;
	private DefaultTableModel tableModel;
	private JProgressBar progressBar;
	
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

	public Object call() throws Exception {
		Thread.sleep(2000l);
		Connection connection = connectionManager.getComboPooledDataSource().getConnection();
		Statement st = connection.createStatement();
		String reqTable ="SELECT * FROM "+tableName;
		ResultSet rs = st.executeQuery(reqTable);
		while(rs.next()){
			int nbColumns=rs.getMetaData().getColumnCount();
			for(int i=1;i<=nbColumns;i++){
				if(Util.isBlobType(rs.getMetaData().getColumnType(i))) continue;
				String value=rs.getString(i);
				String displayValue=value.toString();
				if(value == null) continue;
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
		if(!rs.isClosed()){
			rs.close();
		}
		if(!st.isClosed()){
			st.close();
		}
		if(!connection.isClosed()){
			connection.close();
		}
		return null;
	}

}
