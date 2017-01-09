//Globals

var UNASSIGNED = 0
var N = 16;


function main() {
	var inputString =  "E21CF80B7A49D563" +
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



var result = solve_JS(inputString);
//console.log(result);
//console.log(show(result))
}



function solve_JS(puzzle)
{
	var outputString  = "";
	
	var i;

	var twoDimensionalIntArray = new Array(16);
	for(var row =0; row < twoDimensionalIntArray.length; row++)
	{
		twoDimensionalIntArray[row] = new Array(16);
	}

	var outerIndex = 0;
	var innerIndex = 0;
	//logic to convert string into two dimensinal array
	//print('within function name')
	//print(puzzle)
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
	//print(twoDimensionalIntArray)
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


function convertHexToInt(c){

	var result ;
	if(c >= '0' && c <= '9')
	{
		result = parseInt(c) + 1;
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
function convertIntToHex(n)
{
    var c = '0';
    if(n>=1 && n <=10)
    {
        c = parseInt(n) -1;
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


function SolveSudoku(grid) {
	
	var row , col;
	row  = 0;
	col = 0;
	if (!FindUnassignedLocation(grid))
       return true; // success!

   var foundUnassignedValue = false;
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
    for (var num = 1; num <= 16; num++)
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


/* Returns a boolean which indicates whether it will be legal to assign
 num to the given row,col location. */
function isSafe(grid, row, col, num) {
    /* Check if 'num' is not already placed in current row,
     current column and current 3x3 box */
    return !UsedInRow(grid, row, num) &&
        !UsedInCol(grid, col, num) &&
        !UsedInBox(grid, row - row%4 , col - col%4, num);
}

/* Returns a boolean which indicates whether any assigned entry
 in the specified row matches the given number. */
function UsedInRow(grid, row, num){
    for (var col = 0; col < N; col++)
    if (grid[row][col] == num)
        return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
 in the specified column matches the given number. */
function UsedInCol(grid, col, num) {
    for (var row = 0; row < N; row++)
    if (grid[row][col] == num)
        return true;
    return false;
}


/* Returns a boolean which indicates whether any assigned entry
 within the specified 3x3 box matches the given number. */
function UsedInBox(grid, boxStartRow, boxStartCol, num) {
    for (var row = 0; row < 4; row++)
    for (var col = 0; col < 4; col++)
    if (grid[row+boxStartRow][col+boxStartCol] == num)
        return true;
    return false;
}

function SolveSudoku(grid){

    var row , col;
    row  = 0;
    col = 0;
    if (!FindUnassignedLocation(grid))
        return true; // success!

    var foundUnassignedValue = false;
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
    for (var num = 1; num <= 16; num++)
    {
        // if looks promising
        if (isSafe(grid, row,  col, num))
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
function FindUnassignedLocation(grid) {
    var row, col;
    for (row = 0; row < N; row++){
        for (col = 0; col < N; col++){
            if (grid[row][col] == UNASSIGNED){
                return true;
            }
        }
    }
    return false;
}


//function to display contents of two dimensional integer array  in sudoku format.
function showIntArray(puzzle)
{
    var i = 0;
    var j = 0;
    var output = "";
    //System.out.println();
    console.log('\n')
    for(i=0; i<16; ++i)
    {
        for(j=0; j<16; ++j)
        {
            //print(puzzle[i][j] + " ");
            output += (puzzle[i][j] + " ");
        }
        output += '\n';
        if(((i+1) % 4) == 0)
            //System.out.println();
            // console.log('\n')
            output += '\n';
    }
   // console.log(output);

}



//functin to display contents of string in sudoku format.
function show(x)
{
    var i, j, output = "";
    var counter = 0;
    for (i = 0; i < 16; i++)
    {
        if (((i % 4) == 0))
        {
            //System.out.println();
            output += '\n';
        }
        for (j = 0; j < 16; j++)
        {
            //System.out.print(x[counter]);
            //console.log(x[counter]);
            output += ' '+ x[counter];
            counter++;
            if(j % 4 == 0){
                output += '\t';
            }
        }
        //console.log('\n');
        output += '\n';
    }
    //console.log(output);
}