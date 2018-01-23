package com.yunfeng.game.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.yunfeng.game.dispatcher.DispatcherManager;

public class Server {

	public void startUp(final String host, final int port) {
		System.setProperty("io.netty.noPreferDirect", "true");
		System.setProperty("io.netty.noUnsafe", "true");

		// AppUtils.init();
		DispatcherManager.init();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Server().run(host, port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void run(String host, int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerInitializer serverInitializer = new ServerInitializer();
		// SelfSignedCertificate ssc = new SelfSignedCertificate();
		// SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(),
		// ssc.privateKey())
		// .build();
		// final SslContext sslCtx = SslContextBuilder.forClient()
		// .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		// HttpInitializer serverInitializer = new HttpInitializer(sslCtx);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(serverInitializer);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.bind(host, port).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
