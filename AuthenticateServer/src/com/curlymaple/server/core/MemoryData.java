package com.curlymaple.server.core;

import org.jboss.netty.channel.Channel;

public class MemoryData implements IMemoryData {
	private Channel channel;

	public MemoryData(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
