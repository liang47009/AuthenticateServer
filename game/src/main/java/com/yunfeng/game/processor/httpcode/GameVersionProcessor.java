package com.yunfeng.game.processor.httpcode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import com.yunfeng.game.processor.IHttpProcessor;
import com.yunfeng.game.util.HttpUtils;

public class GameVersionProcessor implements IHttpProcessor {

	@Override
	public void process(ChannelHandlerContext ctx, HttpRequest request,
			String param) {
		// TODO Auto-generated method stub
		String res = "{\"version\":\"20161227\",\"hall\":\"localhost:9000\",\"appweb\":\"http://fir.im/2f17\"}";
		HttpUtils.response(ctx, request, res);
	}

}
