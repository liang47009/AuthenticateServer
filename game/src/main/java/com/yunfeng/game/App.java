package com.yunfeng.game;

import com.yunfeng.game.socket.Server;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		server.startUp("172.19.34.237", 8888);
	}
}
// 2017-10-09T05:56:42.688805Z 1 [Note] A temporary password is generated for
// root@localhost: ddGWplwl#6Pa
//
// If you lose this password, please consult the section How to Reset the Root
// Password in the MySQL reference manual.