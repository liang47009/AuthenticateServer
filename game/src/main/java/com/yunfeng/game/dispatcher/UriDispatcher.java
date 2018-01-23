package com.yunfeng.game.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;

import java.util.HashMap;
import java.util.Map;

import com.yunfeng.game.processor.IHttpProcessor;
import com.yunfeng.game.processor.httpcode.FaviconProcessor;
import com.yunfeng.game.processor.httpcode.GameVersionProcessor;
import com.yunfeng.game.processor.httpcode.GuestProcessor;
import com.yunfeng.game.processor.httpcode.IndexProcessor;
import com.yunfeng.game.processor.httpcode.UserLoginProcessor;
import com.yunfeng.game.util.Log;

public class UriDispatcher implements IDispatcher {

	private Map<String, IHttpProcessor> processors = new HashMap<String, IHttpProcessor>();

	public UriDispatcher() {
		register("/", new IndexProcessor());
		register("/favicon.ico", new FaviconProcessor());
		register("/get_serverinfo", new GameVersionProcessor());
		register("/guest", new GuestProcessor());
		register("/login", new UserLoginProcessor());
	}

	public void register(String uri, IHttpProcessor processor) {
		processors.put(uri, processor);
	}

	public boolean dipatch(ChannelHandlerContext ctx, Object msg) {
		// TODO Auto-generated method stub
		boolean processable = false;
		if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			String[] datas = request.uri().split("\\?");
			String uri = datas[0];
			String paramStr = "";
			if (datas.length == 2) {
				paramStr = datas[1];
			}
			IHttpProcessor processor = processors.get(uri);
			if (processor == null) {
				String log = "no processor found for: " + request.uri();
				Log.d(log);
			} else {
				processor.process(ctx, request, paramStr);
			}
			processable = true;
		} else if (msg instanceof LastHttpContent) {
			LastHttpContent request = (LastHttpContent) msg;
			if (request.content().capacity() == 0) {
				DefaultFullHttpResponse response = new DefaultFullHttpResponse(
						HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
				ctx.writeAndFlush(response);
				ctx.close();
				processable = true;
			}
		}
		return processable;
	}

}
