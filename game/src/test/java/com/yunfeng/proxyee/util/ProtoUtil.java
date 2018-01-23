package com.yunfeng.proxyee.util;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtoUtil {

	public static RequestProto getRequestProto(HttpRequest httpRequest) {
		RequestProto requestProto = new RequestProto();
		int port = -1;
		String hostStr = httpRequest.headers().get(HttpHeaderNames.HOST);
		String uriStr = httpRequest.uri();
		Pattern pattern = Pattern
				.compile("^(?:https?://)?(?<host>[^:]*)(?::(?<port>\\d+))?$");
		Matcher matcher = pattern.matcher(hostStr);
		// 先从host上取端口号没取到再从uri上取端口号 issues#4
		String portTemp = null;
		if (matcher.find()) {
			requestProto.setHost(matcher.group("host"));
			portTemp = matcher.group("port");
			if (portTemp == null) {
				matcher = pattern.matcher(uriStr);
				if (matcher.find()) {
					portTemp = matcher.group("port");
				}
			}
		}
		if (portTemp != null) {
			port = Integer.parseInt(portTemp);
		}
		boolean isSsl = uriStr.indexOf("https") == 0
				|| hostStr.indexOf("https") == 0;
		if (port == -1) {
			if (isSsl) {
				port = 443;
			} else {
				port = 80;
			}
		}
		requestProto.setPort(port);
		requestProto.setSsl(isSsl);
		return requestProto;
	}

	public static class RequestProto {

		private String host;
		private int port;
		private boolean ssl;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public boolean getSsl() {
			return ssl;
		}

		public void setSsl(boolean ssl) {
			this.ssl = ssl;
		}
	}
}
