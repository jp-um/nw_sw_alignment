/*
 * MSc Bioinformatics and Theoretical Systems Biology (2008-2009)
 * Java Programming - Assessed Exercise No.2 -
 *
 * Jean-Paul Ebejer <jp@javaclass.co.uk>
 */
package model;

/**
 * The actual alignment matrix which contains the scores and directions
 * (held as a single object).
 *
 * The matrix initialization depends on which algorithm the matrix is
 * initialized for.
 *
 * Note that the matrix does not contain the actual sequences as part of it.
 *
 * @author <a href="mailto:jp@javaclass.co.uk">JP</a>
 * @version 1.0
 */
public class AlignmentMatrix {

	/**
	 * The cell contents of the matrix, the actual matrix.
	 * Represented as a 2D array
	 */
	private CellContents[][] matrix = null;


	/**
	 * Constuctor which initializes the matrix.
	 * @param firstSequence The first sequence
	 * @param secondSequence The second Sequence
	 * @param algorithm The actual algorithm used - either SW or NW.  This
	 * is important because different initializations occur depending on the
	 * algorithm.
	 */
	public AlignmentMatrix(String firstSequence,
						   String secondSequence,
						   AlignmentAlgorithm algorithm) {

		// initialize the array matrix, to the sequence lengths + 1 (for gap
		// alignment with first element)
		matrix = new CellContents[secondSequence.length() + 1][firstSequence.length() + 1];

		// which algorithm is it ?
		if (AlignmentAlgorithm.NEEDLEMAN_WUNSCH.equals(algorithm)) {

			// first row and column have special handling
			for (int i=0;i<firstSequence.length();i++) {
				matrix[0][i+1] = new CellContents((i+1) * -2, Direction.LEFT);
			}
			for (int i=0;i<secondSequence.length();i++) {
				matrix[i+1][0] = new CellContents((i+1) * -2, Direction.UP);
			}

		} else if (AlignmentAlgorithm.SMITH_WATERMAN.equals(algorithm)) {

			// first row and column are 0s
			for (int i=0;i<firstSequence.length();i++) {
				matrix[0][i+1] = new CellContents(0, null);
			}
			for (int i=0;i<secondSequence.length();i++) {
				matrix[i+1][0] = new CellContents(0, null);
			}

		} else {
			// the algorithm is neither SW nor NW
			throw new UnsupportedOperationException("Alignment Algorithm " + algorithm + " not supported!");
		}

		// the first cell of the matrix will always have the same value
		matrix[0][0] = new CellContents(0, null);

	}

	/**
	 * Allows for an intelligible print of the matrix for debugging and
	 * informational purposes.
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		if (matrix != null) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					sb.append((matrix[i][j] == null) ? '-' : matrix[i][j].toString()).append('\t');
				}
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	/**
	 * Gets the actual matrix - hopefully initialized.
	 * @return the actual matrix containing scores and direction
	 */
	public CellContents[][] getMatrix() {
		return matrix;
	}

}
