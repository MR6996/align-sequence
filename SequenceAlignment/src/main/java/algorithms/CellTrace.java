package algorithms;

class CellTrace {
	
	Cell c;
	Alignment.Builder partialAlignment;
	boolean isFromBottom;
	boolean isFromRight;
	
	public CellTrace(Cell c, String initA, String initB) {
		this.c = c;
		this.partialAlignment = new Alignment.Builder(initA, initB);
		this.isFromBottom = false;
		this.isFromRight = false;
	}
	
	public CellTrace(Cell c, Alignment.Builder builder, boolean isFromBottom, boolean isFromRight) {
		this.c = c;
		this.partialAlignment = builder;
		this.isFromBottom = isFromBottom;
		this.isFromRight = isFromRight;
	}
	
	public CellTrace(Cell c, Alignment.Builder builder) {
		this(c, builder, false, false);
	}
	
	
	
}
