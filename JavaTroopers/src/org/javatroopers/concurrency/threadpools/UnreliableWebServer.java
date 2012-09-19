package org.javatroopers.concurrency.threadpools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UnreliableWebServer extends WebServer {

	@Override
	protected void run() throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				final Socket connection = serverSocket.accept();
				Runnable r = new Runnable() {
					public void run() {
						handleRequest(connection);
					}
				};
				// Don't do this!
				new Thread(r).start();
			}
		} finally {
			if (serverSocket != null && serverSocket.isBound()) {
				serverSocket.close();
			}
		}
	}

}
