package com.curlymaple.server.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {
	public static void main(String[] args) {
		try {
			new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			Log.error("server start error", e);
			System.exit(0);
		}
	}
}
