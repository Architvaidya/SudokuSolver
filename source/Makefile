
default:
	javac *.java
	javah -jni SudokuSolver
	g++ Sudoku.cpp -I/usr/lib/jvm/default-java/include -o libsudoku.so -shared -rdynamic -fPIC -lc
#	java -Djava.library.path=. SudokuSolver
clean:
	rm -rf *.class *.so *.h *.i
