package com.curlymaple.server.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.curlymaple.server.module.user.message.UserService;

/**
 * after spring initialized this class will be initial
 * 
 * @author xialiangliang
 * 
 */
@Component
public class AuthenticateServerBeanFactoryAware implements BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		CommandDispatcher cd = beanFactory.getBean(CommandDispatcher.class);
		cd.init(beanFactory);
		ShutDownHook shutDownHook = beanFactory.getBean(ShutDownHook.class);
		UserService userService = beanFactory.getBean(UserService.class);
		userService.init();
		Runtime.getRuntime().addShutdownHook(new Thread(shutDownHook));

		AuthenticateServer authenticateServer = beanFactory
				.getBean(AuthenticateServer.class);
		authenticateServer.start();
	}

}