package test.dummy;

import beerduino.Beerduino.Echo;

import com.github.boukefalos.tm1638.EchoReceiver;

public class DummyEchoReceiver implements EchoReceiver {
	public void receive(Echo echo) {
		System.out.println("> " + echo.getMessage() + " " + echo.getId());
	}
}
