package com.szhis.frsoft.common.context.eventmsg;


/**
 * 定义事件消息编码，-55～0保留为系统级消息，1～200为应用程序消息
 * @author xiaozheng
 * @version createDate：2017年7月13日 上午11:57:18
 */

public class EventMessageCode {
	
	// -55～0保留为系统级消息，
	
	public final static int SYS_STARTUP_COMPLETE = 0; // 系统（服务）启动完毕
	
	public final static int SYS_SHUTDOWN = -1; // 系统（服务）将要关闭
	
	public final static int SYS_ENTITY_UPDATE = -11; // 实体更新时触发，用于客户端缓存同步	
	
	public final static int SYS_ENTITY_DELETE = -12; // 实体删除时触发，用于客户端缓存同步
	
	// 多用户,指该实体，保存了多个用户的数据，如：CYXM(常用项目) 
	public final static int SYS_ENTITY_UPDATE_MU = -13; // 实体更新时触发(多用户)，用于客户端缓存同步	
	
	public final static int SYS_ENTITY_DELETE_MU = -14; // 实体删除时触发(多用户)，用于客户端缓存同步
	
	public final static int SYS_SERVER_CACHE_CLEAR = -15; // 清空服务端缓存，传递消息内容：Collection<String> cacheNames本次清除的缓存名称
			
	// 1～200为应用程序消息
	
	/**
	 * 当删除门诊收费单时触发
	 */
	public final static int EMS_MZSFD_DELETE = 20;
	
}
