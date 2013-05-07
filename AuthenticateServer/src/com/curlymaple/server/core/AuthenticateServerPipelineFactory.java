package com.curlymaple.server.core;

import static org.jboss.netty.channel.Channels.pipeline;

import javax.annotation.Resource;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.springframework.stereotype.Component;

import com.yunfeng.protocol.netty.NettyDecoder;
import com.yunfeng.protocol.netty.NettyEncoder;

@Component(value = "channelPipelineFactory")
public class AuthenticateServerPipelineFactory implements
		ChannelPipelineFactory {

	@Resource
	private NettyDecoder decoder;

	@Resource
	private NettyEncoder encoder;

	@Resource
	private AuthenticateServerHandler serverHandler;

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		// 处理coder
		pipeline.addLast("decoder", decoder);
		pipeline.addLast("encoder", encoder);
		pipeline.addLast("handler", serverHandler);
		return pipeline;
	}
}