package test;

import java.io.IOException;
import java.io.OutputStream;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Construct;
import tm1638.Tm1638.Command.Type;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.SetLed;

public class TM1638 {
	protected Arduino arduino;
	protected Command command;
	protected OutputStream outputStream;

	public TM1638(Arduino arduino) throws IOException {
		this.arduino = arduino;
		outputStream = arduino.getOutputStream();
	}

	public void ping(int id) throws IOException {
    	command = Command.newBuilder()
			.setType(Type.PING)
			.setPing(Ping.newBuilder()
				.setId(id)).build();
        command.writeDelimitedTo(outputStream);
		
	}

	public void setLed(Color color, int pos) throws IOException {
        command = Command.newBuilder()
        	.setType(Type.SET_LED)
        	.setSetLed(
        		SetLed.newBuilder()
        			.setColor(color)
        			.setPos(pos).build()).build();
       command.writeDelimitedTo(outputStream);
	}

	public void construct(int dataPin, int clockPin, int strobePin) throws IOException {
		command = Command.newBuilder()
			.setType(Type.CONSTRUCT)
			.setConstruct(
				Construct.newBuilder()
					.setDataPin(dataPin)
					.setClockPin(clockPin)
					.setStrobePin(strobePin).build()).build();
		command.writeDelimitedTo(outputStream);		
	}
}
