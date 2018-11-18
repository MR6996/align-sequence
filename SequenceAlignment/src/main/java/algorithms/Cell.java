package algorithms;

/**
 * This class model the data type for pairing matrix to use in
 * {@link AlignmentAlgorithm}.
 * 
 * @author Mario Randazzo
 *
 */
public class Cell implements Comparable<Cell> {

	/**
	 * The coordinate in the pairing matrix.
	 */
	private int x;

	/**
	 * The coordinate in the pairing matrix.
	 */
	private int y;

	/**
	 * The score of alignment up to the current cell.
	 */
	private float score;

	/**
	 * A reference to the {@link Cell} on the left, if this maximize the current
	 * score.
	 */
	private Cell left;

	/**
	 * A reference to the {@link Cell} above, if this maximize the current score.
	 */
	private Cell up;

	/**
	 * A reference to the {@link Cell} on diagonal, if this maximize the current
	 * score.
	 */
	private Cell diagonal;

	/**
	 * Build a Cell.
	 * 
	 * @param x     the coordinate of cell.
	 * @param y     the coordinate of cell.
	 * @param score the initial score of cell.
	 */
	public Cell(int x, int y, float score) {
		this.x = x;
		this.y = y;
		this.score = score;
		left = null;
		up = null;
		diagonal = null;
	}

	/**
	 * Build a Cell. Default score is 0.
	 * 
	 * @param x the coordinate of cell.
	 * @param y the coordinate of cell.
	 */
	public Cell(int x, int y) {
		this(x, y, 0.0f);
	}

	/**
	 * Get the x coordinate.
	 * 
	 * @return the x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y coordinate.
	 * 
	 * @return the y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Get the score of current cell.
	 * 
	 * @return the score of current cell.
	 */
	public float getScore() {
		return score;
	}

	/**
	 * Set the score of current cell.
	 * 
	 * @param value a float value.
	 */
	public void updateScore(float value) {
		score = value;
	}

	/**
	 * Get the {@link Cell} on the left if set.
	 * 
	 * @return the {@link Cell} on left, null if not set.
	 */
	public Cell getLeft() {
		return left;
	}

	/**
	 * Set the {@link Cell} on the left.
	 * 
	 * @param left a {@link Cell}
	 */
	public void setLeft(Cell left) {
		this.left = left;
	}

	/**
	 * Get the {@link Cell} above if set.
	 * 
	 * @return the {@link Cell} above, null if not set.
	 */
	public Cell getUp() {
		return up;
	}

	/**
	 * Set the {@link Cell} above.
	 * 
	 * @param up a {@link Cell}
	 */
	public void setUp(Cell up) {
		this.up = up;
	}

	/**
	 * Get the {@link Cell} on diagonal if set.
	 * 
	 * @return the {@link Cell} on diagonal, null if not set.
	 */
	public Cell getDiagonal() {
		return diagonal;
	}

	/**
	 * Set the {@link Cell} on diagonal.
	 * 
	 * @param diagonal a {@link Cell}
	 */
	public void setDiagonal(Cell diagonal) {
		this.diagonal = diagonal;
	}

	/**
	 * Check if none cell on left, above and diagonal is set.
	 * 
	 * @return true if none cell on left, above and on diagonal is set.
	 */
	public boolean notAny() {
		return left == null && up == null && diagonal == null;
	}

	@Override
	public String toString() {
		return "Cell [" + x + ", " + y + ", score=" + score + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(score);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
			return false;
		return true;
	}

	@Override
	public int compareTo(Cell o) {
		return Float.compare(o.score, this.score);
	}

}
