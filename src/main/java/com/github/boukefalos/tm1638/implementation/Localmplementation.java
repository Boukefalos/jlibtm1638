package com.github.boukefalos.tm1638.implementation;

import java.io.IOException;
import java.io.OutputStream;

import com.github.boukefalos.tm1638.Arduino;
import com.github.boukefalos.tm1638.EchoReceiver;
import com.github.boukefalos.tm1638.TM1638;

public class Localmplementation extends TM1638 {
	protected Arduino arduino;
	protected OutputStream outputStream;

	public Localmplementation() throws Exception {
		this(Arduino.getInstance());
	}

	public Localmplementation(Arduino arduino) throws Exception {
		this.arduino = arduino;
		outputStream = arduino.getOutputStream();
	}

	public void send(byte[] buffer) throws IOException {
		outputStream.write(buffer);		
	}

	public void stop() {
        arduino.close();		
	}

	public void addReceiver(EchoReceiver receiver) {
		arduino.addReceiver(receiver);		
	}
}
