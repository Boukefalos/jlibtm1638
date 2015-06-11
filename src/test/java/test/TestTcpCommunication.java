package test;

import java.util.Properties;

import test.dummy.DummyEchoReceiver;
import tm1638.Tm1638.Color;
import base.work.Work;

import com.github.boukefalos.tm1638.Loader;
import com.github.boukefalos.tm1638.TM1638;

public class TestTcpCommunication {
	public static void main(String[] args) {
		try {
			Properties localProperties = new Properties();
			localProperties.setProperty("implementation", "local");
			localProperties.setProperty("server", "true");
			localProperties.setProperty("server.port", "8883");
			localProperties.setProperty("server.protocol", "tcp");

			Properties remoteProperties = new Properties();
			remoteProperties.setProperty("implementation", "remote");
			remoteProperties.setProperty("protocol", "tcp");
			remoteProperties.setProperty("remote.host", "localhost");
			remoteProperties.setProperty("remote.port", "8883");

			Loader localLoader = new Loader(localProperties);
			Loader remoteLoader = new Loader(remoteProperties);

			TM1638 localTM1638 = localLoader.getTM1638();
			TM1638 remoteTM1638 = remoteLoader.getTM1638();

			Work server = localLoader.getServer();
			server.start();			

			remoteTM1638.addReceiver(new DummyEchoReceiver());
			remoteTM1638.start();
			remoteTM1638.construct(8, 9, 7);
			int i = 123;
			while (i < 10000) {
	        	remoteTM1638.ping(i++);
	        	remoteTM1638.setLed(i % 3 == 0 ? Color.GREEN : Color.RED, i % 7);
	            Thread.sleep(1000);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
