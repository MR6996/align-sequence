import java.io.IOException;

import algorithms.NeedelemanWunsch;
import algorithms.SostitutionMatrix;

public class Main {

	public static void main(String[] args) {
		
		/*SostitutionMatrix sostMat = null;
		try {
			sostMat = SostitutionMatrix.load(Main.class.getResource("blosum/blosum62"));
		} catch (IllegalArgumentException | IOException  e) {
			e.printStackTrace();
		}*/
		

		SostitutionMatrix sostMat = new SostitutionMatrix(
				new int[][] {{1, 0, 0, 0},
							 {0, 1, 0, 0},
							 {0, 0, 1, 0},
							 {0, 0, 0, 1}}, 
				"acgt"
		);
		
		

		NeedelemanWunsch nw = new NeedelemanWunsch("ACGTCGTGCTGTGC", "GTGCAGTGCTAGCTGTACACTGT", 5, 0.1f, sostMat);
		System.out.println(nw);

		for(String alignment : nw.getAlignments())
			System.out.println("Alignment:\n" + alignment + "\n");
	}

}
