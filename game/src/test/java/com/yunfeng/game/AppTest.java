package com.yunfeng.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void test() {
		int loop = 3000000;
//		AppExtension ae = new AppExtension();
		byte[] CONTENT = "hello".getBytes();
		long startTime = System.currentTimeMillis();
		ByteBuf buffer = null;
		for (int i = 0; i < loop; i++) {
//			buffer = Unpooled.directBuffer(10);
			buffer = PooledByteBufAllocator.DEFAULT.directBuffer(10);
			buffer.writeBytes(CONTENT);
			buffer.release();
		}
		System.out.println(buffer.getClass().getName());
		System.out.println(System.currentTimeMillis() - startTime);
	}

}
