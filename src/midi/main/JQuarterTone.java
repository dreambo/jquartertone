package midi.main;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EtchedBorder;

import midi.gui.Device;
import midi.gui.Keyboard;
import midi.gui.Memory;
import midi.gui.Tune;
import midi.receiver.ArabicReceiver;

public class JQuarterTone extends JFrame implements ActionListener {

	public static final String ABOUT1 = "JQuarterTone v1.1";
	public static final String ABOUT2 = "by boudhaim@gmail.com\njmiditools.sourceforge.net";

	private static final long serialVersionUID = 1L;
	private static int look;
	private static LookAndFeelInfo[] infos;

	private JButton bExit  =  new JButton("Exit");
	private JButton bAbout = new JButton("About");
	private JButton bLook  =  new JButton("Look");
	private static JPanel panel = new JPanel();

	static {
		LookAndFeelInfo[] nfos = UIManager.getInstalledLookAndFeels();
		infos = new LookAndFeelInfo[nfos.length + 4];
		for (int i = 0; i < nfos.length; i++) {
			infos[i] = nfos[i];
		}
		infos[infos.length - 1] = new LookAndFeelInfo("Plastic3DJgoodies", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		infos[infos.length - 2] = new LookAndFeelInfo("PlasticJgoodies",   "com.jgoodies.looks.plastic.PlasticLookAndFeel");
		infos[infos.length - 3] = new LookAndFeelInfo("WindowsJgoodies",   "com.jgoodies.looks.windows.WindowsLookAndFeel");
		infos[infos.length - 4] = new LookAndFeelInfo("PlasticXPJgoodies", "com.jgoodies.looks.plastic.PlasticXPLookAndFeel");

		look = infos.length - 4;
	}

	public JQuarterTone(String about) {
		super(about);
		init();
	}

	private void init() {
		JPanel exitPanel  = new JPanel();
		JPanel lookPanel  = new JPanel();
		JPanel aboutPanel = new JPanel();

		panel.setLayout(new GridLayout(1, 3));

		//exit
		exitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		exitPanel.add(bExit); bExit.addActionListener(this);
		panel.add(exitPanel);
		//look
		lookPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		lookPanel.add(bLook); bLook.addActionListener(this);
		panel.add(lookPanel);
		//about
		aboutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		aboutPanel.add(bAbout); bAbout.addActionListener(this);
		panel.add(aboutPanel);

		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(panel);
	}

	public static void main(String[] args) throws MidiUnavailableException {
	
	    JFrame frame = new JQuarterTone(ABOUT1);
		frame.setLayout(new GridLayout(5, 1));
	
	    //Set Look & Feel
        setLookAndFeel(frame);

        // arabic scale keyboard
		frame.add(Keyboard.getInstance().panel);
	
		// memory buttons
		frame.add(Memory.getInstance().panel);
	
		// arabic tune, transpose and octave
		frame.add(Tune.getInstance().panel);
	
		// MIDI input/output devices
		frame.add(Device.getInstance().panel);
	
		// Control
		frame.add(panel);
	
		//frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//.setSize(640, 360);
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (bExit == e.getSource()) {
			ArabicReceiver.getInstance().close();
			System.exit(0);
		} else if (bAbout == e.getSource()) {
			JOptionPane.showMessageDialog(this, JQuarterTone.ABOUT1 + " " + JQuarterTone.ABOUT2);
		} else {
			setLookAndFeel(this);
		}
	}

	private static void setLookAndFeel(JFrame frame) {
		look = (Math.abs(look) % infos.length);
		String lookName = infos[look].getName();

		try {
    		UIManager.setLookAndFeel(infos[look++].getClassName());
    		System.out.println(lookName);
        	SwingUtilities.updateComponentTreeUI(frame);
        	frame.setTitle(JQuarterTone.ABOUT1 + " - " + lookName);
        	//frame.pack();
    	} catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
	}
}
