package org.slingerxv.limitart.net.binary.message;


import org.slingerxv.limitart.net.binary.BinaryClient;
import org.slingerxv.limitart.net.binary.BinaryServer;

import io.netty.channel.Channel;

/**
 * 消息实体
 * 
 * @author Hank
 *
 */
public abstract class Message extends MessageMeta {
	// 消息由什么通道过来
	private transient Channel channel;
	// 当前Message接受的客户端
	private transient BinaryClient client;
	// 当前Message接收的服务器
	private transient BinaryServer server;
	// 预留参数
	private transient Object extra;
	private transient Object extra1;

	public abstract short getMessageId();

	public BinaryClient getClient() {
		return client;
	}

	public void setClient(BinaryClient client) {
		this.client = client;
	}

	public BinaryServer getServer() {
		return server;
	}

	public void setServer(BinaryServer server) {
		this.server = server;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Object getExtra() {
		return extra;
	}

	public void setExtra(Object extra) {
		this.extra = extra;
	}

	public Object getExtra1() {
		return extra1;
	}

	public void setExtra1(Object extra1) {
		this.extra1 = extra1;
	}
}