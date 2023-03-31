/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 - 
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.AlignmentMatrix;
import model.CellContents;
import model.Direction;

/**
 * The Panel which displays the alignment matrix including scores.  This panel 
 * also displays the direction as a graphic.
 *  
 * This class has only presentation logic.
 *  
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class GraphicalAlignmentPanel extends JPanel {
	
	/**
	 * Sets the Matrix on the UI through which we have to find the path 
	 * depending on each cells score.  Each time the path matrix is set
	 * all previous components are removed and re-added (rather heavy).
	 * 
	 * @param firstSequence The first sequence to be displayed at the top of the matrix
	 * @param secondSequence The second sequence to be displayed at the botton of the matrix
	 * @param matrix The actual matrix with scores and directions
	 */
	public void setPathMatrix(String firstSequence,
							  String secondSequence,				 
							  AlignmentMatrix matrix) {
		
		// remove all previously laid components - clear
		removeAll();
		// get the actual matrix
		CellContents[][] matrixContents = matrix.getMatrix();
		// set the layout as a grid
		setLayout(new GridLayout(matrixContents.length + 1, matrixContents[0].length + 1));
		// start by adding two spacer buttons (will not add the same button twice)
		add(new JButton(""));
		add(new JButton(""));
		
		// write the first sequence on the top row - notice this is not part of the matrix
		for (int i=0;i<firstSequence.length();i++) {
			// add
			add(new JButton("" + firstSequence.charAt(i)));
		}
		
		// let us fill the panel appropriately
		for (int i=0;i<matrixContents.length;i++) {
			
			// in front of the row add either the appropriate letter of the 
			// sequence or a space button
			// add a spacer button 
			if (i <= 0) {
				add(new JButton(""));
			} else {
				// add the button with a char from the second sequence
				add(new JButton("" + secondSequence.charAt(i-1)));
			}

			// for each cell in the row
			for (int j=0;j<matrixContents[0].length;j++) {
				// get the score for this cell
				JButton btnMatrix = new JButton("" + matrixContents[i][j].getScore());
				// which image shall we display ?
				ImageIcon image = null;
				// NOTE : since we are using getResource so use /
				try {
					if (Direction.UP.equals(matrixContents[i][j].getDir())) {
						image = new ImageIcon(getClass().getResource("/up.gif"));
					} else if (Direction.LEFT.equals(matrixContents[i][j].getDir())) {
						image = new ImageIcon(getClass().getResource("/left.gif"));
					} else if (Direction.DIAGONAL.equals(matrixContents[i][j].getDir())) {
						image = new ImageIcon(getClass().getResource("/diagonal.gif"));
					}
				} catch (Exception e) {
					System.err.println("Unable to load graphics... :(");
					e.printStackTrace();
				}
				// set the image
				btnMatrix.setIcon(image);
				// align the image and the button text
				btnMatrix.setVerticalTextPosition(AbstractButton.BOTTOM);
				btnMatrix.setHorizontalTextPosition(AbstractButton.CENTER);
				// if this is on the optimal path display as enabled with some
				// nice colouring
				btnMatrix.setEnabled(matrixContents[i][j].isOnOptimalPath());
		
				// set the button sizes - more buttons implies larger canvas size
				// someone outside this class is responsible to put a scrollbar
				// to this panel...
				Dimension buttonSize = new Dimension(100, 40);
				
				// set all sizes - we hope the layout manager will use these
				btnMatrix.setSize(buttonSize);
				btnMatrix.setPreferredSize(buttonSize);
				btnMatrix.setMaximumSize(buttonSize);
				btnMatrix.setMinimumSize(buttonSize);
				
				// if on optimal path, set a colour to highlight path
				if (matrixContents[i][j].isOnOptimalPath()) {
					btnMatrix.setForeground(Color.RED);
					btnMatrix.setBackground(Color.YELLOW);
				}
				
				// add the button to the grid 
				add(btnMatrix);
			}
		}
		
		// dimensions of the panel 
		Dimension panelSize = new Dimension(((matrixContents.length + 1) * 100) + 50, ((matrixContents[0].length + 1) * 40) + 50);
		// set the size of the panel.  again we hope layout manager uses this
		setSize(panelSize);
		setPreferredSize(panelSize);
		setMaximumSize(panelSize);
		setMinimumSize(panelSize);
		
		// update the UI
		updateUI();
	}
		

}
