package algorithms;

import java.util.ArrayList;

public abstract class AlignmentAlgorithm {
	
	protected String a;
	protected String b;
	protected int gop;
	protected float gep;
	protected Cell[][] pairingMatrix;
	protected SostitutionMatrix sostitutionMatrix;
	protected ArrayList<Alignment> alignments;
	protected float maximumScore;

	public AlignmentAlgorithm(String a, String b, 
						 int gop, float gep,
						 SostitutionMatrix sostitutionMatrix) {
		this.a = a.toLowerCase();
		this.b = b.toLowerCase();
		this.gop = gop;
		this.gep = gep;
		this.sostitutionMatrix = sostitutionMatrix;
		
		pairingMatrix = new Cell[a.length()][b.length()];
		
		for(int i = 0; i < a.length(); i++) 
			pairingMatrix[i][0] = new Cell(i,0, Math.max(0.0f, this.sostitutionMatrix.get(this.a.charAt(i), this.b.charAt(0))));
		
		
		for(int j = 1; j < b.length(); j++)
			pairingMatrix[0][j] = new Cell(0,j, Math.max(0.0f, this.sostitutionMatrix.get(this.a.charAt(0), this.b.charAt(j))));
		
		for(int i = 1; i < a.length(); i++)
			for(int j = 1; j < b.length(); j++)
				pairingMatrix[i][j] = new Cell(i,j, sostitutionMatrix.get(this.a.charAt(i), this.b.charAt(j)));
				
		updatePairingMatrix();
	}

	public String getA() { return a; }
	
	public String getB() {return b; }

	public int getGop() { return gop; }

	public float getGep() { return gep; }

	public Cell[][] getPairingMatrix() { return pairingMatrix; }

	
	private void updatePairingMatrix() {
		int lenA = a.length();
		int lenB = b.length();
		
		float penalty = gop;
		for(int i = 1; i < lenA; i++) {			
			float upPenalizedValue = pairingMatrix[i-1][0].getScore() - penalty;
			if(upPenalizedValue >= pairingMatrix[i][0].getScore()  ) {
				pairingMatrix[i][0].updateScore(upPenalizedValue);
				pairingMatrix[i][0].setUp(pairingMatrix[i-1][0]);
				penalty = gep;
			}
			else penalty = gop;
		}
		
		penalty = gop;
		for(int i = 1; i < lenB; i++) {
			float leftPenalizedValue = pairingMatrix[0][i-1].getScore() - penalty;
			if(leftPenalizedValue >= pairingMatrix[0][i].getScore()) {
				pairingMatrix[0][i].updateScore(leftPenalizedValue);
				pairingMatrix[0][i].setLeft(pairingMatrix[0][i-1]);
				penalty = gep;
			}
			else penalty = gop;
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

	private void elaborateResults() {
		ArrayList<Cell> mCells = getMaximumCells();
		
		for(Cell c : mCells) {
			Stack<CellTrace> s = new ArrayListStack<>();
			s.push(new CellTrace(c, a.substring(c.getX()+1), b.substring(c.getY()+1)));
			
			while( !s.isEmpty()) {
				CellTrace top = s.pop();
				int x = top.c.getX();
				int y = top.c.getY();
				
				if(isFinalCell(top.c)) {
					top.partialAlignment.add(a.charAt(x), b.charAt(y));
					top.partialAlignment.add(a.substring(0, x), b.substring(0, y));
					alignments.add(top.partialAlignment.build());
				}
				else {
					if(top.c.getLeft() != null) {
						s.push( 
							new CellTrace(top.c.getLeft(), 
										  new Alignment.Builder(top.partialAlignment.addOnB(b.charAt(y))),
										  false, true)
						);
						
					}

					if(top.c.getUp() != null) {
						s.push( 
							new CellTrace(top.c.getUp(), 
										  new Alignment.Builder(top.partialAlignment.addOnA(a.charAt(x))),
										  true, false)
						);
					}
					
					if(top.c.getDiagonal() != null)
						s.push( 
							new CellTrace(top.c.getDiagonal(), 
										  new Alignment.Builder(top.partialAlignment.add(a.charAt(x), b.charAt(y))))
						);
				}
			}
		}
	}
	
	protected abstract boolean isFinalCell(Cell c);
	
	protected abstract ArrayList<Cell> getMaximumCells();
	
		
	public float getMaximumScore() { return maximumScore; }

	public ArrayList<Alignment> getAlignments() { 
		alignments = null;
		alignments = new ArrayList<>();
		elaborateResults();
		return alignments; 
	}

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
