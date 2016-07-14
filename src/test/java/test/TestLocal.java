package test;

import com.github.boukefalos.arduino.port.StringPort;
import com.github.boukefalos.tm1638.TM1638;
import com.github.boukefalos.tm1638.implementation.Local;

import base.work.ReflectiveListen;

public class TestLocal extends ReflectiveListen {
    public static void main(String[] args) throws Exception {
        TM1638 TM1638 = new Local(StringPort.getInstance());
        main(TM1638);
    }

    public void input(String input) {
        System.out.println("> " + input);
    }

    public static void main(TM1638 TM1638) throws Exception {
        //TM1638.register(new TestLocal());
        TM1638.start();

        TM1638.construct(8, 9, 7);
        TM1638.setupDisplay(true,  1);
        TM1638.setDisplayToString("Rik", 2, 0);
        Thread.sleep(1000);

        TM1638.setDisplayToDecNumber(123, 0, false);
        
        Thread.sleep(1000);
        // Light up all the green LEDs
        TM1638.setLEDs(0x00ff);
        Thread.sleep(1000);
        TM1638.setLEDs(0x0000);

        // Light up all the red LEDs
        TM1638.setLEDs(0xff00);
        Thread.sleep(1000);
        TM1638.setLEDs(0x0000);

        int i = 0;
        while (i < 10000) {
            //TM1638.setLED(i % 2 == 0 ? Color.GREEN : Color.RED, i % 8);
            //Thread.sleep(500);
            TM1638.ping(i++);
            Thread.sleep(1000);
        }
    }
}
