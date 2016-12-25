package com.nettyspring;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
@ChannelHandler.Sharable
@Service
public class SingletonHandler extends ChannelHandlerAdapter{

}
