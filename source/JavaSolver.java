
class JavaSolver
{
	static int N;
	static int UNASSIGNED  = 0;

	static
	{
		N = 16;
	
	}
	static int convertHexToInt( char c)
	{
		int result ;
		if(c >= '0' && c <= '9')
		{
			result = c - '0' + 1;
		}
		else if(c == 'a' || c == 'A')
		{
			result = 11;
		}
		else if(c == 'b' || c == 'B')
		{
			result = 12;
		}
		else if(c == 'c' || c == 'C')
		{
			result = 13;
		}
		else if(c == 'd' || c == 'D')
		{
			result = 14;
		}
		else if(c == 'e' || c == 'E')
		{
			result = 15;
		}
		else if(c == 'f' || c == 'F')
		{
			result = 16;
		}
		else
		{
			result = 0;
		}

		return result;

	}
	
	//function to convert int to hex
	static char convertIntToHex(int n)
	{
		char c = '0';
		if(n>=1 && n <=10)
		{
			c = (char) (n + '0' - 1);
		}

		if(n == 11)
		{
			c = 'A';
		}
		if(n == 12)
		{
			c = 'B';
		}
		if(n == 13)
		{
			c = 'C';
		}
		if(n == 14)
		{
			c = 'D';
		}
		if(n == 15)
		{
			c = 'E';
		}
		if(n == 16)
		{
			c = 'F';
		}
		return c;

	}

	//functin to display contents of string in sudoku format.
	static void show(char x[])
	{
		int i, j;
		int counter = 0;
		for (i = 0; i < 16; i++) 
		{
			if (((i % 4) == 0)) 
			{
				System.out.println();
			}
			for (j = 0; j < 16; j++)
			{
				System.out.print(x[counter]);
				counter++;
			}
			System.out.println();
		}
	}


	//function to display contents of two dimensional integer array  in sudoku format.
	static void showIntArray(int puzzle[][])
	{
		int i = 0;
		int j = 0;
		System.out.println();
		for(i=0; i<16; ++i)
		{
			for(j=0; j<16; ++j)
			{
				System.out.print(puzzle[i][j] + " ");
			}
			System.out.println();
			if(((i+1) % 4) == 0)
				System.out.println();

		}

	}

static boolean SolveSudoku(int grid[][])
{
		
	int row , col;
	row  = 0;
	col = 0;
    if (!FindUnassignedLocation(grid))
       return true; // success!

    boolean foundUnassignedValue = false;
    for (row = 0; row < N; row++)
    {
        for (col = 0; col < N; col++)
        {
            if (grid[row][col] == UNASSIGNED)
            {
            	foundUnassignedValue = true;
            	break;
            }
            
        }
        if(foundUnassignedValue == true)
        {
        	break;
        }
    }       	
        
    // consider digits 1 to 9
    for (int num = 1; num <= 16; num++)
    {
        // if looks promising
        if (isSafe(grid, (int) row, (int) col, num))
        {
            // make tentative assignment
            grid[row][col] = num;

            // return, if success, yay!
            if (SolveSudoku(grid))
                return true;

            // failure, unmake & try again
            grid[row][col] = UNASSIGNED;
        }
    }
    return false; // this triggers backtracking
}

/* Searches the grid to find an entry that is still unassigned. If
   found, the reference parameters row, col will be set the location
   that is unassigned, and true is returned. If no unassigned entries
   remain, false is returned. */
static boolean FindUnassignedLocation(int grid[][])
{ 
	int row, col;
    for (row = 0; row < N; row++)
        for (col = 0; col < N; col++)
            if (grid[row][col] == UNASSIGNED)
                return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   in the specified row matches the given number. */
static boolean UsedInRow(int grid[][], int row, int num)
{
    for (int col = 0; col < N; col++)
        if (grid[row][col] == num)
            return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   in the specified column matches the given number. */
static boolean UsedInCol(int grid[][], int col, int num)
{
    for (int row = 0; row < N; row++)
        if (grid[row][col] == num)
            return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   within the specified 3x3 box matches the given number. */
static boolean UsedInBox(int grid[][], int boxStartRow, int boxStartCol, int num)
{
    for (int row = 0; row < 4; row++)
        for (int col = 0; col < 4; col++)
            if (grid[row+boxStartRow][col+boxStartCol] == num)
                return true;
    return false;
}

/* Returns a boolean which indicates whether it will be legal to assign
   num to the given row,col location. */
static boolean isSafe(int grid[][], int row, int col, int num)
{
    /* Check if 'num' is not already placed in current row,
       current column and current 3x3 box */
    return !UsedInRow(grid, row, num) &&
           !UsedInCol(grid, col, num) &&
           !UsedInBox(grid, row - row%4 , col - col%4, num);
}

public static String solve_java(String puzzle)
{
	String outputString  = "";
	
	int i;

	int twoDimensionalIntArray [][] = new int[16][16] ;
	int outerIndex = 0;
	int innerIndex = 0;
	//logic to convert string into two dimensinal array
	for(i = 0 ; i < 256 ;)
	{
			for(outerIndex = 0 ; outerIndex < 16 ; outerIndex ++ )
			{
				for(innerIndex = 0 ; innerIndex < 16 ; innerIndex ++)
				{
					twoDimensionalIntArray[outerIndex][innerIndex] = convertHexToInt(puzzle.charAt(i));
					 i++;
				}
			}
	}
	//showIntArray(twoDimensionalIntArray);
	if(SolveSudoku(twoDimensionalIntArray))
    {
			 i = 0;
			for(outerIndex = 0 ; outerIndex < 16 ; outerIndex ++ )
			{
				for(innerIndex = 0 ; innerIndex < 16 ; innerIndex ++)
				{

					 outputString += convertIntToHex(twoDimensionalIntArray[outerIndex][innerIndex]);
					 i++;
				}
			}
    }
    else
    {
    		return "no solution";
    }
    return outputString;
}

public static void main(String args[])
{
	String inputString =  "E21CF80B7A49D563" +
						 "D....7.C5.3....." +
						 "3....9.A...E.B.." +
						 "FB7452..C.6..01." +
						 "8.....B.19D4C3A." +
						 "B..7....3.A..9F8" +
						 "A.5..D......2..." +
						 "24...C.8F.5.1..7" +
						 "43B.8...6....A.." +
						 "0.28.E76B5C...3." +
						 "5.....A0ED9..F86" +
						 "9A..C.....F.0..B" +
						 "C.9...E....F...." +
						 "1DEA.B..8.7....F" +
						 "6..F...7D....C.." +
						 "7842.A1...B6.E.D";
// 	 System.out.println("Hello Wrld.");
 	 

 	
 	String result = solve_java(inputString);
 	System.out.println(result);
 	show(result.toCharArray());
 	
}





}