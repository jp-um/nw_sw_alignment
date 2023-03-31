/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 -
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package gui;

import javax.swing.JFrame;

/**
 * The main frame of the application.  Has fixed width so we do not have
 * to worry about application resizing by the user (headache).  We simply
 * add one panel which contained everything.  This was if we want to convert
 * everything to an applet it is easy.
 *
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class SequenceAlignmentFrame extends JFrame {

	/**
	 * Creates the main frame of the application.  This class when initialised
	 * will build all the GUI.
	 */
	public SequenceAlignmentFrame() {
		// create window with specified title
		super("Local & Global Sequence Alignment");
		// add the panel to the frame, just one containing subpanels etc
		getContentPane().add(new SequenceAlignmentMainPanel());
		// fixed size
		setSize(1024, 768);
		// exit when X button is pressed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// place in middle of screen
		setLocationRelativeTo(null);
		// do not allow the user to resize frame, dont bother with component
		// resizing
		setResizable(true);
		// show it !!
		setVisible(true);
	}

}
