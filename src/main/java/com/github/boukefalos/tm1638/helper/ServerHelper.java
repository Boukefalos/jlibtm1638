package com.github.boukefalos.tm1638.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.Server;
import tm1638.Tm1638.SetLed;

import com.github.boukefalos.tm1638.TM1638;

public class ServerHelper {
	protected static Logger logger = LoggerFactory.getLogger(ServerHelper.class);

	public static void receive(TM1638 tm1638, byte[] buffer) {
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		System.out.println("receive() " + new String(buffer).trim());
		try {
			//Command command = Command.parseFrom(buffer);
			Command command = Command.parseDelimitedFrom(input);
			logger.debug("Command type = " + command.getType().name());
			switch (command.getType()) {
				case SERVER:
					Server server = command.getServer();
					// setup new udp server connection, multicast?
					break;
				case PING:
					Ping ping = command.getPing();
					tm1638.ping(ping.getId());
					break;
				case SET_LED:
					SetLed setLed = command.getSetLed();
					logger.debug("Color = " + setLed.getColor().name());
					switch (setLed.getColor()) {
						case RED:
							tm1638.setLed(Color.RED, 1);
						case GREEN:
							tm1638.setLed(Color.GREEN, 1);
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
