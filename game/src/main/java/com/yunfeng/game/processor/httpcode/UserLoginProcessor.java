package com.yunfeng.game.processor.httpcode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;

import net.sf.json.JSONObject;

import com.yunfeng.game.entity.User;
import com.yunfeng.game.processor.IHttpProcessor;
import com.yunfeng.game.util.HttpUtils;

public class UserLoginProcessor implements IHttpProcessor {

	@Override
	public void process(ChannelHandlerContext ctx, HttpRequest request,
			String param) {
		// TODO Auto-generated method stub
		// String res =
		// "{\"version\":\"20161227\",\"hall\":\"172.19.34.237:9001\",\"appweb\":\"http://fir.im/2f17\"}";
		String[] datas = param.split("&");
		if (datas.length < 2) {
			return;
		}
		String account = datas[0];
		// String sign = datas[1];
		/*
		 * var serverSign = crypto.md5(account + req.ip +
		 * config.ACCOUNT_PRI_KEY); if(serverSign != sign){
		 * http.send(res,2,"login failed."); return false; }
		 */
		// HttpRequest req = request.getRequest();
		InetSocketAddress addr = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		String ip = addr.getHostName();

		User user = new User();
		user.setId(1);
		user.setAccount(account);
		user.setCoins(1);
		user.setExp(12);
		user.setGems(12);
		user.setHeadimg("");
		user.setHistory("");
		user.setLastip(ip);
		user.setLv(1);
		user.setName(account);
		user.setRoomid("");
		user.setSex((byte) 1);

		JSONObject json = JSONObject.fromObject(user);
		json.put("ret", "ok");
		String res = json.toString();
		res = "{\"errcode\":0,\"errmsg\":\"ok\"}";
		HttpUtils.response(ctx, request, res);
	}

}
