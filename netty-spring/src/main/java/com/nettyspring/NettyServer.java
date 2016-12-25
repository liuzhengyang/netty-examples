package com.nettyspring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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


	private static final ConcurrentMap<String, Channel> idChannelMap = new ConcurrentHashMap<>();
	@PostConstruct
	public void init() {
		LOGGER.info("Init Netty Server");
		start();
		LOGGER.info("Init Netty Server Finished");
	}

	private int port = 8090;
	private volatile boolean stop;

	@Resource
	private SingletonHandler singletonHandler;

	private EventLoopGroup bossGroup = new NioEventLoopGroup(2);
	EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

	public void start() {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<Channel>() {
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline()
						.addLast(new StringDecoder(StandardCharsets.UTF_8))
						.addLast(new StringEncoder(StandardCharsets.UTF_8))
						.addLast(singletonHandler);

			}
		});
		ChannelFuture bind = serverBootstrap.bind(port);
		try {
			Channel channel = bind.sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@PreDestroy
	public void stop() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
