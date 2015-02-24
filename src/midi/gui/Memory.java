package midi.gui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import midi.ctrl.MemListener;

public class Memory extends MyGUI {

    public JButton prevB, resetB, memB[] = new JButton[7];
    public JCheckBox mem = new JCheckBox("Mem");
    public JCheckBox arDisableCB = new JCheckBox("Disable");
    public boolean[] mems[] = new boolean[7][12];
    public JPanel panel = new JPanel();

    private static Memory instance = null;

    synchronized public static Memory getInstance() {
    	if (instance == null) {
    		instance = new Memory();
    		instance.init();
    	}

    	return instance;
    }

    private Memory() {}

    protected void init() {

    	panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    	mem.setBounds(60, 55, 45, 40);
        MemListener listener = new MemListener();
        resetB = createButton("0", true, listener);
        panel.add(mem);
        resetB.setMnemonic(KeyEvent.VK_0);
        resetB.setBounds(12, 65, 39, 20);

        for (int i = 0; i < memB.length; i++) {
            memB[i] = createButton((i + 1) + "", true, listener);
            memB[i].setBounds(105 + i*42, 65, 39, 20);
            memB[i].setMnemonic(KeyEvent.VK_1 + i);
        }

        arDisableCB.addItemListener(listener);
        arDisableCB.setBounds(405, 55, 60, 40);
        panel.add(arDisableCB);

        mems[0][4 ] = mems[0][11] = true;
        mems[1][4 ] = mems[1][9 ] = true;
        mems[2][1 ] = mems[2][6 ] = true;
        mems[3][6 ] = mems[3][11] = true;
        mems[4][1 ] = mems[4][8 ] = true;
        mems[5][4 ] = true;
        mems[6][11] = true;
    }

    private JButton createButton(String name, boolean state, ActionListener actionListener) {
        JButton b = new JButton(name);
        b.setEnabled(state);
        b.addActionListener(actionListener);
        panel.add(b);

        return b;
    }
}
