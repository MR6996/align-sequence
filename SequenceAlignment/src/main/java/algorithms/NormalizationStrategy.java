package algorithms;

/**
 * Define the strategy for normalize a {@link SubstitutionMatrix}.
 * 
 * @author Mario Randazzo
 *
 */
@FunctionalInterface
public interface NormalizationStrategy {

	/**
	 * This method must implement a procedure for normalize a integer matrix.
	 * 
	 * @param sostitutionMatrix a integer matrix.
	 */
	void applay(int[][] sostitutionMatrix);

}
