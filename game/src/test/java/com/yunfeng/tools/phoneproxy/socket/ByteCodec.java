package com.yunfeng.tools.phoneproxy.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class ByteCodec
		extends
		CombinedChannelDuplexHandler<MessageToMessageDecoder<ByteBuf>, MessageToMessageEncoder<ByteBuf>> {

	public ByteCodec() {
		init(new ByteDecoder(), new ByteEncoder());
	}

	private final class ByteEncoder extends MessageToMessageEncoder<ByteBuf> {

		@Override
		protected void encode(ChannelHandlerContext ctx, ByteBuf msg,
				List<Object> out) throws Exception {
			ByteBuf buff = Unpooled.buffer(msg.readableBytes());
			msg.readBytes(buff);
			out.add(buff);
		}
	}

	private final class ByteDecoder extends MessageToMessageDecoder<ByteBuf> {

		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
				List<Object> out) throws Exception {
			ByteBuf buff = Unpooled.buffer(msg.readableBytes());
			msg.readBytes(buff);
			out.add(buff);
		}
	}
}
