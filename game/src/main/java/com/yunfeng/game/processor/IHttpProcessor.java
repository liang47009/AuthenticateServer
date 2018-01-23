package com.yunfeng.game.processor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface IHttpProcessor extends IProcessor {

	void process(ChannelHandlerContext ctx, HttpRequest request, String param);
}
