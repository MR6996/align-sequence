package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class implement the Smith-Waterman algorithm for align two sequence. See
 * {@link AlignmentAlgorithm} for details.
 * 
 * @author Mario Randazzo
 *
 */
public class SmithWaterman extends AlignmentAlgorithm {

	/**
	 * the number of maximum to use of build {@link Alignment} list.
	 */
	private Integer firstK;

	/**
	 * The threshold to use for build the {@link Alignment} list.
	 */
	private Float threshold;

	/**
	 * Build a {@link AlignmentAlgorithm}.
	 * 
	 * @param a                  the sequence a.
	 * @param b                  the sequence b.
	 * @param gop                the gap opening penalty.
	 * @param gep                the gap extension penalty.
	 * @param substitutionMatrix the substitution matrix to use.
	 * @param firstK             the number of maximum to use of build
	 *                           {@link Alignment} list.
	 */
	public SmithWaterman(String a, String b, int gop, float gep, SubstitutionMatrix substitutionMatrix, int firstK) {
		super(a, b, gop, gep, substitutionMatrix);
		this.firstK = firstK;
		this.threshold = null;
	}

	/**
	 * Build a {@link AlignmentAlgorithm}.
	 * 
	 * @param a                  the sequence a.
	 * @param b                  the sequence b.
	 * @param gop                the gap opening penalty.
	 * @param gep                the gap extension penalty.
	 * @param substitutionMatrix the substitution matrix to use.
	 * @param threshold          the threshold to use for build the
	 *                           {@link Alignment} list.
	 */
	public SmithWaterman(String a, String b, int gop, float gep, SubstitutionMatrix substitutionMatrix,
			float threshold) {
		super(a, b, gop, gep, substitutionMatrix);
		this.firstK = null;
		this.threshold = threshold;
	}

	@Override
	protected List<Cell> getMaximumCells() {
		if (firstK != null)
			return getFirstKCells();
		else
			return getGreaterThanThreshold();
	}

	/**
	 * Support method, return a List of {@link Cell} which score is greater than
	 * threshold.
	 * 
	 * @return a list of {@link Cell}
	 */
	private ArrayList<Cell> getGreaterThanThreshold() {
		ArrayList<Cell> mCells = new ArrayList<>();

		float max = Float.MIN_VALUE;

		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < b.length(); j++) {
				float score = pairingMatrix[i][j].getScore();
				if (score > this.threshold)
					mCells.add(pairingMatrix[i][j]);

				if (score > max)
					max = score;
			}

		this.maximumScore = max;
		Collections.sort(mCells);
		return mCells;
	}

	/**
	 * Support method, return a List with the firstk maximum {@link Cell}
	 * 
	 * @return a list of {@link Cell}
	 */
	private LinkedList<Cell> getFirstKCells() {
		LinkedList<Cell> mCells = new LinkedList<>();
		float max = Float.MIN_VALUE;

		for (int i = 0; i < this.firstK; i++)
			mCells.add(new Cell(-1, -1, Float.MIN_VALUE));

		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < b.length(); j++) {
				ListIterator<Cell> it = mCells.listIterator();
				float score = pairingMatrix[i][j].getScore();
				while (it.hasNext() && it.next().getScore() > score)
					;
				it.previous();
				it.add(pairingMatrix[i][j]);
				mCells.removeLast();

				if (score > max)
					max = score;
			}

		this.maximumScore = max;
		return mCells;
	}

	@Override
	protected boolean isFinalCell(Cell c) {
		return c.getScore() == 0 || (c.getX() == 0 && c.getY() == 0);
	}

}
