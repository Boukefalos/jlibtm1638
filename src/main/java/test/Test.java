package test;

import tm1638.Tm1638.Color;
import beerduino.Beerduino.Echo;

public class Test implements EchoListener {
	public static void main(String[] argv) {
		try {
			new Test().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void start() throws Exception {
		Arduino arduino = new Arduino();
		arduino.connect();
		arduino.addListener(this);
        int i = 123;
        TM1638 TM1638 = new TM1638(arduino);
        TM1638.construct(8, 9, 7);
        while (i < 10000) {
        	TM1638.ping(i++);
        	TM1638.setLed(i % 3 == 0 ? Color.GREEN : Color.RED, i % 7);
            Thread.sleep(1000);
        }
        arduino.close();	
	}

	public void receive(Echo echo) {
		System.out.println("> " + echo.getMessage() + " " + echo.getId());
	}
}
