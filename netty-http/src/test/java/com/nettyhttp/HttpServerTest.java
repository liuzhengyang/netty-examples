package com.nettyhttp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
public class HttpServerTest {

	@Test
	public void test() throws Exception {
		HttpServer httpServer = new HttpServer(8080);
		httpServer.start();
		Thread.sleep(60 * 1000);
	}

}