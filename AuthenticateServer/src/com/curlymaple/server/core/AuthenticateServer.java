package com.curlymaple.server.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateServer {

	@Resource
	private ChannelPipelineFactory channelPipelineFactory;

	private String ip = Config.getConfig(Config.GAMESERVERIP);
	private int port = Integer.valueOf(Config.getConfig(Config.GAMESERVERPORT));

	public void start() {
		// 开始NIO线程
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		// 服务启始点
		ServerBootstrap b = new ServerBootstrap(factory);
		// 处理过滤器
		b.setPipelineFactory(channelPipelineFactory);
		// 设置相关参数
		b.setOption("child.tcpNoDelay", true);
		// 设置相关参数
		b.setOption("child.keepAlive", true);
		// 绑定相关端口
		b.bind(new InetSocketAddress(ip, port));
		Log.info("Server start:" + ip + ", " + port);
	}

}