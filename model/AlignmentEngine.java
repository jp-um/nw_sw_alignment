/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 -
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package model;

/**
 * The main actor which carries out the alignment.  The actual engine.
 *
 * Handles both the NW and SW algorithms.
 *
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class AlignmentEngine {

	/**
	 * Calculates each matrix position score, depending on the topleft, top and
	 * left cells and the algorithm being used.
	 *
	 * Calculates the score using the basic -1/1 for match mismatch of position
	 * sequence.  Also determines the max value between the neighbouring cells
	 * etc.
	 *
	 * @param firstSequenceChar
	 * @param secondSequenceChar
	 * @param topLeft
	 * @param top
	 * @param left
	 * @param algorithm
	 * @return
	 */
	private CellContents calculateCellContents(char firstSequenceChar,
											   char secondSequenceChar,
											   CellContents topLeft,
											   CellContents top,
											   CellContents left,
											   AlignmentAlgorithm algorithm) {

		// get the top left cell and add 1 if the two chars are equal or
		// deduct one if they are not
		int matchScore = topLeft.getScore()
				+ ((firstSequenceChar == secondSequenceChar ? 1 : -1));
		// deduct two for gap
		int horizontalGapScore = left.getScore() - 2;
		int verticalGapScore = top.getScore() - 2;

		// calculate scores
		if ((matchScore >= horizontalGapScore)
				&& (matchScore >= verticalGapScore)) {

			// match score is the max value

			if (algorithm.equals(AlignmentAlgorithm.SMITH_WATERMAN) && (matchScore <= 0)) {
				// if it is negative and we are using smith waterman - then level it to 0
				// WITH NO DIRECTION
				return new CellContents(0, null);
			} else {
				// else return with diagonal direction
				return new CellContents(matchScore, Direction.DIAGONAL);
			}

		} else if ((horizontalGapScore >= matchScore)
				&& (horizontalGapScore >= verticalGapScore)) {

			// horizontal gap score is largest

			if (algorithm.equals(AlignmentAlgorithm.SMITH_WATERMAN) && (horizontalGapScore <= 0)) {
				// if it is negative and we are using smith waterman - then level it to 0
				// WITH NO DIRECTION
				return new CellContents(0, null);
			} else {
				// return score with proper direction
				return new CellContents(horizontalGapScore, Direction.LEFT);
			}

		} else if ((verticalGapScore >= horizontalGapScore)
				&& (verticalGapScore >= matchScore)) {

			// vertical gap score is largest
			if (algorithm.equals(AlignmentAlgorithm.SMITH_WATERMAN) && (verticalGapScore <= 0)) {
				// if it is negative and we are using smith waterman - then level it to 0
				// WITH NO DIRECTION
				return new CellContents(0, null);
			} else {
				// return score with proper direction
				return new CellContents(verticalGapScore, Direction.UP);
			}

		} else {
			// we should never get here...
			throw new UnsupportedOperationException("Unknown resolution");
		}
	}

	/**
	 * Aligns two sequences using the needleman wunsch algorithm
	 * @param firstSequence The first sequence to align
	 * @param secondSequence The second sequence to align it to
	 * @return The alignment result containing everything, including the
	 * matrix and and alignment pair
	 */
	public AlignmentResult alignNeedlemanWunsch(String firstSequence, String secondSequence) {

		// we try to compare like with like (we redo it for completeness)
		firstSequence = firstSequence.toUpperCase();
		secondSequence = secondSequence.toUpperCase();

		// initialize the matrix
		AlignmentMatrix matrix = new AlignmentMatrix(firstSequence,
													 secondSequence,
													 AlignmentAlgorithm.NEEDLEMAN_WUNSCH);

		// get the actual matrix
		CellContents[][] matrixContents = matrix.getMatrix();

		for (int i = 1; i < matrixContents.length; i++) {
			for (int j = 1; j < matrixContents[i].length; j++) {

				// browse through each cell position in the matrix

				if (matrixContents[i][j] == null) {

					// matrix cell is not initialized - do so !
					matrixContents[i][j] = calculateCellContents(firstSequence.charAt(j - 1), // char from seq 1
							secondSequence.charAt(i - 1), // char from seq 2
							matrixContents[i - 1][j - 1], // top left cell
							matrixContents[i - 1][j], // top cell
							matrixContents[i][j - 1], // left cell
							AlignmentAlgorithm.NEEDLEMAN_WUNSCH); // algorithm
				}
			}
		}

		// once the matrix is created call the alignment procedure
		String[] alignmentPair = getAlignmentPair(matrix,
				firstSequence,
				secondSequence,
				null, // no alignment pair in the beginning
				matrixContents.length - 1, // start from the rightmost, bottom cell
				matrixContents[0].length - 1);

		// return the result
		return new AlignmentResult(matrix,
								   alignmentPair,
								   firstSequence,
								   secondSequence);

	}




	/**
	 * Aligns two sequences using the smith waterman algorithm
	 * @param firstSequence The first sequence to align
	 * @param secondSequence The second sequence to align it to
	 * @return The alignment result containing everything, including the
	 * matrix and and alignment pair
	 */
	public AlignmentResult alignSmithWaterman(String firstSequence, String secondSequence) {

		// we try to compare like with like (we redo it for completeness)
		firstSequence = firstSequence.toUpperCase();
		secondSequence = secondSequence.toUpperCase();

		// initialize the matrix
		AlignmentMatrix matrix = new AlignmentMatrix(firstSequence,
				secondSequence,
				AlignmentAlgorithm.SMITH_WATERMAN);

		// get the actual matrix
		CellContents[][] matrixContents = matrix.getMatrix();

		// we want to find the cell with the max score, in the beginning
		// assume its position 1,1 with score 0
		int maxI = 1;
		int maxJ = 1;
		int score = 0;

		for (int i = 1; i < matrixContents.length; i++) {
			for (int j = 1; j < matrixContents[i].length; j++) {

				// browse through all cell contents

				if (matrixContents[i][j] == null) {

					// initialize each cell which does not contain a value
					matrixContents[i][j] = calculateCellContents(
							firstSequence.charAt(j - 1),  // char from 1st seq
							secondSequence.charAt(i - 1), // char from 2nd seq
							matrixContents[i - 1][j - 1], // topleft cell
							matrixContents[i - 1][j],  // top cell
							matrixContents[i][j - 1],  // left cell
							AlignmentAlgorithm.SMITH_WATERMAN); // algorith used

					// is the score smaller then the score of the generated cell
					if (score < matrixContents[i][j].getScore()) {
						// this means that this cell has a higher score
						// so take its position
						maxI = i;
						maxJ = j;
						// and its score
						score = matrixContents[i][j].getScore();
					}
				}
			}
		}

		String[] alignmentPair = getAlignmentPair(matrix,
				firstSequence,
				secondSequence,
				null, // no alignment pair in the beginning
				maxI, // we start from the cell with highest score
				maxJ);

		// return the actual result
		return new AlignmentResult(matrix, alignmentPair, firstSequence,
				secondSequence);

	}

	/**
	 * The main recursive method which builds the alignment pair.
	 *
	 * The alignment pair is build in successive recursive calls.
	 *
	 * @param matrix The matrix with the scores
	 * @param firstSequence The first sequence
	 * @param secondSequence The second sequence
	 * @param alignmentPair The alignment pair till now - starts of null
	 * @param i the x coordinate of the cell to take in consideration
	 * @param j the y coordinate of the cell to take in consideration
	 * @return An array of size 2 with the alignment
	 */
	private String[] getAlignmentPair(AlignmentMatrix matrix,
									  String firstSequence,
									  String secondSequence,
									  String[] alignmentPair,
									  int i,
									  int j) {

		// if the alignment pair is null that means that this is the first call
		// to this method, in which case initialize
		if (alignmentPair == null) {
			alignmentPair = new String[2];
			alignmentPair[0] = "";
			alignmentPair[1] = "";
		}

		// get the matrix
		CellContents[][] matrixContents = matrix.getMatrix();
		if (matrixContents[i][j].getDir() != null) {
			// if cell i,j has a direction we are on the optimal path, mark it
			matrixContents[i][j].setOnOptimalPath(true);
		}

		if (Direction.LEFT.equals(matrixContents[i][j].getDir())) {
			// to the left ?
			alignmentPair[0] = firstSequence.charAt(j - 1) + alignmentPair[0];
			alignmentPair[1] = "-" + alignmentPair[1]; // gap
			j = j - 1; // next cell is left
		} else if (Direction.UP.equals(matrixContents[i][j].getDir())) {
			// up
			alignmentPair[0] = "-" + alignmentPair[0]; // gap
			alignmentPair[1] = secondSequence.charAt(i - 1) + alignmentPair[1];
			i = i - 1; // next cell is up
		} else if (Direction.DIAGONAL.equals(matrixContents[i][j].getDir())) {
			// diagonal
			// align the two sequences - no gaps
			alignmentPair[0] = firstSequence.charAt(j - 1) + alignmentPair[0];
			alignmentPair[1] = secondSequence.charAt(i - 1) + alignmentPair[1];
			j = j - 1; // next cell is topleft
			i = i - 1;
		}

		// if the next cell (updated i,j) has a direction then we can continue
		// the alignment - otherwise just return the alignment pair
		if (matrixContents[i][j].getDir() != null) {
			alignmentPair = getAlignmentPair(matrix, firstSequence,
					secondSequence,
					alignmentPair,
					i,
					j);
		}
		// the result
		return alignmentPair;
	}

}
