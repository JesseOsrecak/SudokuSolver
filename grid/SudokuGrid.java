/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

 package grid;

 import java.io.*;


/**
 * Abstract class representing the general interface for a Sudoku grid.
 * Both standard and Killer Sudoku extend from this abstract class.
 */
public abstract class SudokuGrid
{

    protected int size;
    /**
     * Load the specified file and construct an initial grid from the contents
     * of the file.  See assignment specifications and sampleGames to see
     * more details about the format of the input files.
     *
     * @param filename Filename of the file containing the intial configuration
     *                  of the grid we will solve.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void initGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Write out the current values in the grid to file.  This must be implemented
     * in order for your assignment to be evaluated by our testing.
     *
     * @param filename Name of file to write output to.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void outputGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Converts grid to a String representation.  Useful for displaying to
     * output streams.
     *
     * @return String representation of the grid.
     */
    public abstract String toString();


    /**
     * Checks and validates whether the current grid satisfies the constraints
     * of the game in question (either standard or Killer Sudoku).  Override to
     * implement game specific checking.
     *
     * @return True if grid satisfies all constraints of the game in question.
     */
    public abstract boolean validate();

    // /**
    //  * Uses supplied index and gets the character at index of options array and returns this value
    //  * 
    //  * @param index Index of Options array that you wish to retrieve
    //  * @return Char returned at index of options array
    //  */
    // public abstract char getOptionAt(int index);

    // /**
    //  * Inserts given value into grid at location (col, row)
    //  * 
    //  * @param row what row to insert the value in
    //  * @param col what collum to insert the value in
    //  * @param value What value to to insert into the grid
    //  */
    // public abstract void insertAt(int row, int col, char value);

    // /**
    //  * Gets the value in the grid at (col, row)
    //  * @param row what row to go to in the grid
    //  * @param col whar collum to go to in the grid
    //  * @return Returns the char at (col, row)
    //  */
    // public abstract char getValueAt(int row, int col);

    /**
     * Returns the the length/width of the grid
     * @return returns the parameter size of the grid
     */
    public abstract int getSize();	

    // public abstract int getIndexOfOption(char option);
} // end of abstract class SudokuGrid

