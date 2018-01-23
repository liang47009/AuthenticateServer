package com.yunfeng.game.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;

public final class ByteServerCodec
		extends
		CombinedChannelDuplexHandler<MessageToMessageDecoder<ByteBuf>, MessageToMessageEncoder<DataTransfer>> {

	public ByteServerCodec() {
		init(new ByteDecoder(), new ByteEncoder());
	}

	private final class ByteEncoder extends
			MessageToMessageEncoder<DataTransfer> {

		@Override
		protected void encode(ChannelHandlerContext ctx, DataTransfer msg,
				List<Object> out) throws Exception {
			out.add(msg.toByteBuff());
		}

	}

	private final class ByteDecoder extends MessageToMessageDecoder<ByteBuf> {
		// private static final int MAX_LENGTH = 1024;

		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
				List<Object> out) throws Exception {
			int avi = buffer.readableBytes();
			if (avi > 4) {
				int length = buffer.readInt();
				if ((buffer.readableBytes() >= length) && (length > 0)) {
					ByteBuf buff = Unpooled.buffer(length);
					DataTransfer dt = new DataTransfer();
					dt.setMid(buffer.readByte());
					dt.setPid(buffer.readByte());
					buffer.readBytes(buff, 0, length - 2);
					buffer.markReaderIndex();

					dt.setData(buff);
					out.add(dt);

					// if (buffer.readableBytes() != 0) {
					// Log.e("buffer=" + buffer);
					// }
				} else {
					buffer.resetReaderIndex();
					if (buffer
							.getClass()
							.getName()
							.equalsIgnoreCase(
									"io.netty.buffer.SimpleLeakAwareByteBuf")) {
						Log.e("decode error length=" + length
								+ ", readableBytes=" + buffer);
					}
					// ByteBuf buff = Unpooled.copiedBuffer(buffer);
					// out.add(buff);// 转发给下一个codec
				}
			}
		}
	}
}
