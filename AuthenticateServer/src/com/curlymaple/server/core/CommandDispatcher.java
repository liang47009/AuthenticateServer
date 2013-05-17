package com.curlymaple.server.core;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import com.curlymaple.server.module.user.message.Login_C2S;
import com.curlymaple.server.module.user.message.Test_C2S;

@Component
public class CommandDispatcher {
	private static final short SYSTEM_TEST_C2S_NUMBER = 6;
	private static final short USER_LOGIN_C2S_NUMBER = 100;
	private static final Map<Short, IC2SCommand> mm = new TreeMap<Short, IC2SCommand>();;

	public void init(BeanFactory beanFactory) {
		mm.put(SYSTEM_TEST_C2S_NUMBER, beanFactory.getBean(Test_C2S.class));
		mm.put(USER_LOGIN_C2S_NUMBER, beanFactory.getBean(Login_C2S.class));
	}

	public static IC2SCommand parse(short messageId) {
		return mm.get(messageId);
	}
}
