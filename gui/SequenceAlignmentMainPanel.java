/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 - 
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.AlignmentEngine;
import model.AlignmentResult;

/**
 * Contains all the actual UI for building the application.  We place all components
 * on this panel so we can reuse it in other applications (we can easily build
 * an applet with this functionality).
 * 
 * Also we are not strict with the Model-view-controller model, but rather
 * this class acts as the controller as well as the view.  This is because
 * the controller is very simple (just listens to clicks to two buttons) 
 * 
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class SequenceAlignmentMainPanel extends JPanel implements ActionListener {

	/** 
	 * The actual graphical component where we display the matrix 
	 */
	private GraphicalAlignmentPanel graphicalAlignmentPanel = new GraphicalAlignmentPanel();
	/** 
	 * The text box which contains the first sequence 
	 */
	private TextPanel firstSequencePanel = new TextPanel("Sequence 1", 7, 40, true);
	/** 
	 * The text box which contains the second sequence 
	 */
	private TextPanel secondSequencePanel = new TextPanel("Sequence 2", 7, 40, true);
	/** 
	 * The text box which will contain the actual alignment - not editable! 
	 */
	private TextPanel sequenceAlignment = new TextPanel("Textual Sequence Alignment", 4, 80, false);
	/** 
	 * Button which performs needleman and wunsch algorithm 
	 */
	private JButton btnNeedlemanWunschAlign = new JButton("Needleman & Wunsch Align!");
	/** 
	 * Button which performs smith waterman algorithm 
	 */
	private JButton btnSmithWatermanAlign = new JButton("Smith Waterman Align!");
	/**
	 * The actual engine which does the alignment.  Just one static and immutable
	 * instance.
	 */
	private static final AlignmentEngine alignmentEngine = new AlignmentEngine();
		
	/**
	 * Constructor which builds the actual UI
	 * 
	 * We use a mix of box layouts to place the components correctly
	 * We find that this is much more managable than the grid bag layout.
	 */
	public SequenceAlignmentMainPanel() {
		
		// use box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// add some space
		add(Box.createVerticalGlue());

		// set the size of the graphical alignment panel...
		// the box layout should respect this
		Dimension d = new Dimension(600, 250);
		graphicalAlignmentPanel.setMaximumSize(d);
		graphicalAlignmentPanel.setMinimumSize(d);
		graphicalAlignmentPanel.setPreferredSize(d);

		// add a scroll pane since we know that the alignment panel might be
		// larger than our predefined size
		JScrollPane scrollPane = new JScrollPane(graphicalAlignmentPanel); 
		add(scrollPane); // add
		
		// add some glue
		add(Box.createVerticalGlue());
		
		// create a sub panel which contains the two sequence boxes next to each
		// other (along the x axis)
		JPanel sequencePanel = new JPanel();
		// keep using the box layout, easy
		sequencePanel.setLayout(new BoxLayout(sequencePanel, BoxLayout.X_AXIS));
		// insert some space
		sequencePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// add first sequence panel
		sequencePanel.add(firstSequencePanel);
		// insert some horizontal space
		sequencePanel.add(Box.createHorizontalGlue());
		// add second sequence panel
		sequencePanel.add(secondSequencePanel);
		// add the subpanel to the panel
		add(sequencePanel);
		// create some space
		add(Box.createVerticalGlue());
		// add the panel which will display the actual alignment
		add(sequenceAlignment);
		// spaces again
		add(Box.createVerticalGlue());
		// add a sub panel with the two buttons for NW and SW algorithms
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		// margin
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.add(btnNeedlemanWunschAlign);
		// create a rigidly spaced area (150px wide)
		buttonPanel.add(Box.createRigidArea(new Dimension(150, 10)));
		buttonPanel.add(btnSmithWatermanAlign);
		// add the button panel to the actual panel
		add(buttonPanel);
		// add some space so the buttons are not on the bottom of the panel
		add(Box.createVerticalGlue());

		// register this class as the action listener of the buttons 
		btnNeedlemanWunschAlign.addActionListener(this);
		btnSmithWatermanAlign.addActionListener(this);
		
	}
	
	/**
	 * Validates a user entered sequence.
	 * 
	 * The major validation rules are
	 * <ul>
	 * <li>sequence cannot be null</li>
	 * <li>sequence cannot be empty (or containing only spaces)</li>
	 * <li>sequence may only contain A-Z characters</li>
	 * </ul>
	 * 
	 * We could have added validation for C T A G for nucleotides and 
	 * for appropriate protein residues, but we like comparing any random words.
	 * So we removed that particular validation 
	 * 
	 * This method is also responsible to show an error message if sequence
	 * is invalid.
	 * 
	 * @param sequence The putative sequence
	 * @return true if valid, false otherwise
	 */
	public boolean validateSequence(String sequence) {
		
		// check sequence is not null
		if (sequence == null) {
			JOptionPane.showMessageDialog(this, "Sequence cannot be null!", 
					"Error",
			        JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// check that the sequence is not empty
		if (sequence.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Sequence cannot be empty!", 
					"Error",
			        JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// check that only letters are found in the sequence
		if (!sequence.matches("[A-Za-z]+")) {
			JOptionPane.showMessageDialog(this, "Invalid character in sequence (Only A-Z or a-z allowed) !", 
					"Error",
			        JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// if we got here - this is a valid sequence
		return true;
	}
	
	/**
	 * Calls the actual alignment engine to perform the alignment.
	 * 
	 * The user entered sequences are normalized in the following manner:
	 * <ul>
	 * <li>sequence is trimmed</li>
	 * <li>sequence is converted to uppercase</li>
	 * <li>sequence has any \n characters removed</li>
	 * </ul>
	 * 
	 * This method should really be in the controller of the MVC paragdim
	 * but since it is very simple we just use it here. 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		// hardocded, so that we do not need to have to type it each time 
		// (boring)
//		firstSequencePanel.setText("coelacanth");
//		secondSequencePanel.setText("pelican");
		
		// the actual result of the alignment
		AlignmentResult result = null;
		
		// get the strings from the text boxes and do some normalization.
		// normalization includes, trimming of spaces, removing of newline
		// characters and converting to uppercase
		String firstSequence = firstSequencePanel.getText().trim().toUpperCase().replaceAll("\n", "");
		String secondSequence =  secondSequencePanel.getText().trim().toUpperCase().replaceAll("\n", "");
		
		// if one of the sequences is not valid - just stop execution of this 
		// method
		if (!validateSequence(firstSequence) || (!validateSequence(secondSequence))) {
			return;
		}
		
		// which button was it ?
		if (btnNeedlemanWunschAlign.equals(event.getSource())) {
			// needleman wunsch
			result = alignmentEngine.alignNeedlemanWunsch(firstSequence, secondSequence);
		} else if (btnSmithWatermanAlign.equals(event.getSource())) {
			// smith waterman
			result = alignmentEngine.alignSmithWaterman(firstSequence, secondSequence);
		} else {
			// none of the two ???!!!  worrying...
			throw new UnsupportedOperationException("No handler for " + event.getSource());
		}
		
		// give the raw material to the graphical alignment to display it
		graphicalAlignmentPanel.setPathMatrix(firstSequence, secondSequence, result.getMatrix());
		// display the alignments one under the other (this is why we add \n 
		sequenceAlignment.setText(result.getAlignmentPair()[0] + '\n' + result.getAlignmentPair()[1]);
	}
	
}
