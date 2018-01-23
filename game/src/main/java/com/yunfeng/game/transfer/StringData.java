package com.yunfeng.game.transfer;

import com.yunfeng.game.util.Log;

import io.netty.buffer.ByteBuf;

public class StringData implements IDataResponse {

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return msg;
	}

	public void readBuff(ByteBuf buff) {
		int length = buff.readInt();
		if (length < 0 || length > buff.readableBytes()) {
			Log.e("read error!" + buff);
			return;
		}
		byte[] arr = new byte[length];
		buff.readBytes(arr);
		msg = new String(arr);
	}

	@Override
	public void writeBytes(ByteBuf buff) {
		if (msg != null) {
			byte[] src = msg.getBytes();
			buff.writeInt(src.length);
			buff.writeBytes(src);
		}
	}

}
