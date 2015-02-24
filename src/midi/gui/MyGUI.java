package midi.gui;

import java.util.Observable;

import javax.swing.JPanel;

abstract public class MyGUI extends Observable {

    public JPanel panel;

	public void notifyObs(Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	abstract protected void init() throws Exception;
}
