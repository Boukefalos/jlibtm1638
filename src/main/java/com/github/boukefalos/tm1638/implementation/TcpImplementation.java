package com.github.boukefalos.tm1638.implementation;

import java.io.IOException;
import java.util.ArrayList;

import base.receiver.Receiver;
import base.server.channel.TcpClient;

import com.github.boukefalos.tm1638.EchoReceiver;
import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.helper.ReceiverHelper;

public class TcpImplementation extends TM1638 implements Receiver {
	protected TcpClient client;
	protected ArrayList<EchoReceiver> echoReceiverList = new ArrayList<EchoReceiver>();

	public TcpImplementation(String host, int port) {
		client = new TcpClient(host, port);
	}

	public void send(byte[] buffer) throws IOException {
		if (!client.active()) {
			client.start();
		}
		client.send(buffer);	
	}

	public void addReceiver(EchoReceiver echoReceiver) {
		client.register(this);
		echoReceiverList.add(echoReceiver);		
	}

	public void receive(byte[] buffer) {
		System.out.println(new String(buffer).trim());
		ReceiverHelper.receive(echoReceiverList, buffer);	
	}
}
