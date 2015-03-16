package test;

import java.io.OutputStream;

import beerduino.Beerduino.Echo;
import beerduino.Beerduino.Ping;

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
		OutputStream outputStream = arduino.getOutputStream();
        int i = 123;
        while ( i < 10000) {
            Ping ping = Ping.newBuilder().setId(i++).build();
            System.out.println("writing!");
            ping.writeDelimitedTo(outputStream);
           Thread.sleep(1000);
        }
        arduino.close();	
	}

	public void receive(Echo echo) {
		System.out.println("> " + echo.getMessage() + " " + echo.getId());
	}
}
