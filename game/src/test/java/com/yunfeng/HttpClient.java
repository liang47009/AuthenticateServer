//package com.yunfeng;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.http.DefaultFullHttpRequest;
//import io.netty.handler.codec.http.HttpHeaderNames;
//import io.netty.handler.codec.http.HttpHeaderValues;
//import io.netty.handler.codec.http.HttpMethod;
//import io.netty.handler.codec.http.HttpRequest;
//import io.netty.handler.codec.http.HttpVersion;
//
//import com.yunfeng.tools.phoneproxy.socket.HttpSnoopClientInitializer;
//
//public class HttpClient {
//	public static void main(String[] args) {
//		// Configure the client.
//		EventLoopGroup group = new NioEventLoopGroup();
//		try {
//			Bootstrap b = new Bootstrap();
//			b.group(group).channel(NioSocketChannel.class)
//					.handler(new HttpSnoopClientInitializer());
//
//			// Make the connection attempt.
//			Channel ch = b.connect("172.19.34.82", 80).sync().channel();
//
//			// Prepare the HTTP request.
//			HttpRequest request = new DefaultFullHttpRequest(
//					HttpVersion.HTTP_1_1, HttpMethod.GET,
//					"/xiongmao1/android/beimei/ver_26_1013/nei/2.35/package/");
//			request.headers().set(HttpHeaderNames.HOST, "172.19.34.82");
//			request.headers().set(HttpHeaderNames.CONNECTION,
//					HttpHeaderValues.KEEP_ALIVE);
//			request.headers().set(HttpHeaderNames.ACCEPT_ENCODING,
//					HttpHeaderValues.GZIP);
//			request.headers().set(HttpHeaderNames.ACCEPT_LANGUAGE,
//					"zh-CN,zh;q=0.9,en;q=0.8");
//			request.headers()
//					.set(HttpHeaderNames.ACCEPT,
//							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//			request.headers()
//					.set(HttpHeaderNames.USER_AGENT,
//							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
//
//			// Set some example cookies.
//			// request.headers().set(
//			// HttpHeaderNames.COOKIE,
//			// ClientCookieEncoder.encode(new DefaultCookie(
//			// "my-cookie", "foo"), new DefaultCookie(
//			// "another-cookie", "bar")));
//
//			// Send the HTTP request.
//			ch.writeAndFlush(request);
//
//			// Wait for the server to close the connection.
//			ch.closeFuture().sync();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			// Shut down executor threads to exit.
//			group.shutdownGracefully();
//		}
//	}
//}
