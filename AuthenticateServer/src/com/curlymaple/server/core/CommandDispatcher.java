package com.curlymaple.server.core;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

@Controller
public class CommandDispatcher {
	@Resource
	private BloodWarApplicationContext bwac;

	private static final Map<Short, IC2SCommand> mm = new TreeMap<Short, IC2SCommand>();;

	public BloodWarApplicationContext getBwac() {
		return bwac;
	}

	public void setBwac(BloodWarApplicationContext bwac) {
		this.bwac = bwac;
	}

	public void init() {
		Log.info(bwac);
		// mm.put(SYSTEM_TEST_C2S_NUMBER, bwac.getBean(Test_C2S.class));
		// mm.put(USER_LOGIN_C2S_NUMBER, bwac.getBean(Login_C2S.class));
	}

	public static IC2SCommand parse(short messageId) {
		return mm.get(messageId);
	}
}
