package com.github.boukefalos.tm1638;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import purejavacomm.CommPortIdentifier;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;
import purejavacomm.UnsupportedCommOperationException;
import tm1638.Tm1638.Echo;
import base.work.Listen;

import com.github.boukefalos.tm1638.exception.ArduinoException;
 
public class Arduino implements SerialPortEventListener {
	public static final int BUFFER_SIZE = 1024;
    public static final int TIME_OUT = 1000;
    public static final String PORT_NAMES[] = {
        "tty.usbmodem", // Mac OS X
        "usbdev",       // Linux
        "tty",          // Linux
        "serial",       // Linux
        "COM3",         // Windows
    };

	protected static Logger logger = LoggerFactory.getLogger(Arduino.class);
	protected static Arduino arduino;

	protected int bufferSize;
    protected SerialPort port = null;
    protected InputStream inputStream = null;
    protected ArrayList<Listen<Object>> listenList = new ArrayList<Listen<Object>>();

    protected Arduino(int bufferSize) {
    	this.bufferSize = bufferSize;
    }

	public void register(Listen<Object> listen) {
        listenList.add(listen);
	}
	
	public void remove(Listen<Object> listen) {
		listenList.remove(listen);
	}

    public static Arduino getInstance() {
    	return getInstance(BUFFER_SIZE);
    }

    public static Arduino getInstance(int bufferSize) {
    	if (arduino == null) {
    		arduino = new Arduino(bufferSize);
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
                	// Where should this be parsed, or should byte[] be passed directly?
    				Echo echo = Echo.parseDelimitedFrom(inputStream);
    				System.err.println(echo.getMessage());
                    for (Listen<Object> listen : listenList) {
                    	listen.add(echo);
                    }
                    break; 
                default:
                    break;
            }
		} catch (IOException e) {			
			logger.error("", e);
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

	public void close() {
		port.close();		
	}
}