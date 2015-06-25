package com.github.boukefalos.tm1638;

import java.util.Properties;

import org.picocontainer.PicoCompositionException;

import tm1638.Tm1638.Echo;
import base.exception.LoaderException;

import com.github.boukefalos.arduino.exception.ArduinoException;
import com.github.boukefalos.arduino.port.ParsingPort;
import com.github.boukefalos.tm1638.implementation.Local;
import com.github.boukefalos.tm1638.implementation.Remote;

public class Loader extends com.github.boukefalos.arduino.Loader {
	protected static final String PROPERTIES_FILE = "TM1638.properties";

    public Loader(Properties properties) throws LoaderException {
    	super(Local.class, Remote.class, Server.class, properties);
    	pico.addComponent(ParsingPort.getInstance(Echo.class));    	
	}

	public TM1638 getTM1638() throws ArduinoException {
		try {
			return pico.getComponent(TM1638.class);
		} catch (PicoCompositionException e) {
			logger.error("", e);
			throw new ArduinoException("Failed to load");
		}
    }

    public Server getServer() throws ArduinoException {
    	try {
    		return pico.getComponent(Server.class);
		} catch (PicoCompositionException e) {
			logger.error("", e);
			throw new ArduinoException("Failed to load");
		}
    }
}
