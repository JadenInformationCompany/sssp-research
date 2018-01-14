package com.szhis.frsoft.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSet extends DataSetDefine {
	private List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
/*	private int columnCount = 0;
	private List<ColumnDefine> columns = new ArrayList<ColumnDefine>();*/


	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
		this.setRowCount(rows==null?0:rows.size());
	}
/*
	public int getColumnCount() {
		return columnCount;
	}
	
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public List<ColumnDefine> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefine> columns) {
		this.columns = columns;
	}*/

	public String toString() {
		StringBuilder sbOut = new StringBuilder("");
		sbOut.append(" the RecordMetaData String is:/n");
		List<ColumnDefine> columns = this.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			sbOut.append("    colIndex=" + (i + 1) + ",colName=" + columns.get(i).getName()
					+ "/n");
		}
		return sbOut.toString();
	}
}
