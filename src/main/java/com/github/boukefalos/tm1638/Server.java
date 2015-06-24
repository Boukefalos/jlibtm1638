package com.github.boukefalos.tm1638;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.SetLed;
import base.Control;
import base.Duplex;
import base.Receiver;
import base.exception.worker.ActivateException;
import base.exception.worker.DeactivateException;
import base.work.Listen;

import com.github.boukefalos.tm1638.exception.ArduinoException;

public class Server extends Listen<Object> implements Control, Receiver {
	protected static final boolean DIRECT = false;

    protected TM1638 tm1638;
	protected Duplex duplex;
	protected boolean direct;

	public Server(TM1638 tm1638, Duplex duplex) {
		this(tm1638, duplex, DIRECT);
	}

	public Server(TM1638 tm1638, Duplex duplex, boolean direct) {
		this.tm1638 = tm1638;
		this.duplex = duplex;
		this.direct = direct;
		tm1638.register(this); // Arduino > [input()]
		duplex.register(this); // Client > [receive()]
	}

	public void activate() throws ActivateException {
		duplex.start();
		super.activate();
	}
	
	public void deactivate() throws DeactivateException {
		duplex.stop();
		super.deactivate();
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

	public void input(byte[] buffer) {
		// Arduino > [Server] > Client
		try {
			duplex.send(buffer);
		} catch (IOException e) {
			logger.error("", e);
		}
	}
}