/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 - 
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package model;

/**
 * Has all the results of the alignment.  Basically contains the sequences,
 * the actual matrix, and the alignment pair (as an array of two strings). 
 *  
 * This object is immutable.
 *  
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class AlignmentResult {

	/**
	 * The actual matrix used for the alignment
	 */
	private final AlignmentMatrix matrix;
	/**
	 * The alignment pair - the actual result of the alignment.  Is provided
	 * as an array of length two.
	 */
	private final String[] alignmentPair;
	/**
	 * The first sequence which produces the alignment
	 */
	private final String firstSequence;
	/**
	 * The second sequence which produces the alignment
	 */
	private final String secondSequence;
	
	
	/**
	 * Sets the object attributes after the alignment has occured
	 * @param matrix The actual matrix with scores and directions
	 * @param alignmentPair The alignment pair (the actual result)
	 * @param firstSequence The first sequence
	 * @param secondSequence The second sequence
	 */
	public AlignmentResult(AlignmentMatrix matrix, 
						   String[] alignmentPair,
						   String firstSequence, 
						   String secondSequence) {
		this.matrix = matrix;
		this.alignmentPair = alignmentPair;
		this.firstSequence = firstSequence;
		this.secondSequence = secondSequence;
	}

	// standard getter and setter methods
	
	public AlignmentMatrix getMatrix() {
		return matrix;
	}
	
	public String[] getAlignmentPair() {
		return alignmentPair;
	}
	
	public String getFirstSequence() {
		return firstSequence;
	}
	
	public String getSecondSequence() {
		return secondSequence;
	}
	
}
