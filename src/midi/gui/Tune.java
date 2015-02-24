package midi.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;

import midi.ctrl.TunListener;

public class Tune extends MyGUI {

    public JSlider sTranspose;
    public JSlider sOctave;
    public JSlider arSlider;

    private static Tune instance = null;

    synchronized public static Tune getInstance() {
    	if (instance == null) {
    		instance = new Tune();
    		instance.init();
    	}

    	return instance;
    }

    private Tune() {}

	protected void init() {

    	panel = new JPanel();
    	panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    	TunListener listener = new TunListener();

		// arabic tune
        arSlider = createSlider("Arabic tune", 5, listener);
        arSlider.setBounds(10, 10, 120, 50);

        // transpose
        sTranspose = createSlider("Transpose", 11, listener);
        sTranspose.setBounds(145, 10, 180, 50);

        // octave
        sOctave = createSlider("Octave", 3, listener);
        sOctave.setBounds(340, 10, 120, 50);

        // disable sliders
        arSlider.setEnabled(false);
        sTranspose.setEnabled(false);
        sOctave.setEnabled(false);
	}

	private JSlider createSlider(final String name, int max, ChangeListener listener) {
        JSlider slider = new JSlider(-max, max);
        slider.addChangeListener(listener);
        slider.setToolTipText(name);
        slider.setBorder(BorderFactory.createTitledBorder(name + "=0"));
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        panel.add(slider);

        return slider;
    }
}
