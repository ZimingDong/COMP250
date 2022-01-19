package finalproject;

import java.util.*;
import java.io.*;


public class ChessSudoku
{
	/* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
	 * a standard Sudoku puzzle, SIZE is 3 and N is 9. 
	 */
	public int SIZE, N;

	/* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
	 * not yet been revealed are stored as 0. 
	 */
	public int grid[][];

	/* Booleans indicating whether of not one or more of the chess rules should be 
	 * applied to this Sudoku. 
	 */
	public boolean knightRule;
	public boolean kingRule;
	public boolean queenRule;

	
	// Field that stores the same Sudoku puzzle solved in all possible ways
	public HashSet<ChessSudoku> solutions = new HashSet<ChessSudoku>();
	
	/* The solve() method should remove all the unknown characters ('x') in the grid
	 * and replace them with the numbers in the correct range that satisfy the constraints
	 * of the Sudoku puzzle. If true is provided as input, the method should find finds ALL 
	 * possible solutions and store them in the field named solutions. */
	public void solve(boolean allSolutions) {
		if(allSolutions) {
			this.getAll(0, 0);
			for(ChessSudoku answer:solutions) {
				this.grid = answer.grid;
				break;
		}
		}
		else {
			this.getOne(0, 0);
		
		}
		/*
		 * ADD YOUR CODE HERE
		 */
	}
	private boolean size(int row, int column,int number) {
		int div1 = row-row%SIZE;
		int div2 = column-column%SIZE;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if((i==row)&&(j==column)) {
					continue;
				}
				if(grid[i+div1][j+div2]==number) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean knight(int row,int column,int number) {
		int changeR;
		int changeC;
		//1
		changeR = row-2;
		changeC = column+1;
		if(changeR>=0&&changeC<N) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		//2
		changeC = column-1;
		if(changeR>=0&&changeC>=0) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		//3
		changeR = row+2;
		changeC = column+1;
		if(changeR<N&&changeC<N) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		//4
		changeC = column-1;
		if(changeR<N&&changeC>=0) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		
		changeR = row-1;
		changeC = column+2;
		//5
		if(changeR>=0&&changeC<N) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		//6
		changeC = column-2;
		if(changeR>=0&&changeC>=0) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		//7
		changeR = row+1;
		changeC = column+2;
		if(changeR<N&&changeC<N) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		//8
		changeC = column-2;
		if(changeR<N&&changeC>=0) {
			if(grid[changeR][changeC]==number) {
				return false;
			}
		}
		return true;
	}
	private boolean king(int row, int column,int number) {
		int changeR;
		int changeC;
		//1
		changeR = row-1;
		if(changeR>=0) {
			if(grid[changeR][column]==number) {
				return false;
			}
			//2
			changeC = column-1;
			if(changeC>=0) {
				if(grid[changeR][changeC]==number) {
					return false;
				}
			}
			//3
			changeC = column+1;
			if(changeC<N) {
				if(grid[changeR][changeC]==number) {
					return false;
				}
			}
		}
		//4
		changeR = row+1;
		if(changeR<N) {
			if(grid[changeR][column]==number) {
				return false;
			}
			//5
			changeC = column-1;
			if(changeC>=0) {
				if(grid[changeR][changeC]==number) {
					return false;
				}
			}
			//6
			changeC = column+1;
			if(changeC<N) {
				if(grid[changeR][changeC]==number) {
					return false;
				}
			}
		}
		//7
		changeC = column-1;
		if(changeC>=0) {
			if(grid[row][changeC]==number) {
				return false;
			}
		}
		//8
		changeC = column+1;
		if(changeC<N) {
			if(grid[row][changeC]==number) {
				return false;
			}
		}
		return true;
		
		
	}
	
	private boolean queen(int row, int column,int number) {
		int diff = column - row;
		for (int changeR = 0; changeR < N; changeR++) {
			int changeC = changeR + diff;
			if (changeC < 0 || changeR == row)
				continue;
			if (changeC >= N)
				break;
			if (grid[changeR][changeC] == number)
				return false;
		}
		int sum = row + column;
		for (int changeR = 0; changeR < N; changeR++) {
			int changeC = sum - changeR;
			if (changeC < 0)
				break;
			if (changeC >= N || changeR == row)
				continue;
			if (grid[changeR][changeC] == number)
				return false;
		}
	

	return true;
	}
		
		
	
	private boolean check(int row, int column,int number) {
		for(int j=0;j<N;j++) {
			if(j==column) {
				continue;
			}
			if(grid[row][j] == number) {
				return false;
			}
		}
		for(int i=0;i<N;i++) {
			if(i==row) {
				continue;
			}
			if(grid[i][column]==number) {
				return false;
			}
		}
		return true;
	}
	/*private int[] getWant(int row,int col) {
		int[] want = int[N];
		for(int i=1;i<=N;i++) {
			if(check(row,col,i)&&size(row,col,i)) {
				want[i-1]=i;
			}
			else

		}
	}*/
	
	private ChessSudoku copy() {
		ChessSudoku copy = new ChessSudoku(this.SIZE);
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				copy.grid[i][j]=this.grid[i][j];
			}
		}
		return copy;
		
	}
	private boolean getAll(int row, int col) {
		if (row == N - 1 && col == N) {
			ChessSudoku copy= this.copy();
			solutions.add(copy);
			return false;
		}
		if (col == N) {
			row++;
			col=0;
	     }
		
		 if (grid[row][col] != 0) {
			 return getAll(row,col+1);
		 }
		 for(int i=1;i<=N;i++) {
			 if(knightRule) {
				 if(check(row,col,i)&&size(row,col,i)&&knight(row,col,i)) {
					 grid[row][col] = i;
					 if(getAll(row,col+1)) {
						 return true;
					 }
				 }
				 grid[row][col] = 0;
			 }
			 
			 if(queenRule) {
				 if(i == N) {
					 if(check(row,col,i)&&size(row,col,i)&&queen(row,col,i)) {
						 grid[row][col] = i;
						 if(getAll(row,col+1)) {
							 return true;
						 }
					 }
					 grid[row][col] = 0;
				 }
				 else {
					 if(check(row,col,i)&&size(row,col,i)) {
						 grid[row][col] = i;
						 if(getAll(row,col+1)) {
							 return true;
						 }
					 }
					 grid[row][col] = 0;
				 }
				 
			 }
			 
			 if(kingRule) {
				 if(check(row,col,i)&&size(row,col,i)&&king(row,col,i)) {
					 grid[row][col] = i;
					 if(getAll(row,col+1)) {
						 return true;
					 }
				 }
				 grid[row][col] = 0;
			 }
			 
			 if(!knightRule&&!kingRule&&!queenRule) {
				 if(check(row,col,i)&&size(row,col,i)) {
					 grid[row][col] = i;
					 if(getAll(row,col+1)) {
						 return true;
					 }
				 }
				 grid[row][col] = 0;
			 }
			 
		 }
		 return false;
	            
	}
	
	private boolean getOne(int row, int col) {
		if (row == N - 1 && col == N) {
			return true;
		}
		if (col == N) {
			row++;
			col=0;
	     }
		
		 if (grid[row][col] != 0) {
			 return getOne(row,col+1);
		 }
		 for(int i=1;i<=N;i++) {
			 if(knightRule) {
				 if(check(row,col,i)&&size(row,col,i)&&knight(row,col,i)) {
					 grid[row][col] = i;
					 if(getOne(row,col+1)) {
						 return true;
					 }
				 }
				 grid[row][col] = 0;
			 }
			 
			 if(queenRule) {
				 if(i == N) {
					 if(check(row,col,i)&&size(row,col,i)&&queen(row,col,i)) {
						 grid[row][col] = i;
						 if(getOne(row,col+1)) {
							 return true;
						 }
					 }
					 grid[row][col] = 0;
				 }
				 else {
					 if(check(row,col,i)&&size(row,col,i)) {
						 grid[row][col] = i;
						 if(getOne(row,col+1)) {
							 return true;
						 }
					 }
					 grid[row][col] = 0;
				 }
				 
			 }
			 
			 if(kingRule) {
				 if(check(row,col,i)&&size(row,col,i)&&king(row,col,i)) {
					 grid[row][col] = i;
					 if(getOne(row,col+1)) {
						 return true;
					 }
				 }
				 grid[row][col] = 0;
			 }
			 
			 if(!knightRule&&!kingRule&&!queenRule) {
				 if(check(row,col,i)&&size(row,col,i)) {
					 grid[row][col] = i;
					 if(getOne(row,col+1)) {
						 return true;
					 }
				 }
				 grid[row][col] = 0;
			 }
			 
		 }
		 return false;
	            
	}
	/*****************************************************************************/
	/* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE METHODS BELOW THIS LINE. */
	/*****************************************************************************/

	/* Default constructor.  This will initialize all positions to the default 0
	 * value.  Use the read() function to load the Sudoku puzzle from a file or
	 * the standard input. */
	public ChessSudoku( int size ) {
		SIZE = size;
		N = size*size;

		grid = new int[N][N];
		for( int i = 0; i < N; i++ ) 
			for( int j = 0; j < N; j++ ) 
				grid[i][j] = 0;
	}


	/* readInteger is a helper function for the reading of the input file.  It reads
	 * words until it finds one that represents an integer. For convenience, it will also
	 * recognize the string "x" as equivalent to "0". */
	static int readInteger( InputStream in ) throws Exception {
		int result = 0;
		boolean success = false;

		while( !success ) {
			String word = readWord( in );

			try {
				result = Integer.parseInt( word );
				success = true;
			} catch( Exception e ) {
				// Convert 'x' words into 0's
				if( word.compareTo("x") == 0 ) {
					result = 0;
					success = true;
				}
				// Ignore all other words that are not integers
			}
		}

		return result;
	}


	/* readWord is a helper function that reads a word separated by white space. */
	static String readWord( InputStream in ) throws Exception {
		StringBuffer result = new StringBuffer();
		int currentChar = in.read();
		String whiteSpace = " \t\r\n";
		// Ignore any leading white space
		while( whiteSpace.indexOf(currentChar) > -1 ) {
			currentChar = in.read();
		}

		// Read all characters until you reach white space
		while( whiteSpace.indexOf(currentChar) == -1 ) {
			result.append( (char) currentChar );
			currentChar = in.read();
		}
		return result.toString();
	}


	/* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
	 * grid is filled in one row at at time, from left to right.  All non-valid
	 * characters are ignored by this function and may be used in the Sudoku file
	 * to increase its legibility. */
	public void read( InputStream in ) throws Exception {
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				grid[i][j] = readInteger( in );
			}
		}
	}


	/* Helper function for the printing of Sudoku puzzle.  This function will print
	 * out text, preceded by enough ' ' characters to make sure that the printint out
	 * takes at least width characters.  */
	void printFixedWidth( String text, int width ) {
		for( int i = 0; i < width - text.length(); i++ )
			System.out.print( " " );
		System.out.print( text );
	}


	/* The print() function outputs the Sudoku grid to the standard output, using
	 * a bit of extra formatting to make the result clearly readable. */
	public void print() {
		// Compute the number of digits necessary to print out each number in the Sudoku puzzle
		int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

		// Create a dashed line to separate the boxes 
		int lineLength = (digits + 1) * N + 2 * SIZE - 3;
		StringBuffer line = new StringBuffer();
		for( int lineInit = 0; lineInit < lineLength; lineInit++ )
			line.append('-');

		// Go through the grid, printing out its values separated by spaces
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				printFixedWidth( String.valueOf( grid[i][j] ), digits );
				// Print the vertical lines between boxes 
				if( (j < N-1) && ((j+1) % SIZE == 0) )
					System.out.print( " |" );
				System.out.print( " " );
			}
			System.out.println();

			// Print the horizontal line between boxes
			if( (i < N-1) && ((i+1) % SIZE == 0) )
				System.out.println( line.toString() );
		}
	}


	/* The main function reads in a Sudoku puzzle from the standard input, 
	 * unless a file name is provided as a run-time argument, in which case the
	 * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
	 * outputs the completed puzzle to the standard output. */
	public static void main( String args[] ) throws Exception {
		InputStream in = new FileInputStream("queenSudokuEasy3x3.txt");//veryE

		// The first number in all Sudoku files must represent the size of the puzzle.  See
		// the example files for the file format.
		int puzzleSize = readInteger( in );
		if( puzzleSize > 100 || puzzleSize < 1 ) {
			System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
			System.exit(-1);
		}

		ChessSudoku s = new ChessSudoku( puzzleSize );
		
		// You can modify these to add rules to your sudoku
		s.knightRule = false;
		s.kingRule = false;
		s.queenRule = true;
		
		// read the rest of the Sudoku puzzle
		s.read( in );

		System.out.println("Before the solve:");
		s.print();
		System.out.println();

		// Solve the puzzle by finding one solution.
		s.solve(true);

		// Print out the (hopefully completed!) puzzle
		System.out.println("After the solve:");
		s.print();
	}
}

