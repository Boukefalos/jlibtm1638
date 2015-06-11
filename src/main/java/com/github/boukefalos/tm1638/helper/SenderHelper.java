package com.github.boukefalos.tm1638.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Construct;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.Command.Type;
import tm1638.Tm1638.SetLed;
import base.sender.Sender;


public class SenderHelper {
	public static final int BUFFER_SIZE = 1024;
	protected static Logger logger = LoggerFactory.getLogger(SenderHelper.class);

	public static void command(Sender sender, Command command) {
		ByteArrayOutputStream output = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			command.writeDelimitedTo(output);
			byte[] buffer = output.toByteArray();
			System.out.println("command() " + new String(buffer).trim());
			sender.send(buffer);
		} catch (IOException e) {
			logger.error("Failed to send command");
		}
	}

	public static void construct(Sender sender, int dataPin, int clockPin, int strobePin) {
		command(sender, Command.newBuilder()
			.setType(Type.CONSTRUCT)
			.setConstruct(
				Construct.newBuilder()
					.setDataPin(dataPin)
					.setClockPin(clockPin)
					.setStrobePin(strobePin).build()).build());	
	}

	public static void ping(Sender sender, int id) {
    	command(sender, Command.newBuilder()
			.setType(Type.PING)
			.setPing(Ping.newBuilder()
				.setId(id)).build());
	}

	public static void setLed(Sender sender, Color color, int pos) {
		command(sender, Command.newBuilder()
        	.setType(Type.SET_LED)
        	.setSetLed(
        		SetLed.newBuilder()
        			.setColor(color)
        			.setPos(pos).build()).build());
		
	}
}
