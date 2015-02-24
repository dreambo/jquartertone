/*
 * Created on 24 janv. 07
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package midi.gui.key;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Black and white keys or notes on the piano.
 */
public class Key extends Rectangle {

	private static final long serialVersionUID = 1L;

	public boolean noteOn = false;

    // int kNum;
    private final static Color colorOn = Color.pink;
    private Color colorOff;

    //public Key(int x, int y, int width, int height, int num, Color colorOff)
    public Key(int x, int y, int width, int height, Color colorOff) {

    	super(x, y, width, height);
        // kNum = num;
        this.colorOff = colorOff;
    }

    public void paint(Graphics2D g) {

    	g.setColor(noteOn ? colorOn : colorOff);
        g.fill(this);
        g.setColor(Color.black);
        g.draw(this);
    }

    public void setNoteOn(boolean note) {
        noteOn = note;
    }
}
