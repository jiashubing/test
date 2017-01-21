package sudoku.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sudoku.model.Square;


public class SolutionAlgorithm {

	public boolean valid = false;
	
	/**
	 * 
	 * @param sudokuList
	 * @return
	 */
	public List<Square> convertStringToSquareList(String sudokuList){
		List<Square> ans = new ArrayList<Square>();
		Integer y;
		for(int i=0; i<sudokuList.length(); i++){
			y = Integer.parseInt(sudokuList.substring(i, i+1));
			ans.add( new Square(i,y));
		}
		return ans;
	}
	
	public boolean isValid(List<Square> zoneSquares) {
		if (areValidZones(zoneSquares) && areValidRows(zoneSquares) && areValidCols(zoneSquares) ) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean areValidZones(List<Square> zoneSquares) {
		boolean valid = true;
		List<Square> zoneSquares1 = new ArrayList<Square>();
		List<Square> zoneSquares2 = new ArrayList<Square>();
		List<Square> zoneSquares3 = new ArrayList<Square>();
		List<Square> zoneSquares4 = new ArrayList<Square>();
		List<Square> zoneSquares5 = new ArrayList<Square>();
		List<Square> zoneSquares6 = new ArrayList<Square>();
		List<Square> zoneSquares7 = new ArrayList<Square>();
		List<Square> zoneSquares8 = new ArrayList<Square>();
		List<Square> zoneSquares9 = new ArrayList<Square>();
		
		for (int i = 0; i < zoneSquares.size() && valid; i++) {
			Square zoneSquare = zoneSquares.get(i);
			if (zoneSquare.getZone() == 1) {
				zoneSquares1.add(zoneSquare);
				if (zoneSquares1.size() == 9) {
					valid = isUnique(zoneSquares1);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 2) {
				zoneSquares2.add(zoneSquare);
				if (zoneSquares2.size() == 9) {
					valid = isUnique(zoneSquares2);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 3) {
				zoneSquares3.add(zoneSquare);
				if (zoneSquares3.size() == 9) {
					valid = isUnique(zoneSquares3);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 4) {
				zoneSquares4.add(zoneSquare);
				if (zoneSquares4.size() == 9) {
					valid = isUnique(zoneSquares4);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 5) {
				zoneSquares5.add(zoneSquare);
				if (zoneSquares5.size() == 9) {
					valid = isUnique(zoneSquares5);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 6) {
				zoneSquares6.add(zoneSquare);
				if (zoneSquares6.size() == 9) {
					valid = isUnique(zoneSquares6);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 7) {
				zoneSquares7.add(zoneSquare);
				if (zoneSquares7.size() == 9) {
					valid = isUnique(zoneSquares7);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 8) {
				zoneSquares8.add(zoneSquare);
				if (zoneSquares8.size() == 9) {
					valid = isUnique(zoneSquares8);
					if (!valid) {
						return false;
					}
				}
			}
			if (zoneSquare.getZone() == 9) {
				zoneSquares9.add(zoneSquare);
				if (zoneSquares9.size() == 9) {
					valid = isUnique(zoneSquares9);
					if (!valid) {
						return false;
					}
				}
			}
			
		}
		return valid;
	}
	
	public boolean areValidRows(List<Square> rowSquares) {
		boolean valid = true;
		List<Square> rowSquares1 = new ArrayList<Square>();
		List<Square> rowSquares2 = new ArrayList<Square>();
		List<Square> rowSquares3 = new ArrayList<Square>();
		List<Square> rowSquares4 = new ArrayList<Square>();
		List<Square> rowSquares5 = new ArrayList<Square>();
		List<Square> rowSquares6 = new ArrayList<Square>();
		List<Square> rowSquares7 = new ArrayList<Square>();
		List<Square> rowSquares8 = new ArrayList<Square>();
		List<Square> rowSquares9 = new ArrayList<Square>();
		
		for (int i = 0; i < rowSquares.size() && valid; i++) {
			Square rowSquare = rowSquares.get(i);
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
		return valid;
	}
	
	public boolean areValidCols(List<Square> colSquares) {
		boolean valid = true;
		List<Square> colSquares1 = new ArrayList<Square>();
		List<Square> colSquares2 = new ArrayList<Square>();
		List<Square> colSquares3 = new ArrayList<Square>();
		List<Square> colSquares4 = new ArrayList<Square>();
		List<Square> colSquares5 = new ArrayList<Square>();
		List<Square> colSquares6 = new ArrayList<Square>();
		List<Square> colSquares7 = new ArrayList<Square>();
		List<Square> colSquares8 = new ArrayList<Square>();
		List<Square> colSquares9 = new ArrayList<Square>();
		
		for (int i = 0; i < colSquares.size() && valid; i++) {
			Square colSquare = colSquares.get(i);
			if (colSquare.getZone() == 1) {
				colSquares1.add(colSquare);
				if (colSquares1.size() == 9) {
					valid = isUnique(colSquares1);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 2) {
				colSquares2.add(colSquare);
				if (colSquares2.size() == 9) {
					valid = isUnique(colSquares2);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 3) {
				colSquares3.add(colSquare);
				if (colSquares3.size() == 9) {
					valid = isUnique(colSquares3);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 4) {
				colSquares4.add(colSquare);
				if (colSquares4.size() == 9) {
					valid = isUnique(colSquares4);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 5) {
				colSquares5.add(colSquare);
				if (colSquares5.size() == 9) {
					valid = isUnique(colSquares5);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 6) {
				colSquares6.add(colSquare);
				if (colSquares6.size() == 9) {
					valid = isUnique(colSquares6);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 7) {
				colSquares7.add(colSquare);
				if (colSquares7.size() == 9) {
					valid = isUnique(colSquares7);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 8) {
				colSquares8.add(colSquare);
				if (colSquares8.size() == 9) {
					valid = isUnique(colSquares8);
					if (!valid) {
						return false;
					}
				}
			}
			if (colSquare.getZone() == 9) {
				colSquares9.add(colSquare);
				if (colSquares9.size() == 9) {
					valid = isUnique(colSquares9);
					if (!valid) {
						return false;
					}
				}
			}
			
		}
		return valid;
	}
	
	public boolean isUnique(List<Square> zoneSquares) {
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < zoneSquares.size(); i++) {
			list.add(zoneSquares.get(i).getValue());
		}
		
		Set<Integer> set = new HashSet<Integer>(list);
		if (set.size() < zoneSquares.size()) {
			return false;
		} else {
			return true;
		}
	}

}
