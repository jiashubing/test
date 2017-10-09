package sudoku.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import sudoku.model.Square;


/**
 * 负责生成棋盘
 * @author JiaShubing
 */
public class GenerationAlgorithm {
	
	private List<Square> squares;
	private List<Square> solution;
	private List<List<Square>> puzzle;
	private boolean flag;
	
	public GenerationAlgorithm() {
		this.squares = generate();
		this.flag = true;
		this.puzzle = createPuzzle(this.squares);
	}

	private int getRandomNumber(Integer[] available) {
		int random;
		Random r = new Random();
		random = r.nextInt(available.length);
		return available[random];
	}
	
	//生成了一个有解的数独
	private List<Square> generate() {
		List<Square> squares = new ArrayList<>();
		List<Integer[]> available = new ArrayList<>();
		//81个格子，每一个格子都可能是9个数字，从1到9赋值
		for(int x = 0; x < 81; x++) {
			Integer[] numbers = new Integer[9];
			for(int z = 0; z < 9; z++) {
				numbers[z] = z + 1;
			}
			available.add(numbers);
		}
		
		int i = 0; // index of fields
		while (i < 81) {
			
			if(available.get(i).length != 0) {
				
				// random value
				Integer n = getRandomNumber(available.get(i));
							
				// index of random number
				int l = Arrays.asList(available.get(i)).indexOf(n);
				
				Square square = new Square(i, n);
				
				if(validSquare(squares, square)) {
					squares.add(square);
					
					available.set(i, ArrayUtils.remove(available.get(i), l));
					
					i++;
				} else {
					available.set(i, ArrayUtils.remove(available.get(i), l));
				}
			} else {
				Integer[] numbers = new Integer[9];
				
				for(int z = 0; z < 9; z++) {
					numbers[z] = z + 1;
				}
				
				available.set(i, numbers);
				
				i--;
				
				squares.remove(i);
			}
		}
//		System.out.println("******" +squares );
		this.solution = new ArrayList<>(squares);
			
		return squares;
	}
	
	private boolean validSquare(List<Square> squares, Square square) {
		for (Square square1 : squares) {
			if (square1.getRow() == square.getRow() | square1.getCol() == square.getCol() |
					square1.getZone() == square.getZone()) {
				if (square1.getValue() == square.getValue()) {
					return false;
				}
			}
		}
		return true;
	}
	
	private List<List<Square>> createPuzzle(List<Square> squares) {
		
		List<List<Square>> zoneArray = new ArrayList<>();
		
		//第一个3*3的九宫格块
		List<Square> zoneSquares1 = new ArrayList<Square>();
		zoneSquares1 = getZoneSquares(squares, 1);
		zoneSquares1 = removeSquaresFromZone(zoneSquares1, 4);
		
		List<Square> zoneSquares2 = new ArrayList<Square>();
		zoneSquares2 = getZoneSquares(squares, 2);
		zoneSquares2 = removeSquaresFromZone(zoneSquares2, 3);
		
		List<Square> zoneSquares3 = new ArrayList<Square>();
		zoneSquares3 = getZoneSquares(squares, 3);
		zoneSquares3 = removeSquaresFromZone(zoneSquares3, 2);
		
		List<Square> zoneSquares4 = new ArrayList<Square>();
		zoneSquares4 = getZoneSquares(squares, 4);
		zoneSquares4 = removeSquaresFromZone(zoneSquares4, 3);
		
		List<Square> zoneSquares5 = new ArrayList<Square>();
		zoneSquares5 = getZoneSquares(squares, 5);
		zoneSquares5 = removeSquaresFromZone(zoneSquares5, 5);
		
		List<Square> zoneSquares6 = new ArrayList<Square>();
		zoneSquares6 = getZoneSquares(squares, 6);
		zoneSquares6 = removeSquaresFromZone(zoneSquares6, 3);
		
		List<Square> zoneSquares7 = new ArrayList<Square>();
		zoneSquares7 = getZoneSquares(squares, 7);
		zoneSquares7 = removeSquaresFromZone(zoneSquares7, 2);
		
		List<Square> zoneSquares8 = new ArrayList<Square>();
		zoneSquares8 = getZoneSquares(squares, 8);
		zoneSquares8 = removeSquaresFromZone(zoneSquares8, 3);
		
		List<Square> zoneSquares9 = new ArrayList<Square>();
		zoneSquares9 = getZoneSquares(squares, 9);
		zoneSquares9 = removeSquaresFromZone(zoneSquares9, 4);
		
		zoneArray.add(zoneSquares1);
		zoneArray.add(zoneSquares2);
		zoneArray.add(zoneSquares3);
		zoneArray.add(zoneSquares4);
		zoneArray.add(zoneSquares5);
		zoneArray.add(zoneSquares6);
		zoneArray.add(zoneSquares7);
		zoneArray.add(zoneSquares8);
		zoneArray.add(zoneSquares9);
		
		return zoneArray;
	}
	
	/**
	 * 把有解的序列生成去除一些值的序列
	 */
	private List<Square> removeSquaresFromZone(List<Square> squares, int numberToRemove) {
		Integer[] temp = createArrayOfNineRandomNumbers();
		Integer[] indexesToBeRemoved = new Integer[numberToRemove];

		System.arraycopy(temp, 0, indexesToBeRemoved, 0, numberToRemove);
		
		for (int i = 0; i < numberToRemove; i++) {
			int squareIndex = indexesToBeRemoved[i] - 1;
			Square tempSquare = squares.get(squareIndex);
			tempSquare.setValue(0);
			squares.set(squareIndex, tempSquare);
		}
		
		return squares;
	}
	
	
	private List<Square> getZoneSquares(List<Square> squares, int zone) {
		List<Square> tempSquares = new ArrayList<>();
		for (int i = 0; i < 81; i++) {
			if(squares.get(i).getZone() == zone) {
				tempSquares.add(squares.get(i));
			}
		}
//		System.out.println(tempSquares.toString());
		return tempSquares;
	}
	
	private static Integer[] createArrayOfNineRandomNumbers() {
		Integer[] randomArray = new Integer[9];
		Random random = new Random();
		
		for(int i = 0; i < randomArray.length; i++) {
			randomArray[i] = i + 1;
		}
		
		for(int i = 0; i < randomArray.length; i++) {
			int r = random.nextInt(randomArray.length);
			int temp = randomArray[i];
			randomArray[i] = randomArray[r];
			randomArray[r] = temp;
		}
		
		return randomArray;
	}

	public List<Square> getSquares() {
		return squares;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	public List<Square> getSolution() {
		return solution;
	}

	public void setSolution(List<Square> solution) {
		this.solution = solution;
	}

	public List<List<Square>> getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(List<List<Square>> puzzle) {
		this.puzzle = puzzle;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
