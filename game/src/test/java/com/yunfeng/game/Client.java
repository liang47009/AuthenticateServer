package com.yunfeng.game;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.yunfeng.game.socket.ByteCodec;

public class Client {

	public static void main(String[] args) throws Exception {
		// String host = "::1";
		String host = "172.17.28.42";
		int port = 4999;
		new Client().start(host, port);
		// for (int i = 0; i < 5; i++) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// try {
		// new Client().start(host, port);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();
		// }
	}

	public void start(String host, int port) throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.option(ChannelOption.TCP_NODELAY, false);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.group(workerGroup).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				// TODO Auto-generated method stub
				ChannelPipeline pipe = ch.pipeline();
				// pipe.addLast("frameDecoder", new
				// LengthFieldBasedFrameDecoder(
				// Integer.MAX_VALUE, 0, 4, 0, 4));
				// pipe.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipe.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
				pipe.addLast("bytecodec", new ByteCodec());
				pipe.addLast("handler", new ClientHandler());
			}
		});
		// b.group(bossGroup, workerGroup)
		// .channel(NioServerSocketChannel.class)
		// .childHandler(serverInitializer);
		// b.conn(host, port).sync().channel().closeFuture().sync();
		b.connect(host, port).sync().channel();
	}
}
