package algorithms;

import java.util.ArrayList;

public class NeedelemanWunsch extends AlignmentAlgorithm {

	public NeedelemanWunsch(String a, String b, int gop, float gep, SostitutionMatrix sostitutionMatrix) {
		super(a, b, gop, gep, sostitutionMatrix.normalize(m -> {
			int min = m[0][0];

			for(int i = 0; i < m.length; i++)
				for(int j = 0; j < m.length; j++)
					if(m[i][j] < min) min = m[i][j];
			
			min = Math.abs(min);
			
			for(int i = 0; i < m.length; i++)
				for(int j = 0; j < m.length; j++)
					m[i][j] += min;
		}));
	}
	
	public NeedelemanWunsch(String a, String b, int gop, float gep, SostitutionMatrix sostitutionMatrix, Normalizator n) {
		super(a,b, gop, gep, sostitutionMatrix.normalize(n));
	}

	
	@Override
	protected void elaborateResults() {
		ArrayList<Cell> mCells = getMaximumCells();
		
		for(Cell c : mCells) {
			Stack<CellTrace> s = new ArrayListStack<>();
			s.push(new CellTrace(c, a.substring(c.getX()+1), b.substring(c.getY()+1)));
			
			while( !s.isEmpty()) {
				CellTrace top = s.pop();
				int x = top.c.getX();
				int y = top.c.getY();
				
				if(top.c.isTerminal()) {
					top.partialAlignment.add(a.charAt(x), b.charAt(y));
					top.partialAlignment.add(a.substring(0, x), b.substring(0, y));
					alignments.add(top.partialAlignment.build());
				}
				else {
					if(top.c.getLeft() != null)
						s.push( 
							new CellTrace(top.c.getLeft(), 
										  new Alignment.Builder(top.partialAlignment.addOnB(b.charAt(y))))
						);

					if(top.c.getUp() != null)
						s.push( 
							new CellTrace(top.c.getUp(), 
										  new Alignment.Builder(top.partialAlignment.addOnA(a.charAt(x))))
						);
					
					if(top.c.getDiagonal() != null)
						s.push( 
							new CellTrace(top.c.getDiagonal(), 
										  new Alignment.Builder(top.partialAlignment.add(a.charAt(x), b.charAt(y))))
						);
				}
			}
		}
		
	}
	
	private ArrayList<Cell> getMaximumCells() {
		ArrayList<Cell> mCells = new ArrayList<>();
		
		int lenA = a.length();
		int lenB = b.length();

		float  max = 0;
		for(int i = 0; i < lenA; i++)
			if(pairingMatrix[i][lenB-1].getScore() > max)
				max = pairingMatrix[i][lenB-1].getScore();
			
		for(int i = 0; i < lenB-1; i++)
			if(pairingMatrix[lenA-1][i].getScore() > max)
				max = pairingMatrix[lenA-1][i].getScore();
		
		for(int i = 0; i < lenA; i++)
			if(pairingMatrix[i][lenB-1].getScore() == max)
				mCells.add(pairingMatrix[i][lenB-1]);
		
		for(int i = 0; i < lenB-1; i++)
			if(pairingMatrix[lenA-1][i].getScore() == max)
				mCells.add(pairingMatrix[lenA-1][i]);
		
		return mCells;
	}
	
	
	
}
