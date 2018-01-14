package com.szhis.frsoft.common.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 数据集基类定义，仅包含字段定义
 * @author redhan by 2015年8月20日
 *
 */
public class DataSetDefine {	
	private int columnCount = 0;	//列数
	private int rowCount = 0;		//行数
	private int flag = 0;			//标志 (缓存： 0=全部更新  1=增量更新 2=更新失败name存储错误信息)
	private String name;			//表名
	private Date lastDate;			//最后访问日期
	
	private List<ColumnDefine> columns;	
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public List<ColumnDefine> getColumns() {
		return columns;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setColumns(List<ColumnDefine> columns) {
		this.columns = columns;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * 返回列名在列表中的下标
	 * @param name
	 * @return
	 */
	@Transient
	@JsonIgnore
	public int indexOfColumn(String name){
		int result = -1;
		if(columns!=null){
			int index = 0;
			for(ColumnDefine temp:columns){
				if(temp.getName().compareTo(name)==0){
					result = index;
					break;
				}
				index++;
			}	
		}
		return result;
	}
	
	/**
	 * 判断列名是否存在
	 * @param name
	 * @return
	 */
	@Transient
	@JsonIgnore
	public Boolean existsColumn(String name){
		return indexOfColumn(name)>=0;
	}
}
