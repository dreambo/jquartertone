
package midi.receiver;

import java.util.Observable;
import java.util.Observer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import midi.util.Messages;

/*
 * Created on 22 janv. 07
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class ArabicReceiver implements Receiver, Observer {

    private Receiver receiver;

    private boolean[] arTones = new boolean[12];
    private float arRatio = 0.75f;
    private byte arChannel = 15;
    private int transpose;
    private boolean disable;

    private static ArabicReceiver instance = null;

    synchronized public static ArabicReceiver getInstance() {
    	if (instance == null) {
    		instance = new ArabicReceiver();
    	}

    	return instance;
    }

    private ArabicReceiver() {}

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
        init();
    }

    public void close() {
        if (receiver != null) {
        	receiver.close();
        	receiver = null;
        }
    }

    public void send(MidiMessage midiMessage, long l) {

        if (!disable && (midiMessage instanceof ShortMessage)) {
            ShortMessage shortMessage = (ShortMessage) midiMessage;
            int channel = shortMessage.getChannel();

            // skip drum channel (9) and arabic channel
            if (channel != 9 && channel != arChannel) {
                int command = shortMessage.getCommand();
                try {
                    if (command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF) {
                        ShortMessage returnMessage = new ShortMessage();
                        int note = shortMessage.getData1();
                        int chan = (arTones[note % 12] ? arChannel : channel);

                        returnMessage.setMessage(command, chan, note + transpose , shortMessage.getData2());
                        midiMessage = returnMessage;

                    } else if (command == ShortMessage.PITCH_BEND) {
                        ShortMessage returnMessage = new ShortMessage();
                        returnMessage.setMessage(command, arChannel, Math.round(arRatio * shortMessage.getData1()), Math.round(arRatio * shortMessage.getData2()));
                        receiver.send(returnMessage, l);

                    } else if (command != 0xF0) {
                        ShortMessage returnMessage = new ShortMessage();
                        returnMessage.setMessage(command, arChannel, shortMessage.getData1(), shortMessage.getData2());
                        receiver.send(returnMessage, l);
                        Messages.showMessage(returnMessage);
                    }

                } catch (InvalidMidiDataException e) {e.printStackTrace();}
            }
        }

        receiver.send(midiMessage, l);
    }


    /**
     * fix the quarter tone in the channel arChannel
     */
    private void init() {
    	if (receiver != null) {
	        ShortMessage returnMessage = new ShortMessage();

	        try {
	            returnMessage.setMessage(ShortMessage.PITCH_BEND, arChannel, 0, Math.round(arRatio * 0x40));
	        } catch(InvalidMidiDataException e) {}

	        receiver.send(returnMessage, 0);
    	}
    }


    /**
     * this will be called when a GUI change its values
     */
    public void update(Observable o, Object arg) {

    	if (receiver != null) {
	        if (arg instanceof boolean[]) { // Arabic scale
	            System.out.println("Update " + o + ": " + arg);
	            arTones = (boolean[]) arg;

	        } else if (arg instanceof Boolean) { // disable/enable Arabic scale (zero button)
	            System.out.println("Update " + o + ": " + arg);
	            disable = (Boolean) arg;

	        } else if (arg instanceof Integer[]) { // tune)
	            System.out.println("Update " + o + ": " + arg);
	            Integer[] values = (Integer[]) arg;

	            if (values[1] == null) { // Arabic tune
	                arRatio = 0.75f + values[0]/24f;
	                System.out.println("Arabic tune:" + arRatio);
	            } else { // transpose and/or octave
	                transpose = values[0] + 12*values[1];
	                System.out.println("transpose:" + transpose);
	            }

	            init();
	       	}
        }
    }
}
