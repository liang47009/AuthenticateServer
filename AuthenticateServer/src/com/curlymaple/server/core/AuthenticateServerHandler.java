package com.curlymaple.server.core;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateServerHandler extends SimpleChannelHandler {

	private Logger logger = Logger.getLogger("std");

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.error("", e.getCause());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		logger.info("messageReceived");
		MemoryData memoryData = (MemoryData) ctx.getAttachment();
		ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
		short messageId = channelBuffer.readShort();
		IC2SCommand command = CommandDispatcher.parse(messageId);
		if (null != command) {
			command.execute(channelBuffer, memoryData);
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		ctx.setAttachment(new MemoryData(ctx.getChannel()));
		logger.info("connected:" + e.getChannel().getRemoteAddress());
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		logger.info("disConnected:" + e.getChannel().getRemoteAddress());
	}

}