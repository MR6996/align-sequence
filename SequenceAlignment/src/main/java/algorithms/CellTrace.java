package algorithms;

import algorithms.Alignment.Builder;

/**
 * A support data type, for calculate the alignment on pairing matrix. This type
 * wrap a {@link Cell} and trace the {@link Alignment} up to this cell.
 * 
 * @author Mario Randazzo
 *
 */
class CellTrace {

	/**
	 * The cell to wrap
	 */
	Cell c;

	/**
	 * The {@link Alignment.Builder}
	 */
	Alignment.Builder partialAlignment;

	/**
	 * Indicate if on the backtracking path this cell come from bottom cell.
	 */
	boolean isFromBottom;

	/**
	 * Indicate if on the backtracking path this cell come from right cell.
	 */
	boolean isFromRight;

	/**
	 * Build a {@link CellTrace} from two initial string(That are not aligned).
	 * 
	 * @param c     The cell to wrap.
	 * @param initA the initial sequence not aligned.
	 * @param initB the initial sequence not aligned.
	 */
	public CellTrace(Cell c, String initA, String initB) {
		this.c = c;
		this.partialAlignment = new Alignment.Builder(initA, initB);
		this.isFromBottom = false;
		this.isFromRight = false;
	}

	/**
	 * Build a {@link CellTrace} from a {@link Builder} and the flag values.
	 * 
	 * @param c            The cell to wrap.
	 * @param builder      The Alignment Builder up to this cell.
	 * @param isFromBottom true if on the backtracking path this cell come from
	 *                     bottom cell.
	 * @param isFromRight  true if on the backtracking path this cell come from
	 *                     right cell.
	 */
	public CellTrace(Cell c, Alignment.Builder builder, boolean isFromBottom, boolean isFromRight) {
		this.c = c;
		this.partialAlignment = builder;
		this.isFromBottom = isFromBottom;
		this.isFromRight = isFromRight;
	}

	/**
	 * Build a {@link CellTrace} from a {@link Builder}, both the flag are set to
	 * false.
	 * 
	 * @param c       The cell to wrap.
	 * @param builder The Alignment Builder up to this cell.
	 */
	public CellTrace(Cell c, Alignment.Builder builder) {
		this(c, builder, false, false);
	}

}
