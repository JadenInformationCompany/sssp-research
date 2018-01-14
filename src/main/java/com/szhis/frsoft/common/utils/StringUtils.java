package com.szhis.frsoft.common.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public final static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    
    public final static Object valueOf(final String cs, Class<?> clazz) {
    	if(cs == null) {
    		return null;
    	}
    	if(clazz == String.class) {
    		return cs;
    	} if(cs.length() == 0) {
			return null;
		} else if (clazz == Integer.class) {
    		return Integer.valueOf(cs);
    	} else if (clazz == BigDecimal.class) {
    		return new BigDecimal(cs);
    	} else if (clazz == Date.class) {
    		return DateUtils.parse(cs);
    	}
    	return null;
    }
	
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
    	if(cs1 == null) {
    		if(cs2 != null)
    			return false;
    	} else if(!cs1.equals(cs2))
    		return false;
    	return true;
    }   
    
    /**JAVA自带Character.isDigit方法判断是否是数字，Unicode中收录有多个字符范围数字，
     * 包含全角和半角阿拉伯数字、阿拉伯印度数字, 以及许多其他的字符范围包含数字.
     * 下面列出Uncode中包含数字的各区域段
     *   '\u0030' 到 '\u0039', ISO-LATIN-1 digits ('0' through '9')
     *   '\u0660' 到 '\u0669', Arabic-Indic digits
     *   '\u06F0' 到 '\u06F9', Extended Arabic-Indic digits
     *   '\u0966' 到 '\u096F', Devanagari digits
     *   '\uFF10' 到 '\uFF19', Fullwidth digits（中文全角数字） 
     */   
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }  
    
    /**注意这里只判断Latin的0和9, 没有使用JAVA自带Character.isDigit方法判断是否是数字，
     */  
    public static boolean isNumericLatin(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        int aChar;
        for (int i = 0; i < sz; i++) {
            aChar = cs.charAt(i);  
            if(aChar < 0x30 || aChar > 0x39) {//0和9的十六进制
            	return false;
            }
        }
        return true;
    }
    
    
    public static boolean isAllEnglishChar(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        int aChar;
        for (int i = 0; i < sz; i++) {
            aChar = cs.charAt(i);  
            //A~Z(0x41~0x5A) a~z(0x61~0x7A)
            if((aChar < 0x41) || ((aChar > 0x5A && aChar < 0x61)) || (aChar > 0x7A)) {
            	return false;
            }
        }
        return true;   	
    }
	
	/** 根据Unicode编码判断是否是中文汉字、中文符号
	 * 参见：http://www.micmiu.com/lang/java/java-check-chinese/
	 */
	public static boolean isCNChar(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 字符串中是否包含中文汉字、中文符号
	public static boolean isIncludeCNChar(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }		
		for (int i = 0; i < cs.length(); i++) {
			if (isCNChar(cs.charAt(i))) {
				return true;
			}
		}
		return false;
	}


	// 使用正则表达式判断，只能判断部分CJK字符（CJK统一汉字）
	public static boolean isIncludeCNCharByREG(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }		
		Pattern pattern = Pattern.compile("([\\u4E00-\\u9FBF]|[\\uF900-\\uFA2D])+");
		return pattern.matcher(cs).find();
	}
	
	public static boolean isIDNumber(String number) {
		Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
		Matcher m = p.matcher(number);
		return m.matches();		
	}
	
	public static boolean isMobilePhoneNumber(String number){
		//Pattern p = Pattern.compile("^((13[0-9])|(15([^4,\\D]))|(18[0-9])|(17[6-8]))\\d{8}$");
		// 目前存在170开头的手机号码
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15([^4,\\D]))|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}
	
	public static boolean isPhoneNumber(String number) {
		if(isMobilePhoneNumber(number))
			return true;		
		String reg = "(\\(?(010|021|022|023|024|025|026|027|028|029|852|)\\)?-?\\d{8}(\\-?[0-9]{1,4})?)|(\\(?(0[3-9][0-9]{2})\\)?-?\\d{7,8}(\\-?[0-9]{1,4})?)";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(number);
		return m.matches();
		/*2) 固定电话: 
		--------------- 区号-号码 -------------------
		String regex1 = "\\(?(010|021|022|023|024|025|026|027|028|029|852)?\\)?-?\\d{8}";//3位区号,8位号码
		String regex2 = "\\(?(0[3-9][0-9]{2})?\\)?-?\\d{7,8}";//4位区号
		String regex3 = "(\\(?(010|021|022|023|024|025|026|027|028|029|852)?\\)?-?\\d{8})|(\\(?(0[3-9][0-9]{2})?\\)?-?\\d{7,8})";
		--------------- 加上分机号 (\\-?[0-9]{1,4})? ------- 区号-号码-分机号 ---------------
		String regex1 = "\\(?(010|021|022|023|024|025|026|027|028|029|852|)\\)?-?\\d{8}(\\-?[0-9]{1,4})?";//3位区号
		String regex2 = "\\(?(0[3-9][0-9]{2})\\)?-?\\d{7,8}(\\-?[0-9]{1,4})?";//4位区号
		String regex3 = "(\\(?(010|021|022|023|024|025|026|027|028|029|852|)\\)?-?\\d{8}(\\-?[0-9]{1,4})?)|(\\(?(0[3-9][0-9]{2})\\)?-?\\d{7,8}(\\-?[0-9]{1,4})?)";*/		
	}
	
	 
    /***************************************************************************
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     * 
     * @param src
     *            - 源字符串 例如: 空格(" "), 星号("*"), "浙江" 等等...
     * @param num
     *            - 重复生成次数
     * @return 返回已生成的重复字符串    
     **************************************************************************/
    public static String repeat(String src, int num) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }

    /**
     * 字符串加单引号
     * @param src
     * @return
     */
    public static String quoted(String src){
    	char c;
    	StringBuilder n = new StringBuilder();
    	n.append("'");
    	for (int i = 0; i < src.length(); i++) {
    		c = src.charAt(i);
    		if(c==39){
    			n.append("''");
    		}else{
    			n.append(c);
    		}
    	}
    	n.append("'");
    	return new String(n);
    }
    
	//返回隐藏后的电话号码 type:隐藏类型   0=中间四位  1=后四位   2=全部隐藏   3=不隐藏
	public static String hidePhoneNumber(String number, int type) {
		if(isEmpty(number)) 
			return null;
		int len = number.length();
		
		if(type==3 || len<5)
			return number;	
			
		//不足4位时，全部隐藏
		if(type==2)
			return repeat("*", len);
		
		if(type==1)
			return number.substring(0,number.length()-4)+"****";
		
		if(len<8){
			return number.substring(0,3)+"****";
		}else{
			return number.substring(0,3)+"****"+number.substring(7); 
		}
		
//		if(type==3){
//			return number;
//		}else if(type==1 || number.length()<5){
//			return repeat("*", number.length());
//		}else if(number.length()<8){	
//			return number.substring(0,3)+"****";
//		}else if(type==2){
//			//隐藏后四位
//			return number.substring(0,number.length()-4)+"****";
//		}else{
//			//隐藏中间四位
//			return number.substring(0,3)+"****"+number.substring(7); 
//		}
	}
	
	public static String[] split(String str, String... separator) {
		if (StringUtils.isEmpty(str)) {
			return new String[0];
		} else if (separator.length > 0) {
			return str.split(separator[0]);
		} else if (str.contains(",")) {
			return str.split(",");
		} else {
			return str.split(";");
		}
	}
	
	// java字符编码为UTF-16,中文字符可能需要超过2个字节以上存储
	public static String string2Unicode(String str) {
		StringBuilder retStr = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			int cp = Character.codePointAt(str, i);
			int charCount = Character.charCount(cp);
			if (charCount > 1) {
				i += charCount - 1; // 2.
				if (i >= str.length()) {
					throw new IllegalArgumentException("truncated unexpectedly");
				}
			}

			if (cp < 128) { // unicode转义，ascii码不需要加\\u
				retStr.appendCodePoint(cp);
			} else {
				retStr.append(String.format("\\u%x", cp));
			}
		}
		return retStr.toString();
	}
	
	/*j = i + 2;
	codePoint = 0*/
	/**
	 * java字符编码为UTF-16,中文字符可能需要超过2个字节以上存储。目前只支持UCS-2，不 
	 * 参考http://www.oracle.com/us/technologies/java/supplementary-142654.html
	 * @param s
	 * @return
	 */
	public static String unicode2String(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '\\' && chars[i + 1] == 'u') {
				char cc = 0;
				for (int j = 0; j < 4; j++) {
					char ch = Character.toLowerCase(chars[i + 2 + j]);
					if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
						cc |= (Character.digit(ch, 16) << (3 - j) * 4);
					} else {
						cc = 0;
						break;
					}
				}
				if (cc > 0) {
					i += 5;
					sb.append(cc);
					continue;
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * 字符串反转,如：abc调用reverse后变成cba
	 * @author xiaozheng
	 * @param s
	 * @return
	 */
	public static String reverse(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
	}
	
	/**
	 * 用于截断字符串中前后指定字符，如trim("%abc%", "%") = "abc"
	 * @author xiaozheng
	 * @param s
	 * @param trimStr
	 * @return
	 */
	public static String trim(String s, String trimStr) {
		int toffset = 0;
		int trimStrlen = trimStr.length();
		while (s.startsWith(trimStr, toffset)) {
			toffset = toffset + trimStrlen;
		}
		
		s = s.substring(toffset);
		while (s.endsWith(trimStr)) {
			s = s.substring(0, s.length() - trimStrlen);
		}
		return s;
	}
	
	
	/*public static int indexOf(String s, String searchStr, int number) {
		
	}*/
}
