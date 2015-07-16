package test;

import tm1638.Tm1638.Color;
import base.work.Listen;

import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.implementation.Local;

public class TestLocal extends Listen<Object> {
	public static void main(String[] args) throws Exception {
        TM1638 TM1638 = new Local();
        main(TM1638);
	}

	public static void main(TM1638 TM1638) throws InterruptedException {
		TM1638.register(new TestLocal());
		TM1638.start();
		TM1638.construct(8, 9, 7);
		TM1638.setupDisplay(true,  1);
        int i = 0;
		while (i < 10000) {
            //TM1638.setLED(i % 2 == 0 ? Color.GREEN : Color.RED, i % 8);
			//Thread.sleep(500);
			TM1638.ping(i++);
			Thread.sleep(1000);
        }
	}

	public TestLocal() {
		super();
		start();
	}
}
