package com.yunfeng.game.transfer;

import io.netty.buffer.ByteBuf;

public interface IDataResponse {

	void writeBytes(ByteBuf buff);

}
