package com.szhis.frsoft.common.utils;

public class CompareSwap<T extends Comparable<T>> {
	public T min;
	public T max;
	
	public CompareSwap(T a, T b) {
		this.min = a;
		this.max = b;
		if(a.compareTo(b) > 0){
			T tmp = a;			
			this.min = b;
			this.max = tmp;
		}
	}
}
