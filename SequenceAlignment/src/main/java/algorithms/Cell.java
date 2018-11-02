package algorithms;

public class Cell {
	
	private int x;
	private int y;

	private float score;
	private Cell left;
	private Cell up;
	private Cell diagonal;
	
	public Cell(int x, int y, float score) {
		this.x = x;
		this.y = y;
		this.score = score;
		left = null;
		up = null;
		diagonal = null;
	}
	
	public Cell(int x, int y) {
		this(x,y, 0.0f);
	}
	
	
	public int getX() { return x; }

	public int getY() { return y; }
	
	public float getScore() { return score; }
	
	public void updateScore(float value) { score = value; }
	
	public Cell getLeft() { return left; }

	public void setLeft(Cell left) { this.left = left; }

	public Cell getUp() { return up; }

	public void setUp(Cell up) { this.up = up; }

	public Cell getDiagonal() {	return diagonal; }

	public void setDiagonal(Cell diagonal) { this.diagonal = diagonal; }

	public boolean notAny() { return left == null && up == null && diagonal == null; }
	
	
}
