package com.github.boukefalos.tm1638;

import tm1638.Tm1638.Color;

import com.github.boukefalos.arduino.Arduino;

public interface TM1638 extends Arduino {
	public void construct(int dataPin, int clockPin, int strobePin);
	public void ping(int i);
	public void setLed(Color color, int pos);
}