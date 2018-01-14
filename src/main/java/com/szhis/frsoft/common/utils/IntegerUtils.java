package com.szhis.frsoft.common.utils;

import java.util.ArrayList;
import java.util.List;



public class IntegerUtils {

	public final static Integer valueOf(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		return Integer.valueOf(s);
	}
	
	public final static boolean equals(Integer intA, Integer intB) {
		if (intA == null) {
			return intB == null ? true : false;
		}
		return intA.equals(intB);
	}
	
	public final static boolean equals(Integer intA, int intB) {				
		return (intA != null) && (intA.intValue() == intB) ? true : false;
	}
	
	//判断整数是否是正数
	public final static boolean isPositive(Integer a) {
		if(a == null) {
			return false;
		}
		return a.intValue() > 0;
	}
	
	public final static Boolean isNegative(Integer a) {
		if(a == null) {
			return false;
		}	
		return a.intValue() < 0;
	}	
	
	public final static Boolean isNullOrNegative(Integer a) {
		if(a == null) {
			return true;	
		}
		return a.intValue() < 0;
	}
	
	public final static Boolean isNullOrZero(Integer a) {
		if(a == null) {
			return true;
		}
		return a.intValue() == 0;
	}	
	
	public final static Integer negate(Integer aInt) {
		return aInt * -1;
	}
	
	/**
	 * 按《逗号》分割字符串
	 * @param source 
	 * @return List<Integer>
	 */
	public final static List<Integer> split(String source){
		String[] s = source.split(",");
		List<Integer> result = new ArrayList<Integer>(s.length);
		for(int i=0, len=s.length; i<len; i++){
			result.add(Integer.valueOf(s[i]));
		}
		return result;
	}
	
	/**
	 * 把整数对应的二进制每位权值组合成字符串。比如：二进制第零位权值为1，第一位为2，第二位为4，第四位为8.
	 * 则构造的结果为1,2,4,8
	 * @param value
	 * @param separator
	 * @param bitCount 需要构造的位数。
	 * @return
	 */
	public static String concatBinaryRightStr(Integer value, String separator, Integer bitCount) {
		if (value == null) {
			value = (1 << bitCount) - 1;
		}	
		StringBuilder sb = new StringBuilder();
		int base = 1;
		for (int i=0; i<bitCount; i++) {
			if ((base & value) == base) {
				if (sb.length() > 0) {
					sb.append(separator);
				}
				sb.append(base);
			}
			base <<= 1;
		}
		return sb.toString();		
	}
	
	/**
	 * 与concatBinaryRightStr相反的功能，把权值组合串解析成整型值
	 * @param binaryRightStr，形如："2,8"
	 * @param separator
	 * @return
	 */
	public static Integer parseBinaryRightStr(String binaryRightStr, String separator) {
		if (binaryRightStr == null || binaryRightStr == "") {
			return Integer.valueOf(0);
		}
		String[] strList = binaryRightStr.split(separator);
		int value = 0;
		for (int i=0, l=strList.length; i<l; i++) {
			value = value | Integer.valueOf(strList[i]);
		}
		return Integer.valueOf(value);
	}
	
	/**
	 * 位运算，判断 bitValue所在位是否打开
	 * @param source
	 * @param bitValue
	 * @return
	 */
	public static boolean isBitValueOn(Integer source, int bitValue){
		return source == null ? false : (source.intValue()&bitValue) == bitValue;
	}
	
	public static boolean isBitValueOn(Integer source, Integer bitValue){
		return isBitValueOn(source, bitValue==null?0:bitValue.intValue());
	}
	
	/**
	 * 位运算，打开bitValue所在位开关
	 * @param source
	 * @param bitValue
	 * @return
	 */
	public static int bitValueOn(Integer source, int bitValue){
		return bitValueOn(source==null?0:source.intValue(), bitValue);
	}
	
	public static int bitValueOn(int source, int bitValue){
		if((source&bitValue)==0){
			return source | bitValue;
		}else{
			return source; 
		}
	}
	
	/**
	 * 位运算，关闭bitValue所在位开关
	 * @param source
	 * @param bitValue
	 * @return
	 */
	public static int bitValueOff(Integer source, int bitValue){
		return bitValueOff(source==null?0:source.intValue(), bitValue);
	}
	
	public static int bitValueOff(int source, int bitValue){
		if((source&bitValue)==0){
			return source;
		}else{
			return source ^ bitValue; 
		}
	}
	
	/**
	 * 位运算，判断 bitIndex所在位是否打开(第bitIndex位是否为1）
	 * @param source 原值
	 * @param bitIndex 第几位(从0位开始）
	 * @return
	 */
	public static boolean isBitOn(Integer source, int bitIndex){
		if (source == null ) {
			return false;
		}
		Integer value = 1 << bitIndex;
		return (source & value) == value;
	}	
	
	/**
	 * 位运算，判断 bitIndex所在位是否关闭(第bitIndex位是否为0）
	 * @param source 原值
	 * @param bitIndex 第几位(从0位开始）
	 * @return
	 */	
	public static boolean isBitOff(Integer source, int bitIndex){
		if (source == null ) {
			return false;
		}
		Integer value = 1 << bitIndex;
		return (source & value) != value;
	}	
	
	
	/**
	 * 位运算，打开bitIndex所在位开关（把source第bitIndex位标记成1）
	 * @param source 原值
	 * @param bitIndex 第几位(从0位开始）
	 * @return
	 */
	public static int bitOn(Integer source, int bitIndex) {
		Integer value = 1 << bitIndex;
		if (source == null ) {
			return value;
		}	
		return source | value;
	}
	
	/**
	 * 位运算，关闭bitIndex所在位开关（把source第bitIndex位标记成0）
	 * @param source 原值
	 * @param bitIndex 第几位(从0位开始）
	 * @return
	 */
	public static int bitOff(Integer source, int bitIndex) {
		if (source == null ) {
			return 0;
		}	
		Integer value = ~(1 << bitIndex);
		return source & value;
	}	
}
