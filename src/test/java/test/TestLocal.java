package test;

import tm1638.Tm1638.Color;
import tm1638.Tm1638.Echo;
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

        int i = 123;  
		while (i < 10000) {
        	TM1638.ping(i++);
        	TM1638.setLed(i % 3 == 0 ? Color.GREEN : Color.RED, i % 7);
            Thread.sleep(1000);
        }
	}

	public TestLocal() {
		super();
		start();
	}

	public void input(Echo echo) {
		System.out.println("> " + echo.getMessage() + " " + echo.getId());
	}
}
