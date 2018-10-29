package algorithms;

public class SmithWaterman extends AlignmentAlgorithm {

	public SmithWaterman(String a, String b, int gop, float gep, SostitutionMatrix sostitutionMatrix) {
		super(a, b, gop, gep, sostitutionMatrix);
	}

	@Override
	protected void elaborateResults() {
		System.out.println(sostitutionMatrix);
	}

}
