package com.nettyhttp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
public class HttpServerInitializer extends ChannelInitializer<Channel> {
	private final SslContext sslCtx;

	public HttpServerInitializer(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}

	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline()
				.addLast(sslCtx.newHandler(ch.alloc()))
				.addLast(new HttpServerCodec())
				.addLast(new HttpObjectAggregator(1024 * 512))
				.addLast(new HttpServerHandler())
		;
	}
}
