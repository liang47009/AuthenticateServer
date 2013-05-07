package com.curlymaple.server.module.user.message;

import javax.annotation.Resource;

import org.jboss.netty.buffer.ChannelBuffer;
import org.springframework.stereotype.Controller;

import com.curlymaple.server.core.IC2SCommand;
import com.curlymaple.server.core.IMemoryData;
import com.yunfeng.protocol.netty.BufferReader;

@Controller
public class Authenticate_C2S implements IC2SCommand {
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute(ChannelBuffer channelBuffer, IMemoryData memoryData) {
		String username = BufferReader.readString(channelBuffer);
		String password = BufferReader.readString(channelBuffer);
		userService.authenticate(memoryData, username, password);
	}
}
