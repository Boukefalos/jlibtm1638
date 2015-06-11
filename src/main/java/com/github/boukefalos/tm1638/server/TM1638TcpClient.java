package com.github.boukefalos.tm1638.server;

import java.nio.channels.SocketChannel;

import base.server.channel.TcpServerClient;

public class TM1638TcpClient extends TcpServerClient {
	protected TM1638TcpServer server;

	public TM1638TcpClient(TM1638TcpServer server, SocketChannel socketChannel, Integer bufferSize) {
		super(server, socketChannel, bufferSize);
		this.server = server;
	}

	public void receive(byte[] buffer) {
		System.out.println("serverClient.receive() " + new String(buffer).trim());
		server.receive(buffer);
	}
}
