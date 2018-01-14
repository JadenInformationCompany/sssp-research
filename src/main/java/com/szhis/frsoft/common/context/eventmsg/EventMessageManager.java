package com.szhis.frsoft.common.context.eventmsg;

/**
 * 事件消息的管理（消息的发送以及注册消息监听回调方法）
 * @author xiaozheng
 * @version createDate：2017年7月13日 下午2:46:45
 */

public interface EventMessageManager {

	/**
	 * 返回是否启用消息
	 * @return
	 */
	boolean getEnabled();
	
	/**
	 * 发送消息
	 * @author xiaozheng
	 * @param message
	 */
	void send(EventMessage message);
	
	/**
	 * 发送消息
	 * @author chenxiaohan
	 * @param msgCode	消息代码
	 * @param content	消息内容
	 */
	void send(int msgCode, Object content);
		
	void registerListener(int msgCode, EventMessageListener listener);
}
