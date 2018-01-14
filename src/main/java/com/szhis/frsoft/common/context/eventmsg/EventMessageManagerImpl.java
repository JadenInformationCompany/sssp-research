package com.szhis.frsoft.common.context.eventmsg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.szhis.frsoft.common.exception.Exceptions;

/**
 * 管理事件消息的监听和发送
 * 
 * @author xiaozheng
 * @version createDate：2017年7月13日 上午11:48:16
 */

@Component
public class EventMessageManagerImpl implements ApplicationListener<EventMessage>, EventMessageManager {

	private static Logger logger = LoggerFactory.getLogger(EventMessageManagerImpl.class);
	
	/**
	 * 数组下标代表消息id,-55～0保留为系统级消息，1～200为应用程序消息.数组每个元素是一个监听对象列表。
	 * 根据消息id定位消息监听列表。
	 */
	private static EventMessageListenerList[] msgListener = new EventMessageListenerList[256];
	
	private static final int MESSAGE_LOW_CODE = -55; // 系统消息最小id值
	private static final int MESSAGE_HIGHT_CODE = msgListener.length + MESSAGE_LOW_CODE - 1;
	private boolean eventMessage = true; 
			
	@Autowired private ApplicationContext applicationContext;
	
	/**
	 * 设置是否启用消息，暂不启用
	 * @param eventMessage
	 */
//	@Value("${eventMessage.enabled}") 
//	public void setEventMessage(String eventMessage) {		
//		this.eventMessage = String.valueOf(true).equals(eventMessage);
//	}	
	
	/**
	 * 返回是否启用消息
	 */
	@Override
	public boolean getEnabled(){
		return this.eventMessage;
	};
	
	@Async
	@Override
	public void onApplicationEvent(EventMessage event) {
		if (this.eventMessage && event instanceof EventMessage) {
			logger.debug("receive message(code:" + event.getCode() + ", content:" + event.getContent() + ")");
			int index = event.getCode() - MESSAGE_LOW_CODE;
			EventMessageListenerList listenerList = msgListener[index]; 
			if (listenerList == null) {
				return;
			}
			Iterator<EventMessageListener> iterListener = listenerList.getElements().iterator();
			EventMessageListener listener;
			while (iterListener.hasNext()) {
				listener = iterListener.next();
				try {
					listener.onReceiveMessage(event);
				} catch (Exception ex) {
					logger.error(Exceptions.getStackTraceAsString(ex));
				}
			}
		}
	}

	/**
	 * 发送消息
	 */
	@Override
	public void send(EventMessage message) {
		if (!this.eventMessage){
			return;
		}
		logger.debug("send message(code:" + message.getCode() + ", content:" + message.getContent() + ")");
		applicationContext.publishEvent(message);		
	}
	
	public void send(int msgCode, Object content){
		send(new EventMessage(msgCode, content == null ? Integer.valueOf(0) : content));
	}
	
	@Override
	public void registerListener(int msgCode, EventMessageListener listener) {
		if (msgCode < MESSAGE_LOW_CODE) {
			throw new RuntimeException("msgCode can't less than " + MESSAGE_LOW_CODE);
		} else if (msgCode > MESSAGE_HIGHT_CODE) {
			throw new RuntimeException("msgCode can't greater than " + MESSAGE_HIGHT_CODE);
		}
		
		int index = msgCode - MESSAGE_LOW_CODE;
		EventMessageListenerList listenerList = msgListener[index]; 
		if (listenerList == null) {
			listenerList = new EventMessageListenerList();
			msgListener[index] = listenerList;
		}
		listenerList.getElements().add(listener);
	}

	
	private class EventMessageListenerList {
		private Collection<EventMessageListener> elements = new ArrayList<EventMessageListener>(4);

		public Collection<EventMessageListener> getElements() {
			return elements;
		}
	}
}
