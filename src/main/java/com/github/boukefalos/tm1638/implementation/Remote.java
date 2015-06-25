package com.github.boukefalos.tm1638.implementation;

import java.io.IOException;
import java.util.ArrayList;

import base.Duplex;
import base.Receiver;
import base.work.Listen;

import com.github.boukefalos.arduino.exception.ArduinoException;
import com.github.boukefalos.tm1638.AbstractTM1638;

public class Remote extends AbstractTM1638 implements Receiver {
	protected Duplex duplex;
	protected ArrayList<Listen<Object>> listenList;

	public Remote(Duplex duplex) {
		this.duplex = duplex;
    	listenList = new ArrayList<Listen<Object>>();
    	duplex.register(this); // Server > [receive()]
	}

	public void start() {
		duplex.start();
	}

	public void stop() {
		duplex.stop();
	}

	public void exit() {
		super.stop();
		duplex.exit();
	}

	public void register(Listen<Object> listen) {
		listenList.add(listen);		
	}

	public void remove(Listen<Object> listen) {
		listenList.remove(listen);		
	}

	public void receive(byte[] buffer) {
		// Arduino > Server > [Client]
		// Should give option to decode here?
		for (Listen<Object> listen : listenList) {
			listen.add(buffer);
		}
	}

	public void send(byte[] buffer) throws ArduinoException {
		// [Client] > Server > Arduino
		try {
			duplex.send(buffer);
		} catch (IOException e) {
			throw new ArduinoException("Failed to send");
		}
	}
}