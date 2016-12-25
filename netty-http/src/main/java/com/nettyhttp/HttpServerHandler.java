package com.nettyhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class);


	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		LOGGER.info("Receive {}", msg);
		DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("hello".getBytes()));
		ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
	}
}
