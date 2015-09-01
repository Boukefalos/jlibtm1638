package test;

import java.util.Properties;

import base.exception.worker.ActivateException;

import com.github.boukefalos.arduino.exception.ArduinoException;
import com.github.boukefalos.tm1638.Loader;
import com.github.boukefalos.tm1638.Server;
import com.github.boukefalos.tm1638.TM1638;

public class TestRemoteImplementation extends TestLocal {
    protected TM1638 tm1638;

    public TestRemoteImplementation(Loader loader) throws ArduinoException {
        tm1638 = loader.getTM1638();
        //tm1638.register(this);
    }

    public void activate() throws ActivateException {
        tm1638.start();
        super.activate();        
    }

    public static void main(Properties localProperties, Properties remoteProperties) throws Exception {
        Loader localLoader = new Loader(localProperties);
        Loader remoteLoader = new Loader(remoteProperties);

        try {
            Server server = localLoader.getServer();    
            TM1638 TM1638 = remoteLoader.getTM1638();
            server.start();
            main(TM1638);
        } catch (ArduinoException e) {
            e.printStackTrace();
            return;
        }
    }
}
