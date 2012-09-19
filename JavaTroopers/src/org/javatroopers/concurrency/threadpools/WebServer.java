package org.javatroopers.concurrency.threadpools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class WebServer {
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 1111;

	public enum Operation {
		GET, POST, PUT, DELETE
	}

	protected abstract void run() throws IOException;

	protected void handleRequest(final Socket client) {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String operation = br.readLine();
			pw = new PrintWriter(client.getOutputStream());
			pw.println("Client: " + client.getRemoteSocketAddress() + ", Op " + operation + ", Server thread  " + client.getLocalSocketAddress() + " (" + Thread.currentThread().getId() + ")");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new UnreliableWebServer().run();
//		new ReliableWebServer().run();
	}

}
