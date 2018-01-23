package com.yunfeng.game.socket;

import io.netty.channel.Channel;

public class MemoryData {
	private String loginTime;
	private Channel channel;

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
