package com.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-24
 */
public class EchoServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);

	private int port;
	private boolean stop;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			Selector selector = Selector.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			ServerSocketChannel bind = serverSocketChannel.bind(new InetSocketAddress(port));

			while(!stop) {
				int select = selector.select();
				if (select > 0) {
					Set<SelectionKey> selectionKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectionKeys.iterator();
					while(iterator.hasNext()) {
						SelectionKey next = iterator.next();
						iterator.remove();
						if (next.isAcceptable()) {
							ServerSocketChannel channel = (ServerSocketChannel) next.channel();
							SocketChannel accept = channel.accept();
							accept.configureBlocking(false);
							accept.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(1024));
							accept.write(ByteBuffer.wrap("hello\r\n".getBytes()));
						}
						if (next.isReadable()) {
							SocketChannel channel = (SocketChannel) next.channel();
							ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
							channel.read(byteBuffer);
						}
						if (next.isWritable()) {
							SocketChannel channel = (SocketChannel) next.channel();
							ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
							byteBuffer.flip();
							channel.write(byteBuffer);
							byteBuffer.compact();
						}
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.stop = true;
	}
}
