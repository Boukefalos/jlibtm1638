package com.github.boukefalos.tm1638;

import java.util.Properties;

import org.picocontainer.Parameter;
import org.picocontainer.parameters.ConstantParameter;

import base.loader.AbstractLoader;
import base.work.Work;

import com.github.boukefalos.tm1638.implementation.Localmplementation;
import com.github.boukefalos.tm1638.implementation.TcpImplementation;
import com.github.boukefalos.tm1638.implementation.UdpImplementation;
import com.github.boukefalos.tm1638.server.TM1638Server;
import com.github.boukefalos.tm1638.server.TM1638TcpServer;
import com.github.boukefalos.tm1638.server.TM1638UdpServer;

public class Loader extends AbstractLoader {
    protected static final String PROPERTIES_FILE = "TM1638.properties";

	public Loader(Properties properties) {
		super();
	
		/* Add implementation */
		switch (properties.getProperty("implementation")) {
			case "local":
				pico.addComponent(Localmplementation.class);
				break;				
			case "remote":
				//pico.addComponent(Remote.class);
				break;
		}

		/* Add protocol */
		if (properties.getProperty("protocol") != null) {
			switch (properties.getProperty("protocol")) {
				case "tcp":
					pico.addComponent(TcpImplementation.class, TcpImplementation.class, new Parameter[]{
						new ConstantParameter(properties.getProperty("remote.host")),
						new ConstantParameter(Integer.valueOf(properties.getProperty("remote.port")))});
					break;
				case "udp":
					pico.addComponent(UdpImplementation.class, UdpImplementation.class, new Parameter[] {
						new ConstantParameter(properties.getProperty("remote.host")),
						new ConstantParameter(Integer.valueOf(properties.getProperty("remote.port")))});
					break;
			}
		}

		/* Add server */
		if (properties.getProperty("server") != null) {
			switch (properties.getProperty("server.protocol")) {
				case "tcp":
					pico.addComponent(TM1638TcpServer.class, TM1638TcpServer.class, new Parameter[]{
						new ConstantParameter(getTM1638()),
						new ConstantParameter(Integer.valueOf(properties.getProperty("server.port")))});
					break;
				case "udp":
					pico.addComponent(TM1638UdpServer.class, TM1638UdpServer.class, new Parameter[]{
						new ConstantParameter(getTM1638()),
						new ConstantParameter(Integer.valueOf(properties.getProperty("server.port")))});
			}			
		}
	}

    public TM1638 getTM1638() {
    	return pico.getComponent(TM1638.class);
    }

    public Work getServer() {
    	return (Work) pico.getComponent(TM1638Server.class);
    }
}
