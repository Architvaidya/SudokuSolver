import java.io.*;
import javax.script.*;

class SudokuSolver
{
	
	public native String MySolveC(String input);

	static 
	{
		System.loadLibrary("sudoku");

	}
	public String solve_Python(String inputString){
		String  solved_python_string = "";
		ProcessBuilder pb = new ProcessBuilder("python2.7", "sudoku_solver.py",inputString);
		pb.redirectErrorStream(true);
		try{
			Process p = pb.start();
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );
            out.flush();
            Reader reader = new InputStreamReader(p.getInputStream());
            int ch;
            String str_out = "";
            while ((ch = reader.read()) != -1) {
            		str_out = str_out + (char) ch ;
            		solved_python_string = str_out;
                  }
            	   //System.out.print(solved_python_string);
                   reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return solved_python_string;
	}
	
	public String solve_JS(String inputString) throws Exception 
	{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
	
			String script1 = null;
			BufferedReader br = new BufferedReader(new FileReader("javascript.js"));
			try
			{
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while(line!=null)
				{
					sb.append(line);
					sb.append("\n");
					line = br.readLine();

				}
				script1 = sb.toString();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			engine.eval(script1);
			

			Invocable inv = (Invocable) engine;

			Object obj = inv.invokeFunction("solve_JS", inputString );  //This one works.
			return obj.toString();	
	}
	
	public static void main(String args[]) throws Exception
	{
		
		
		File fout ;
		FileOutputStream fos ;

		long startTime = System.currentTimeMillis();


		String fileName;
		String fileOutName;
		
		if(args.length > 2)
		{
			
			fileOutName = args[2];
		}
		else
		{
			
			fileOutName = "out.txt";
		}
		
		if(args.length > 3)
		{
			fileName = args[3];
		}
		else
		{
			fileName = "input.txt";
		}

		String languageString = args[0];
		//System.out.println(languageString);
		// logic to parse language name 
		int srcIndex = languageString.indexOf('=');
		String languageName = languageString.substring(srcIndex + 1).trim();
		//System.out.println(languageName);
		int selectedLanguae  = 2;
		
		if(languageName.equalsIgnoreCase("C"))
		{
			selectedLanguae = 1;			
		}
		else if(languageName.equalsIgnoreCase("JAVA"))
		{
			selectedLanguae = 2;			
		}
		else if(languageName.equalsIgnoreCase("python"))
		{
			selectedLanguae = 3;			
		}
		else if(languageName.equalsIgnoreCase("JS"))
		{
			selectedLanguae = 4;			
		}
		else if(languageName.equalsIgnoreCase("PROLOG"))
		{
			selectedLanguae = 5;			
		}
		else
		{
				selectedLanguae = 2;			
		}
		FileInputStream fstream = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String inputString  = br.readLine(); 
		String outputString = "";

		fout = new File(fileOutName);
		fos = new FileOutputStream(fout);
	 	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		
		SudokuSolver objSudokuSolver = new SudokuSolver();
			/*
		 * 1 = C
		 * 2 = Java
		 * 3 = Python
		 * 4 = Javascript
		 * 5 = Prologue
		 */
		
		
		
		if(selectedLanguae == 1)
		{
			//call methods of C
			System.out.println("C");
			outputString = objSudokuSolver.MySolveC(inputString);
		}
		
		if(selectedLanguae == 2)
		{
			//call methods of java
			System.out.println("Java");
			JavaSolver sjObj = new JavaSolver();
			outputString = sjObj.solve_java(inputString);
		}
		
		if(selectedLanguae == 3)
		{
			System.out.println("Python");
			outputString = objSudokuSolver.solve_Python(inputString);
		}
		
		if(selectedLanguae == 4)
		{
			// call javascript methods
			System.out.println("JavaScript");
			outputString = objSudokuSolver.solve_JS(inputString);
		}
		
		if(selectedLanguae == 5)
		{
			// call Prologue methods
		}
	
		long endTime = System.currentTimeMillis();
		
		double timeTaken = (double)(endTime - startTime) / 1000;

		System.out.println("That took " + timeTaken + " milliseconds");

		System.out.println(outputString);
		bw.write(outputString + "\n");
		bw.write("Time Taken = " + timeTaken + " seconds\n");
		br.close();
		bw.close();
	}
}
