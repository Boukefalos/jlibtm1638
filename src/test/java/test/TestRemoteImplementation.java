package test;

import java.util.Properties;

import base.exception.worker.ActivateException;

import com.github.boukefalos.tm1638.Loader;
import com.github.boukefalos.tm1638.Server;
import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.exception.ArduinoException;

public class TestRemoteImplementation extends TestLocal {
	protected TM1638 tm1638;

	public TestRemoteImplementation(Loader loader) throws ArduinoException {
		tm1638 = loader.getTM1638();
		tm1638.register(this);
	}

    public void activate() throws ActivateException {
		tm1638.start();
        super.activate();        
    }
	
	public static void main(Properties localProperties, Properties remoteProperties) throws Exception {
		Loader localLoader = new Loader(localProperties);
		Loader remoteLoader = new Loader(remoteProperties);

		Server server = localLoader.getServer();
		server.start();

		TM1638 TM1638 = remoteLoader.getTM1638();
		main(TM1638);
	}
}
