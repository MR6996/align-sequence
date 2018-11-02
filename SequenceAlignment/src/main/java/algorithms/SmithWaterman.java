package algorithms;

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
	protected void elaborateResults() {
		
	}

}
