package com.szhis.frsoft.common.utils.chinesechar;

/*生成汉字对应输入法的键位码（字母组合）*/
public interface IGenerateIMECode {
	
	//为每个汉字生成全码拼接字符串
	String generateFullCode(String text);
	
	//为每个汉字生成全码首字母拼接字符串
	String generateShortCode(String text);
}
