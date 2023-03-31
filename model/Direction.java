/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 - 
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package model;

/**
 * The direction which a particular cell might point to.  UP, LEFT showing gaps 
 * and DIAGONAL showing alignment. 
 * 
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public enum Direction {

	// the directions
	UP("U"),
	LEFT("L"),
	DIAGONAL("D");
	
	/**
	 * A one letter representation to be able to draw a proper matrix
	 */
	private String representationChar;
	
	/**
	 * Private constructor to limit access to this class.
	 * @param representationChar An abbreviation for the direction
	 */
	private Direction(String representationChar) {
		this.representationChar = representationChar;
	}

	/**
	 * Gets the representation char
	 * @return The representation char.
	 */
	public String getRepresentationChar() {
		return representationChar;
	}
	
}
