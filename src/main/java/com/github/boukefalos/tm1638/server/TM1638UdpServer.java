package com.github.boukefalos.tm1638.server;

import base.receiver.Receiver;
import base.server.datagram.UdpServer;

import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.helper.ServerHelper;

public class TM1638UdpServer extends UdpServer implements TM1638Server, Receiver {
    protected TM1638 tm1638;

	public TM1638UdpServer(TM1638 tm1638, int port) {
		this(tm1638, port, BUFFER_SIZE);
	}

	public TM1638UdpServer(TM1638 tm1638, int port, int bufferSize) {
		super(port, bufferSize);
		this.tm1638 = tm1638;
		this.bufferSize = bufferSize;
		addReceiver(this);
	}

	public void receive(byte[] buffer) {		
		ServerHelper.receive(tm1638, buffer);
	}
}