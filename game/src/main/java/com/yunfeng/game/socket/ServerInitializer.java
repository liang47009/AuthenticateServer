package com.yunfeng.game.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	private ServerHandler handler = new ServerHandler();

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// Add SSL handler first to encrypt and decrypt everything.
		// In this example, we use a bogus certificate in the server side
		// and accept any invalid certificates in the client side.
		// You will need something more complicated to identify both
		// and server in the real world.
		//
		// Read SecureChatSslContextFactory
		// if you need client certificate authentication.
		// SSLEngine engine = SslContextFactory.getServerContext()
		// .createSSLEngine();
		// engine.setUseClientMode(false);
		// On top of the SSL handler, add the text line codec.
		// pipeline.addLast("ssl", new SslHandler(engine));

		// pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
		// Delimiters.lineDelimiter()));
		pipeline.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
		// pipeline.addLast("idleStateHandler", new IdleStateHandler(5, 3, 0));
		// pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
		// pipeline.addLast("writeTimeoutHandler", new WriteTimeoutHandler(30));
		// pipeline.addLast("idleHandler", new IdleHandler());
		// pipeline.addLast("bytecodec", new ByteServerCodec());
		pipeline.addLast("bytecodec", new ByteCodec());
		// pipeline.addLast("httpcodec", new HttpServerCodec());
		// and then business logic.
		pipeline.addLast("handler", handler);
	}

}
