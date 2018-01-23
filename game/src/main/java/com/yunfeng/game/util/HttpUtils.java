package com.yunfeng.game.util;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpUtils {

	// Access-Control-Allow-Headers:X-Requested-With
	// Access-Control-Allow-Methods:PUT,POST,GET,DELETE,OPTIONS
	// Access-Control-Allow-Origin:*
	// Connection:keep-alive
	// Content-Length:80
	// Content-Type:application/json; charset=utf-8
	// Date:Tue, 19 Sep 2017 11:02:48 GMT
	// ETag:W/"50-cCsntwuxnsfrQROajvpQMA"
	// X-Powered-By:3.2.1

	// Accept:*/*
	// Accept-Encoding:gzip, deflate
	// Accept-Language:zh-CN,zh;q=0.8,en;q=0.6
	// Connection:keep-alive
	// Host:172.19.34.237:9000
	// If-None-Match:W/"85-WLhQwhbrbZqmSTDKHME1SA"
	// Origin:http://172.19.34.237:7456
	// Referer:http://172.19.34.237:7456/
	// User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36
	// (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36
	public static void response(ChannelHandlerContext ctx, HttpRequest request,
			String res) {

		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(res.getBytes()));
		String ETag = request.headers().get("If-None-Match");

		if (ETag == null) {
			ETag = "";
		} else {
			response.headers().set("ETag", ETag);
		}

		response.headers().set(HttpHeaderNames.CONTENT_TYPE,
				"application/json; charset=utf-8");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
				response.content().readableBytes());
		// response.headers().set(HttpHeaderNames.CONNECTION,
		// HttpHeaderNames.KEEP_ALIVE);
		response.headers()
				.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS,
				"X-Requested-With");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS,
				"PUT,POST,GET,DELETE,OPTIONS");
		ctx.writeAndFlush(response);
	}

}
