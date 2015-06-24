package com.github.boukefalos.tm1638;

import com.github.boukefalos.tm1638.exception.ArduinoException;

import tm1638.Tm1638.Color;
import base.Control;
import base.work.Listen;

public interface TM1638 extends Control {
	public void register(Listen<Object> listen);
	public void remove(Listen<Object> listen);

	public void send(byte[] buffer) throws ArduinoException;

	public void construct(int dataPin, int clockPin, int strobePin);
	public void ping(int i);
	public void setLed(Color color, int pos);
}