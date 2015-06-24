package com.github.boukefalos.tm1638;

import java.util.Properties;

import org.picocontainer.Parameter;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import base.exception.LoaderException;
import base.loader.AbstractLoader;

import com.github.boukefalos.tm1638.exception.ArduinoException;
import com.github.boukefalos.tm1638.implementation.Local;
import com.github.boukefalos.tm1638.implementation.Remote;

public class Loader extends AbstractLoader<Loader> {
    protected static final String PROPERTIES_FILE = "TM1638.properties";

	public Loader(Properties properties) throws LoaderException {
		super();

		/* Add implementation */
		switch (properties.getProperty("implementation")) {
			case "local":
				pico.addComponent(TM1638.class, Local.class);
				break;				
			case "remote":
				pico.addComponent(TM1638.class, Remote.class);

				/* Add remote duplex implementation */
				try {
					String protocol = properties.getOrDefault("server.protocol", "tcp").toString();
					String implementation = properties.getOrDefault("tcp.implementation", "socket").toString();
					String host = properties.getProperty("remote.host");
					int port = Integer.valueOf(properties.getProperty("remote.port"));
					addClientDuplex(protocol, implementation, host, port);
				} catch (NumberFormatException e) {
					throw new LoaderException("Failed to parse remote.port");
				}				
				break;
		}

		/* Add server */
		if (properties.getProperty("server") != null) {
			boolean direct = Boolean.parseBoolean(properties.getOrDefault("server.direct", Server.DIRECT).toString());
			pico.addComponent(Server.class, Server.class, new Parameter[] {
				new ComponentParameter(),
				new ComponentParameter(),
				new ConstantParameter(direct)});

			/* Add server forwarder implementation */
			try {
				String protocol = properties.getOrDefault("server.protocol", "tcp").toString();
				String implementation = properties.getOrDefault("tcp.implementation", "socket").toString();
				int port = Integer.valueOf(properties.getProperty("server.port"));
				addServerDuplex(protocol, implementation, port);
			} catch (NumberFormatException e) {
				throw new LoaderException("Failed to parse server.port");
			}
		}
	}

	public TM1638 getTM1638() throws ArduinoException {
		try {
			return pico.getComponent(TM1638.class);
		} catch (PicoCompositionException e) {
			throw new ArduinoException("Failed to load");
		}
    }

    public Server getServer() throws ArduinoException {
    	try {
    		return pico.getComponent(Server.class);
		} catch (PicoCompositionException e) {
			throw new ArduinoException("Failed to load");
		}
    }
}
