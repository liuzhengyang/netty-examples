package com.nettyspring;

import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
@Service
public class NettyServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);


	@PostConstruct
	public void init() {
		LOGGER.info("Init Netty Server");
	}
	private EventLoopGroup bossGroup;

	public void start() {

	}
}
