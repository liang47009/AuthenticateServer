package com.yunfeng.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * server Created by xll on 2017/12/8.
 */
public class HttpsServer {

	public static void main(String[] args) {
		 System.setProperty("javax.net.ssl.trustStore",
		 "D:\\ProjectHome\\JEEProject\\game\\src\\test\\resources\\servertrust");
		Security.addProvider(new BouncyCastleProvider());
		InputStream in = HttpsServer.class.getClassLoader()
				.getResourceAsStream("server.bks");
		HttpsServer.startup(in);
	}

	public static void startup(InputStream ksIn) {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			KeyStore ks = KeyStore.getInstance("BKS", "BC");
			ks.load(ksIn, "a123456".toCharArray());
			String algorithm = KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory kf = KeyManagerFactory.getInstance(algorithm);
			kf.init(ks, "a123456".toCharArray());

			InputStream trustIn = HttpsServer.class.getClassLoader()
					.getResourceAsStream("client.bks");
			KeyStore ksTrust = KeyStore.getInstance("BKS", "BC");
			ksTrust.load(trustIn, "a123456".toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ksTrust);

			context.init(kf.getKeyManagers(), tmf.getTrustManagers(), null);

			ServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket _socket = (SSLServerSocket) factory
					.createServerSocket(8888);
			_socket.setNeedClientAuth(false);
			while (true) {
				new HttpsSocket(_socket.accept()).start();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}

/**
 * aa Created by xll on 2017/12/8.
 */
class HttpsSocket extends Thread {

	private Socket socket;

	public HttpsSocket(Socket socket) {
		this.socket = socket;
		System.out.println("Client connected!");
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String data = reader.readLine();
			System.out.println("server recv: " + data);
			pw.println(data);
			pw.flush();
			pw.close();
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			try {
				socket.close();
			} catch (IOException e1) {
				System.err.println(e1.getMessage());
			}
		}
	}

}
