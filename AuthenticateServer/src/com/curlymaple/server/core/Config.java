package com.curlymaple.server.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 服务器自定义配置
 */
public class Config {
	public static final String GAMESERVERIP = "gameserverip";// 游戏服ip
	public static final String GAMESERVERPORT = "gameserverport";// 游戏服端口
	public static final String ONLINELIMIT = "onlineLimit";

	private static final Logger LOGGER = Logger.getLogger(Module.SERVER);
	private static final String fileName = "config.properties";

	private static Config instance = null;
	private static Properties config = null;
	private static long modifyTime;
	private static File file = null;

	static {
		try {
			config = new Properties();
			config.load(ClassLoader.getSystemResourceAsStream(fileName));
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource(fileName);
			file = new File(url.getFile());
			modifyTime = file.lastModified();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public Properties getConfig() {
		reloadOnchange();
		return config;
	}

	public static final String getConfig(String key) {
		reloadOnchange();
		return String.valueOf(config.get(key));
	}

	private static void reloadOnchange() {
		try {
			long fileLastModifytime = file.lastModified();
			if (fileLastModifytime != modifyTime) {
				if (config != null) {
					config.clear();
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("reloadOnChange() - reload ini file:"
								+ file);
					}
					config.load(ClassLoader.getSystemResourceAsStream(file
							.getName()));
					modifyTime = file.lastModified();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}