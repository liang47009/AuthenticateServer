package com.curlymaple.server.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BloodWarApplicationContext extends ClassPathXmlApplicationContext {
	public BloodWarApplicationContext(String path) {
		super(path);
	}
}
