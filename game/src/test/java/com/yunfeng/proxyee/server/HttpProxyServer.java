package com.yunfeng.proxyee.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.yunfeng.proxyee.crt.CertUtil;
import com.yunfeng.proxyee.handler.HttpProxyServerHandle;
import com.yunfeng.proxyee.intercept.HttpProxyIntercept;
import com.yunfeng.proxyee.intercept.HttpProxyInterceptInitializer;
import com.yunfeng.proxyee.intercept.HttpProxyInterceptPipeline;
import com.yunfeng.proxyee.proxy.ProxyConfig;

public class HttpProxyServer {

	// http代理隧道握手成功
	public final static HttpResponseStatus SUCCESS = new HttpResponseStatus(
			200, "Connection established");

	public static SslContext clientSslCtx;
	public static String issuer;
	public static PrivateKey caPriKey;
	public static PrivateKey serverPriKey;
	public static PublicKey serverPubKey;
	public static EventLoopGroup proxyGroup;

	private HttpProxyInterceptInitializer proxyInterceptInitializer;
	private ProxyConfig proxyConfig;

	private void init() throws Exception {
		// 注册BouncyCastleProvider加密库
		Security.addProvider(new BouncyCastleProvider());
		clientSslCtx = SslContextBuilder.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		// 读取CA证书使用者信息
		issuer = CertUtil.getSubject(classLoader.getResourceAsStream("ca.crt"));
		// CA私钥用于给动态生成的网站SSL证书签证
		caPriKey = CertUtil.loadPriKey(classLoader
				.getResourceAsStream("ca_private.pem"));
		// 生产一对随机公私钥用于网站SSL证书动态创建
		KeyPair keyPair = CertUtil.genKeyPair();
		serverPriKey = keyPair.getPrivate();
		serverPubKey = keyPair.getPublic();
		proxyGroup = new NioEventLoopGroup();
		if (proxyInterceptInitializer == null) {
			proxyInterceptInitializer = new HttpProxyInterceptInitializer();
		}
	}

	public HttpProxyServer proxyInterceptInitializer(
			HttpProxyInterceptInitializer proxyInterceptInitializer) {
		this.proxyInterceptInitializer = proxyInterceptInitializer;
		return this;
	}

	public HttpProxyServer proxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
		return this;
	}

	public void start(int port) {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			init();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					// .option(ChannelOption.SO_BACKLOG, 100)
					// .handler(new LoggingHandler(LogLevel.ERROR))
					.childHandler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast("httpCodec",
									new HttpServerCodec());
							ch.pipeline().addLast(
									"serverHandle",
									new HttpProxyServerHandle(
											proxyInterceptInitializer,
											proxyConfig));
						}
					});
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		// new HttpProxyServer().start(9999);

		new HttpProxyServer()
		// .proxyConfig(new ProxyConfig(ProxyType.SOCKS5, "127.0.0.1", 1085))
		// //使用socks5二级代理
				.proxyInterceptInitializer(new HttpProxyInterceptInitializer() {
					@Override
					public void init(HttpProxyInterceptPipeline pipeline) {
						pipeline.addLast(new HttpProxyIntercept() {
							@Override
							public void beforeRequest(Channel clientChannel,
									HttpRequest httpRequest,
									HttpProxyInterceptPipeline pipeline)
									throws Exception {
								// 替换UA，伪装成手机浏览器
								httpRequest
										.headers()
										.set(HttpHeaderNames.USER_AGENT,
												"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
								// 转到下一个拦截器处理
								pipeline.beforeRequest(clientChannel,
										httpRequest);
							}

							@Override
							public void afterResponse(Channel clientChannel,
									Channel proxyChannel,
									HttpResponse httpResponse,
									HttpProxyInterceptPipeline pipeline)
									throws Exception {
								// 拦截响应，添加一个响应头
								httpResponse.headers().add("intercept", "test");
								// 转到下一个拦截器处理
								pipeline.afterResponse(clientChannel,
										proxyChannel, httpResponse);
							}
						});
					}
				}).start(8888);
	}

}
