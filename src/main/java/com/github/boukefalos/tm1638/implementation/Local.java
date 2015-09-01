package com.github.boukefalos.tm1638.implementation;

import java.io.IOException;
import java.io.OutputStream;

import tm1638.Tm1638.Message;

import com.github.boukefalos.arduino.exception.ArduinoException;
import com.github.boukefalos.arduino.port.ParsingPort;
import com.github.boukefalos.arduino.port.Port;
import com.github.boukefalos.tm1638.AbstractTM1638;

public class Local extends AbstractTM1638 {
    protected Port arduino;
    protected OutputStream outputStream;

    public Local() throws ArduinoException {
        this(ParsingPort.getInstance(Message.class));
    }

    public Local(Port arduino) throws ArduinoException {
        this.arduino = arduino;
        outputStream = arduino.getOutputStream();
        arduino.register(this);
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
