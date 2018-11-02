package algorithms;

public class Alignment {
	
	private String a;
	private String markers;
	private String b;
	
	public Alignment(String a, String markers, String b) {
		this.a = a;
		this.markers = markers;
		this.b = b;
	}

	public String getA() { return a; }

	public String getMarkers() { return markers; }

	public String getB() { return b; }

	@Override
	public String toString() {
		return "Alignment: \n" + a + "\n" + markers + "\n" + b + "\n\n";
	}



	public static class Builder {
		
		private StringBuilder partialA;
		private StringBuilder partialMarker;
		private StringBuilder partialB;
		
		public Builder(String initA, String initB) {
			this.partialA = new StringBuilder(initA);
			this.partialMarker = new StringBuilder();
			this.partialB = new StringBuilder(initB);
		}
		
		public Builder(Builder b) {
			this.partialA = new StringBuilder(b.partialA);
			this.partialB = new StringBuilder(b.partialB);
			this.partialMarker = new StringBuilder(b.partialMarker);
		}
		
		public Alignment.Builder addOnA(Character c) {
			partialA.insert(0, c);
			partialB.insert(0, '-');
			partialMarker.insert(0, ' ');
			
			return this;
		}
		
		public Alignment.Builder addOnB(Character c) {
			partialA.insert(0, '-');
			partialB.insert(0, c);
			partialMarker.insert(0, ' ');
			
			return this;
		}
		
		public Alignment.Builder add(Character cA, Character cB) {
			partialA.insert(0, cA);
			partialB.insert(0, cB);
			
			if(cA.equals(cB)) partialMarker.insert(0, '|');
			else partialMarker.insert(0, '*');
			
			return this;
		}
		
		public Alignment.Builder add(String a, String b) {
			int i, j;
			
			for(i = a.length()-1 , j = b.length()-1; i >= 0 && j >= 0; i--, j--) {
				partialA.insert(0, a.charAt(i));
				partialB.insert(0, b.charAt(j));
				partialMarker.insert(0, ' ');
			}
			
			for(; i >= 0; i--) {
				partialA.insert(0, a.charAt(i));
				partialB.insert(0, ' ');
				partialMarker.insert(0, ' ');
			}
			
			for(; j >= 0; j--) {
				partialA.insert(0, ' ');
				partialB.insert(0, b.charAt(j));
				partialMarker.insert(0, ' ');
			}
			
			return this;
		}
		
		public Alignment build() { 
			return new Alignment(partialA.toString(), partialMarker.toString(), partialB.toString()); 
		}
		
	}
}
