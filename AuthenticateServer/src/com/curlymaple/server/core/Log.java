package com.curlymaple.server.core;

import org.apache.log4j.Logger;

/**
 * 日志
 * @author xialiangliang
 *
 */
public class Log {
	private static Logger logger = Logger.getLogger(Module.SERVER);

	public static void debug(Object message) {
		logger.debug(message);
	}

	public static void info(Object message) {
		logger.info(message);
	}

	public static void warn(Object message) {
		logger.warn(message);
	}

	public static void error(Object message) {
		logger.error(message);
	}
}
