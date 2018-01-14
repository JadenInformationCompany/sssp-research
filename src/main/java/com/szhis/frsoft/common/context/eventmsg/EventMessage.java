package com.szhis.frsoft.common.context.eventmsg;

import org.springframework.context.ApplicationEvent;

/**
 * 事件消息：用于定义程序内部消息格式
 * @author xiaozheng
 * @version createDate：2017年7月13日 上午11:44:24
 */

public class EventMessage extends ApplicationEvent {
	private int code; // 消息编码
	private Object content; // 消息内容

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5048626515224492523L;

	public EventMessage(int msgCode, Object content) {
		super(content);
		this.code = msgCode;
		this.content = content;
		// TODO Auto-generated constructor stub
	}

}
