package com.curlymaple.server.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.stereotype.Component;

import com.curlymaple.server.module.user.message.UserService;

@Component
public class AuthenticateServer {

	@Resource
	private ChannelPipelineFactory channelPipelineFactory;

	public void init() {
		BloodWarApplicationContext bwac = new BloodWarApplicationContext(
				"applicationContext.xml");
		// String[] names = bwac.getBeanFactory().getBeanDefinitionNames();
		// for (int i = 0; i < names.length; i++) {
		// System.out.println(names[i]);
		// }
		CommandDispatcher cd = bwac.getBean(CommandDispatcher.class);
		cd.init();
		ShutDownHook shutDownHook = bwac.getBean(ShutDownHook.class);
		UserService userService = bwac.getBean(UserService.class);
		userService.init();
		Runtime.getRuntime().addShutdownHook(new Thread(shutDownHook));
		this.start("127.0.0.1", 8888);
	}

	private void start(String ip, int port) {
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