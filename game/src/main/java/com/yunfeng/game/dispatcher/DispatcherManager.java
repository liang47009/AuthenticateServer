package com.yunfeng.game.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.yunfeng.game.socket.MemoryData;

public class DispatcherManager {

	private static Executor executor = Executors.newCachedThreadPool();

	private static List<IDispatcher> dispatchers = new ArrayList<IDispatcher>();

	private static Map<String, MemoryData> memoryDatas = Collections
			.synchronizedMap(new HashMap<String, MemoryData>());

	public static void init() {
		registerDispatcher(new UriDispatcher());
		registerDispatcher(new ByteDispatcher());
	}

	public static boolean registerDispatcher(IDispatcher dispatcher) {
		return dispatchers.add(dispatcher);
	}

	public static void dipatch(final ChannelHandlerContext ctx, final Object msg) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				for (IDispatcher dispatch : dispatchers) {
					if (dispatch.dipatch(ctx, msg)) {
						break;
					}
				}
			}
		});
	}

	public static void channelActive(final ChannelHandlerContext ctx) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String cid = ctx.channel().id().asLongText();
				synchronized (memoryDatas) {
					if (memoryDatas.containsKey(cid)) {
						MemoryData old = memoryDatas.remove(cid);
						old.getChannel().close();
					}
					AttributeKey<MemoryData> d = AttributeKey.valueOf(cid);
					Attribute<MemoryData> attr = ctx.channel().attr(d);
					MemoryData data = new MemoryData();
					data.setChannel(ctx.channel());
					data.setLoginTime(System.currentTimeMillis() + "");
					attr.set(data);
				}
			}
		});
	}

	public static void channelInactive(ChannelHandlerContext ctx) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String cid = ctx.channel().id().asLongText();
				synchronized (memoryDatas) {
					if (memoryDatas.containsKey(cid)) {
						MemoryData old = memoryDatas.remove(cid);
						old.getChannel().close();
					}
				}
			}
		});
	}

}
