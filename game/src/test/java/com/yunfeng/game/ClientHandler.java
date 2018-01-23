package com.yunfeng.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.AppUtils;
import com.yunfeng.game.util.Log;

public class ClientHandler extends SimpleChannelInboundHandler<DataTransfer> {

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Log.d("channelInactive: " + ctx.channel().id());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {

	}

	Random r = new Random();

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		Log.d("channelActive: " + ctx.channel().id());
		// ctx.channel().writeAndFlush(new DataTransfer());
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Channel channel = ctx.channel();
				if (channel.isActive()) {
					// DataTransfer dt = new DataTransfer();
					// AppUtils.random(dt);
					// channel.writeAndFlush(dt);
					int i = r.nextInt();
					String msg = "config;172.19.34.237;5274" + ", Random:" + i;
					ByteBuf buff = Unpooled.buffer(msg.length());
					buff.writeBytes(msg.getBytes());
					channel.writeAndFlush(buff);
				} else {
					this.cancel();
				}
			}
		}, 0, 1000);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, DataTransfer msg)
			throws Exception {
		Log.d(msg);
	}

}
