package com.szhis.frsoft.common.persistence;

import java.util.List;

public class QueryResult<T> {
	private List<T> resultList;
	private Long total;

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
