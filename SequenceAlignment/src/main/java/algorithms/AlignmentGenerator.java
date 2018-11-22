package algorithms;

import java.util.ListIterator;

class AlignmentGenerator {

	private AlignmentAlgorithm algorithm;
	private ListIterator<Cell> maximumCells;
	private ArrayListStack<CellTrace> s;

	public AlignmentGenerator(AlignmentAlgorithm algorithm) {
		this.algorithm = algorithm;
		this.maximumCells = algorithm.getMaximumCells().listIterator();
		this.s = new ArrayListStack<>();
		
		setNextMaximum();
		
	}

	public Alignment next() {
		while (!s.isEmpty()) {
			CellTrace top = s.pop();
			int x = top.c.getX();
			int y = top.c.getY();
			
			if (algorithm.isFinalCell(top.c)) {
				top.partialAlignment.add(algorithm.a.charAt(x), algorithm.b.charAt(y));
				top.partialAlignment.add(algorithm.a.substring(0, x), algorithm.b.substring(0, y));
				if (s.isEmpty())
					setNextMaximum();
				return top.partialAlignment.build();
			} else {
				if (top.c.getLeft() != null) {
					s.push(new CellTrace(top.c.getLeft(),
							new Alignment.Builder(top.partialAlignment.addOnB(algorithm.b.charAt(y))), false, true));

				}

				if (top.c.getUp() != null) {
					s.push(new CellTrace(top.c.getUp(),
							new Alignment.Builder(top.partialAlignment.addOnA(algorithm.a.charAt(x))), true, false));
				}

				if (top.c.getDiagonal() != null)
					s.push(new CellTrace(top.c.getDiagonal(), new Alignment.Builder(
							top.partialAlignment.add(algorithm.a.charAt(x), algorithm.b.charAt(y)))));
			}
		}

		return null;
	}

	private void setNextMaximum() {
		s.clear();
		if (maximumCells.hasNext()) {
			Cell init = maximumCells.next();
			s.push(new CellTrace(init, algorithm.a.substring(init.getX() + 1), algorithm.b.substring(init.getY() + 1)));
		}
	}

}
