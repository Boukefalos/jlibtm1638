package com.github.boukefalos.tm1638;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import tm1638.Tm1638.ClearDisplayDigit;
import tm1638.Tm1638.Color;
import tm1638.Tm1638.Command;
import tm1638.Tm1638.Command.Type;
import tm1638.Tm1638.Construct;
import tm1638.Tm1638.Message;
import tm1638.Tm1638.Ping;
import tm1638.Tm1638.SetDisplay;
import tm1638.Tm1638.SetDisplayDigit;
import tm1638.Tm1638.SetDisplayToNumber;
import tm1638.Tm1638.SetDisplayToString;
import tm1638.Tm1638.SetDisplayToString.Builder;
import tm1638.Tm1638.SetLED;
import tm1638.Tm1638.SetLEDs;
import tm1638.Tm1638.SetupDisplay;
import base.work.Listen;

import com.github.boukefalos.arduino.AbstractArduino;
import com.google.protobuf.ByteString;

public abstract class AbstractTM1638 extends AbstractArduino implements TM1638 {
    public void input(Message message) {
        //System.out.println(message);
        Object object;
        switch (message.getType()) {
            case PONG:
                object = message.getPong();                
                break;
            case TEXT:
                object = message.getText();                
                break;
            case BUTTONS:
                object = message.getButtons();                
                break;
            default:
                return;
        }
        for (Listen<Object> listen : listenList) {
            listen.add(object);
        }
    }

    public void input(String input) {
        for (Listen<Object> listen : listenList) {
            listen.add(input);
        }
    }

    public void command(Command command) {
        //System.out.println(command.toString());
        //System.out.println(command.toString().length());
        ByteArrayOutputStream output = new ByteArrayOutputStream(BUFFER_SIZE);        
        try {
            command.writeDelimitedTo(output);
            byte[] buffer = output.toByteArray();
            //System.out.println("[" + new String(buffer).trim() + "]");
            //System.out.println(buffer.length);
            send(buffer);
        } catch (IOException e) {
            logger.error("Failed to send command");
        }
    }

    public void ping(int id) {
        command(Command.newBuilder()
            .setType(Type.PING)
            .setPing(Ping.newBuilder()
                .setId(id)).build());
    }

    public void construct(int dataPin, int clockPin, int strobePin) {
        command(Command.newBuilder()
            .setType(Type.CONSTRUCT)
            .setConstruct(
                Construct.newBuilder()
                    .setDataPin(dataPin)
                    .setClockPin(clockPin)
                    .setStrobePin(strobePin).build()).build());    
    }

    public void clearDisplay() {
        command(Command.newBuilder()
            .setType(Type.CLEAR_DISPLAY).build());    
    }

    public void clearDisplayDigit(int pos, boolean dot) {
        command(Command.newBuilder()
            .setType(Type.CLEAR_DISPLAY_DIGIT)
            .setClearDisplayDigit(
                ClearDisplayDigit.newBuilder()
                    .setPos(pos)
                    .setDot(dot).build()).build());
    }

    public void setDisplay(byte[] values) {
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY)
            .setSetDisplay(
                SetDisplay.newBuilder()
                    .setValues(ByteString.copyFrom(values)).build()).build());    
    }

    public void setDisplayDigit(int digit, int pos, boolean dot) {
        setDisplayDigit(digit, pos, dot, null);
    }

    public void setDisplayDigit(int digit, int pos, boolean dot, byte[] font) {
        tm1638.Tm1638.SetDisplayDigit.Builder builder = SetDisplayDigit.newBuilder()
            .setDigit(digit)
            .setPos(pos)
            .setDot(dot);
        boolean has_font = font != null;
        builder.setHasFont(has_font);
        if (has_font) {
            builder.setFont(ByteString.copyFrom(font));
        }
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY_DIGIT)
            .setSetDisplayDigit(builder.build()).build());    
    }

    public void setDisplayToBinNumber(int number, int dots) {
        setDisplayToBinNumber(number, dots, null);
    }

    public void setDisplayToBinNumber(int number, int dots, byte[] font) {
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY_TO_BIN_NUMBER)
            .setSetDisplayToNumber(
                SetDisplayToNumber.newBuilder()
                    .setNumber(number)
                    .setDots(dots)
                    .setFont(ByteString.copyFrom(font)).build()).build());
    }

    public void setDisplayToDecNumber(int number, int dots, boolean leadingZeros) {
        setDisplayToDecNumber(number, dots, leadingZeros, null);
    }

    public void setDisplayToDecNumber(int number, int dots, boolean leadingZeros, byte[] font) {
        tm1638.Tm1638.SetDisplayToNumber.Builder builder = SetDisplayToNumber.newBuilder()
            .setNumber(number)
            .setDots(dots)
            .setLeadingZeros(leadingZeros);
        boolean has_font = font != null;
        builder.setHasFont(has_font);
        if (has_font) {
            builder.setFont(ByteString.copyFrom(font));
        }
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY_TO_DEC_NUMBER)
            .setSetDisplayToNumber(builder.build()).build());
    }

    public void setDisplayToHexNumber(int number, int dots,    boolean leadingZeros) {
        setDisplayToHexNumber(number, dots, leadingZeros, null);
    }

    public void setDisplayToHexNumber(int number, int dots,    boolean leadingZeros, byte[] font) {
        tm1638.Tm1638.SetDisplayToNumber.Builder builder = SetDisplayToNumber.newBuilder()
            .setNumber(number)
            .setDots(dots)
            .setLeadingZeros(leadingZeros);
        boolean has_font = font != null;
        builder.setHasFont(has_font);
        if (has_font) {
            builder.setFont(ByteString.copyFrom(font));
        }
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY_TO_HEX_NUMBER)
            .setSetDisplayToNumber(
                builder.build()).build());
    }

    public void setDisplayToError() {
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY_TO_ERROR).build());
    }

    public void setDisplayToString(String string, int dots, int pos) {
        setDisplayToString(string, dots, pos, null);
    }

    public void setDisplayToString(String string, int dots, int pos, byte[] font) {
        Builder builder = SetDisplayToString.newBuilder()
            .setString(string)
            .setDots(dots)
            .setPos(pos);
        boolean has_font = font != null;
        builder.setHasFont(has_font);
        if (has_font) {
            builder.setFont(ByteString.copyFrom(font));
        }
        if (has_font) {
            builder.setFont(ByteString.copyFrom(font));
        }
        command(Command.newBuilder()
            .setType(Type.SET_DISPLAY_TO_STRING)
            .setSetDisplayToString(
                builder.build()).build());
    }

    public void setLED(Color color, int pos) {
        command(Command.newBuilder()
            .setType(Type.SET_LED)
            .setSetLED(
                SetLED.newBuilder()
                    .setColor(color)
                    .setPos(pos).build()).build());
    }

    public void setLEDs(int led) {
        command(Command.newBuilder()
            .setType(Type.SET_LEDS)
            .setSetLEDs(
                SetLEDs.newBuilder()
                    .setLed(led).build()).build());
    }

    public void setupDisplay(boolean active, int intensity) {
        command(Command.newBuilder()
            .setType(Type.SETUP_DISPLAY)
            .setSetupDisplay(
                SetupDisplay.newBuilder()
                    .setActive(active)
                    .setIntensity(intensity).build()).build());
    }
}
