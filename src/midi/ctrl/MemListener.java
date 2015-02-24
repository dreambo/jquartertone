package midi.ctrl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import midi.gui.Keyboard;
import midi.gui.Memory;

public class MemListener implements ActionListener, ItemListener {

	private Memory memory;
	private Keyboard keyboard;
    private Color background;

    public MemListener() {
    	memory = Memory.getInstance();
    	keyboard = Keyboard.getInstance();
	}

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        boolean noNotify = false;

        if (button == memory.resetB) {
            if (memory.prevB != null && memory.prevB != button) {
            	memory.prevB.setBackground(background);
            }
            keyboard.reset();

        } else if (memory.mem.isSelected()) {
        	memory.mem.setSelected(false);
        	memory.mems[Integer.parseInt(button.getText()) - 1] = keyboard.getKeysStatus();
            button.setBackground(Color.pink);
            if (memory.prevB != null && memory.prevB != button) {
            	memory.prevB.setBackground(background);
            }
            memory.prevB = button;
            noNotify = true;

        } else { // memory buttons
            if (button.getBackground() == Color.pink) {
                button.setBackground(background);
                keyboard.reset();
            } else {
                button.setBackground(Color.pink);
                keyboard.setKeys(memory.mems[Integer.parseInt(button.getText()) - 1]);
            }

            if (memory.prevB != null && memory.prevB != button) {
            	memory.prevB.setBackground(background);
            }

            memory.prevB = button;
        }

        memory.panel.repaint();
        keyboard.panel.repaint();

        if (!noNotify) {
            keyboard.notifyObs(keyboard.getKeysStatus());
        }
    }

	public void itemStateChanged(ItemEvent e) {
        memory.notifyObs(memory.arDisableCB.isSelected());
	}
}
