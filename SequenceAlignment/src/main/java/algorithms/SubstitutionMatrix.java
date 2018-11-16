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

/**
 * This class model a {@link SubstitutionMatrix} that is defined by an alphabet
 * and the scores to pairing the letters. It's used on
 * {@link AlignmentAlgorithm} class for calculate the alignment between two
 * sequence.
 * 
 * @author Mario Randazzo
 *
 */
public class SubstitutionMatrix {

	/**
	 * A matrix n x n of integer, n is the size of the alphabet.
	 */
	private int[][] matrix;

	/**
	 * A string contains the letters of alphabet.
	 */
	private String alphabet;

	/**
	 * Build a {@link SubstitutionMatrix}.
	 * 
	 * @param matrix   a integer matrix
	 * @param alphabet the alphabet.
	 * @throws IllegalArgumentException if the size of matrix don't agree the
	 *                                  alphabet size.
	 */
	public SubstitutionMatrix(int[][] matrix, String alphabet) throws IllegalArgumentException {
		if (matrix[0].length != alphabet.length())
			throw new IllegalArgumentException("The size of substituion matrix don't agree the alphabet size.");

		this.matrix = matrix;
		this.alphabet = alphabet;
	}

	/**
	 * Get the pairing score of two {@link Character}.
	 * 
	 * @param x a {@link Character}
	 * @param y a {@link Character}
	 * @return the pairing score
	 * @throws IndexOutOfBoundsException if one of the character is not contained in
	 *                                   the alphabet.
	 */
	public int get(Character x, Character y) throws IndexOutOfBoundsException {
		int i = alphabet.indexOf(x);
		int j = alphabet.indexOf(y);
		return this.matrix[i][j];
	}

	/**
	 * Applay the {@link NormalizationStrategy} to this matrix.
	 * 
	 * @param n a {@link NormalizationStrategy}.
	 * @return a reference to this object.
	 */
	protected SubstitutionMatrix normalize(NormalizationStrategy n) {
		n.applay(matrix);
		return this;
	}

	/**
	 * Load a {@link SubstitutionMatrix} from a resource file.
	 * 
	 * @param url resource file.
	 * @return a {@link SubstitutionMatrix} loaded from resources.
	 * @throws IllegalArgumentException if the url or the file are not valid for load a {@link SubstitutionMatrix}.
	 * @throws FileNotFoundException if the resource file is not found.
	 * @throws IOException if there is an error in IO operations.
	 */
	public static SubstitutionMatrix load(URL url) throws IllegalArgumentException, FileNotFoundException, IOException {

		if (url == null)
			throw new IllegalArgumentException("Url not valid");
		File matrixFile = new File(URLDecoder.decode(url.getFile(), "UTF-8"));

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(matrixFile)));
		String alphabet = reader.readLine().toLowerCase();

		int size = alphabet.length();
		int[][] matrix = new int[size][size];

		try {
			for (int i = 0; i < size; i++) {
				String[] values = reader.readLine().split("[\t\n]+");
				for (int j = 0; j < size; j++)
					matrix[i][j] = Integer.parseInt(values[j]);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("File format invalid!");
		} finally {
			reader.close();
		}

		return new SubstitutionMatrix(matrix, alphabet);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Alphabet: " + Arrays.toString(alphabet.toCharArray()) + "\nMatrix:\n");
		for (int i = 0; i < alphabet.length(); i++) {
			for (int j = 0; j < alphabet.length(); j++)
				builder.append(String.format("%3d, ", matrix[i][j]));
			builder.append("\n");
		}
		builder.append("\n");

		return builder.toString();
	}

}
