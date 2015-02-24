/*
 * Created on 25 janv. 07
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package midi.util;

import java.util.Hashtable;
import java.util.Map;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

public class Messages {

    private static final int[] msgInt     = {0xF1, 0xF2, 0xF3, 0xF6, 0xF7, 0xF8, 0xFA, 0xFB, 0xFC, 0xFE, 0xFF, 0x80, 0x90, 0xA0, 0xB0, 0xC0, 0xD0, 0xE0};
    private static final String[] msgText = {"MIDI_TIME_CODE", "SONG_POSITION_POINTER", "SONG_SELECT", "TUNE_REQUEST", "END_OF_EXCLUSIVE", "TIMING_CLOCK", "START", "CONTINUE", "STOP", "ACTIVE_SENSING", "SYSTEM_RESET", "NOTE_OFF", "NOTE_ON", "POLY_PRESSURE", "CONTROL_CHANGE", "PROGRAM_CHANGE", "CHANNEL_PRESSURE", "PITCH_BEND"};
    private static final Map<Integer, String> coms;

    static {
        coms = new Hashtable<Integer, String>();
        for (int i = 0; i < msgInt.length; i++) {
            coms.put(msgInt[i], msgText[i]);
        }
    }

    public static void showMessage(MidiMessage msg) {
        if (msg instanceof ShortMessage) {
            ShortMessage sMsg = (ShortMessage) msg;
            
            System.out.println(sMsg.getChannel() + "-" + coms.get(sMsg.getCommand()));
        } else if (msg instanceof SysexMessage) {
            // TODO print sysex
            System.out.println("sysex message: " + msg);
        }
    }
}
