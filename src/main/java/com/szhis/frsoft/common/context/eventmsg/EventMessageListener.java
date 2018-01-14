package com.szhis.frsoft.common.context.eventmsg;

/**
 * 注册事件监听回调函数
 * @author xiaozheng
 * @version createDate：2017年7月13日 下午3:00:55
 */

public interface EventMessageListener {
	
	/**
	 * 事件监听回调函数
	 * @author xiaozheng
	 * @param message 接收到的消息
	 */
	void onReceiveMessage(EventMessage message);
	
}
