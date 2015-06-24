package com.github.boukefalos.tm1638;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Command.Type;
import tm1638.Tm1638.Construct;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.SetLed;
import base.Sender;

public abstract class AbstractTM1638 implements TM1638, Sender {
	public static final int BUFFER_SIZE = 1024;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void start() {}

	public void stop() {}

	public void exit() {
		stop();
	}

	public void command(Command command) {
		ByteArrayOutputStream output = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			command.writeDelimitedTo(output);
			byte[] buffer = output.toByteArray();
			send(buffer);
		} catch (IOException e) {
			logger.error("Failed to send command");
		}
	}

	public void construct(int dataPin, int clockPin, int strobePin) {
		command(Command.newBuilder()
			.setType(Type.CONSTRUCT)
			.setConstruct(
				Construct.newBuilder()
					.setDataPin(dataPin)
					.setClockPin(clockPin)
					.setStrobePin(strobePin).build()).build());	
	}

	public void ping(int id) {
    	command(Command.newBuilder()
			.setType(Type.PING)
			.setPing(Ping.newBuilder()
				.setId(id)).build());
	}

	public void setLed(Color color, int pos) {
		command(Command.newBuilder()
        	.setType(Type.SET_LED)
        	.setSetLed(
        		SetLed.newBuilder()
        			.setColor(color)
        			.setPos(pos).build()).build());
	}
}
