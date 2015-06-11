package com.github.boukefalos.tm1638;

import com.github.boukefalos.tm1638.helper.SenderHelper;

import tm1638.Tm1638.Color;
import base.sender.Sender;

public abstract class TM1638 implements Sender {
	public void ping(int id) {
		SenderHelper.ping(this, id);
	}

	public void setLed(Color color, int pos) {
		SenderHelper.setLed(this, color, pos);
	}

	public void construct(int dataPin, int clockPin, int strobePin) {
		SenderHelper.construct(this, dataPin, clockPin, strobePin);
	}

	public void start() {}
	public void stop() {}

	public abstract void addReceiver(EchoReceiver receiver);
}