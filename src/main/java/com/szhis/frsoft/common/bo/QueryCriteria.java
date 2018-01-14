package com.szhis.frsoft.common.bo;

import java.util.ArrayList;
import java.util.List;

public class QueryCriteria {	
	private String los;//逻辑运算符(logic operation symbol los) and or
	private List<String> qsList = new ArrayList<String>();//查询语句query statement qs 
	private List<QueryCriteria> criterias = new ArrayList<QueryCriteria>();//复合查询条件
	
	public String getLos() {
		return los;
	}
	
	public void setLos(String los) {
		this.los = los;
	}

	public List<String> getQsList() {
		return qsList;
	}
	
	public void setQsList(List<String> qsList) {
		this.qsList = qsList;
	}
	
	public List<QueryCriteria> getCriterias() {
		return criterias;
	}
	
	public void setCriterias(List<QueryCriteria> criterias) {
		this.criterias = criterias;
	}
	
	public String toQL() {
		QueryCriteria criteria;
		StringBuilder QL = new StringBuilder();
		int i;
		for(i = 0; i < this.qsList.size(); i++) {
			if (QL.length() > 0) QL.append(" " + this.los + " ");
			QL.append(this.qsList.get(i));
		}

		for(i = 0; i < this.criterias.size(); i++) {
			if (QL.length() > 0) QL.append(" " + this.los + " ");
			criteria = this.criterias.get(i);
			QL.append(criteria.toQL());
		}
		return "(" + QL.toString() + ")";		
	}
}
