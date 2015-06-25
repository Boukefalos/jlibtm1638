package test;

import java.util.Properties;

import com.github.boukefalos.arduino.exception.ArduinoException;

public class TestTcpImplementation {
	public static void main(String[] args) throws Exception {
		Properties localProperties = new Properties();
		localProperties.setProperty("implementation", "local");
		localProperties.setProperty("server", "true");
		localProperties.setProperty("server.direct", "false");
		localProperties.setProperty("server.port", "8883");
		localProperties.setProperty("server.protocol", "tcp");

		Properties remoteProperties = new Properties();
		remoteProperties.setProperty("implementation", "remote");
		remoteProperties.setProperty("protocol", "tcp");
		remoteProperties.setProperty("remote.host", "localhost");
		remoteProperties.setProperty("remote.port", "8883");

		try {
			TestRemoteImplementation.main(localProperties, remoteProperties);
		} catch (ArduinoException e) {
			System.err.println(e.getMessage());
		}
	}
}