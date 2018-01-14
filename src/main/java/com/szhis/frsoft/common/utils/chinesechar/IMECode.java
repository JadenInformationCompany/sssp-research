package com.szhis.frsoft.common.utils.chinesechar;

public class IMECode {
	private int flag;		//1=拼音 2=五笔	 4=完整编码(否则返回首字母)
	private String text;		//源文本 
	private String code;		//编码
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}	
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
