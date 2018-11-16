package algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a generic alignment algorithm for two sequence based on
 * dynamic programming. The alignment is based on a generic
 * {@link SubstitutionMatrix}, where is defined the alphabet and the scores of
 * pairings between letters.
 * 
 * <br><br>
 *  
 * For concrete implementation view {@link NeedelemanWunsch} or
 * {@link SmithWaterman} for global and local alignment respectively.
 * 
 * @author Mario Randazzo
 *
 */
public abstract class AlignmentAlgorithm {

	/**
	 * The sequence a to align.
	 */
	protected String a;

	/**
	 * The sequence b to align.
	 */
	protected String b;

	/**
	 * The gap opening penalty to use.
	 */
	protected int gop;

	/**
	 * The gap extension penalty to use.
	 */
	protected float gep;

	/**
	 * A matrix of {@link Cell} as pairing matrix.
	 */
	protected Cell[][] pairingMatrix;

	/**
	 * The {@link SubstitutionMatrix} to use in the algorithm.
	 */
	protected SubstitutionMatrix substitutionMatrix;

	/**
	 * The list of {@link Alignment} calculated with the algorithm.
	 */
	protected ArrayList<Alignment> alignments;

	/**
	 * The maximum score in pairing matrix.
	 */
	protected float maximumScore;

	/**
	 * Builds the data structures for the algorithms, and aligns the strings a and
	 * b.
	 * 
	 * @param a                  the sequence a.
	 * @param b                  the sequence b.
	 * @param gop                the gap opening penalty.
	 * @param gep                the gap extension penalty.
	 * @param substitutionMatrix the substitution matrix to use.
	 */
	public AlignmentAlgorithm(String a, String b, int gop, float gep, SubstitutionMatrix substitutionMatrix) {
		this.a = a.toLowerCase();
		this.b = b.toLowerCase();
		this.gop = gop;
		this.gep = gep;
		this.substitutionMatrix = substitutionMatrix;
		this.maximumScore = Float.MAX_VALUE;

		pairingMatrix = new Cell[a.length()][b.length()];

		for (int i = 0; i < a.length(); i++)
			pairingMatrix[i][0] = new Cell(i, 0,
					Math.max(0.0f, this.substitutionMatrix.get(this.a.charAt(i), this.b.charAt(0))));

		for (int j = 1; j < b.length(); j++)
			pairingMatrix[0][j] = new Cell(0, j,
					Math.max(0.0f, this.substitutionMatrix.get(this.a.charAt(0), this.b.charAt(j))));

		for (int i = 1; i < a.length(); i++)
			for (int j = 1; j < b.length(); j++)
				pairingMatrix[i][j] = new Cell(i, j, substitutionMatrix.get(this.a.charAt(i), this.b.charAt(j)));

		updatePairingMatrix();
	}

	/**
	 * Gets the sequence a.
	 * 
	 * @return the sequence a.
	 */
	public String getA() {
		return a;
	}

	/**
	 * Gets the sequence b.
	 * 
	 * @return the sequence b.
	 */
	public String getB() {
		return b;
	}

	/**
	 * Gets the gap opening penalty.
	 * 
	 * @return the gap opening penalty.
	 */
	public int getGop() {
		return gop;
	}

	/**
	 * Gets the gap extension penalty.
	 * 
	 * @return the gap extension penalty.
	 */
	public float getGep() {
		return gep;
	}

	/**
	 * Gets the pairing matrix calculated with the algorithm.
	 * 
	 * @return a matrix of {@link Cell}.
	 */
	public Cell[][] getPairingMatrix() {
		return pairingMatrix;
	}

	/**
	 * Support method for fill {@link pairingMatrix} using the
	 * {@link SubstitutionMatrix} scores. This is a pre-processing step, before
	 * maximization step.
	 */
	private void updatePairingMatrix() {
		int lenA = a.length();
		int lenB = b.length();

		float penalty = gop;
		for (int i = 1; i < lenA; i++) {
			float upPenalizedValue = pairingMatrix[i - 1][0].getScore() - penalty;
			if (upPenalizedValue >= pairingMatrix[i][0].getScore()) {
				pairingMatrix[i][0].updateScore(upPenalizedValue);
				pairingMatrix[i][0].setUp(pairingMatrix[i - 1][0]);
				penalty = gep;
			} else
				penalty = gop;
		}

		penalty = gop;
		for (int i = 1; i < lenB; i++) {
			float leftPenalizedValue = pairingMatrix[0][i - 1].getScore() - penalty;
			if (leftPenalizedValue >= pairingMatrix[0][i].getScore()) {
				pairingMatrix[0][i].updateScore(leftPenalizedValue);
				pairingMatrix[0][i].setLeft(pairingMatrix[0][i - 1]);
				penalty = gep;
			} else
				penalty = gop;
		}

		for (int i = 1; i < lenA; i++)
			for (int j = 1; j < lenB; j++)
				maximize(i, j);
	}

	/**
	 * Support method for maximize score of generic {@link Cell}.
	 * 
	 * @param i the coordinate y
	 * @param j the coordinate x
	 */
	private void maximize(int i, int j) {
		float[] values = { pairingMatrix[i][j - 1].getScore() - (pairingMatrix[i][j - 1].getLeft() != null ? gep : gop),
				pairingMatrix[i - 1][j].getScore() - (pairingMatrix[i - 1][j].getUp() != null ? gep : gop),
				pairingMatrix[i - 1][j - 1].getScore() + pairingMatrix[i][j].getScore() };

		float max = 0;
		for (int k = 0; k < 3; k++)
			if (values[k] > max)
				max = values[k];

		if (values[0] == max)
			pairingMatrix[i][j].setLeft(pairingMatrix[i][j - 1]);
		if (values[1] == max)
			pairingMatrix[i][j].setUp(pairingMatrix[i - 1][j]);
		if (values[2] == max)
			pairingMatrix[i][j].setDiagonal(pairingMatrix[i - 1][j - 1]);

		pairingMatrix[i][j].updateScore(max);
	}

	/**
	 * Support method, implement an iterative procedure for build a list of
	 * {@link Alignment}.
	 */
	private void elaborateResults() {
		List<Cell> mCells = getMaximumCells();

		for (Cell c : mCells) {
			Stack<CellTrace> s = new ArrayListStack<>();
			s.push(new CellTrace(c, a.substring(c.getX() + 1), b.substring(c.getY() + 1)));

			while (!s.isEmpty()) {
				CellTrace top = s.pop();
				int x = top.c.getX();
				int y = top.c.getY();

				if (isFinalCell(top.c)) {
					top.partialAlignment.add(a.charAt(x), b.charAt(y));
					top.partialAlignment.add(a.substring(0, x), b.substring(0, y));
					alignments.add(top.partialAlignment.build());
				} else {
					if (top.c.getLeft() != null) {
						s.push(new CellTrace(top.c.getLeft(),
								new Alignment.Builder(top.partialAlignment.addOnB(b.charAt(y))), false, true));

					}

					if (top.c.getUp() != null) {
						s.push(new CellTrace(top.c.getUp(),
								new Alignment.Builder(top.partialAlignment.addOnA(a.charAt(x))), true, false));
					}

					if (top.c.getDiagonal() != null)
						s.push(new CellTrace(top.c.getDiagonal(),
								new Alignment.Builder(top.partialAlignment.add(a.charAt(x), b.charAt(y)))));
				}
			}
		}
	}

	/**
	 * Abstract method used in internal procedure for build the list of
	 * {@link Alignment}. This method must return True if c is final {@link Cell} in
	 * the alignment.
	 * 
	 * @param c the {@link Cell} to check.
	 * @return true is c is the final cell in the alignment.
	 */
	protected abstract boolean isFinalCell(Cell c);

	/**
	 * Abstract method used in internal procedure for build the list of
	 * {@link Alignment}. This method must return a list of Cell, that are the
	 * {@link Cell} with maximum score.
	 * 
	 * @return a list of {@link Cell}.
	 */
	protected abstract List<Cell> getMaximumCells();

	/**
	 * Gets the maximum score in pairing matrix.
	 * 
	 * @return the maximum score.
	 */
	public float getMaximumScore() {
		return maximumScore;
	}

	/**
	 * Calculates and gets a list of alignments.
	 * 
	 * @return a list of {@link Alignment}.
	 */
	public List<Alignment> getAlignments() {
		alignments = null;
		alignments = new ArrayList<>();
		elaborateResults();
		return alignments;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pairing Matrix:\n");

		for (int i = 0; i < a.length(); i++) {
			for (int j = 0; j < b.length(); j++)
				builder.append(String.format("%6.1f, ", pairingMatrix[i][j].getScore()));
			builder.append("\n");
		}
		builder.append("\n");

		return builder.toString();
	}

}
