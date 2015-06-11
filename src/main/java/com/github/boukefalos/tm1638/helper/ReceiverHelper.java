package com.github.boukefalos.tm1638.helper;

import java.util.ArrayList;

import beerduino.Beerduino.Echo;

import com.github.boukefalos.tm1638.EchoReceiver;
import com.google.protobuf.InvalidProtocolBufferException;

public class ReceiverHelper {

	public static void receive(ArrayList<EchoReceiver> echoReceiverList, byte[] buffer) {
		try {
			Echo echo = Echo.parseFrom(buffer);
	        for (EchoReceiver echoReceiver : echoReceiverList) {
	        	echoReceiver.receive(echo);
	        }
		} catch (InvalidProtocolBufferException e) {}
	}
}
