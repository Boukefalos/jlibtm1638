package com.github.boukefalos.tm1638;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Command.Type;
import tm1638.Tm1638.Construct;
import tm1638.Tm1638.Echo;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.SetLed;

import com.github.boukefalos.arduino.AbstractArduino;
import com.google.protobuf.InvalidProtocolBufferException;

public abstract class AbstractTM1638 extends AbstractArduino implements TM1638 {
	public void input(byte[] buffer) {
		System.out.println(new String(buffer));
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(buffer);
			Echo echo = Echo.parseDelimitedFrom(input);
			System.out.println(echo.getMessage());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
