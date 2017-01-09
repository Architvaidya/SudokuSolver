// A Backtracking program  in C++ to solve Sudoku problem
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "SudokuSolver.h"
using namespace std;
// UNASSIGNED is used for empty cells in sudoku grid
#define UNASSIGNED 0

// N is used for size of Sudoku grid. Size will be NxN
#define N 16

// This function finds an entry in grid that is still unassigned
bool FindUnassignedLocation(int grid[N][N], int &row, int &col);

// Checks whether it will be legal to assign num to the given row,col
bool isSafe(int grid[N][N], int row, int col, int num);

/* Takes a partially filled-in grid and attempts to assign values to
  all unassigned locations in such a way to meet the requirements
  for Sudoku solution (non-duplication across rows, columns, and boxes) */

int convertHexToInt( char c)
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

char convertIntToHex(int n)
{
	char c = '0';
	if(n>=1 && n <=10)
	{
		c = n + '0' - 1;
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
void show(char *x)
{
	int i, j;
	for (i = 0; i < 16; i++) {
		if (!(i % 4)) putchar('\n');
		for (j = 0; j < 16; j++)
			printf(j % 4 ? "%2c" : "%3c", *x++);
		putchar('\n');
	}
}


//function to display contents of two dimensional integer array  in sudoku format.
void showIntArray(int puzzle[][16])
{
	int i = 0;
	int j = 0;
	printf ("\n");
	for(i=0; i<16; ++i)
	{
		for(j=0; j<16; ++j)
		{
			printf("%3d", puzzle[i][j]);

		}
		printf("\n");
		if(((i+1) % 4) == 0)
				 printf("\n");

	}

}

bool SolveSudoku(int grid[N][N])
{
    int row, col;

    // If there is no unassigned location, we are done
    if (!FindUnassignedLocation(grid, row, col))
       return true; // success!

    // consider digits 1 to 9
    for (int num = 1; num <= 16; num++)
    {
        // if looks promising
        if (isSafe(grid, row, col, num))
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
bool FindUnassignedLocation(int grid[N][N], int &row, int &col)
{
    for (row = 0; row < N; row++)
        for (col = 0; col < N; col++)
            if (grid[row][col] == UNASSIGNED)
                return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   in the specified row matches the given number. */
bool UsedInRow(int grid[N][N], int row, int num)
{
    for (int col = 0; col < N; col++)
        if (grid[row][col] == num)
            return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   in the specified column matches the given number. */
bool UsedInCol(int grid[N][N], int col, int num)
{
    for (int row = 0; row < N; row++)
        if (grid[row][col] == num)
            return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   within the specified 3x3 box matches the given number. */
bool UsedInBox(int grid[N][N], int boxStartRow, int boxStartCol, int num)
{
    for (int row = 0; row < 4; row++)
        for (int col = 0; col < 4; col++)
            if (grid[row+boxStartRow][col+boxStartCol] == num)
                return true;
    return false;
}

/* Returns a boolean which indicates whether it will be legal to assign
   num to the given row,col location. */
bool isSafe(int grid[N][N], int row, int col, int num)
{
    /* Check if 'num' is not already placed in current row,
       current column and current 3x3 box */
    return !UsedInRow(grid, row, num) &&
           !UsedInCol(grid, col, num) &&
           !UsedInBox(grid, row - row%4 , col - col%4, num);
}

/* A utility function to print grid  */
void printGrid(int grid[N][N])
{
    for (int row = 0; row < N; row++)
    {
       for (int col = 0; col < N; col++)
             printf("%2d", grid[row][col]);
        printf("\n");
    }
}


char * solve_c (char * str )
{

			char  * outputString ;
			outputString = (char *)malloc(sizeof(char) * 256);
			int i;

			int twoDimensionalIntArray [16][16];
			int outerIndex = 0;
			int innerIndex = 0;
			//logic to convert string into two dimensinal array
			for(i = 0 ; i < 256 ;)
			{
					for(outerIndex = 0 ; outerIndex < 16 ; outerIndex ++ )
					{
						for(innerIndex = 0 ; innerIndex < 16 ; innerIndex ++)
						{
							twoDimensionalIntArray[outerIndex][innerIndex] = convertHexToInt(str[i]);
							 i++;
						}
					}
			}

			if (SolveSudoku(twoDimensionalIntArray) == true)
		    {
					 i = 0;
					for(outerIndex = 0 ; outerIndex < 16 ; outerIndex ++ )
					{
						for(innerIndex = 0 ; innerIndex < 16 ; innerIndex ++)
						{

							 outputString[i] = convertIntToHex(twoDimensionalIntArray[outerIndex][innerIndex]);
							 i++;
						}
					}
		    }
		    else
		    		return NULL;

		    return outputString;
}

jstring JNICALL Java_SudokuSolver_MySolveC  (JNIEnv *env, jobject o, jstring input)
{
   
   
   char * nativeString;
   
  const char * _nativeString = env->GetStringUTFChars(input, 0);
     nativeString = strdup (_nativeString);
     env->ReleaseStringUTFChars(input, _nativeString);

    char * inputString  =  nativeString;
   
   //printf(" \n input string  is %s " , nativeString);
 // show(inputString); 
  //char * inputString =  (char *)malloc(sizeof(char) * 256);
	/*
   inputString = "E21CF80B7A49D563"
			"D....7.C5.3....."
			"3....9.A...E.B.."
			"FB7452..C.6..01."
			"8.....B.19D4C3A."
			"B..7....3.A..9F8"
			"A.5..D......2..."
			"24...C.8F.5.1..7"
			"43B.8...6....A.."
			"0.28.E76B5C...3."
			"5.....A0ED9..F86"
			"9A..C.....F.0..B"
			"C.9...E....F...."
			"1DEA.B..8.7....F"
			"6..F...7D....C.."m
			"7842.A1...B6.E.D";
   */
   char * result = solve_c(inputString);
   char * result2 = (char *)malloc(sizeof(char) * 257);
   result2[256] = '\0';
   
   
	//	printf("solution is");
		if(result != NULL)
		{
      		 memcpy(result2, result, 256);
			//show(result2);
		}
   
   //printf(" \n result is %s " , result2);
   

  return env->NewStringUTF( result2);
  //  return env->NewStringUTF( "Hello");
  
}

/* Driver Program to test above functions */

/*
int main()
{
    // 0 means unassigned cells




	/*char * inputString = "..0....2.....E.8"
			".9..0.1.F.265.7."
			"..3..5.8.4....F."
			".6.4B.7....C.9.."
			"....4....2..6..."
			".8..A.60.1...4.9"
			"3.6F..9.5....0.."
			".4...2.1..3F..E."
			"4..5.C..3..B.A.0"
			".2........E.8.B."
			"7.F.2..5.0..1..."
			"...6.E3...9....7"
			"A...9....7.0.F.B"
			"..1..F..4..A..0."
			".7..1..A..58.2.."
			"..5...2..3..7.D.";*/

/*
	char * inputString = "E21CF80B7A49D563"
			"D....7.C5.3....."
			"3....9.A...E.B.."
			"FB7452..C.6..01."
			"8.....B.19D4C3A."
			"B..7....3.A..9F8"
			"A.5..D......2..."
			"24...C.8F.5.1..7"
			"43B.8...6....A.."
			"0.28.E76B5C...3."
			"5.....A0ED9..F86"
			"9A..C.....F.0..B"
			"C.9...E....F...."
			"1DEA.B..8.7....F"
			"6..F...7D....C.."
			"7842.A1...B6.E.D";

	/*char * inputString = ".21.F...7...D..."
				"D....7.C5.3....."
				"3....9.A...E.B.."
				"FB7452..C.6..01."
				"......B.19D4C3A."
				"...7....3.A..9F8"
				"..5..D......2..."
				".4...C.8F.5.1..7"
				".3B.8...6....A.."
				"..28.E76B5C...3."
				"5.....A0ED9..F86"
				"9A..C.....F.0..B"
				"C.9...E....F...."
				".DEA.B..8.7....F"
				"...F...7D....C.."
				".842.A1...B6.E.D";*/



/*
	char * result = solve_c(inputString);
		printf("solution is");
		if(result != NULL)
		{
			show(result);
		}
		else
		{
			printf("Solution does not exist");
		}

}
*/