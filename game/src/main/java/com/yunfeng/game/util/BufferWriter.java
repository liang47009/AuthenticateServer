package com.yunfeng.game.util;

import io.netty.buffer.ByteBuf;

public class BufferWriter {

	public static ByteBuf writeInt(int i, ByteBuf buf) {
		return buf.writeInt(i);
	}

	public static ByteBuf writeLong(long l, ByteBuf buf) {
		return buf.writeLong(l);
	}

	public static ByteBuf writeString(String str, ByteBuf buf) {
		if (null != str) {
			byte[] bs = str.getBytes();
			buf.writeInt(bs.length);
			buf.writeBytes(bs);
		}
		return buf;
	}

	public static ByteBuf writeByte(byte b, ByteBuf buf) {
		return buf.writeByte(b);
	} 

}
