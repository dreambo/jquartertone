package midi.gui;

import static midi.gui.key.Metric.interKeysX;
import static midi.gui.key.Metric.posX;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import midi.ctrl.KeyListener;
import midi.gui.key.Key;
import midi.gui.key.ListKey;

public class Keyboard extends MyGUI {

	public ListKey keys;
    public JPanel panel;
    private static Keyboard instance = null;

    synchronized public static Keyboard getInstance() {
    	if (instance == null) {
    		instance = new Keyboard();
    		instance.init();
    	}

    	return instance;
    }

    private Keyboard() {}

    protected void init() {

    	keys  = new ListKey(3, posX);						//first  half-octave : 3 white keys 
    	final ListKey keys2 = new ListKey(4, posX + 3*(2*interKeysX));	//second half-octave : 4 white keys

    	keys.addAll(keys2); // add the second half octave
        //arabicKeys = keys.toArray(new Key[0]);

    	panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;

               	keys.paint(g2);
            }
        };
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        KeyListener keyListener = new KeyListener();
        panel.addMouseListener(keyListener);
        panel.addComponentListener(keyListener);
    }

    public boolean[] getKeysStatus() {
        boolean[] status = new boolean[12];
        for (Key key: keys) {
            status[keys.indexOf(key)] = key.noteOn;
        }

        return status;
    }

    public void reset() {
    	setKeys(new boolean[12]);
    }

    public void setKeys(boolean[] bs) {
    	for (Key key: keys) {
            key.setNoteOn(bs[keys.indexOf(key)]);
        }
    }

	public void updatePosX(int width) {
		keys.updatePosX(width);
		panel.repaint();
	}
}
