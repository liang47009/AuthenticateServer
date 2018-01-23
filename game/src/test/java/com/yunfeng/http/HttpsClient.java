package com.yunfeng.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Security;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class HttpsClient {

	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.keyStore",
				"D:\\ProjectHome\\JEEProject\\game\\src\\test\\resources\\client.bks");
		System.setProperty("javax.net.ssl.trustStore",
				"D:\\ProjectHome\\JEEProject\\game\\src\\test\\resources\\clienttrust");
		Security.addProvider(new BouncyCastleProvider());
		InputStream in = HttpsServer.class.getClassLoader()
				.getResourceAsStream("client.bks");
		SSLContext context = SSLContext.getInstance("TLS");
		KeyStore ks = KeyStore.getInstance("BKS", "BC");
		// KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, "a123456".toCharArray());
		String algorithm = KeyManagerFactory.getDefaultAlgorithm();
		KeyManagerFactory kf = KeyManagerFactory.getInstance(algorithm);
		kf.init(ks, "a123456".toCharArray());
		context.init(kf.getKeyManagers(), null, null);
//		SocketFactory sf = context.getSocketFactory();

		SocketFactory sf = SSLSocketFactory.getDefault();
		Socket s = sf.createSocket("172.19.34.237", 8888);
		// Socket s = sf.createSocket("172.17.36.136", 8888);
		// Socket s = sf.createSocket("172.19.34.167", 8888);
		PrintWriter pw = new PrintWriter(s.getOutputStream());
		pw.write("hello world!");
		pw.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		System.out.println(reader.readLine());
		reader.close();
		pw.close();
		s.close();
	}
}
