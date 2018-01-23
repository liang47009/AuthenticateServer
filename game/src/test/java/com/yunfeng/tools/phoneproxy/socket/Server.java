package com.yunfeng.tools.phoneproxy.socket;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) {
		new Server().startup("172.19.34.237", 8888);
	}

	public void startup(final String host, final int port) {
		System.setProperty("io.netty.noPreferDirect", "true");
		System.setProperty("io.netty.noUnsafe", "true");
		System.setProperty("javax.net.debug", "SSL,handshake,data,trustmanager");

		System.err.println(System.getProperty("java.library.path"));
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Server().run(host, port);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (SSLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void run(String host, int port) throws InterruptedException,
			SSLException, CertificateException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerInitializer serverInitializer = new ServerInitializer();
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
