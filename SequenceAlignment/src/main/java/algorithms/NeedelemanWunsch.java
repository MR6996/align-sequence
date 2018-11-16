package algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implement the Needeleman-Wunsch algorithm for align two sequence.
 * See {@link AlignmentAlgorithm} for details.
 * 
 * @author Mario Randazzo
 *
 */
public class NeedelemanWunsch extends AlignmentAlgorithm {

	/**
	 * Build a {@link AlignmentAlgorithm} with a {@link SubstitutionMatrix}
	 * normalized. In this case the default normalization is sum in absolute value
	 * the minimum value in substitution matrix.
	 * 
	 * @param a                  the sequence a.
	 * @param b                  the sequence b.
	 * @param gop                the gap opening penalty.
	 * @param gep                the gap extension penalty.
	 * @param substitutionMatrix the substitution matrix to use.
	 */
	public NeedelemanWunsch(String a, String b, int gop, float gep, SubstitutionMatrix substitutionMatrix) {
		super(a, b, gop, gep, substitutionMatrix.normalize(m -> {
			int min = m[0][0];

			for (int i = 0; i < m.length; i++)
				for (int j = 0; j < m.length; j++)
					if (m[i][j] < min)
						min = m[i][j];

			min = Math.abs(min);

			for (int i = 0; i < m.length; i++)
				for (int j = 0; j < m.length; j++)
					m[i][j] += min;
		}));
	}

	/**
	 * Build a {@link AlignmentAlgorithm} with a {@link SubstitutionMatrix}
	 * normalized. The normalization is defined by the {@link NormalizationStrategy}.
	 *
	 * @param a                  the sequence a.
	 * @param b                  the sequence b.
	 * @param gop                the gap opening penalty.
	 * @param gep                the gap extension penalty.
	 * @param substitutionMatrix the substitution matrix to use.
	 * @param n                  the {@link NormalizationStrategy}y to use.
	 */
	public NeedelemanWunsch(String a, String b, int gop, float gep, SubstitutionMatrix substitutionMatrix,
			NormalizationStrategy n) {
		super(a, b, gop, gep, substitutionMatrix.normalize(n));
	}

	@Override
	protected List<Cell> getMaximumCells() {
		ArrayList<Cell> mCells = new ArrayList<>();

		int lenA = a.length();
		int lenB = b.length();

		float max = 0;
		for (int i = 0; i < lenA; i++)
			if (pairingMatrix[i][lenB - 1].getScore() > max)
				max = pairingMatrix[i][lenB - 1].getScore();

		for (int i = 0; i < lenB - 1; i++)
			if (pairingMatrix[lenA - 1][i].getScore() > max)
				max = pairingMatrix[lenA - 1][i].getScore();

		for (int i = 0; i < lenA; i++)
			if (pairingMatrix[i][lenB - 1].getScore() == max)
				mCells.add(pairingMatrix[i][lenB - 1]);

		for (int i = 0; i < lenB - 1; i++)
			if (pairingMatrix[lenA - 1][i].getScore() == max)
				mCells.add(pairingMatrix[lenA - 1][i]);

		this.maximumScore = max;

		return mCells;
	}

	@Override
	protected boolean isFinalCell(Cell c) {
		return c.notAny();
	}

}
