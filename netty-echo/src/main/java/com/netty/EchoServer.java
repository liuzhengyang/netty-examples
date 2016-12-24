package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-24
 */
public class EchoServer {
	private int port;
	private volatile boolean stop;

	EventLoopGroup bossGroup = new NioEventLoopGroup(2);
	EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<Channel>() {
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline()
						.addLast(new IdleStateHandler(6, 3, 3))
						.addLast(new IdleHandler())
						.addLast(new EchoServerHandler());

			}
		});
		ChannelFuture bind = serverBootstrap.bind(port);
		try {
			Channel channel = bind.sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
