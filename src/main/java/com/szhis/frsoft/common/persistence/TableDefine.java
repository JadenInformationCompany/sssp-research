package com.szhis.frsoft.common.persistence;

import java.util.ArrayList;
import java.util.List;

public class TableDefine {
	private String tableName;
	private List<String> primaryKeys;
	private List<ColumnDefine> columns;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public List<ColumnDefine> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefine> columns) {
		this.columns = columns;
	}
	
	public void checkSinglePrimaryKey() {
		int count = primaryKeys.size();		
		if(count == 0)
			throw new RuntimeException(String.format("table %s not define primary key", tableName));
		else if(count > 1) 
			throw new RuntimeException(String.format("table %s using a composite primary key, should using a single", 
					tableName));
	}
	
	public List<ColumnDefine> getPrimaryKeyColDef() {
		int count = primaryKeys.size();
		List<ColumnDefine> result = new ArrayList<ColumnDefine>(count);
		if(count == 0)
			return result;
		for(String pkName: primaryKeys) {
			for(ColumnDefine colDef: columns) {
				if(pkName.equals(colDef.getName())) {
					result.add(colDef);
					break;
				}
			}			
		}
		return result;
	}
	
	public ColumnDefine getColumnDefine(String columnName) {
		for (ColumnDefine colDef: columns) {
			if (colDef.getName().equalsIgnoreCase(columnName)) {
				return colDef;
			}
		}
		return null;
	}
	
	/**
	 * 参考:Spring TableMetaDataContext createInsertString方法
	 * namedParameter:是否使用字段名作为命名参数
	 * @return
	 */
	public String createInsertString(boolean namedParameter) {
		StringBuilder insertStatement = new StringBuilder();
		insertStatement.append("INSERT INTO ");
		/*if (this.getSchemaName() != null) {
			insertStatement.append(this.getSchemaName());
			insertStatement.append(".");
		}*/
		insertStatement.append(this.tableName);
		insertStatement.append(" (");
		int i = 0;
		for (ColumnDefine colDef: this.columns) {			
			if (i > 0) {
				insertStatement.append(", ");
			}
			insertStatement.append(colDef.getName());
			i++;
		}
		
		insertStatement.append(") VALUES(");
		
		i = 0;
		for (ColumnDefine colDef: this.columns) {	
			if (i > 0) {
				insertStatement.append(", ");
			}
			insertStatement.append(namedParameter ? ":" + colDef.getName(): "?");
			i++;
		}
		insertStatement.append(")");
		return insertStatement.toString();		
	}
	
	public String createUpdateString(boolean namedParameter) {
		StringBuilder updateStatement = new StringBuilder();
		updateStatement.append("update ");
		/*if (this.getSchemaName() != null) {
			insertStatement.append(this.getSchemaName());
			insertStatement.append(".");
		}*/
		
		updateStatement.append(this.tableName + " set ");	
		
		int i = 0;
		for (ColumnDefine colDef: this.columns) {	
			if (i > 0) {
				updateStatement.append(", ");
			}
			if(namedParameter) {
				updateStatement.append(colDef.getName() + " = :" + colDef.getName());
			} else {
				updateStatement.append(colDef.getName() + " = ?");	
			}
			i++;
		}		
		
		updateStatement.append(" where ");
		i = 0;
		for(String colName: this.getPrimaryKeys()) {
			if (i > 0) {
				updateStatement.append(" and ");
			}
			if(namedParameter) {
				updateStatement.append(colName + " = :" + colName);
			} else {
				updateStatement.append(colName + " = ?");	
			}			
		}
		
		return updateStatement.toString();
	}
}
