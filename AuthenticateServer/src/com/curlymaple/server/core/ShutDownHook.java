package com.curlymaple.server.core;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * system shutdown hook
 * 
 * @author xialiangliang
 * 
 */
@Component
public class ShutDownHook implements Runnable {

	@Resource
	private DataSourceSelector dataSourceSelector;

	public DataSourceSelector getDataSourceSelector() {
		return dataSourceSelector;
	}

	public void setDataSourceSelector(DataSourceSelector dataSourceSelector) {
		this.dataSourceSelector = dataSourceSelector;
	}

	@Override
	public void run() {
		System.out.println("shutting down");
		System.out.println(this.dataSourceSelector.getSessionFactory()
				.getCurrentSession());
	}

}
