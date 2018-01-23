package com.yunfeng.game.util;

import org.apache.log4j.Logger;

public class Log {

	private static final Logger loggerDebug = Logger.getLogger("logDebug");
	private static final Logger loggerErr = Logger.getLogger("logError");

	public static void d(Object msg) {
		// System.out.println(msg);
		if (null == msg) {
			msg = "null";
		}
		loggerDebug.debug(msg.toString());
	}

	public static void e(Object msg) {
		if (null == msg) {
			msg = "null";
		}
		loggerErr.error(msg.toString());
	}

	public static void e(Object msg, Throwable cause) {
		if (null == msg) {
			msg = "null";
		}
		loggerErr.error(msg.toString(), cause);
	}
}
