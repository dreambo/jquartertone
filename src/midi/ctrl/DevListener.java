package midi.ctrl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.swing.JButton;

import midi.gui.Device;
import midi.gui.Keyboard;
import midi.gui.Memory;
import midi.gui.Tune;
import midi.receiver.ArabicReceiver;

public class DevListener implements ActionListener {

	private Device device;
	private Memory memory;
	private Keyboard keyboard;
	private Tune tune;
    private Color background;

    private MidiDevice inDevice;
    private MidiDevice outDevice;
    private ArabicReceiver arabicReceiver;

    public DevListener() {
    	device	 = Device.getInstance();
    	memory	 = Memory.getInstance();
    	keyboard = Keyboard.getInstance();
    	tune = Tune.getInstance();
    	background = device.panel.getBackground();
    	arabicReceiver = ArabicReceiver.getInstance();

    	addObservers();
	}

    public void actionPerformed(ActionEvent e) {

    	JButton bouton = (JButton) e.getSource();

		if (bouton.getBackground() == Color.pink) {

			close();
    		device.cbDeviceIn.setEnabled(true);
    		device.cbDeviceOut.setEnabled(true);

    		bouton.setBackground(background);
    		bouton.setText("Start");

    		// disable sliders
            tune.arSlider.setEnabled(false);
            tune.sTranspose.setEnabled(false);
            tune.sOctave.setEnabled(false);

    	} else {
	        Info inInfo  = (Info) device.cbDeviceIn.getSelectedItem();
	        Info outInfo = (Info) device.cbDeviceOut.getSelectedItem();

	        System.out.println(inInfo + " --> " + outInfo);
	
	        if (inInfo != null && outInfo != null) try {
	            // get the in/out devices
	        	close();
	        	inDevice  = MidiSystem.getMidiDevice(inInfo);
	        	outDevice = MidiSystem.getMidiDevice(outInfo);
	
	            // redirect in to out
	            if (inDevice != null && outDevice != null) {
	            	inDevice.open();
	            	outDevice.open();

	            	arabicReceiver.setReceiver(outDevice.getReceiver());

	                // plug the receiver to the transmitter
	                inDevice.getTransmitter().setReceiver(arabicReceiver);
	
	        		bouton.setBackground(Color.pink);
	        		bouton.setText("Stop");

	        		device.cbDeviceIn.setEnabled(false);
	        		device.cbDeviceOut.setEnabled(false);
	        		// enable sliders
	                tune.arSlider.setEnabled(true); tune.arSlider.setValue(0);
	                tune.sTranspose.setEnabled(true); tune.sTranspose.setValue(0);
	                tune.sOctave.setEnabled(true); tune.sOctave.setValue(0);
	            }
	        } catch(Exception ex) {
	        	ex.printStackTrace();
	        	close();
	        }
    	}

		keyboard.reset();
		if (memory.prevB != null) {
			memory.prevB.setBackground(background);
		}
		keyboard.notifyObs(keyboard.getKeysStatus());

		keyboard.panel.repaint();
    }

    private void addObservers() {

    	// add observers
        device.addObserver(arabicReceiver);
        keyboard.addObserver(arabicReceiver);
        memory.addObserver(arabicReceiver);
        tune.addObserver(arabicReceiver);
	}

	private void close() {

		arabicReceiver.close();

		if (inDevice != null) {
			inDevice.close();
			inDevice = null;
		}
		
		if (outDevice != null) {
			outDevice.close();
			outDevice = null;
		}
	}
}
