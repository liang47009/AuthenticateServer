package com.yunfeng.game.processor;

import io.netty.channel.ChannelHandlerContext;

import com.yunfeng.game.transfer.DataTransfer;

public interface IByteProcessor extends IProcessor {

	void process(ChannelHandlerContext ctx, DataTransfer request);

}
