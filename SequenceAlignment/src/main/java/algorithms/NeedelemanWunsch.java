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
	protected ArrayList<Cell> getMaximumCells() {
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
		
		this.maximumScore = max;
		
		return mCells;
	}

	@Override
	protected boolean isFinalCell(Cell c) {
		return c.notAny();
	}
	
	
	
}
