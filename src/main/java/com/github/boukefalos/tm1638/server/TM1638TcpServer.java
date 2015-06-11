package com.github.boukefalos.tm1638.server;

import base.server.channel.TcpServerClient;
import base.server.channel.TcpServer;

import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.helper.ServerHelper;

public class TM1638TcpServer extends TcpServer implements TM1638Server {
    protected TM1638 tm1638;

	public TM1638TcpServer(TM1638 tm1638, int port) {
		this(tm1638, port, BUFFER_SIZE);
	}

	public TM1638TcpServer(TM1638 tm1638, int port, int bufferSize) {
		super(port, TM1638TcpClient.class, bufferSize);
		this.tm1638 = tm1638;
		//addReceiver(this); make sure client messages get passed on to arduino
	}

	protected void initClient(TcpServerClient serverClient) {
		
	}

	protected void receive(byte[] buffer) {
		// write directly to arduino instead of decoding?
		ServerHelper.receive(tm1638, buffer);
	}
}