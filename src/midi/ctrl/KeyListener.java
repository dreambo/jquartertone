package midi.ctrl;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import midi.gui.Keyboard;
import midi.gui.Memory;
import midi.gui.key.Key;

public class KeyListener implements MouseListener, ComponentListener {

	private Memory memory;
	private Keyboard keyboard;
    private Color background;

    public KeyListener() {
    	memory     = Memory.getInstance();
    	keyboard   = Keyboard.getInstance();
        background = keyboard.panel.getBackground();
	}

	public void mouseClicked(MouseEvent e) {

        Key aKey = getKey(e.getPoint());

        if (null != aKey) {
            aKey.setNoteOn(!aKey.noteOn);
            keyboard.notifyObs(keyboard.getKeysStatus());
        }

        // disable any memory button
        if (memory.prevB != null) {
        	memory.prevB.setBackground(background);
            memory.panel.repaint();
        }
        if (keyboard != null) {
        	keyboard.panel.repaint();
        }
    }

    private Key getKey(Point point) {
        for (Key key: keyboard.keys) {
            if (key.contains(point)) {
            	return key;
            }
        }

        return null;
    }

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void componentResized(ComponentEvent e) {
		keyboard.updatePosX(e.getComponent().getWidth());
	}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
}
