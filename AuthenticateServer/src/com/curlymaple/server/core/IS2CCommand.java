package com.curlymaple.server.core;

import org.jboss.netty.channel.Channel;

public interface IS2CCommand {
	void send(Channel channel);
}
