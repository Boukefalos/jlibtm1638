package com.github.boukefalos.tm1638.implementation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import base.receiver.Receiver;
import base.sender.UdpSender;

import com.github.boukefalos.tm1638.EchoReceiver;
import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.helper.ReceiverHelper;

public class UdpImplementation extends TM1638 implements Receiver {
	protected UdpSender sender; 
    protected ArrayList<EchoReceiver> echoReceiverList = new ArrayList<EchoReceiver>();

	public UdpImplementation(String host, int port) throws UnknownHostException {
		sender = new UdpSender(host, port);
	}

	public void send(byte[] buffer) throws IOException {
		sender.send(buffer);
	}

	public void addReceiver(EchoReceiver receiver) {
		// Start 2-way communication

	}

	public void receive(byte[] buffer) {
		ReceiverHelper.receive(echoReceiverList, buffer);	
	}
}
