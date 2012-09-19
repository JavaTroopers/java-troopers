package org.javatroopers.concurrency.threadpools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ReliableWebServer extends WebServer{

	@Override
	protected void run() throws IOException {
		ServerSocket serverSocket = null;
		Executor pool = Executors.newFixedThreadPool(50);
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				final Socket connection = serverSocket.accept();
				Runnable r = new Runnable() {
					public void run() {
						handleRequest(connection);
					}
				};
				pool.execute(r);
			}
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}
