/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 - 
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package model;

/**
 * The alignment matrix is built from a multi dimensional array of instances
 * of this class.  
 *
 * In contains the score, the direction and whether this particular cell 
 * lies on the optimal path (on the alignment).
 *  
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class CellContents {

	/**
	 * The score for this cell
	 */
	private int score;
	
	/**
	 * The direction for this cell
	 */
	private Direction dir;
	
	/**
	 * Is this cell on the optimal alignment path
	 */
	private boolean onOptimalPath = false;

	/**
	 * Sets the cell contents
	 * @param score The score
	 * @param dir The direction, UP, DOWN, DIAGONAL
	 */
	public CellContents(int score, Direction dir) {
		super();
		this.score = score;
		this.dir = dir;
	}

	// self explaining getters and setters
	
	public int getScore() {
		return score;
	}

	public Direction getDir() {
		return dir;
	}

	public boolean isOnOptimalPath() {
		return onOptimalPath;
	}

	public void setOnOptimalPath(boolean onOptimalPath) {
		this.onOptimalPath = onOptimalPath;
	}

	/**
	 * Decent String representation for informational and debugging
	 * purposes 
	 */
	@Override
	public String toString() {
		return ((dir == null) ? "-" : dir.getRepresentationChar()) + ((score >= 0) ? "/ " : "/") + score;
	}

	
	
}
