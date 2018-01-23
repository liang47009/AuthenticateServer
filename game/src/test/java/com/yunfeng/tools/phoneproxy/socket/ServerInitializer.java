package com.yunfeng.tools.phoneproxy.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("httpCodec", new HttpServerCodec());
		pipeline.addLast("httpObject", new HttpObjectAggregator(6553600));
		pipeline.addLast("handler", new ServerHandler());
	}

}
