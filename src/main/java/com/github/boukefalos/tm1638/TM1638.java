package com.github.boukefalos.tm1638;

import tm1638.Tm1638.Color;

import com.github.boukefalos.arduino.Arduino;

public interface TM1638 extends Arduino {	
    public void ping(int i);
	public void construct(int dataPin, int clockPin, int strobePin);
	public void clearDisplay();
	public void clearDisplayDigit(int pos, boolean dot);
	public void setDisplay(byte[] values);
	public void setDisplayDigit(int digit, int pos, boolean dot);
	public void setDisplayDigit(int digit, int pos, boolean dot, byte[] font);
	public void setDisplayToBinNumber(int number, int dots, byte[] font);
	public void setDisplayToDecNumber(int number, int dots, boolean leadingZeros);
	public void setDisplayToDecNumber(int number, int dots, boolean leadingZeros, byte[] font);
	public void setDisplayToHexNumber(int number, int dots, boolean leadingZeros);
	public void setDisplayToHexNumber(int number, int dots, boolean leadingZeros, byte[] font);
	public void setDisplayToError();
	public void setDisplayToString(String string, int dots, int pos);
	public void setDisplayToString(String string, int dots, int pos, byte[] font);	
	public void setLED(Color color, int pos);
	public void setLEDs(int led);
	public void setupDisplay(boolean active, int intensity);
}