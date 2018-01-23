package com.yunfeng.game.processor.httpcode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.io.InputStream;

import com.yunfeng.game.processor.IHttpProcessor;

public class FaviconProcessor implements IHttpProcessor {

	private InputStream in = this.getClass().getClassLoader().getResourceAsStream("favicon.ico");
	private ByteBuf src = Unpooled.buffer();

	public FaviconProcessor() {
		try {
			byte[] b = new byte[in.available()];
			in.read(b);
//			src.writeBytes(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		File file = new File("favicon.ico");
//		if (file.exists()) {
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(file);
//				byte[] src = new byte[(int) file.length()];
//				fis.read(src);
//			} catch (IOException e) {
//				Log.e("FaviconProcessor", e);
//			} finally {
//				if (fis != null) {
//					try {
//						fis.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}
	}

	@Override
	public void process(ChannelHandlerContext ctx, HttpRequest request,
			String param) {
		// TODO Auto-generated method stub
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
		 response.content().writeBytes(src.array());
		ctx.writeAndFlush(response);
		ctx.close();
	}
}
