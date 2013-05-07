package com.curlymaple.server.core;

import org.jboss.netty.buffer.ChannelBuffer;

public interface IC2SCommand {
	void execute(ChannelBuffer channelBuffer, IMemoryData memoryData);
}
