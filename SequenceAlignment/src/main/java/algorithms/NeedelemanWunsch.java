package algorithms;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			if(c.getX() == a.length()-1)
				s.push( 
					new CellTrace(c, 
								  new StringBuilder(), 
								  new StringBuilder(b.substring(c.getY()+1)))
				);
			else
				s.push( 
					new CellTrace(c, 
								  new StringBuilder(a.substring(c.getX()+1)), 
								  new StringBuilder())
				);
			
			while( !s.isEmpty()) {
				CellTrace top = s.pop();
				int x = top.c.getX();
				int y = top.c.getY();
				
				if(top.c.isTerminal()) {
					if(x == 0) { 
						String padding = Stream.generate(()->" ").limit(y).collect(Collectors.joining());
						alignments.add(
							padding + a.substring(0, 1) + top.partialA + 
							"\n" + 
							b.substring(0, y+1) +top.partialB
						);
					}
					else {
						String padding = Stream.generate(()->" ").limit(x).collect(Collectors.joining());
						alignments.add(
							a.substring(0, x+1) + top.partialA + 
							"\n" + 
							padding + b.substring(0, 1) + top.partialB
						);
					}
				}
				else {
					if(top.c.getLeft() != null)
						s.push( 
							new CellTrace(top.c.getLeft(), 
										  new StringBuilder(top.partialA.insert(0, "-")), 
										  new StringBuilder(top.partialB.insert(0, b.charAt(y))))
						);

					if(top.c.getUp() != null)
						s.push( 
							new CellTrace(top.c.getUp(), 
										  new StringBuilder(top.partialA.insert(0, a.charAt(x))), 
										  new StringBuilder(top.partialB.insert(0, "-")))
						);
					
					if(top.c.getDiagonal() != null)
						s.push( 
							new CellTrace(top.c.getDiagonal(), 
										  new StringBuilder(top.partialA.insert(0, a.charAt(x))), 
										  new StringBuilder(top.partialB.insert(0, b.charAt(y))))
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
