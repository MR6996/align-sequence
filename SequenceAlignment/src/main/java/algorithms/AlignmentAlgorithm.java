package algorithms;

import java.util.ArrayList;

public abstract class AlignmentAlgorithm {
	
	protected String a;
	protected String b;
	protected int gop;
	protected float gep;
	protected Cell[][] pairingMatrix;
	protected SostitutionMatrix sostitutionMatrix;
	protected ArrayList<String> alignments;

	public AlignmentAlgorithm(String a, String b, 
						 int gop, float gep,
						 SostitutionMatrix sostitutionMatrix) {
		this.a = a.toLowerCase();
		this.b = b.toLowerCase();
		this.gop = gop;
		this.gep = gep;
		this.sostitutionMatrix = sostitutionMatrix;
		
		pairingMatrix = new Cell[a.length()][b.length()];
		alignments = new ArrayList<>();
		
		for(int i = 0; i < a.length(); i++)
			for(int j = 0; j < b.length(); j++)
				pairingMatrix[i][j] = new Cell(i,j);	
		fillPairingMatrix();
		elaborateResults();
	}

	public String getA() { return a; }
	
	public String getB() {return b; }

	public void setB(String b) { this.b = b; }

	public int getGop() { return gop;	}

	public float getGep() { return gep; }

	public Cell[][] getPairingMatrix() { return pairingMatrix; }

	
	private void fillPairingMatrix() {
		int lenA = a.length();
		int lenB = b.length();
		
		for(int i = 0; i < lenA; i++)
			for(int j = 0; j < lenB; j++) 
				pairingMatrix[i][j].updateScore(
					sostitutionMatrix.get(a.charAt(i), b.charAt(j))
				);
		
		for(int i = 1; i < lenA; i++) {
			float upPenalizedValue = 
				pairingMatrix[i-1][0].getScore() - ((pairingMatrix[i-1][0].getUp() != null) ? gep:gop);
			if(pairingMatrix[i][0].getScore() < upPenalizedValue) {
				pairingMatrix[i][0].updateScore(upPenalizedValue);
				pairingMatrix[i][0].setUp(pairingMatrix[i-1][0]);
			}
		}
		

		for(int i = 1; i < lenB; i++) {
			float leftPenalizedValue = 
				 pairingMatrix[0][i-1].getScore() - ((pairingMatrix[0][i-1].getLeft() != null) ? gep:gop);
			if(pairingMatrix[0][i].getScore() < leftPenalizedValue) {
				pairingMatrix[0][i].updateScore(leftPenalizedValue);
				pairingMatrix[0][i].setLeft(pairingMatrix[0][i-1]);
			}
		}
					
		for(int i = 1; i < lenA; i++)
			for(int j = 1; j < lenB; j++)
				maximize(i,j);
				
	}

	private void maximize(int i, int j) {
		float[] values = {pairingMatrix[i][j-1].getScore() - 
							(pairingMatrix[i][j-1].getLeft() != null ? gep:gop),
						  pairingMatrix[i-1][j].getScore() - 
							(pairingMatrix[i-1][j].getUp() != null ? gep:gop),
						  pairingMatrix[i-1][j-1].getScore() + pairingMatrix[i][j].getScore()};
		
		float max = 0;
		for(int k = 0; k < 3; k++)
			if(values[k] > max)
				max = values[k];
		
		if(values[0] == max)
			pairingMatrix[i][j].setLeft(pairingMatrix[i][j-1]);
		if(values[1] == max)
			pairingMatrix[i][j].setUp(pairingMatrix[i-1][j]);
		if(values[2] == max)
			pairingMatrix[i][j].setDiagonal(pairingMatrix[i-1][j-1]);
		
		pairingMatrix[i][j].updateScore(max);
	}

		
	protected abstract void elaborateResults();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pairing Matrix:\n");

		for(int i = 0; i < a.length(); i++) {
			for(int j = 0; j < b.length(); j++)
				builder.append(String.format("%6.1f, ", pairingMatrix[i][j].getScore()));
			builder.append("\n");
		}
		builder.append("\n");
		
		return builder.toString();
	}
	
	

}
