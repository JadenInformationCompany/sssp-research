package com.szhis.frsoft.common.persistence;

public class QueryParam {
	private String paramName;
	private int dataType;
	private String paramValue;

	
	public QueryParam(){
		
	}
	
	public QueryParam(String paramName, int dataType, String paramValue){
		this.paramName = paramName;
		this.dataType = dataType;
		this.paramValue = paramValue;
	}
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
