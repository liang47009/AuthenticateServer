package com.yunfeng.game.processor.httpcode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import com.yunfeng.game.processor.IHttpProcessor;
import com.yunfeng.game.util.HttpUtils;

public class GuestProcessor implements IHttpProcessor {

	@Override
	public void process(ChannelHandlerContext ctx, HttpRequest request,
			String param) {
		// TODO Auto-generated method stub
		String res = "{\"errcode\":0,\"errmsg\":\"ok\",\"account\":\"guest_1505819236975\",\"halladdr\":\"localhost:9000\",\"sign\":\"5ec679b02bf294f3895f58b044928171\"}";
		HttpUtils.response(ctx, request, res);
	}

}
