package com.yunfeng.game.util;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class BufferReader {

	public static int readInt(ByteBuf buf) {
		return buf.readInt();
	}

	public static long readLong(ByteBuf buf) {
		return buf.readLong();
	}

	public static String readString(ByteBuf buf)
			throws UnsupportedEncodingException {
		int length = readInt(buf);
		byte[] array = new byte[length];
		buf.readBytes(array);
		return new String(array, "utf-8");
	}

}
