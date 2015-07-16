package com.github.boukefalos.tm1638;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.SetLED;
import base.Duplex;

import com.github.boukefalos.arduino.exception.ArduinoException;

public class Server extends com.github.boukefalos.arduino.Server {
    protected TM1638 tm1638;

	public Server(TM1638 tm1638, Duplex duplex, boolean direct) {
		super(tm1638, duplex, direct);
		this.tm1638 = tm1638;
	}

	public void receive(byte[] buffer) {
		// Client > [Server] > Arduino
		if (direct) {
			try {
				tm1638.send(buffer);
			} catch (ArduinoException e) {
				logger.error("", e);
			}
		} else {
			ByteArrayInputStream input = new ByteArrayInputStream(buffer);
			try {
				Command command = Command.parseDelimitedFrom(input);
				logger.debug("Command type = " + command.getType().name());
				switch (command.getType()) {
					case PING:
						Ping ping = command.getPing();
						tm1638.ping(ping.getId());
						break;
					case SET_LED:
						SetLED setLED = command.getSetLED();
						logger.debug("Color = " + setLED.getColor().name());
						switch (setLED.getColor()) {
							case RED:
								tm1638.setLED(Color.RED, 1);
							case GREEN:
								tm1638.setLED(Color.GREEN, 1);
							default:
								break;
						}
						break;
					default:
						break;
				}
			} catch (IOException e) {
				logger.error("Failed to parse input");
				return;
			}
		}
	}
}