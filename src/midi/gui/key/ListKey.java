package midi.gui.key;

import static midi.gui.key.Metric.interKeysX;
import static midi.gui.key.Metric.interKeysY;
import static midi.gui.key.Metric.kh;
import static midi.gui.key.Metric.kw;
import static midi.gui.key.Metric.posY;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Class that represents an half of octave 
 * We must have 2 for a complete octave:
 * first with 3 white keys
 * second with 4 white keys
 */
public class ListKey extends ArrayList<Key> {

	private static final long serialVersionUID = 1L;

	public ListKey(int nbWKeys, int posX) {

		boolean white = false;
        for (int i = 0, x = posX; i++ < (2*nbWKeys - 1); x += interKeysX) {
            if (white = !white) { // white key
                add(new Key(x, posY + interKeysY, kw, kh, Color.white));

            } else { // black key
                add(new Key(x, posY, kw, kh, Color.black));
            }
        }
	}

    public void paint(Graphics2D g) {
    	for (Key key: this) {
    		key.paint(g);
    	}
    }

	public void updatePosX(int width) {

		int newPosX = Math.round((width - (kw + size()*interKeysX))/2f);
		int diff = newPosX - get(0).x;

		for (Key key: this) {
			key.x += diff;
		}
	}
}
