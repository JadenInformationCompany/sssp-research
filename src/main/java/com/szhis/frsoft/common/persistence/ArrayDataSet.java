package com.szhis.frsoft.common.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组数据集，返回的列数据，不包含字段名
 * @author redhan by 2015年8月20日
 *
 */
public class ArrayDataSet extends DataSetDefine {
	private List<Object> rows = new ArrayList<Object>();

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
		this.setRowCount(rows==null?0:rows.size());
	}
}
