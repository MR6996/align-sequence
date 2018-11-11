package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SmithWaterman extends AlignmentAlgorithm {

	private Integer firstK;
	private Float threshold;

	public SmithWaterman(String a, String b, int gop, float gep, SostitutionMatrix sostitutionMatrix, int firstK) {
		super(a, b, gop, gep, sostitutionMatrix);
		this.firstK = firstK;
		this.threshold = null;
	}

	public SmithWaterman(String a, String b, int gop, float gep, SostitutionMatrix sostitutionMatrix, float threshold) {
		super(a, b, gop, gep, sostitutionMatrix);
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
		return mCells;
	}

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
		return c.getScore() == 0;
	}

}
