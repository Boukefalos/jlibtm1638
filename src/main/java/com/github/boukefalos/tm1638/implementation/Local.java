package com.github.boukefalos.tm1638.implementation;

import java.io.IOException;
import java.io.OutputStream;

import base.work.Listen;

import com.github.boukefalos.tm1638.AbstractTM1638;
import com.github.boukefalos.tm1638.Arduino;
import com.github.boukefalos.tm1638.exception.ArduinoException;

public class Local extends AbstractTM1638 {
	protected Arduino arduino;
	protected OutputStream outputStream;

	public Local() throws Exception {
		this(Arduino.getInstance());
	}

	public Local(Arduino arduino) throws ArduinoException {
		this.arduino = arduino;
		outputStream = arduino.getOutputStream();
	}

	public void register(Listen<Object> listen) {
		arduino.register(listen);		
	}

	public void remove(Listen<Object> listen) {
		arduino.remove(listen);		
	}

	public void stop() {
        arduino.close();		
	}

	public void send(byte[] buffer) throws ArduinoException {
		try {
		outputStream.write(buffer);
		} catch (IOException e) {
			throw new ArduinoException("Failed to write to arduino");
		}
	}
}
