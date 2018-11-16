package algorithms;

/**
 * This class model an alignment representation between to sequences. This
 * consist of three string, two for the sequence and one for the markers of
 * match or mismatch. This string are properly shifted with space character. For
 * Build an {@link Alignment} is advisable use a {@link Builder}.
 * 
 * @author Mario Randazzo
 *
 */
public class Alignment {

	/**
	 * The first sequence in the alignment.
	 */
	private String a;

	/**
	 * The marker | or *, indicates if there is match or mismatch between two
	 * charcters.
	 */
	private String markers;

	/**
	 * The second sequence in the alignment.
	 */
	private String b;

	/**
	 * Build an alignment. Don't check the validity of alignment.
	 * 
	 * @param a       first sequence in the alignment.
	 * @param markers the string of markers.
	 * @param b       second sequence in the alignment
	 */
	protected Alignment(String a, String markers, String b) {
		this.a = a;
		this.markers = markers;
		this.b = b;
	}

	/**
	 * Get the first sequence in the alignment.
	 * 
	 * @return a sequence
	 */
	public String getA() {
		return a;
	}

	/**
	 * Get the markers of match or mismatch.
	 * 
	 * @return a string of |'s or *'s.
	 */
	public String getMarkers() {
		return markers;
	}

	/**
	 * Get the second sequence in the alignment
	 * 
	 * @return a sequence
	 */
	public String getB() {
		return b;
	}

	@Override
	public String toString() {
		return "Alignment: \n" + a + "\n" + markers + "\n" + b + "\n\n";
	}

	/**
	 * This class provide an efficient builder for {@link Alignment} type. Use a
	 * {@link StringBuilder} for all operation.
	 * 
	 * @author Mario Randazzo
	 *
	 */
	public static class Builder {

		/**
		 * The builder for the first sequence.
		 */
		private StringBuilder partialA;

		/**
		 * The builder for the markers string.
		 */
		private StringBuilder partialMarker;

		/**
		 * The builder for the second sequence.
		 */
		private StringBuilder partialB;

		/**
		 * Build an alignment {@link Builder} from two initial string(that are not
		 * aligned).
		 * 
		 * @param initA the first string
		 * @param initB the second string
		 */
		public Builder(String initA, String initB) {
			this.partialA = new StringBuilder(initA);
			this.partialMarker = new StringBuilder();
			this.partialB = new StringBuilder(initB);
		}

		/**
		 * Build an alignment {@link Builder} from another Builder.
		 * 
		 * @param b {@link Builder}
		 */
		public Builder(Builder b) {
			this.partialA = new StringBuilder(b.partialA);
			this.partialB = new StringBuilder(b.partialB);
			this.partialMarker = new StringBuilder(b.partialMarker);
		}

		/**
		 * Add a {@link Character} on first sequence. On second sequence will be a gap
		 * character.
		 * 
		 * @param c the {@link Character} to add.
		 * @return a reference to this object.
		 */
		public Alignment.Builder addOnA(Character c) {
			partialA.insert(0, c);
			partialB.insert(0, '-');
			partialMarker.insert(0, ' ');

			return this;
		}

		/**
		 * Add a {@link Character} on second sequence. On first sequence will be a gap
		 * character.
		 * 
		 * @param c the {@link Character} to add.
		 * @return a reference to this object.
		 */
		public Alignment.Builder addOnB(Character c) {
			partialA.insert(0, '-');
			partialB.insert(0, c);
			partialMarker.insert(0, ' ');

			return this;
		}

		/**
		 * Add two {@link Character} on first and second sequence respectively.
		 * 
		 * @param cA the {@link Character} to add on first sequence.
		 * @param cB the {@link Character} to add on second sequence.
		 * @return a reference to this object.
		 */
		public Alignment.Builder add(Character cA, Character cB) {
			partialA.insert(0, cA);
			partialB.insert(0, cB);

			if (cA.equals(cB))
				partialMarker.insert(0, '|');
			else
				partialMarker.insert(0, '*');

			return this;
		}

		/**
		 * Add two string on first and second sequence respectively(this string are not aligned).
		 * 
		 * @param a the {@link String} to add on first sequence.
		 * @param b the {@link String} to add on second sequence.
		 * @return a reference to this object.
		 */
		public Alignment.Builder add(String a, String b) {
			int i, j;

			for (i = a.length() - 1, j = b.length() - 1; i >= 0 && j >= 0; i--, j--) {
				partialA.insert(0, a.charAt(i));
				partialB.insert(0, b.charAt(j));
				partialMarker.insert(0, ' ');
			}

			for (; i >= 0; i--) {
				partialA.insert(0, a.charAt(i));
				partialB.insert(0, ' ');
				partialMarker.insert(0, ' ');
			}

			for (; j >= 0; j--) {
				partialA.insert(0, ' ');
				partialB.insert(0, b.charAt(j));
				partialMarker.insert(0, ' ');
			}

			return this;
		}

		/**
		 * Build the current {@link Alignment}
		 * .
		 * @return the current {@link Alignment}.
		 */
		public Alignment build() {
			return new Alignment(partialA.toString(), partialMarker.toString(), partialB.toString());
		}

	}
}
