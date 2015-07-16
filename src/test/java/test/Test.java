package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import tm1638.Tm1638.Buttons;
import tm1638.Tm1638.Message;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.Pong;
import tm1638.Tm1638.Text;

public class Test {
	public static void main(String[] args) throws Exception {
		Ping ping = Ping.newBuilder().setId(123).build();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ping.writeDelimitedTo(output);
		byte[] buffer = output.toByteArray();
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		Class<?> messageClass = Message.class;
		Method m = messageClass.getMethod("parseDelimitedFrom", InputStream.class);
		Object object = m.invoke(null, input);
		Message message = (Message) object;
		switch (message.getType()) {
			case PONG:
				Pong pong = message.getPong();
				System.out.println(pong.getId());
				break;
			case TEXT:
				Text text = message.getText();
				System.out.println(text.getText());
				break;
			case BUTTONS:
				Buttons buttons = message.getButtons();
				System.out.println(buttons.getButtons());
				break;
		}
	}
}
