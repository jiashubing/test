package sudoku.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sudoku.model.Square;

public class SolutionAlgorithm {

	public List<Square> convertStringToSquareList(String sudokuList){
		List<Square> ans = new ArrayList<>();
		Integer y;
		for(int i=0; i<sudokuList.length(); i++){
			y = Integer.parseInt(sudokuList.substring(i, i+1));
			ans.add( new Square(i,y));
		}
		return ans;
	}
	
	public boolean isValid(List<Square> zoneSquares) {
		return areValidZones(zoneSquares) && areValidRows(zoneSquares) && areValidCols(zoneSquares);
	}
	
	
	private boolean areValidZones(List<Square> zoneSquares) {
		return areValidParam(zoneSquares);
	}
	
	private boolean areValidRows(List<Square> rowSquares) {
		return areValidParam(rowSquares);
	}

	private boolean areValidParam(List<Square> rowSquares) {
		List<Square> rowSquares1 = new ArrayList<>();
		List<Square> rowSquares2 = new ArrayList<>();
		List<Square> rowSquares3 = new ArrayList<>();
		List<Square> rowSquares4 = new ArrayList<>();
		List<Square> rowSquares5 = new ArrayList<>();
		List<Square> rowSquares6 = new ArrayList<>();
		List<Square> rowSquares7 = new ArrayList<>();
		List<Square> rowSquares8 = new ArrayList<>();
		List<Square> rowSquares9 = new ArrayList<>();

		for (Square rowSquare : rowSquares) {
			boolean valid;
			if (rowSquare.getZone() == 1) {
				rowSquares1.add(rowSquare);
				if (rowSquares1.size() == 9) {
					valid = isUnique(rowSquares1);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 2) {
				rowSquares2.add(rowSquare);
				if (rowSquares2.size() == 9) {
					valid = isUnique(rowSquares2);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 3) {
				rowSquares3.add(rowSquare);
				if (rowSquares3.size() == 9) {
					valid = isUnique(rowSquares3);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 4) {
				rowSquares4.add(rowSquare);
				if (rowSquares4.size() == 9) {
					valid = isUnique(rowSquares4);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 5) {
				rowSquares5.add(rowSquare);
				if (rowSquares5.size() == 9) {
					valid = isUnique(rowSquares5);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 6) {
				rowSquares6.add(rowSquare);
				if (rowSquares6.size() == 9) {
					valid = isUnique(rowSquares6);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 7) {
				rowSquares7.add(rowSquare);
				if (rowSquares7.size() == 9) {
					valid = isUnique(rowSquares7);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 8) {
				rowSquares8.add(rowSquare);
				if (rowSquares8.size() == 9) {
					valid = isUnique(rowSquares8);
					if (!valid) {
						return false;
					}
				}
			}
			if (rowSquare.getZone() == 9) {
				rowSquares9.add(rowSquare);
				if (rowSquares9.size() == 9) {
					valid = isUnique(rowSquares9);
					if (!valid) {
						return false;
					}
				}
			}

		}
		return true;
	}

	private boolean areValidCols(List<Square> colSquares) {
		return areValidParam(colSquares);
	}
	
	private boolean isUnique(List<Square> zoneSquares) {
		List<Integer> list = new ArrayList<Integer>();

		for (Square zoneSquare : zoneSquares) {
			list.add(zoneSquare.getValue());
		}
		
		Set<Integer> set = new HashSet<Integer>(list);
		return set.size() >= zoneSquares.size();
	}

}
