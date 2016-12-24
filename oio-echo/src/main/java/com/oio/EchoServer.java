package com.oio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-24
 */
public class EchoServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);

	private int port;
	private volatile boolean stop;
	private Thread serverThread;
	private ExecutorService handleExecutors = Executors.newCachedThreadPool();

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() {
		serverThread = new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				LOGGER.info("Server Started At Port {}", port);
				while (!stop) {
					Socket accept = serverSocket.accept();
					handleExecutors.submit(() -> {
						handle(accept);
					});
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		serverThread.start();
	}

	private void handle(Socket accept) {
		try (
				InputStream inputStream = accept.getInputStream();
				OutputStream outputStream = accept.getOutputStream();
		) {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			PrintWriter pw = new PrintWriter(outputStream);
			String line;
			while ((line = br.readLine()) != null) {
				LOGGER.info("Receive {}", line);
				pw.println(line);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		stop = true;
		LOGGER.info("Stopping Server");
	}
}
