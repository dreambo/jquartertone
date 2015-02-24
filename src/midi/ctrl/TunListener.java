package midi.ctrl;

import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import midi.gui.Tune;

public class TunListener implements ChangeListener {

	private Tune tune;

    public TunListener() {
    	this.tune = Tune.getInstance();
	}

    // sliders change : Arabic ratio, transpose and octave
	public void stateChanged(ChangeEvent e) {

        JSlider slider = (JSlider) e.getSource();
        TitledBorder border = (TitledBorder) slider.getBorder();
        String title = border.getTitle();
        border.setTitle(title.replaceAll("=.*", "=" + slider.getValue()));
        slider.setBorder(border);

        // notify observers for the changes
        Integer[] values = new Integer[2];
        values[0] = (slider == tune.arSlider ? slider.getValue() : tune.sTranspose.getValue());
        values[1] = (slider == tune.arSlider ? null : tune.sOctave.getValue());
        tune.notifyObs(values);
	}
}
