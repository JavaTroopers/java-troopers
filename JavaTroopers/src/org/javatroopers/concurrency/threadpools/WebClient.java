package org.javatroopers.concurrency.threadpools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebClient {
	private static final int CLIENTS = 50;
	private static final int OPERATIONS = 100000;

	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("mm.ss.SSS");
		ExecutorService clientThreadPool = Executors.newFixedThreadPool(CLIENTS);
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch endSignal = new CountDownLatch(OPERATIONS);
		
		long initTime = System.currentTimeMillis();
		for (int i = 0; i < OPERATIONS; i++) {
			clientThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					Socket client = null;
					PrintWriter pw = null;
					BufferedReader br = null;
					try {
						startSignal.await();
						client = new Socket(WebServer.HOST, WebServer.PORT);
						pw = new PrintWriter(client.getOutputStream());
						pw.println(WebServer.Operation.GET);
						pw.flush();
						br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						System.out.println(br.readLine());
						endSignal.countDown();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						if (pw != null) {
							pw.close();
						}
						if (br != null) {
							try {
								br.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (client != null) {
							try {
								client.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
		startSignal.countDown();
		try {
			endSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(df.format(new Date(System.currentTimeMillis() - initTime)));
		clientThreadPool.shutdown();
	}

}
