package com.yunfeng.game.processor.httpcode;

import com.yunfeng.game.processor.IHttpProcessor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class IndexProcessor implements IHttpProcessor {

	@Override
	public void process(ChannelHandlerContext ctx, HttpRequest request,
			String param) {
		// TODO Auto-generated method stub
		String ret = "<!DOCTYPE html>\n"
				+ "\t\t<html>\n"
				+ "\t\t<head>\n"
				+ "\t\t<title>Welcome to nginx!</title>\n"
				+ "\t\t<style>\n"
				+ "\t\t    body {\n"
				+ "\t\t        width: 35em;\n"
				+ "\t\t        margin: 0 auto;\n"
				+ "\t\t        font-family: Tahoma, Verdana, Arial, sans-serif;\n"
				+ "\t\t    }\n"
				+ "\t\t</style>\n"
				+ "\t\t</head>\n"
				+ "\t\t<body>\n"
				+ "\t\t<h1>Welcome to nginx!</h1>\n"
				+ "\t\t<p>If you see this page, the nginx web server is successfully installed and\n"
				+ "\t\tworking. Further configuration is required.</p>\n"
				+ "\n"
				+ "\t\t<p>For online documentation and support please refer to\n"
				+ "\t\t<a href=\"http://nginx.org/\">nginx.org</a>.<br/>\n"
				+ "\t\tCommercial support is available at\n"
				+ "\t\t<a href=\"http://nginx.com/\">nginx.com</a>.</p>\n"
				+ "\n" + "\t\t<p><em>Thank you for using nginx.</em></p>\n"
				+ "\t\t</body>\n" + "\t\t</html>";
		// ctx.writeAndFlush(ret);
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.content().writeBytes(ret.getBytes());
		ctx.writeAndFlush(response);
		ctx.close();
	}
}
