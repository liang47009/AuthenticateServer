package com.curlymaple.server.module.user.message;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.springframework.stereotype.Controller;

import com.curlymaple.server.core.IS2CCommand;
import com.yunfeng.protocol.netty.BufferWriter;

@Controller
public class Authenticate_S2C implements IS2CCommand {
	private final short messageId = 101;
	private int result;

	public Authenticate_S2C() {
	}

	public Authenticate_S2C(int result) {
		this.result = result;
	}

	public void send(Channel channel) {
		ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer(channel
				.getConfig().getBufferFactory());
		int length = 0;
		length += BufferWriter.writeShort(channelBuffer, messageId);
		length += BufferWriter.writeInt(channelBuffer, result);
		channelBuffer = ChannelBuffers.copiedBuffer(channelBuffer.array(), 0,
				length);
		channel.write(channelBuffer);
	}

	public void send(Channel channel, int result) {
		ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer(channel
				.getConfig().getBufferFactory());
		int length = 0;
		length += BufferWriter.writeShort(channelBuffer, messageId);
		length += BufferWriter.writeInt(channelBuffer, result);
		channelBuffer = ChannelBuffers.copiedBuffer(channelBuffer.array(), 0,
				length);
		channel.write(channelBuffer);

	}
}
