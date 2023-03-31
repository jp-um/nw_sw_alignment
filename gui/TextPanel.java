/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 - 
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * The generic text area to be used for various purposes.
 * (Sequence input, sequence alignment etc.)
 * 
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class TextPanel extends JPanel {
	
	/**
	 * The actual text area.  Final, cannot change the instance once
	 * created.
	 */
	private final JTextArea textArea;
	
	/**
	 * The constructor which sets the basic properties of the text area
	 * @param title The title on the border
	 * @param rows the number of rows
	 * @param cols the number of columns
	 * @param editable if the user can edit the contents of the text area
	 */
	public TextPanel(String title, int rows, int cols, boolean editable) {
	
		// create a text area
		textArea = new JTextArea(rows, cols);
		// allow editing ?
		textArea.setEditable(editable);
		// set the font to a monospaced font - this is to make sure that
		// the alignments are font-wise under each other.  I and F take the
		// same space
		Font font = new Font("Monospaced", Font.PLAIN, 14);
		textArea.setFont(font);
		
		// build a title on the panel 
		TitledBorder borderedTitle = BorderFactory.createTitledBorder(title);
		setBorder(borderedTitle);

		// add a scrollbar, to be displayed only if the text in the text
		// area is too large
		JScrollPane scrollPane = new JScrollPane(textArea); 
		add(scrollPane); // add
	}
	
	/**
	 * Sets the actual text in the text area 
	 * @param text The text to set in the TextArea
	 */
	public void setText(String text) {
		textArea.setText(text);
	}
	
	/**
	 * Gets the text from the text area
	 * @return The text in the text area
	 */
	public String getText() {
		return textArea.getText();
	}
	
}
