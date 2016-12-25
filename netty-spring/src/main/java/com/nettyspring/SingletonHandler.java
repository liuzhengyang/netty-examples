package com.nettyspring;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
@ChannelHandler.Sharable
@Service
public class SingletonHandler extends SimpleChannelInboundHandler<String> {

	@Resource
	private BusinessService businessService;


	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		ctx.writeAndFlush(businessService.dealInput(msg));
	}
}
