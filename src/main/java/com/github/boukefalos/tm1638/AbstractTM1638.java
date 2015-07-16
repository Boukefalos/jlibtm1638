package com.github.boukefalos.tm1638;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import tm1638.Tm1638.Buttons;
import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Command.Type;
import tm1638.Tm1638.Construct;
import tm1638.Tm1638.Message;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.Pong;
import tm1638.Tm1638.SetLED;
import tm1638.Tm1638.SetupDisplay;
import tm1638.Tm1638.Text;
import base.work.Listen;

import com.github.boukefalos.arduino.AbstractArduino;
import com.github.boukefalos.arduino.exception.ArduinoException;

public class AbstractTM1638 extends AbstractArduino implements TM1638 {
	public void input(Message message) {
		System.out.println(message);
		switch (message.getType()) {
			case PONG:
				input(message.getPong());				
				break;
			case TEXT:
				input(message.getText());				
				break;
			case BUTTONS:
				input(message.getButtons());				
				break;
		}
	}

	public void input(Pong pong) {}
	public void input(Text text) {}
	public void input(Buttons buttons) {}

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

	public void ping(int id) {
    	command(Command.newBuilder()
			.setType(Type.PING)
			.setPing(Ping.newBuilder()
				.setId(id)).build());
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

	public void clearDisplay() {
	}

	public void clearDisplayDigit(int pos, boolean dot) {
	}

	public void setDisplay(byte[] values) {
	}

	public void setDisplayDigit(int digit, int pos, boolean dot, byte[] font) {
	}

	public void setDisplayToBinNumber(int number, int dots, byte[] font) {
	}

	public void setDisplayToDecNumber(int number, int dots, boolean leadingZeros, byte[] font) {
	}

	public void setDisplayToHexNumber(int number, int dots,	boolean leadingZeros, byte[] font) {
	}

	public void setDisplayToError() {
	}

	public void setDisplayToString(String string, int dots, int pos, byte[] font) {
	}

	public void setLED(Color color, int pos) {
		command(Command.newBuilder()
        	.setType(Type.SET_LED)
        	.setSetLED(
        		SetLED.newBuilder()
        			.setColor(color)
        			.setPos(pos).build()).build());
	}

	public void setLEDs(int led) {
	}

	public void setupDisplay(boolean active, int intensity) {
		command(Command.newBuilder()
        	.setType(Type.SETUP_DISPLAY)
        	.setSetupDisplay(
        		SetupDisplay.newBuilder()
        			.setActive(active)
        			.setIntensity(intensity).build()).build());
	}

	public void register(Listen<Object> listen) {
	}

	public void remove(Listen<Object> listen) {
	}

	public void send(byte[] buffer) throws ArduinoException {
	}
}
