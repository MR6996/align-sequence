package algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;

public class SostitutionMatrix  {

	private int[][] matrix;
	private String alphabet;


	public SostitutionMatrix(int[][] matrix, String alphabet) {
		this.matrix = matrix;
		this.alphabet = alphabet;
	}
	
	public int get(Character x, Character y) throws IndexOutOfBoundsException {
		int i = alphabet.indexOf(x);
		int j = alphabet.indexOf(y);
		return this.matrix[i][j];
	}
	
	protected SostitutionMatrix normalize(Normalizator n) {
		n.applay(matrix);
		return this;
	}


	public static SostitutionMatrix load(URL url) throws IllegalArgumentException, 
														 FileNotFoundException,
														 IOException {
		
		if(url == null) throw new IllegalArgumentException("Url not valid");
		File matrixFile = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(matrixFile)));
		String alphabet = reader.readLine().toLowerCase();
		
		int size = alphabet.length();
		int[][] matrix = new int[size][size];
		
		try {
			for(int i = 0; i < size; i++) {
				String[] values = reader.readLine().split("[\t\n]+");
				for(int j = 0; j < size; j++)
					matrix[i][j] = Integer.parseInt(values[j]);
			}
		}
		catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("File format invalid!");
		}
		finally {
			reader.close();
		}
		
		return new SostitutionMatrix(matrix, alphabet);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Alphabet: " + Arrays.toString(alphabet.toCharArray()) + "\nMatrix:\n");
		for(int i = 0; i < alphabet.length(); i++) {
			for(int j = 0; j < alphabet.length(); j++)
				builder.append(String.format("%3d, ", matrix[i][j]));
			builder.append("\n");
		}
		builder.append("\n");
			
		return builder.toString();
	} 
	
	
}
