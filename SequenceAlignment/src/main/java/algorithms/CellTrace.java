package algorithms;

class CellTrace {
	
	protected Cell c;
	protected Alignment.Builder partialAlignment;
	
	public CellTrace(Cell c, String initA, String initB) {
		this.c = c;
		this.partialAlignment = new Alignment.Builder(initA, initB);
	}
	
	public CellTrace(Cell c, Alignment.Builder builder) {
		this.c = c;
		this.partialAlignment = builder;
	}
	
	
	
}
