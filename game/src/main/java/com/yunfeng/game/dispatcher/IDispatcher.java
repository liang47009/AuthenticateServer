package com.yunfeng.game.dispatcher;

import io.netty.channel.ChannelHandlerContext;

public interface IDispatcher {
	boolean dipatch(ChannelHandlerContext ctx, Object msg);
}
