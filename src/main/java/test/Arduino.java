package test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

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

    protected SerialPort port = null;
    protected InputStream inputStream = null;
    protected ArrayList<EchoListener> listenerList = new ArrayList<EchoListener>();

    public void connect() throws UnsupportedCommOperationException, PortInUseException, IOException, TooManyListenersException {
        CommPortIdentifier portid = null;
        Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            portid = (CommPortIdentifier)portEnum.nextElement();
            if (portid != null) {              
                System.out.println("Trying: " + portid.getName());
                for ( String portName: PORT_NAMES) {
                    if ( portid.getName().equals(portName) || portid.getName().contains(portName)) {
                        port = (SerialPort) portid.open("", TIME_OUT);
                        port.setFlowControlMode(
                                SerialPort.FLOWCONTROL_XONXOFF_IN + 
                                SerialPort.FLOWCONTROL_XONXOFF_OUT);
                        inputStream = port.getInputStream();
                        System.out.println( "Connected on port: " + portid.getName());
                        port.addEventListener(this);
                        port.notifyOnDataAvailable(true);
                    }
                }
            }
        }
    }
 
    public void serialEvent(SerialPortEvent event) {
		try {
			switch (event.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE:
            		Echo echo = Echo.parseDelimitedFrom(inputStream);
                    for (EchoListener listener : listenerList) {
                    	listener.receive(echo);
                    }
                    break; 
                default:
                    break;
            }
		} catch (IOException e) {			
			e.printStackTrace();
		}

    }

	public InputStream getInputStream() throws IOException {
		return port.getInputStream();
	}

	public OutputStream getOutputStream() throws IOException {
		return port.getOutputStream();
	}

	public void addListener(EchoListener listener) {
        listenerList.add(listener);
	}
	
	public void removeListener(EchoListener listener) {
		listenerList.remove(listener);
	}

	public void close() {
		port.close();		
	}
}