package midi.gui;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import midi.ctrl.DevListener;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Device extends MyGUI {

	public JComboBox cbDeviceIn;
    public JComboBox cbDeviceOut;
    public JPanel panel;

    private static Device instance = null;

    synchronized public static Device getInstance() {
    	if (instance == null) try {
    		instance = new Device();
    		instance.init();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}

    	return instance;
    }

    private Device() {}

    // init MIDI input/output devices
	protected void init() throws MidiUnavailableException {

		panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    	initComboDevices();

        JLabel midiIn  = new JLabel("MIDI IN");
        JLabel midiOut = new JLabel("MIDI OUT");
        JButton apply = new JButton("Start");

		DevListener actionListener = new DevListener();
        apply.addActionListener(actionListener);

        midiIn.setBounds(12, 68, 50, 20);
        cbDeviceIn.setBounds(51, 68, 145, 20);
        apply.setBounds(200, 68, 57, 20);
        cbDeviceOut.setBounds(261, 68, 145, 20);
        midiOut.setBounds(410, 68, 50, 20);
        panel.add(midiIn);
        panel.add(cbDeviceIn);
        panel.add(apply);
        panel.add(cbDeviceOut);
        panel.add(midiOut);
	}

	private void initComboDevices() throws MidiUnavailableException {
        Info[] infos = MidiSystem.getMidiDeviceInfo();
        List<Info> inDevices  = new ArrayList<Info>();
        List<Info> outDevices = new ArrayList<Info>();

        for (Info info: infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            if (device.getMaxReceivers() == 0) {
                inDevices.add(info);
            } else if (device.getMaxTransmitters() == 0) {
                outDevices.add(info);
            } else {
                inDevices.add(info);
                outDevices.add(info);
            }
        }

        cbDeviceIn  = new JComboBox(inDevices.toArray());
        cbDeviceOut = new JComboBox(outDevices.toArray());
    }
}
