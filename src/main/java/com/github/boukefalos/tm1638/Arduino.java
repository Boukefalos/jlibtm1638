package com.github.boukefalos.tm1638;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import com.github.boukefalos.tm1638.exception.ArduinoException;

import purejavacomm.CommPortIdentifier;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;
import purejavacomm.UnsupportedCommOperationException;
import beerduino.Beerduino.Echo;
 
public class Arduino implements SerialPortEventListener {
    public static final int TIME_OUT = 1000; 
    public static final String PORT_NAMES[] = {
        "tty.usbmodem", // Mac OS X
        "usbdev",       // Linux
        "tty",          // Linux
        "serial",       // Linux
        "COM3",         // Windows
    };

	protected static Arduino arduino;

    protected SerialPort port = null;
    protected InputStream inputStream = null;
    protected ArrayList<EchoReceiver> echoReceiverList = new ArrayList<EchoReceiver>();

    private Arduino() {}

    public static Arduino getInstance() {
    	if (arduino == null) {
    		arduino = new Arduino();
    	}
    	return arduino;
    }

    protected void connect() throws ArduinoException {
        CommPortIdentifier portid = null;
        Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            portid = (CommPortIdentifier)portEnum.nextElement();
            if (portid != null) {              
                System.out.println("Trying: " + portid.getName());
                for ( String portName: PORT_NAMES) {
                    if (portid.getName().equals(portName) || portid.getName().contains(portName)) {
                        try {
                        	port = (SerialPort) portid.open("", TIME_OUT);
	                        port.setFlowControlMode(
	                        		SerialPort.FLOWCONTROL_XONXOFF_IN + 
	                        		SerialPort.FLOWCONTROL_XONXOFF_OUT);
	                        inputStream = port.getInputStream();
	                        System.out.println( "Connected on port: " + portid.getName());
	                        port.addEventListener(this);
	                    } catch (UnsupportedCommOperationException | PortInUseException | IOException | TooManyListenersException e) {
	                    	throw new ArduinoException("Failed to connect");
	                    }
                        port.notifyOnDataAvailable(true);
                        return;
                    }
                }
            }
        }
        throw new ArduinoException("No Arduino available");
    }
 
    public void serialEvent(SerialPortEvent event) {
		try {
			switch (event.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE:
            		Echo echo = Echo.parseDelimitedFrom(inputStream);
                    for (EchoReceiver echoReceiver : echoReceiverList) {
                    	echoReceiver.receive(echo);
                    }
                    break; 
                default:
                    break;
            }
		} catch (IOException e) {			
			e.printStackTrace();
		}

    }

	public InputStream getInputStream() throws ArduinoException {		
		if (port == null) {
			connect();	
		}
		try {
			return port.getInputStream();
		} catch (IOException e) {
			throw new ArduinoException("Failed to get inputstream");
		}
	}

	public OutputStream getOutputStream() throws ArduinoException {
		if (port == null) {
			connect();	
		}
		try {
			return port.getOutputStream();
		} catch (IOException e) {
			throw new ArduinoException("Failed to get inputstream");
		}
	}

	public void addReceiver(EchoReceiver receiver) {
        echoReceiverList.add(receiver);
	}
	
	public void removeReceiver(EchoReceiver receiver) {
		echoReceiverList.remove(receiver);
	}

	public void close() {
		port.close();		
	}
}