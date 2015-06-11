package test;

import test.dummy.DummyEchoReceiver;
import tm1638.Tm1638.Color;

import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.implementation.Localmplementation;

public class TestLocal {
	public void start() throws Exception {		
        int i = 123;
        TM1638 TM1638 = new Localmplementation();
        TM1638.addReceiver(new DummyEchoReceiver());
        TM1638.construct(8, 9, 7);
        while (i < 10000) {
        	TM1638.ping(i++);
        	TM1638.setLed(i % 3 == 0 ? Color.GREEN : Color.RED, i % 7);
            Thread.sleep(1000);
        }
        TM1638.stop();
	}

	public static void main(String[] args) throws Exception {
		new TestLocal().start();
	}
}
