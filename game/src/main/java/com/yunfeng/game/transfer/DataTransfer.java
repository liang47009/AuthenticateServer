package com.yunfeng.game.transfer;

import com.yunfeng.game.util.BufferWriter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DataTransfer {
	private int len;
	private byte mid;
	private byte pid;
	private ByteBuf data;
	private IDataResponse response;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte getMid() {
		return mid;
	}

	public void setMid(byte mid) {
		this.mid = mid;
	}

	public byte getPid() {
		return pid;
	}

	public void setPid(byte pid) {
		this.pid = pid;
	}

	public ByteBuf getData() {
		return data;
	}

	public void setData(ByteBuf data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mid=");
		sb.append(mid);
		sb.append(", pid=");
		sb.append(pid);
		sb.append(", data=");
		sb.append(data.toString());
		return sb.toString();
	}

	public IDataResponse getResponse() {
		return response;
	}

	public void setResponse(IDataResponse response) {
		this.response = response;
	}

	public IDataResponse readData(ByteBuf msg) {
		int length = msg.readIntLE();
		ByteBuf buff = msg.readBytes(length);
		StringData data = new StringData();
		data.readBuff(buff);
		return data;
	}

	public ByteBuf toByteBuff() {
		ByteBuf buff = Unpooled.buffer();
		BufferWriter.writeByte(mid, buff).writeByte(pid);
		if (response != null) {
			response.writeBytes(buff);
		}
		ByteBuf temp = Unpooled.buffer(buff.readableBytes() + 4);
		temp.writeInt(buff.readableBytes());
		buff.readBytes(temp);
		buff.release();
		return temp;
	}

}
