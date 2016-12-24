package com.netty;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-24
 */
public class IdleHandler extends ChannelDuplexHandler{

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				ctx.close();
			}
			if (event.state() == IdleState.WRITER_IDLE) {
				ctx.writeAndFlush(Unpooled.wrappedBuffer("hello\r\n".getBytes())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			}
		}
	}
}
