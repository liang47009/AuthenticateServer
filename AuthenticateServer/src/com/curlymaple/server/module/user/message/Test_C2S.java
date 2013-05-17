package com.curlymaple.server.module.user.message;

import org.jboss.netty.buffer.ChannelBuffer;
import org.springframework.stereotype.Component;

import com.curlymaple.server.core.IC2SCommand;
import com.curlymaple.server.core.IMemoryData;

@Component
public class Test_C2S implements IC2SCommand {

	@Override
	public void execute(ChannelBuffer channelBuffer, IMemoryData memoryData) {
		// TODO Auto-generated method stub
		
	}

}
