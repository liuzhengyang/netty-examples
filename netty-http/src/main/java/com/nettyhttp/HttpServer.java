package com.nettyhttp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
public class HttpServer {
	private int port;

	public HttpServer(int port) {
		this.port = port;
	}

	private EventLoopGroup bossGroup = new NioEventLoopGroup(2);
	private EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

	public void start() throws Exception {
		SelfSignedCertificate ssc = new SelfSignedCertificate();
		SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new HttpServerInitializer(sslCtx));
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
