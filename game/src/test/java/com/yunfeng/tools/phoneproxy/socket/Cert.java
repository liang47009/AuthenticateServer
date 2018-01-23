package com.yunfeng.tools.phoneproxy.socket;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.yunfeng.CertTool;

public class Cert {

	private static Map<String, X509Certificate> certCache = new HashMap<String, X509Certificate>();
	private static String issuer = null;
	private static PrivateKey caPriKey = null;
	public static PrivateKey serverPriKey = null;
	private static PublicKey serverPubKey = null;
	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			// 读取CA证书使用者信息
			issuer = CertUtil.getSubject(classLoader
					.getResourceAsStream("server.cer"));
			// CA私钥用于给动态生成的网站SSL证书签证
			// caPriKey = CertUtil.loadPriKey(classLoader
			// .getResourceAsStream("server.bks"));
			KeyStore keystore = KeyStore.getInstance("BKS", "BC");
			InputStream in = classLoader.getResourceAsStream("server.bks");
			keystore.load(in, "a123456".toCharArray());
			KeyPair caKeyPair = CertTool.getKeyPair(keystore, "server",
					"a123456".toCharArray());
			caPriKey = caKeyPair.getPrivate();
			// 生产一对随机公私钥用于网站SSL证书动态创建
			KeyPair keyPair = CertUtil.genKeyPair();
			serverPriKey = keyPair.getPrivate();
			serverPubKey = keyPair.getPublic();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static X509Certificate getCert(String host) throws Exception {
		X509Certificate cert = null;
		if (host != null) {
			String key = host.trim().toLowerCase();
			if (certCache.containsKey(key)) {
				return certCache.get(key);
			} else {
				cert = CertUtil.genCert(issuer, serverPubKey, caPriKey, key);
				certCache.put(key, cert);
			}
		}
		return cert;
	}

}
