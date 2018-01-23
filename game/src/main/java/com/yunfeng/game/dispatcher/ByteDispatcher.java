package com.yunfeng.game.dispatcher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

import com.yunfeng.game.processor.IByteProcessor;
import com.yunfeng.game.processor.bytecode.UserByteProcessor;
import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;

public class ByteDispatcher implements IDispatcher {

	private static final byte USER_PROCESSOR_MODULEID = 1;

	private Map<Byte, IByteProcessor> processors = new HashMap<Byte, IByteProcessor>();

	public ByteDispatcher() {
		register(USER_PROCESSOR_MODULEID, new UserByteProcessor());
	}

	public void register(byte uri, IByteProcessor processor) {
		processors.put(uri, processor);
	}

	@Override
	public boolean dipatch(ChannelHandlerContext ctx, Object msg) {
		// TODO Auto-generated method stub
		boolean processable = false;
		if (msg instanceof DataTransfer) {
			DataTransfer request = (DataTransfer) msg;
			byte mid = request.getMid();
			IByteProcessor processor = processors.get(mid);
			if (processor == null) {
				String log = "no processor found for: " + request.getMid();
				Log.d(log);
			} else {
				processor.process(ctx, request);
			}
			processable = true;
		} else if (msg instanceof ByteBuf) {
			String log = "ByteBuf msg: ";
			ByteBuf buf = (ByteBuf) msg;
			byte[] arr = buf.array();
			for (int i = 0; i < arr.length; i++) {
				int val = arr[i];
				String hex = Integer.toHexString(val & 0xFF);
				if (hex.length() == 1) {
					hex = "0" + hex;
				}
				log += hex;
			}
			Log.d(log);
		}
		return processable;
	}

}
